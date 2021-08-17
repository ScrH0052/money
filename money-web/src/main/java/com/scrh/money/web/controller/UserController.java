package com.scrh.money.web.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.scrh.money.common.utils.Result;
import com.scrh.money.exterface.domain.BidInfo;
import com.scrh.money.exterface.domain.FinanceAccount;
import com.scrh.money.exterface.domain.User;
import com.scrh.money.exterface.service.BidInfoService;
import com.scrh.money.exterface.service.FinanceAccountService;
import com.scrh.money.exterface.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathConstants;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关控制层
 *
 * @author ScrH0052
 * @date 2021/8/4
 */
@Controller
public class UserController {

    @DubboReference(interfaceClass = UserService.class, version = "0.0.1", timeout = 20000)
    private UserService userService;

    @DubboReference(interfaceClass = FinanceAccountService.class, version = "0.0.1", timeout = 20000)
    private FinanceAccountService financeAccountService;

    @DubboReference(interfaceClass = BidInfoService.class, version = "0.0.1", timeout = 20000)
    private BidInfoService bidInfoService;

    /**
     * 校验手机号是否已经被注册
     *
     * @param phone 手机号码
     * @return 校验结果
     */
    @GetMapping("/user/register/checkPhoneExist")
    @ResponseBody
    public Map<String, Object> checkPhoneExist(Long phone) {
        if (!userService.queryUserExistByPhone(phone)) {
            return Result.getSuccess();
        }
        return Result.getError("该手机号码已被注册！");
    }

