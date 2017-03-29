<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理</title>
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
		<form:form id="inputForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="20"  minlength="1"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同号：</label></td>
					<td class="width-35">
						<form:input path="contractNumber" htmlEscape="false" maxlength="50"  minlength="1"   class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>联系人手机号：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false" maxlength="11"  minlength="11"   class="form-control required number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">联系人电话：</label></td>
					<td class="width-35">
						<form:input path="telephone" htmlEscape="false" maxlength="20"  minlength="1"   class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">联系地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false" maxlength="200"  minlength="1"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>订购时间：</label></td>
					<td class="width-35">
						<form:input path="orderDate" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">合同总金额：</label></td>
					<td class="width-35">
						<form:input path="totalFee" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">已支付金额：</label></td>
					<td class="width-35">
						<form:input path="payFee" htmlEscape="false"    class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">状态(0-已签订；1-进行中；2-已完成)：</label></td>
					<td class="width-35">
						<form:select path="contractStatus" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('contract_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">支付状态(0-未支付；1-部分支付；2-已支付)：</label></td>
					<td class="width-35">
						<form:select path="payStatus" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">安装状态(0-未安装；1-进行中；2-已完成)：</label></td>
					<td class="width-35">
						<form:select path="installStatus" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('install_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">安装人员：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkContract/selectdkInstallPerson" id="dkInstallPerson" name="dkInstallPerson.id"  value="${dkContract.dkInstallPerson.id}"  title="选择安装人员" labelName="dkInstallPerson.name" 
						 labelValue="${dkContract.dkInstallPerson.name}" cssClass="form-control required" fieldLabels="安装人员表" fieldKeys="name|mobile" searchLabel="姓名|手机号" searchKey="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">销售人员：</label></td>
					<td class="width-35">
						<sys:treeselect id="tuser" name="tuser.id" value="${dkContract.tuser.id}" labelName="tuser.name" labelValue="${dkContract.tuser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:input path="remark" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>