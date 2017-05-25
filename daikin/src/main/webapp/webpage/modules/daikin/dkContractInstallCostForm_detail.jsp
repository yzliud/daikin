<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>安装付款管理</title>
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
			
					laydate({
			            elem: '#payDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="dkContractInstallCost" action="${ctx}/daikin/dkContractInstallCost/checkInstallCost" method="post" class="form-horizontal">
		<form:hidden path="id"/>
        <form:hidden path="reviewStatus"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同：</label></td>
					<td class="width-35">
						${dkContractInstallCost.dkContract.name}
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>支付时间：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${dkContractInstallCost.payDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>费用：</label></td>
					<td class="width-35">
						${dkContractInstallCost.payFee} 元
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>安装队：</label></td>
					<td class="width-35">
						${dkContractInstallCost.installTeam}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35" colspan=3>
						${dkContractInstallCost.remark}
					</td>
		  		</tr>
		  		<c:if test="${checkType == '1' }">
				<tr>
					<td class="width-15 active"><label class="pull-right">审核意见：</label></td>
					<td class="width-35" colspan=3>
						<input name="remark" id="remark"   class="form-control required"/>
					</td>
				</tr>
				</c:if>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>