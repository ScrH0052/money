const MIN_LEN_OF_PWD = 6
const MAX_LEN_OF_PWD = 20
const PHONE_LEN = 11
const MESSAGE_CODE_LEN = 6
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

$(function() {
	//注册协议确认
	$("#agree").click(function(){
		let btnRegist = $("#btnRegist")
		let isChecked = document.getElementById("agree").checked;
		if (isChecked) {
			btnRegist.attr("disabled", false);
			btnRegist.removeClass("fail");
		} else {
			btnRegist.attr("disabled","disabled");
			btnRegist.addClass("fail");
		}
	})

	/**
	 * 手机号输入时进行校验
	 */
	$('#phone').keyup(function (){
		checkPhone($(this));
	})

	/**
	 * 校验密码
	 */
	$('#loginPassword').keyup(function (){
		checkPwd($(this))
	})

	/**
	 * 发送验证码
	 */
	$("#messageCodeBtn").click(function (){
		getMessCode($.trim($('#phone').val()))
		let self = $(this)
		$.leftTime(60,function(d){
			if(d.status){
				self.addClass("on");
				self.attr("disabled",true)
				self.html((d.s=="00"?"60":d.s)+"秒后重新获取");
			}else{
				self.attr("disabled",false)
				self.removeClass("on");
				self.html("获取验证码");
			}
		});
	})

	/**
	 * 校验验证码格式
	 */
	$('#messageCode').keyup(function () {
		checkMessCodeFormat($.trim($(this).val()))
	});

	/**
	 * 点击注册按钮触发事件，校验手机号、密码、验证码
	 */
	$("#btnRegist").click(function () {
		registSubmit($('#phone'),$('#loginPassword'),$('#messageCode'))
	});
})

/**
 * 手机号校验标志
 */
let phoneFlag
/**
 *
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

	//判断手机号是否已经被注册
	$.get("/user/register/checkPhoneExist",
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
 * 密码格式校验位
 */
let pwdFlag;

/**
 * 验证密码格式是否符合要求
 * @param element 密码输入框标签元素
 * @returns {boolean} 验证结果
 */
function checkPwd(element){
	pwdFlag = false
	let id = 'loginPassword'
	//获取密码
	let pwd = $.trim(element.val())

	//验证长度是否正确
	if (MIN_LEN_OF_PWD > pwd.length || MAX_LEN_OF_PWD < pwd.length) {
		showError(id,"请输入"+MIN_LEN_OF_PWD+"-"+MAX_LEN_OF_PWD+"位密码")
		return pwdFlag
	}

	//验证密码包含字符是否合法
	if (!/^[0-9a-zA-Z]+$/.test(pwd)) {
		showError(id,"密码不能包含字母或数字以外的特殊符号！")
		return  pwdFlag
	}
	//验证密码是否同时包含数字和字母
	if (!/^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*/.test(pwd)) {
		showError(id,"必须同时包含数字和字母")
		return  pwdFlag
	}

	pwdFlag = true
	showSuccess(id)
}

/**
 * 获取手机验证码
 * @param phone 手机号码
 */
function getMessCode(phone) {
	let id = "messageCode"
	if (!phoneFlag){
		showError("phone","请先输入正确的手机号码！")
		return false
	}
	$.get("/user/register/getMessCode", {phone: phone}, function (data) {
		if (!data.flag) {
			showError(id, data.message);
			return false;
		} else {
			alert("验证码为：" + data.message);
		}
	});
}

/**
 * 验证码格式校验位
 */
let messCodeFormatFlag

function checkMessCodeFormat(messCode) {
	//初始化校验位
	messCodeFormatFlag = false
	//绑定验证码输入框ID
	let id = "messageCode"
	//判断不能为空
	if ($.isEmptyObject(messCode)){
		showError(id,"验证码不能为空！")
		return messCodeFormatFlag
	}
	//判断长度是否正确
	if (messCode.length !== MESSAGE_CODE_LEN) {
		showError(id,"请输入6位数字验证码！")
		return messCodeFormatFlag;
	}
	//判断是否为数字
	if (!/^[1-9]+/.test(messCode)) {
		showError(id,"验证码仅包含数字！")
	}
	messCodeFormatFlag = true
	showSuccess(id)
	return messCodeFormatFlag;
}


/**
 * 从服务端校验手机号和验证码是否匹配
 * @param phone 手机号码
 * @param messCode 验证码
 * @returns {boolean} 校验结果
 */
function checkMessCodeBeforeSubmit(phone,messCode) {
	$.get("/user/register/checkMessCode",
		{
			phone:phone,
			messCode:messCode,
		},
		function (data) {
			if (!data.flag) {
				showError('messageCode', data.message);
			}
			return data.flag;
		}
	);
}

/**
 * 提交注册信息。
 * 1. 检验手机号、密码、验证码格式的校验位是否为true
 * 2. 校验验证码是否正确
 * 3. 提交注册信息
 */
function registSubmit(phone,loginPassword,messageCode) {

	//1. 检验手机号、密码、验证码格式的校验位是否为true
	if (!phoneFlag){
		checkPhone(phone)
		return false
	}
	if (!pwdFlag) {
		checkPwd(loginPassword);
		return false;
	}
	if (!messCodeFormatFlag) {
		checkMessCodeFormat($.trim(messageCode.val()));
		return false;
	}
	//2. 校验验证码是否正确
	if (!checkMessCodeBeforeSubmit(phone.val(),messageCode.val())) {
		return false
	}

	//3. 提交注册信息
	$.get("/user/register/submit",
		{
			phone: phone.val(),
			pwd: loginPassword.val(),
		},
		function (data) {
			if (data.flag) {
				window.location.href='/loan/page/realName'
			}else {
				alert(data.message)
				return false
			}
		}
	);

}