package com.scrh.money.web.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.scrh.money.common.utils.PageModel;
import com.scrh.money.common.utils.Result;
import com.scrh.money.exterface.domain.RechargeRecord;
import com.scrh.money.exterface.domain.User;
import com.scrh.money.exterface.service.RechargeService;
import com.scrh.money.web.config.AlipayConfig;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 充值
 *
 * @author ScrH0052
 * @date 2021/8/11
 */
@Controller
public class RechargeController {

    @DubboReference(interfaceClass = RechargeService.class, version = "0.0.1", timeout = 20000)
    private RechargeService rechargeService;

    @GetMapping("/loan/page/myRecharge/{targetPage}")
    public String myRecharge(HttpSession session, Model model, @PathVariable(name = "targetPage") Integer targetPage) {
        User user = (User) session.getAttribute("userAccount");
        Integer uid = user.getId();
        //获取页码模型
        if (targetPage == null) {
            targetPage = 1;
        }
        PageModel pageModel = rechargeService.queryPageModelByUidAndTargetPage(uid, targetPage);
        model.addAttribute("pageModel", pageModel);

        //获取充值记录表
        List<RechargeRecord> records = rechargeService.queryRecordsByUidAndPageModel(uid, pageModel);
        model.addAttribute("list", records);

        return "myRecharge";
    }

    /**
     * 处理支付宝支付
     *
     * @param rechargeMoney 充值金额
     * @param session       请求的session
     * @param model         模型
     * @return 视图
     */
    @PostMapping("/loan/page/alipay")
    public String alipay(Double rechargeMoney, String rootPath, HttpSession session, Model model) {
        //校验是否登录，未登录则跳转登录页面
        User user = (User) session.getAttribute("userAccount");
        if (ObjectUtils.isEmpty(user)) {
            return "redirect:/loan/page/login";
        }
        //生成充值记录
        Date date = new Date();
        RechargeRecord record = new RechargeRecord();
        record.setUid(user.getId());
        record.setRechargeNo(new SimpleDateFormat("yyyMMddHHmmss").format(date) +
                user.getId() +
                Result.messCode(6));
        record.setRechargeMoney(rechargeMoney);
        record.setRechargeStatus("0");
        record.setRechargeTime(date);
        record.setRechargeDesc("支付宝支付");

        //生成充值订单
        if (rechargeService.createRechargeRecord(record) == 0) {
            model.addAttribute("trade_msg", "订单生成失败");
            return "toRechargeBack";
        }

        Map<String, Object> parasMap = new HashMap<>();
        parasMap.put("out_trade_no", record.getRechargeNo());
        parasMap.put("total_amount", record.getRechargeMoney());
        parasMap.put("subject", record.getRechargeDesc());

        String url = rootPath + "/pay/aliPay";
        String result = HttpUtil.post(url, parasMap);
        model.addAttribute("result", result);
        return "alipay";
    }

    @RequestMapping("/loan/page/payBack")
    public String payBack(HttpServletRequest request, Model model) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, String> returnMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            returnMap.put(s, request.getParameter(s));
        }

        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(returnMap, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        model.addAttribute("tradeNo_alipay", returnMap.get("trade_no"));
        model.addAttribute("tradeNo", returnMap.get("out_trade_no"));
        model.addAttribute("amountMoney", returnMap.get("total_amount"));

        if (signVerified) {
            Map<String, Object> parasMap = new HashMap<>(1);
            parasMap.put("out_trade_no", returnMap.get("out_trade_no"));
            String result = "";
            try {
                result = HttpUtil.get("http://localhost/pay/queryPay", parasMap);
                System.out.println("result ======> " + result);
                JSONObject jsonObject = JSONUtil.parseObj(result);
                String trade_status = jsonObject.getJSONObject("alipay_trade_query_response").getStr("trade_status");
                System.out.println("trade_status ==== >" + trade_status);
                if (StringUtils.equals("WAIT_BUYER_PAY", trade_status)) {
                    model.addAttribute("payStatus", "未支付");
                } else if (StringUtils.equals("TRADE_CLOSED", trade_status)) {
                    if (rechargeService.updateRechargeByTradeNoAndStatusCode(returnMap.get("out_trade_no"), 2) == 0) {
                        model.addAttribute("payStatus", "订单状态更新发生异常！");
                        return "payBack";
                    }
                    model.addAttribute("payStatus", "交易失败，订单已关闭！");
                } else if (StringUtils.equals("TRADE_SUCCESS", trade_status)) {
                    if (rechargeService.updateRechargeByTradeNoAndStatusCode(returnMap.get("out_trade_no"), 1) == 0) {
                        model.addAttribute("payStatus", "订单状态更新发生异常！");
                        return "payBack";
                    }
                    model.addAttribute("payStatus", "交易已完成，请返回充值页面点击确认！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("payStatus", "查询请求发生异常");
            }

        }

        return "payBack";
    }
}
