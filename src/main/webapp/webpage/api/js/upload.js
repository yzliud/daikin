function fileSelect1() {
	document.getElementById("img1").click();
}

function fileToUpload1() {
	var file = $("#img1").val();
	if (file == "") {
		alert("请选择一个文件，再点击上传。");
		return;
	}
	$.ajaxFileUpload
	({
		url: '../../a/api/worker/upload', //用于文件上传的服务器端请求地址
		secureuri: false, //是否需要安全协议，一般设置为false
		fileElementId: 'img1', //文件上传input的ID
		dataType: 'json', //返回值类型 一般设置为json
		data: {//加入的文本参数  
	           "uuid": uuid 
	    },  
		success: function (data, status){//服务器成功响应处理函数
			alert(data.url);
		},
		error: function (data, status, e)//服务器响应失败处理函数
		{
			alert(e);
		}
	});
}