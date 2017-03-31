<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报价单管理</title>
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
		<form:form id="inputForm" modelAttribute="dkQuotation" action="${ctx}/daikin/dkQuotation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>顾客名称：</label></td>
					<td class="width-35">
						<form:input path="memberName" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>联系人手机号：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>联系地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>会员ID：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkQuotation/selectdkMember" id="dkMember" name="dkMember.id"  value="${dkQuotation.dkMember.id}"  title="选择会员ID" labelName="dkMember.name" 
						 labelValue="${dkQuotation.dkMember.name}" cssClass="form-control required" fieldLabels="姓名|联系方式" fieldKeys="name|mobile" searchLabel="姓名" searchKey="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同总金额：</label></td>
					<td class="width-35">
						<form:input path="totalFee" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>商品类型：</label></td>
					<td class="width-35">
						<form:select path="productType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">销售人员：</label></td>
					<td class="width-35">
						<sys:treeselect id="tuser" name="tuser.id" value="${dkQuotation.tuser.id}" labelName="tuser.name" labelValue="${dkQuotation.tuser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">连接率：</label></td>
					<td class="width-35">
						<form:input path="connectionRatio" htmlEscape="false"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）：</label></td>
					<td class="width-35">
						<form:select path="reviewStatus" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('review_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:textarea path="remark" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>