<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>维保记录管理</title>
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
			            elem: '#serviceDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="dkContractService" action="${ctx}/daikin/dkContractService/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkContractService/selectdkContract" id="dkContract" name="dkContract.id"  value="${dkContractService.dkContract.id}"  title="选择合同" labelName="dkContract.name" 
						 labelValue="${dkContractService.dkContract.name}" cssClass="form-control required" fieldLabels="合同名称|合同号|客户|合同金额" fieldKeys="name|contractNumber|memberName|totalFee" searchLabel="合同名称" searchKey="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>维保时间：</label></td>
					<td class="width-35">
						<input id="serviceDate" name="serviceDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${dkContractService.serviceDate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>维保人员：</label></td>
					<td class="width-35">
						<form:input path="servicePerson" htmlEscape="false" maxlength="20"  minlength="1"   class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">维保记录：</label></td>
					<td class="width-35" colspan=3>
						<form:input path="serviceContent" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>