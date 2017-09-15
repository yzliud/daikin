<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>记录管理</title>
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
			jQuery.validator.addMethod("isMobile", function(value, element) {
				var length = value.length;
				var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
				return this.optional(element) || (length == 11 && mobile.test(value));
			}, "请正确填写您的手机号码");
			
			validateForm = $("#inputForm").validate({
				rules: {
					mobile:{
				    	isMobile: true
		            }
		        },
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
			
					laydate({
			            elem: '#visitDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="dkVisit" action="${ctx}/daikin/dkVisit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>手机号：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false" maxlength="20"    class="form-control required number"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>联系地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false" maxlength="100"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>上门时间：</label></td>
					<td class="width-35">
						<input id="visitDate" name="visitDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${dkVisit.visitDate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>咨询内容：</label></td>
					<td class="width-35" colspan=3>
						<form:textarea path="content" htmlEscape="false" rows="4" maxlength="200"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35" colspan=3>
						<form:input path="remark" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>