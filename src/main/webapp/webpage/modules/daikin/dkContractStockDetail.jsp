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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			$(obj).parent().parent().remove();
		}
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/stockSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="deleteIds"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">主合同：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkContract/selectparent" id="parent" name="parent.id"  value="${dkContract.parent.id}"  title="选择主合同" labelName="parent.name" 
						 disabled="disabled" labelValue="${dkContract.parent.name}" cssClass="form-control" fieldLabels="合同名称|合同号|顾客姓名|合同金额" fieldKeys="name|contractNumber|memberName|contractFee" searchLabel="合同名称" searchKey="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同号：</label></td>
					<td class="width-35">
						<form:input path="contractNumber" htmlEscape="false"    class="form-control required" readonly="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>顾客名称：</label></td>
					<td class="width-35">
						<form:input path="memberName" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>联系方式：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false"    class="form-control required number"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>联系地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">合同商品表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						
						<th>商品</th>
						<th>成本价</th>
						<th>销售价</th>
						<th>销售数量</th>
						<th>已出库数量</th>
						<th>出库数量</th>
						<th>成本总价</th>
						<th>销售总价</th>
						<th>描述</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="dkContractProductList">
				</tbody>
			</table>
			<script type="text/template" id="dkContractProductTpl">//<!--
				<tr id="dkContractProductList{{idx}}">
					<td class="hide">
						<input id="dkContractProductList{{idx}}_id" name="dkContractProductList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="dkContractProductList{{idx}}_delFlag" name="dkContractProductList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
						<input id="dkContractProductList{{idx}}_name" readonly name="dkContractProductList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="50"    class="form-control "/>
					</td>

					<td>
						<input id="dkContractProductList{{idx}}_costPrice" readonly readonly name="dkContractProductList[{{idx}}].costPrice" type="text" value="{{row.costPrice}}"   max="10000000"   onchange="" class="form-control required number"/>
					</td>	
					
					<td>
						<input id="dkContractProductList{{idx}}_price" readonly name="dkContractProductList[{{idx}}].price" type="text" value="{{row.price}}"  max="10000000"  class="form-control required number"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_amount" readonly name="dkContractProductList[{{idx}}].amount" type="text" value="{{row.amount}}"    max="1000"  min="1" class="form-control required digits"/>
					</td>

					<td>
						<input id="dkContractProductList{{idx}}_stockOut" readonly name="dkContractProductList[{{idx}}].stockOut" type="text" value="{{row.stockOut}}"    max="1000"  min="0" class="form-control required digits"/>
					</td>

					<td>
						<input id="dkContractProductList{{idx}}_nowStockOut"  name="dkContractProductList[{{idx}}].nowStockOut" type="text" value="0" onchange="priceChange('dkContractProductList{{idx}}');"   max="1000"  min="0" class="form-control required digits"/>
					</td>
					
					<td>
						<input id="dkContractProductList{{idx}}_totalCostPrice" readonly name="dkContractProductList[{{idx}}].totalCostPrice" type="text" value="{{row.totalCostPrice}}"   max="100000000" class="form-control number"/>
					</td>	
					
					<td>
						<input id="dkContractProductList{{idx}}_totalPrice" readonly name="dkContractProductList[{{idx}}].totalPrice" readonly type="text" value="{{row.totalPrice}}"   max="100000000"   class="form-control required number"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_descript" readonly name="dkContractProductList[{{idx}}].descript" type="text" value="{{row.descript}}" maxlength="50"    class="form-control "/>
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var dkContractProductRowIdx = 0, dkContractProductTpl = $("#dkContractProductTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(dkContract.dkContractProductList)};
					for (var i=0; i<data.length; i++){
						addRow('#dkContractProductList', dkContractProductRowIdx, dkContractProductTpl, data[i]);
						dkContractProductRowIdx = dkContractProductRowIdx + 1;
					}
				});
			</script>
			
			
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>