//登录后返回页面
function reBack() {
    let referrer;
    referrer = document.referrer;
    if (!referrer) {
        try {
            if (window.opener) {
                // IE下如果跨域则抛出权限异常，Safari和Chrome下window.opener.location没有任何属性
                referrer = window.opener.location.href;
            }
        } catch (e) {
        }
        console.log(referrer)
    }
    window.location.href = referrer
}


$(function () {
    let imgCaptcha = $("#img-captcha")
    getImgCaptcha(imgCaptcha)

    imgCaptcha.click(function () {
        getImgCaptcha(imgCaptcha)
    });

    let loginAct = $("#phone")
    let loginPwd = $("#loginPassword")
    let captcha = $("#captcha")
    //按键盘Enter键即可登录
    $(document).keyup(function (event) {
        if (event.keyCode === 13) {
            login(loginAct, loginPwd, captcha);
        }
    });

    $('#btnLogin').click(function () {
        login(loginAct, loginPwd, captcha);
    });
});

//获取图片验证码
function getImgCaptcha(element) {
    element.html(`<img src="/user/getYzm?width=78&height=36&codeCount=4" style="cursor:pointer;border:0; display:inline;vertical-align:middle;"/>`)
}

//登录
function login(loginAct, loginPwd, captcha) {
    console.log("start")
    $.get("/user/login",
        {
            loginAct: $.trim(loginAct.val()),
            loginPwd: $.trim(loginPwd.val()),
            captcha: $.trim(captcha.val()),
        },
        function (data) {

            console.log(data)
            if (!data.flag) {
                console.log("00")
                if ("01" === data.message) {
                    console.log("01")
                    showError("loginPassword", "帐号或密码错误！")
                } else if ("02" === data.message) {
                    console.log("02")
                    showError("captcha", "验证码已失效！")
                } else if ("03" === data.message) {
                    console.log("03")
                    showError("captcha", "验证码错误！")
                }
                return 0
            }
            reBack()
        }
    );
}