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
			
			jQuery.validator.addMethod("checkSel", function(value, element) {
				
				 if($("#contractFlag").val() == '1' && $("#parentName").val() == ''){
					return false;
				 }else{
				    return true;
				 }
			
			}, "请选择后面的主合同");
			
			
			validateForm = $("#inputForm").validate({
				rules: {
					contractNumber:{
						 checkSel: true
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
			
		});
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/add" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">合同类型：</label></td>
					<td class="width-35">
						<form:select path="contractFlag" class="form-control " onchange="selChange();" >
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('contract_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>报价单：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkContract/selectdkQuotation" id="dkQuotation" name="dkQuotation.id"  value="${dkContract.dkQuotation.id}"  title="选择报价单ID" labelName="dkQuotation.name" 
						 labelValue="${dkContract.dkQuotation.name}" cssClass="form-control required" fieldLabels="名称|顾客姓名|联系方式|联系地址|金额" fieldKeys="name|memberName|mobile|address|totalFee" searchLabel="名称" searchKey="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同号：</label></td>
					<td class="width-35">
						<form:input path="contractNumber" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active" name="td_contract"><label class="pull-right">主合同：</label></td>
					<td class="width-35" name="td_contract">
						<sys:gridselect url="${ctx}/daikin/dkContract/selectparent" id="parent" name="parent.id"  value="${dkContract.parent.id}"  title="选择主合同ID" labelName="parent.name" 
						 labelValue="${dkContract.parent.name}" cssClass="form-control" fieldLabels="合同名称|合同号|顾客姓名|合同金额" fieldKeys="name|contractNumber|memberName|contractFee" searchLabel="合同名称" searchKey="name" ></sys:gridselect>
					</td>
				</tr>
				
		 	</tbody>
		</table>
		
		
	</form:form>
<script type="text/javascript">
selChange();
function selChange(){
	if($("#contractFlag").val() == '0'){
		$("*[name='td_contract']").hide();
		 $("#contractNumber").removeAttr("readonly"); 
	}else{
		$("*[name='td_contract']").show();
		$("#contractNumber").attr("readonly",true);
	}
}
</script>
</body>
</html>