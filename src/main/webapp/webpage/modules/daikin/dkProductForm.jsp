<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="dkProduct" action="${ctx}/daikin/dkProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="100"  minlength="1"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">型号：</label></td>
					<td class="width-35">
						<form:input path="model" htmlEscape="false" maxlength="100"  minlength="1"   class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">图片：</label></td>
					<td class="width-35">
						<form:hidden id="picPath" path="picPath" htmlEscape="false" maxlength="500" class="form-control"/>
						<sys:ckfinder input="picPath" type="files" uploadPath="/daikin/dkProduct" selectMultiple="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35">
						<form:input path="descript" htmlEscape="false" maxlength="500"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">价格：</label></td>
					<td class="width-35">
						<form:input path="price" htmlEscape="false"   max="500000"  min="0" class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">库存：</label></td>
					<td class="width-35">
						<form:input path="inventory" htmlEscape="false"   max="10000"  min="0" class="form-control  digits"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>