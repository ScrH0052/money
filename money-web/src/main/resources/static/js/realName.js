//手机号长度
const PHONE_LEN = 11

$(function() {
	//初始化验证码
	getYzm($('#Yzm'))
	//同意实名认证协议
	$("#agree").click(function(){
		let btnRealName = $('#btnRealName')
		let isChecked = document.getElementById("agree").checked;
		if (isChecked) {
			btnRealName.attr("disabled", false);
			btnRealName.removeClass("fail");
		} else {
			btnRealName.attr("disabled","disabled");
			btnRealName.addClass("fail");
		}
	});
	//点击更新验证码
	$("#Yzm").click(function (){
		getYzm($(this))
	});

	/**
	 * 手机号输入时进行校验
	 */
	$('#phone').keyup(function (){
		checkPhone($(this));
	})

	//校验姓名格式
	$('#realName').keyup(function () {
		checkRealName($(this));
	});

	//校验身份证号码格式
	$("#idCard").keyup(function () {
		checkIdCard($(this));
	});

	//校验验证码是否输入正确
	$("#captcha").keyup(function () {
		checkCaptcha($(this));
	});

	//点击认证按钮触发验证
	$('#btnRealName').click(function () {
		realNameSubmit($("#phone"),$('#realName'),$('#idCard'),$("#captcha"))
	});
});
//打开注册协议弹层
function alertBox(maskId,bosId){
	$("#"+maskId).show();
	$("#"+bosId).show();
}
//关闭注册协议弹层
function closeBox(maskId,bosId){
	$("#"+maskId).hide();
	$("#"+bosId).hide();
}

//错误提示
function showError(id,msg) {
	let ok = $("#"+id+"Ok")
	let err = $("#"+id+"Err")
	ok.hide();
	err.html("<i></i><p>"+msg+"</p>");
	err.show();
	$("#"+id).addClass("input-red");
}
//错误隐藏
function hideError(id) {
	let err = $("#"+id+"Err")
	err.hide();
	err.html("");
	$("#"+id).removeClass("input-red");
}
//显示成功
function showSuccess(id) {
	let ok = $("#"+id+"Ok");
	let err = $("#"+id+"Err")
	err.hide();
	err.html("");
	ok.show();
	$("#"+id).removeClass("input-red");
}

/**
 * 获取/更新验证码
 * @param element 包含验证码图片的标签元素
 */
function getYzm(element) {
	let img = "/user/getYzm?width=100&height=38&codeCount=4"
	element.empty().html(`<img src=`+img+` alt="图片验证码"/>`)
}

/**
 * 手机号校验标志
 */
let phoneFlag
/**
 * 判断手机号码格式，并校验是否和上一步提交的手机号码相同
 * @param element 通过Id获取的标签元素
 * @returns {boolean} 校验结果
 */
function checkPhone(element) {
	phoneFlag = false
	//获取输入框内容
	let phone = $.trim(element.val())
	let id = 'phone'
	//判断是否为空
	if ($.isEmptyObject(phone)) {
		showError(id,"手机号码不能为空！")
		return phoneFlag
	}

	//判断格式是否正确
	if (!/^1[1-9]\d{9}$/.test(phone) && !(phone.length === PHONE_LEN)) {
		showError(id,"请输入正确的手机号码")
		return phoneFlag
	}

	//判断手机号是否匹配
	$.get("/user/realName/checkPhone",
		{phone:phone},
		function (data){
			if (!data.flag){
				showError(id,data.message)
				return phoneFlag
			}
			phoneFlag = true
			showSuccess(id)
		})
}

/**
 * 声明姓名校验位
 */
let realNameFlag
/**
 * 校验姓名格式是否正确
 * @param element 姓名输入框
 * @returns {boolean} 判断结果
 */
function checkRealName(element) {
	realNameFlag =false
	let id = "realName"
	let realName = $.trim(element.val())
	//校验是否为空
	if ($.isEmptyObject(realName)) {
		showError(id,"请输入真实姓名！")
		return false
	}

	//校验是否为中文汉字
	if (!/^[\u4e00-\u9fa5]*$/.test(realName)) {
		showError(id,"真实姓名仅包含中文汉字！")
		return false
	}

	//标志位置为true
	realNameFlag =true
	showSuccess(id)
	return true
}

/**
 * 声明身份证号码格式校验位
 */
let idCardFormatFlag

function checkIdCard(element) {
	//初始化校验位
	idCardFormatFlag =false

	let id = "idCard"
	let idCard = $.trim(element.val())

	//校验是否为空
	if ($.isEmptyObject(idCard)) {
		showError(id,"身份证号码不能为空！")
		return false
	}

	//校验是否包含非法字符
	if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idCard)) {
		showError(id,"请输入正确格式的身份证号码！")
		return false
	}

	idCardFormatFlag = true
	showSuccess(id)
	return true
}

/**
 * 验证码校验标志位
 */
let captchaFlag

/**
 * 校验验证码格式
 * @param element 验证码输入框jQuery选择器
 * @returns {boolean} 校验结果
 */
function checkCaptcha(element) {
	//初始化标志位
	captchaFlag =false

	//id和值的设置
	let id = "captcha"
	let inputCaptcha = $.trim(element.val())

	//判空
	if ($.isEmptyObject(inputCaptcha)) {
		showError(id,"请输入验证码！")
		return false
	}

	//长度
	if (inputCaptcha.length < 4) {
		showError(id,"请输入正确格式的验证码！");
		return false;
	}

	//格式
	if (!/^[0-9a-zA-Z]+$/.test(inputCaptcha)) {
		showError(id,"请输入正确格式的验证码！");
		return false;
	}

	captchaFlag = true
	showSuccess(id)
	return true

}

/**
 * 提交实名认证信息，先检验校验位是否都为true，再检查验证码是否正确，最后核实实名信息是否匹配
 * @param e_phone 手机号输入框
 * @param e_realName 姓名输入框
 * @param e_idCard 身份证号码输入框
 * @param e_captcha 验证码输入框
 * @returns {number} 返回提交结果状态码
 */
function realNameSubmit(e_phone, e_realName, e_idCard, e_captcha) {
	//1. 校验手机号、真实姓名、身份证号码、验证码是否通过格式校验
	if (!phoneFlag) {
		checkPhone(e_phone);
		return 0
	}
	if (!realNameFlag) {
		checkRealName(e_realName)
		return 0
	}
	if (!idCardFormatFlag) {
		checkIdCard(e_idCard)
		return 0
	}
	if (!captchaFlag) {
		checkCaptcha(e_captcha)
		return 0
	}

	//2. 验证码核对
	$.get("/user/realName/checkCaptcha",
		{
			inputCaptcha: $.trim(e_captcha.val()),
		},
		function (data) {
			if (!data.flag) {
				showError("captcha",data.message)
				return 0
			}
			//3. 校验姓名与身份证号是否匹配
			$.get("/user/realName/checkRealNameAndIdCard",
				{
					name: $.trim(e_realName.val()),
					idCode: $.trim(e_idCard.val()),
					phone: $.trim(e_phone.val()),
				},
				function (data) {
					console.log(data)
					if (!data.flag) {
						showError(data.message)
						return 0
					}

					window.location.href="/loan/page/complete"
				}
			);
		}
	);



}