package com.scrh.money.web.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000118603029";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQCzO/Re5LbOE5sxtCnDDNLNnXKB7a9Xe3BZ/aTjDl7ecH0LpdkizOmtxQB37T+mJMe05Q9GfwbuwtGPSftFsNDgZUtpaC2lPB448WHSuGLv54om82yiHFFYoGRMo8PeZTDUwGQnNYKEVAhydtZvI+RwpnwpdE5Ywx59yYNGy5987pfwFBbYFmmce1RaSt+wJ3MhACbmJN7Z9Yo6xQ+W4j498JUaYUOIczh06Y2U4rZviPQZyrP3cJNAD8g8qW+QBA6mB9+B+XE8zy6PXg98n/qxTQ3vNYdEviXp69mrhDIWp3juC0RaGEYFDRHwx6baufmtSEHmBBu6Ut3mo69LWf15AgMBAAECggEBAJbALK/Lyv3Ve8j4cc++b2fFr9QcaEg6aKsoLZFUOvL4xhQP8GvW0gz4D4bPI6PId7hsmMaAfQJBFiVg4lkheO3kBOOpXgsGX50hWVf1AjIqGwbb41kOXFUgwnu7mjjp/4JSN2rLFPXcACbWUVG1bKRbQz9StbrukizWWvYmugRG9XT9ao71yduBiC7k/q87WE8EG5B44ZTtITALX8nJPDZWXW/r0SD6A7v1yhMowATae+SqmJ4RrjidzxV4Alu9oUYKT3nYFwaA8C+alDWD6/2EONwJWlV6amZjNdiVNgsY4k+4a7fHBFZVerYil03Pg7ko/L4Y40gBcnDShFgKNykCgYEA+GeExk0BTdN5nlNqJEGdYJV/3hZCSHo+ZTpCKdIYB9muDjXT/TJcnqs35enC2OCafjlJwMM/2lb8Mx7R8YfWmc8L9/LQNUVcxgYAAhD+05L0rTgFbXykw3m0F9JHKVe+hs3wf2HIH0KA65JfHN1D7bNnigfx+cJszDIT0wmdifcCgYEAuLb6yNY0xOnJ4R+bja9YUO8b4elXwItlVx4CSjfjWNz6ywEBhksJ+XDaD0lSeRPKqx9drPh0ZZnDD94GWCB1LWmwNKJmSI8cCBBYGXB1ewrpqEN677NwJhsb9KhWwqdENTtfRk1MoWrdcBfiVQ/6gxao1tIqKjZepBTSya1eWA8CgYEA0XCu0Wfx9oWc4HJiZwlvwRSsCDvqTSPM4hrZ2FsM6emOyOp3v98rmpiwe8i6AZR2joCM/9tgRh7ba9rLlwM6XNrms3tmM9Q04b4DWbqssQUSCc7XPLZAUZSW1RL17xTNTunqoex3CGxROM5kJXkmIhAG96m1Sym0BAMhFengi4cCgYEAgDOPj8EMUvcohLVZU1c15sZuBbfcjeix52sOj9CGXHDTJ/6Evl5qh9QJpL5xHVPdQDO+4oR21OWJewPQR24PpftKrt1gPFAHVplxbAafo0yJq51aP++8kNYT6xTDUsqhDokssTCJVSdElb4d+ofiiQU7Hg4z8aRWv28sdYl8h4UCgYEArWFnZNdwNUf6GDY/xF5czbriYR5H1jp30W0K7IuniSpem+Ir5Qpk9Zt3I6ntZgAlOBMTJrQlFvX0HJpZsKHhBZ5l0DITKBq+pNmbNPPves2bCsNQPZ8h27URZ09v1fZolH/fQul/6H6x/5nlCdp7N4ekPVomeRHlDK7WgaOfzDs=";
    
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwhH7HuzyqG4mz0Nq75raCFp5H2XURWSZ2/vDe6qjMDxPwQJVpPHhIVAt1lXhjboe0+4X/joa/PAdOs4az9+9ZEQXvG7EV11iDghO7L/ViwgoEvskgVbzj2FC/xQz2UTIpO3Vm3KrPX2oMPQxHE31B24OkQMnwxHbGvgj0qDp5UU+LsRXZYIeRV0rb+8hDBKEzR9w5ulhFoR8wqbAD0bs0JhzRI+clsTFlxi4btlgBpbW12Z2RYSFAlpZwodvIr3jc+/tOJlgI9oYIM26Y4W+8dMnyCq1BZoEWD5mHrE2cH8NeBFiss7trV/9Za6f0+HXtrCiUzfvtZINlm5iH5r6nwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost/loan/page/payBack";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost/loan/page/payBack";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 日志输出地址
    public static String log_path = "log\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".log");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

