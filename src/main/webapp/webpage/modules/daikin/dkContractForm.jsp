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
	<form:form id="inputForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
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
					<td class="width-15 active"><label class="pull-right">合同类型：</label></td>
					<td class="width-35">
						<form:select path="contractFlag" class="form-control " onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('contract_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" readonly="true"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>报价单：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkContract/selectdkQuotation" id="dkQuotation" name="dkQuotation.id"  value="${dkContract.dkQuotation.id}"  title="选择报价单" labelName="dkQuotation.name" 
						 disabled="disabled" labelValue="${dkContract.dkQuotation.name}" cssClass="form-control required" fieldLabels="名称|顾客姓名|联系方式|联系地址|金额" fieldKeys="name|memberName|mobile|address|totalFee" searchLabel="名称" searchKey="name" ></sys:gridselect>
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
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同金额：</label></td>
					<td class="width-35">
						<form:input path="contractFee" htmlEscape="false"    class="form-control required number" />
					</td>
					<td class="width-15 active"><label class="pull-right">工程监理：</label></td>
					<td class="width-35">
						<sys:treeselect id="supervisionUser" name="supervisionUser.id" value="${dkContract.supervisionUser.id}" labelName="supervisionUser.name" labelValue="${dkContract.supervisionUser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">连接率：</label></td>
					<td class="width-35">
						<form:input path="connectionRatio" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">安装人员：</label></td>
					<td class="width-35">
						<sys:treeselect id="installUser" name="installUser.id" value="${dkContract.installUser.id}" labelName="installUser.name" labelValue="${dkContract.installUser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>销售人员：</label></td>
					<td class="width-35">
						<sys:treeselect id="saleUser" name="saleUser.id" value="${dkContract.saleUser.id}" labelName="saleUser.name" labelValue="${dkContract.saleUser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>商品类型：</label></td>
					<td class="width-35">
						<form:select path="productType" class="form-control required" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:input path="remark" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
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
			<a class="btn btn-white btn-sm" onclick="addRow('#dkContractProductList', dkContractProductRowIdx, dkContractProductTpl);dkContractProductRowIdx = dkContractProductRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						
						<th>商品</th>
						<th>单价</th>
						<th>数量</th>
						<th>总价</th>
						<th>功率</th>
						<th width="100">楼层</th>
						<th>位置</th>
						<th>需求面积</th>
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
						<dk:gridProduct url="${ctx}/daikin/dkCommon/selectdkProduct" 
                            id="dkContractProductList{{idx}}_productId" name="dkContractProductList[{{idx}}].productId"  value="{{row.productId}}"  title="选择商品" labelName="dkProduct.name" 
						    labelValue="{{row.name}}" cssClass="form-control required" fieldLabels="名称|型号|单价|功率"
                          fieldKeys="name|model|price|power" searchLabel="商品名称" searchKey="name" rowkeys="dkContractProductList{{idx}}" feekeys="contractFee"></dk:gridProduct>
					</td>	
					
					<td>
						<input id="dkContractProductList{{idx}}_price" name="dkContractProductList[{{idx}}].price" type="text" value="{{row.price}}" onchange="priceChange('dkContractProductList{{idx}}');"  max="1000000"  class="form-control required number"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_amount" name="dkContractProductList[{{idx}}].amount" type="text" value="{{row.amount}}" onchange="priceChange('dkContractProductList{{idx}}');"   max="1000"  min="1" class="form-control required digits"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_totalPrice" name="dkContractProductList[{{idx}}].totalPrice" readonly type="text" value="{{row.totalPrice}}"   max="10000000"   class="form-control required number"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_power" name="dkContractProductList[{{idx}}].power" type="text" value="{{row.power}}" maxlength="10"    class="form-control  number"/>
					</td>

					<td>
						<select id="dkContractProductList{{idx}}_floor" name="dkContractProductList[{{idx}}].floor" data-value="{{row.floor}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('floor')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_position" name="dkContractProductList[{{idx}}].position" type="text" value="{{row.position}}" maxlength="10"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_demandArea" name="dkContractProductList[{{idx}}].demandArea" type="text" value="{{row.demandArea}}" maxlength="10"    class="form-control  number"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_descript" name="dkContractProductList[{{idx}}].descript" type="text" value="{{row.descript}}" maxlength="50"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#dkContractProductList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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
			
			<script type="text/javascript">
				
				function delRow_rewrite(str,strvalue){
					//设置总价
					var sum=0;
					$("input[id$='_totalPrice']").each(function(){
						if($(this).val()!=""){
							sum = parseFloat(sum) + parseFloat($(this).val());
						}
					})
					$("#contractFee").val(sum);
					delRow(str, strvalue);
				}
				
				function priceChange(idstr){
					
					var num1 = $('#'+idstr+'_price').val();
					var num2 = $('#'+idstr+'_amount').val();
					var num3 = num1 * num2 * 10000 / 10000;
					$('#'+idstr+'_totalPrice').val(num3);
					
					//设置总价
					var sum=0;
					$("input[id$='_totalPrice']").each(function(){
						if($(this).val()!=""){
							sum = parseFloat(sum) + parseFloat($(this).val());
						}
					})
					$("#contractFee").val(sum);
				}
			</script>
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>