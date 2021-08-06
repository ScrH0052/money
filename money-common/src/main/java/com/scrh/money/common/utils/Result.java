package com.scrh.money.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果实现工具
 *
 * @author ScrH0052
 * @date 2021/8/5
 */
public class Result {

    /**
     * 请求成功时返回一个 Map 类型结果
     *
     * @return 成功结果
     */
    public static Map<String, Object> getSuccess() {
        Map<String, Object> result = new HashMap<>(10);
        result.put("flag", true);
        result.put("message", "");
        return result;
    }

    /**
     * 请求成功时返回一个 Map 类型结果
     *
     * @param msg 成功信息
     * @return 成功结果
     */
    public static Map<String, Object> getSuccess(String msg) {
        Map<String, Object> result = new HashMap<>(10);
        result.put("flag", true);
        result.put("message", msg);
        return result;
    }

    /**
     * 请求失败时返回一个 Map 类型结果
     *
     * @return 错误结果
     */
    public static Map<String, Object> getError() {
        Map<String, Object> result = new HashMap<>(10);
        result.put("flag", false);
        result.put("message", "");
        return result;
    }

    /**
     * 请求失败时返回一个 Map 类型结果
     *
     * @param msg 失败信息
     * @return 失败结果
     */
    public static Map<String, Object> getError(String msg) {
        Map<String, Object> result = new HashMap<>(10);
        result.put("flag", false);
        result.put("message", msg);
        return result;
    }

    /**
     * 得到一串长度为 len 的随机数，主要用于获取验证码
     * @param len 限定数字串长度
     * @return 长度为 len 的随机数字字符串
     */
    public static String messCode(int len) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(Math.round(Math.random() * 9));
        }
        return stringBuilder.toString();
    }


}
