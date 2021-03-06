<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
				
				var tel = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
				return this.optional(element) || tel.test(value) || (length == 11 && mobile.test(value)) ;
			}, "请正确填写您的手机号码");
			
			jQuery.validator.addMethod("checkAddress", function(value, element) {
				var checkFlag = 0;
				$.ajax({
		 			url:'${ctx}/daikin/dkMember/checkAddress',
		 			dataType:'json',
		 			async:false,
		 			data:{
		 				address:$('#address').val(),
		 				id:$('#id').val()
					},
		 			type:'post',
		 			success:function(data){
		 				if(data.rtnCode == '0'){
		 					checkFlag = 1;
		 				}
		 			}
			    });
			    if(checkFlag == 1){
			        return true;
			    }else{
			        return false;
			    }
			
			}, "该地址已存在,请重新输入！");
			
			validateForm = $("#inputForm").validate({
				rules: {
					mobile:{
				    	isMobile: true
		            },
		            address:{
		            	checkAddress: true
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
		<form:form id="inputForm" modelAttribute="dkMember" action="${ctx}/daikin/dkMember/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="20"  minlength="1"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>手机号：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false" maxlength="20"  minlength="1"   class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>信息来源：</label></td>
					<td class="width-35" colspan=3>
						<form:select path="sourceInfo" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('source_info')}" itemLabel="label" itemValue="value" htmlEscape="false" readonly="true"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>联系地址：</label></td>
					<td class="width-35" colspan=3>
						<form:input path="address" htmlEscape="false" maxlength="100"  minlength="1"   class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>备注：</label></td>
					<td class="width-35" colspan=3>
						<form:input path="remark" htmlEscape="false" maxlength="200"  placeholder="请填写有无介绍费"  class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>