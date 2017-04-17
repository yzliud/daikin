<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同商品管理</title>
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
		<form:form id="inputForm" modelAttribute="dkContractProduct" action="${ctx}/daikin/dkContractProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">合同ID：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkContractProduct/selectdkContract" id="dkContract" name="dkContract.id"  value="${dkContractProduct.dkContract.id}"  title="选择合同ID" labelName="dkContract.name" 
						 labelValue="${dkContractProduct.dkContract.name}" cssClass="form-control required" fieldLabels="合同名称|合同号|顾客|合同金额" fieldKeys="name|contractNumber|memberName|totalFee" searchLabel="合同名称" searchKey="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">规格：</label></td>
					<td class="width-35">
						<form:input path="model" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>单价：</label></td>
					<td class="width-35">
						<form:input path="price" htmlEscape="false"   max="1000000"  min="1" class="form-control required number"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>数量：</label></td>
					<td class="width-35">
						<form:input path="amount" htmlEscape="false"   max="1000"  min="1" class="form-control required digits"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>总价：</label></td>
					<td class="width-35">
						<form:input path="totalPrice" htmlEscape="false"   max="10000000"  min="1" class="form-control required number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">分类：</label></td>
					<td class="width-35">
						<form:select path="classifyId" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('classify_id')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">功率：</label></td>
					<td class="width-35">
						<form:input path="power" htmlEscape="false" maxlength="10"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">产地：</label></td>
					<td class="width-35">
						<form:input path="place" htmlEscape="false" maxlength="10"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">品牌：</label></td>
					<td class="width-35">
						<form:select path="brandId" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('brand_id')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">单位：</label></td>
					<td class="width-35">
						<form:input path="unit" htmlEscape="false" maxlength="10"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">位置：</label></td>
					<td class="width-35">
						<form:input path="position" htmlEscape="false" maxlength="10"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">楼层：</label></td>
					<td class="width-35">
						<form:select path="floor" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('floor')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">需求面积：</label></td>
					<td class="width-35">
						<form:input path="demandArea" htmlEscape="false" maxlength="10"    class="form-control  number"/>
					</td>
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35">
						<form:input path="descript" htmlEscape="false" maxlength="50"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>