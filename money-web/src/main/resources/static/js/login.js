let referrer;//登录后返回页面
referrer = document.referrer;
if (!referrer) {
	try {
		if (window.opener) {                
			// IE下如果跨域则抛出权限异常，Safari和Chrome下window.opener.location没有任何属性              
			referrer = window.opener.location.href;
		}  
	} catch (e) {
	}
}

//按键盘Enter键即可登录
$(document).keyup(function(event){
	if(event.keyCode == 13){
		login();
	}
});

$(function () {
	let imgCaptcha = $("#img-captcha")
	getImgCaptcha(imgCaptcha)

	imgCaptcha.click(function () {
		getImgCaptcha(imgCaptcha)
	});

});

function getImgCaptcha(element) {
	element.html(`<img src="/user/getYzm?width=78&height=36&codeCount=4" style="cursor:pointer;border:0; display:inline;vertical-align:middle;"/>`)
}