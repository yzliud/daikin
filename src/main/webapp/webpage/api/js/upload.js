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
	           "uuid": document.getElementById("uid").value
	    },  
		success: function (data, status){//服务器成功响应处理函数
			document.getElementById("upImage1").src="../../upload/"+data.imgname+"?rand=" + Math.random();
			document.getElementById("imgUrl1").value="../../upload/"+data.imgname;
		},
		error: function (data, status, e)//服务器响应失败处理函数
		{
			alert(e);
		}
	});
}

function fileSelect2() {
	document.getElementById("img2").click();
}

function fileToUpload2() {
	var file = $("#img2").val();
	if (file == "") {
		alert("请选择一个文件，再点击上传。");
		return;
	}
	$.ajaxFileUpload
	({
		url: '../../a/api/worker/upload', //用于文件上传的服务器端请求地址
		secureuri: false, //是否需要安全协议，一般设置为false
		fileElementId: 'img2', //文件上传input的ID
		dataType: 'json', //返回值类型 一般设置为json
		data: {//加入的文本参数  
	           "uuid": document.getElementById("uid").value
	    },  
		success: function (data, status){//服务器成功响应处理函数
			document.getElementById("upImage2").src="../../upload/"+data.imgname+"?rand=" + Math.random();
			document.getElementById("imgUrl2").value="../../upload/"+data.imgname;
		},
		error: function (data, status, e)//服务器响应失败处理函数
		{
			alert(e);
		}
	});
}

function fileSelect3() {
	document.getElementById("img3").click();
}

function fileToUpload3() {
	var file = $("#img3").val();
	if (file == "") {
		alert("请选择一个文件，再点击上传。");
		return;
	}
	$.ajaxFileUpload
	({
		url: '../../a/api/worker/upload', //用于文件上传的服务器端请求地址
		secureuri: false, //是否需要安全协议，一般设置为false
		fileElementId: 'img3', //文件上传input的ID
		dataType: 'json', //返回值类型 一般设置为json
		data: {//加入的文本参数  
	           "uuid": document.getElementById("uid").value 
	    },  
		success: function (data, status){//服务器成功响应处理函数
			document.getElementById("upImage3").src="../../upload/"+data.imgname+"?rand=" + Math.random();
			document.getElementById("imgUrl3").value="../../upload/"+data.imgname;
		},
		error: function (data, status, e)//服务器响应失败处理函数
		{
			alert(e);
		}
	});
}

function fileSelect4() {
	document.getElementById("img4").click();
}

function fileToUpload4() {
	var file = $("#img4").val();
	if (file == "") {
		alert("请选择一个文件，再点击上传。");
		return;
	}
	$.ajaxFileUpload
	({
		url: '../../a/api/worker/upload', //用于文件上传的服务器端请求地址
		secureuri: false, //是否需要安全协议，一般设置为false
		fileElementId: 'img4', //文件上传input的ID
		dataType: 'json', //返回值类型 一般设置为json
		data: {//加入的文本参数  
	           "uuid": document.getElementById("uid").value 
	    },  
		success: function (data, status){//服务器成功响应处理函数
			document.getElementById("upImage4").src="../../upload/"+data.imgname+"?rand=" + Math.random();
			document.getElementById("imgUrl4").value="../../upload/"+data.imgname;
		},
		error: function (data, status, e)//服务器响应失败处理函数
		{
			alert(e);
		}
	});
}