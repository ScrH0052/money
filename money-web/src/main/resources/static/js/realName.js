
//同意实名认证协议
$(function() {
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