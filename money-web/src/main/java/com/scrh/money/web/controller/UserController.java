package com.scrh.money.web.controller;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.scrh.money.common.utils.Result;
import com.scrh.money.exterface.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;

import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathConstants;
import java.util.HashMap;
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
        result =" {\n" +
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
     * @param phone 手机号码
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

    @GetMapping("/user/register/submit")
    @ResponseBody
    public Map<String, Object> submit(String phone, String pwd, HttpSession session) {
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


}
