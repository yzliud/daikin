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
			jQuery.validator.addMethod("checkQuotationName", function(value, element) {
				
				var checkFlag = 0;
				$.ajax({
		 			url:'${ctx}/daikin/dkQuotation/getSingle',
		 			dataType:'json',
		 			async:false,
		 			data:{
		 				name:$('#name').val(),
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
			
			}, "此报价单名称已存在,请重新输入！");
			
			validateForm = $("#inputForm").validate({
				rules: {
				    name:{
				    	checkQuotationName: true
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
	<form:form id="inputForm" modelAttribute="dkQuotation" action="${ctx}/daikin/dkQuotation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="deleteIds"/>
		<form:hidden path="reviewStatus"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>会员：</label></td>
					<td class="width-35">
						<dk:gridMember url="${ctx}/daikin/dkQuotation/selectdkMember" id="dkMember" name="dkMember.id"  value="${dkQuotation.dkMember.id}"  title="选择会员" labelName="dkMember.name" 
						 labelValue="${dkQuotation.dkMember.name}" cssClass="form-control required" fieldLabels="姓名|联系方式|联系地址" fieldKeys="name|mobile|address" searchLabel="姓名" searchKey="name" ></dk:gridMember>
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
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>销售金额：</label></td>
					<td class="width-35">
						<form:input path="totalFee" htmlEscape="false"    class="form-control required number" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>成本价：</label></td>
					<td class="width-35">
						<form:input path="costFee" htmlEscape="false"    class="form-control required number" readonly="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>签单价：</label></td>
					<td class="width-35">
						<form:input path="signFee" htmlEscape="false"    class="form-control required number"/>
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
						<c:if test="${dkQuotation.tuser.id == null  }">
							<sys:treeselect id="tuser" name="tuser.id" value="${fns:getUser().id}" labelName="tuser.name" labelValue="${fns:getUser().name}"
								title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
						</c:if>
						<c:if test="${dkQuotation.tuser.id != null  }">
						<sys:treeselect id="tuser" name="tuser.id" value="${dkQuotation.tuser.id}" labelName="tuser.name" labelValue="${dkQuotation.tuser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>备注：</label></td>
					<td class="width-35" colspan = 3>
						<form:input path="remark" htmlEscape="false"     class="form-control required"/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
		
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">报价商品表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="checkType();" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>商品</th>
						<th>成本价</th>
						<th>销售价</th>
						<th>数量</th>
						<th>成本总价</th>
						<th>销售总价</th>
						<th>描述</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="dkQuotationProductList">
				</tbody>
			</table>
			<script type="text/template" id="dkQuotationProductTpl">//<!--
				<tr id="dkQuotationProductList{{idx}}">
					<td class="hide">
						<input id="dkQuotationProductList{{idx}}_id" name="dkQuotationProductList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="dkQuotationProductList{{idx}}_delFlag" name="dkQuotationProductList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
						<dk:gridProduct url="${ctx}/daikin/dkCommon/selectdkProduct" 
                            id="dkQuotationProductList{{idx}}_productId" name="dkQuotationProductList[{{idx}}].productId"  value="{{row.productId}}"  title="选择商品" labelName="dkProduct.name" 
						    labelValue="{{row.name}}" cssClass="form-control required" fieldLabels="名称|型号|销售价|功率|成本价"
                          fieldKeys="name|model|price|power|costPrice" searchLabel="商品名称" searchKey="name" rowkeys="dkQuotationProductList{{idx}}" feekeys="totalFee"></dk:gridProduct>
					</td>
					
					<td>
						<input id="dkQuotationProductList{{idx}}_costPrice" readonly name="dkQuotationProductList[{{idx}}].costPrice" type="text" value="{{row.costPrice}}"   max="100000"   onchange="" class="form-control required number"/>
					</td>

					<td>
						<input id="dkQuotationProductList{{idx}}_price" name="dkQuotationProductList[{{idx}}].price" type="text" value="{{row.price}}" onchange="priceChange('dkQuotationProductList{{idx}}');"  max="1000000" class="form-control required number"/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_amount" name="dkQuotationProductList[{{idx}}].amount" type="text" value="{{row.amount}}" onchange="priceChange('dkQuotationProductList{{idx}}');"  max="1000"  min="1" class="form-control required digits"/>
					</td>
					
					<td>
						<input id="dkQuotationProductList{{idx}}_totalCostPrice" readonly name="dkQuotationProductList[{{idx}}].totalCostPrice" type="text" value="{{row.totalCostPrice}}"   max="10000000" class="form-control number"/>
					</td>					

					<td>
						<input id="dkQuotationProductList{{idx}}_totalPrice" readonly name="dkQuotationProductList[{{idx}}].totalPrice" type="text" value="{{row.totalPrice}}"   max="100000000"  class="form-control  number"/>
					</td>
					
					
					

					<td>
						<input id="dkQuotationProductList{{idx}}_descript" name="dkQuotationProductList[{{idx}}].descript" type="text" value="{{row.descript}}" maxlength="100"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow_rewrite(this, '#dkQuotationProductList{{idx}}', '{{row.id}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			
			
			<script type="text/javascript">
				var dkQuotationProductRowIdx = 0, dkQuotationProductTpl = $("#dkQuotationProductTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(dkQuotation.dkQuotationProductList)};
					for (var i=0; i<data.length; i++){
						addRow('#dkQuotationProductList', dkQuotationProductRowIdx, dkQuotationProductTpl, data[i]);
						dkQuotationProductRowIdx = dkQuotationProductRowIdx + 1;
					}
				});
			</script>
			
			<script type="text/javascript">
				function checkType(){
					if(typeof($('#productType').val()) == "undefined" || $('#productType').val() == ''){
						layer.alert('请先选择报价单类型', {
							  icon: 2,
							  skin: 'layer-ext-moon' 
							})
					}else{
						addRow('#dkQuotationProductList', dkQuotationProductRowIdx, dkQuotationProductTpl);
						dkQuotationProductRowIdx = dkQuotationProductRowIdx + 1;
					}
				}
				
				function delRow_rewrite(str,strvalue, delids){
					
					delRow(str, strvalue);
					
					$("#deleteIds").val($("#deleteIds").val() + delids);
					
					//设置总价
					var sum=0;
					$("input[id$='_totalPrice']").each(function(){
						if($(this).val()!=""){
							sum = parseFloat(sum) + parseFloat($(this).val());
						}
					})
					$("#totalFee").val(sum);
					
					var costsum=0;
					$("input[id$='_totalCostPrice']").each(function(){
						if($(this).val()!=""){
							costsum = parseFloat(costsum) + parseFloat($(this).val());
						}
					})
					$("#costFee").val(costsum);
				}
				
				function priceChange(idstr){
					var price = $('#'+idstr+'_price').val();
					var num = $('#'+idstr+'_amount').val();
					var totalprice = num * price * 10000 / 10000;
					$('#'+idstr+'_totalPrice').val(totalprice);
					
					var costPrice = $('#'+idstr+'_costPrice').val();
					var totalCostPrice = costPrice * num * 10000 / 10000;
					
					$('#'+idstr+'_totalCostPrice').val(totalCostPrice);
					//设置总价
					var sum=0;
					$("input[id$='_totalPrice']").each(function(){
						if($(this).val()!=""){
							sum = parseFloat(sum) + parseFloat($(this).val());
						}
					})
					$("#totalFee").val(sum);
					
					var costsum=0;
					$("input[id$='_totalCostPrice']").each(function(){
						if($(this).val()!=""){
							costsum = parseFloat(costsum) + parseFloat($(this).val());
						}
					})
					$("#costFee").val(costsum);
					$("#signFee").val(sum);
				}
			</script>
			
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>