    /**
     * 生成一个验证码并在redis缓存中存储5分钟
     *
     * @param phone 手机号码
     * @return 获取验证码结果
     */
    @GetMapping("/user/register/getMessCode")
    @ResponseBody
    public Map<String, Object> getMessCode(String phone) {

        //生成一个6位随机数的字符串作为验证码,并存储
        userService.saveMessCode(phone, Result.messCode(6));
        //获取验证码
        String messCode = userService.queryMessCode(phone);

        String result;
        //模拟报文
        result = " {\n" +
                "    \"code\": \"10000\",\n" +
                "    \"charge\": false,\n" +
                "    \"remain\": 0,\n" +
                "    \"msg\": \"查询成功\",\n" +
                "    \"result\": \"<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\" ?><returnsms>\\n <returnstatus>Success</returnstatus>\\n <message>ok</message>\\n <remainpoint>-7544679</remainpoint>\\n <taskID>189395259</taskID>\\n <successCounts>1</successCounts></returnsms>\",\n" +
                "    \"requestId\": \"b806afffdb7147c693f52463faaafebe\"\n" +
                "}";
        try {
//            Map<String,Object> parasMap = new HashMap<>(3);
//            parasMap.put("mobile", phone);
//            parasMap.put("content", "【凯信通】您的验证码是：" + messCode);
//            parasMap.put("appkey", "230d797eb95abbc2e4f0268a4dd755f8");
            ////免费测试的短信次数有限，开发时采用前端接收alert提示
//            result = HttpUtil.get("https://way.jd.com/kaixintong/kaixintong",parasMap);

            System.out.println("RESULT =" + result);
            JSONObject jsonObject = JSONUtil.parseObj(result);
            String code = jsonObject.getStr("code");
            String successCode = "10000";
            if (!StringUtils.equals(successCode, code)) {
                return Result.getError("通信异常。");
            }

            //返回json中的result属性
            String xml = jsonObject.getStr("result");
            Document document = XmlUtil.parseXml(xml);
            String returnStatus = (String) XmlUtil.getByXPath("//returnsms/returnstatus", document, XPathConstants.STRING);
            System.out.println(returnStatus);
            successCode = "Success";
            if (!StringUtils.equals(successCode, returnStatus)) {
                return Result.getError("短信发送失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.getError("发生异常！请重新发送短信");
        }

        return Result.getSuccess(messCode);
    }

    /**
     * 验证用户输入的验证码是否正确
     *
     * @param phone    手机号码
     * @param messCode 用户输入的验证码
     * @return 校验结果
     */
    @GetMapping("/user/register/checkMessCode")
    @ResponseBody
    public Map<String, Object> checkMessCode(String phone, String messCode) {
        if (!StringUtils.equals(messCode, userService.queryMessCode(phone))) {
            return Result.getError("验证码错误！");
        }
        return Result.getSuccess();
    }

    /**
     * 接收提交的注册手机号和密码，存于缓存中等待实名认证完成
     *
     * @param phone 手机号
     * @param pwd   密码
     * @return 存储结果
     */
    @GetMapping("/user/register/submit")
    @ResponseBody
    public Map<String, Object> submit(String phone, String pwd) {
        System.out.println("========submit========");
        Map<String, Object> someInfo = new HashMap<>(2);
        someInfo.put("ppp", phone);
        someInfo.put("pMwMd", SecureUtil.md5(pwd + phone));
        try {
            userService.cacheSomeInfo(someInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getError("服务器发生异常，提交信息失败！");
        }

        return Result.getSuccess();

    }

    /**
     * 获取图片验证码，以流的形式输出到前端页面
     *
     * @param response 响应对象
     * @param session  session对象
     */
    @RequestMapping("/user/getYzm")
    public void getYzm(HttpServletResponse response,
                       HttpSession session,
                       Integer width,
                       Integer height,
                       Integer codeCount) {
        //生产验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, 25);
        //将验证码存储到session中
        String code = lineCaptcha.getCode();
        session.setAttribute("YZM", code);
        //设置验证码有效时间5分钟
        session.setMaxInactiveInterval(300);
        try {
            lineCaptcha.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查是否与注册时提交的手机号码相同
     *
     * @param phone 实名认证中提交的手机号码
     * @return 检查结果
     */
    @GetMapping("/user/realName/checkPhone")
    @ResponseBody
    public Map<String, Object> checkPhone(Long phone) {
        if (!userService.checkPhone(phone)) {
            return Result.getSuccess();
        }
        return Result.getError("手机号码与上一步不一致！");
    }

    @GetMapping("/user/realName/checkCaptcha")
    @ResponseBody
    public Map<String, Object> checkCaptcha(String inputCaptcha, HttpSession session) {
        //从session中读取正确的验证码
        String currentCaptcha = (String) session.getAttribute("YZM");
        System.out.println("YZM=============" + currentCaptcha);
        //判断验证码是否已经失效
        if (StringUtils.isEmpty(currentCaptcha)) {
            return Result.getError("验证码已失效，请点击进行更新！");
        }

        if (!StringUtils.equals(currentCaptcha, inputCaptcha)) {
            return Result.getError("验证码错误！");
        }
        return Result.getSuccess();
    }

    /**
     * 通过平台api校验实名认证信息是否正确,正确则将注册信息存入数据库中，完成注册，并返回注册结果
     *
     * @param name   用户提供的姓名
     * @param idCode 用户提供的身份证号码
     * @return 校验结果
     */
    @GetMapping("/user/realName/checkRealNameAndIdCard")
    @ResponseBody
    public Map<String, Object> checkRealNameAndIdCard(String name, String idCode, String phone) {
        String result;
        //模拟报文
        result = "{\n" +
                "    \"code\": \"10000\",\n" +
                "    \"charge\": false,\n" +
                "    \"remain\": 0,\n" +
                "    \"msg\": \"查询成功\",\n" +
                "    \"result\": {\n" +
                "        \"error_code\": 0,\n" +
                "        \"reason\": \"成功\",\n" +
                "        \"result\": {\n" +
                "            \"realname\": \"张**\",\n" +
                "            \"idcard\": \"333333************\",\n" +
                "            \"isok\": true,\n" +
                "            \"IdCardInfor\": {\n" +
                "                \"city\": \"上海市\",\n" +
                "                \"district\": \"徐汇区\",\n" +
                "                \"area\": \"上海市徐汇区\",\n" +
                "                \"sex\": \"男\",\n" +
                "                \"birthday\": \"1998-4-20\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"requestId\": \"70b64e7c54fc41999c1f3f0a2005b3de\"\n" +
                "}";
        try {
//            Map<String,Object> parasMap = new HashMap<>(3);
//            parasMap.put("Name", name);
//            parasMap.put("cardNo", idCode);
//            parasMap.put("appkey", "230d797eb95abbc2e4f0268a4dd755f8");
//            //免费测试的短信次数有限，开发时采用前端接收alert提示
//            result = HttpUtil.get("https://way.jd.com/hl/idcheck",parasMap);

            System.out.println("RESULT of RealName =" + result);
            JSONObject jsonObject = JSONUtil.parseObj(result);
            String code = jsonObject.getStr("code");
            String successCode = "10000";
            if (!StringUtils.equals(successCode, code)) {
                return Result.getError("请求发生异常。");
            }

            //返回json中的result属性
            Boolean isOk = jsonObject.getJSONObject("result")
                    .getJSONObject("result")
                    .get("isok", Boolean.class);

            if (!isOk) {
                return Result.getError("身份证号码与姓名不匹配！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.getError("发生异常！请重新发送请求");
        }
        User user = new User();
        user.setPhone(phone);
        user.setName(name);
        user.setIdCard(idCode);
        user.setAddTime(new Date());
        if (!userService.registComplete(user)) {
            return Result.getError("服务器异常，请重试！");
        }

        return Result.getSuccess();
    }

    @GetMapping("/user/login")
    @ResponseBody
    public Map<String, Object> login(HttpSession session, String loginAct, String loginPwd, String captcha) {
        String currentCaptcha = (String) session.getAttribute("YZM");
        //开发测试阶段注释掉不予校验
//        if (ObjectUtils.isEmpty(currentCaptcha)) {
//            //验证码失效，返回状态码“03”
//            return Result.getError("02");
//        } else if (!StringUtils.equals(captcha, currentCaptcha)) {
//            //验证码错误，返回状态码“04”
//            return Result.getError("03");
//        }

        //校验账号密码
        User user = userService.queryUserByPhoneAndPwd(loginAct, SecureUtil.md5(loginPwd + loginAct));

        if (ObjectUtils.isEmpty(user)) {
            return Result.getError("01");
        }
        //设置session时间有效期为一天
        session.setMaxInactiveInterval(24 * 60 * 60);
        session.setAttribute("userAccount", user);
        updateFinanceAccount(session, user.getId());
        return Result.getSuccess();
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userAccount");
        session.removeAttribute("availableMoney");
        return "redirect:/";
    }

    @GetMapping("/user/myCenter")
    public String myCenter(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userAccount");
        if (user == null) {
            return "redirect:/index";
        }
        Integer uid = user.getId();
        Integer pageStart = 0;
        Integer pageSize = 5;
        //节约时间，仅完成bidInfo部分作为练习
        List<BidInfo> bidInfoList = bidInfoService.queryUserBidInfo(uid, pageStart, pageSize);
        model.addAttribute("bidInfoList", bidInfoList);
        updateFinanceAccount(session, uid);
        return "myCenter";
    }

    private void updateFinanceAccount(HttpSession session, Integer uid) {
        FinanceAccount financeAccount = financeAccountService.queryAccountMoney(uid);
        session.setAttribute("availableMoney", financeAccount.getAvailableMoney());
    }

}
