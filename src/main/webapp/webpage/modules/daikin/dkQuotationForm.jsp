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
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="dkQuotation" action="${ctx}/daikin/dkQuotation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>会员：</label></td>
					<td class="width-35">
						<dk:gridselectreturn url="${ctx}/daikin/dkQuotation/selectdkMember" id="dkMember" name="dkMember.id"  value="${dkQuotation.dkMember.id}"  title="选择会员" labelName="dkMember.name" 
						 labelValue="${dkQuotation.dkMember.name}" cssClass="form-control required" fieldLabels="姓名|联系方式|联系地址" fieldKeys="name|mobile|address" searchLabel="姓名" searchKey="name" ></dk:gridselectreturn>
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
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>审核状态：</label></td>
					<td class="width-35">
						<form:select path="reviewStatus" class="form-control required">
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
		
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">报价商品表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#dkQuotationProductList', dkQuotationProductRowIdx, dkQuotationProductTpl);dkQuotationProductRowIdx = dkQuotationProductRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<c:if test="true">
							<th>楼层</th>
							<th>位置</th>
							<th>需求面积</th>
						</c:if>
						
						<th>商品</th>
						<th>名称</th>
						<th>规格</th>
						<th>分类</th>
						<c:if test="true">
							<th>品牌</th>
							<th>产地</th>
							<th>单位</th>
						</c:if>
						<th>单价</th>
						<th>数量</th>
						<th>总价</th>
						<th>功率</th>
						
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
						<input id="dkQuotationProductList{{idx}}_floor" name="dkQuotationProductList[{{idx}}].floor" type="text" value="{{row.floor}}"    class="form-control "/>
					</td>

					<td>
						<input id="dkQuotationProductList{{idx}}_position" name="dkQuotationProductList[{{idx}}].position" type="text" value="{{row.position}}" maxlength="10"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_demandArea" name="dkQuotationProductList[{{idx}}].demandArea" type="text" value="{{row.demandArea}}" maxlength="8"    class="form-control  number"/>
					</td>


					<td>
						<sys:gridselect url="${ctx}/daikin/dkProductStockRecord/selectdkProduct" 
                            id="dkProduct" name="dkProduct.id"  value="${dkProductStockRecord.dkProduct.id}"  title="选择商品" labelName="dkProduct.name" 
						    labelValue="${dkProductStockRecord.dkProduct.name}" cssClass="form-control required" fieldLabels="名称|型号"
                          fieldKeys="name|model" searchLabel="商品名称" searchKey="name" ></sys:gridselect>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_name" name="dkQuotationProductList[{{idx}}].name" type="text" value="{{row.name}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_model" name="dkQuotationProductList[{{idx}}].model" type="text" value="{{row.model}}"    class="form-control "/>
					</td>

					<td>
						<select id="dkQuotationProductList{{idx}}_classifyId" name="dkQuotationProductList[{{idx}}].classifyId" data-value="{{row.classifyId}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('classify_id')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>

					<td>
						<input id="dkQuotationProductList{{idx}}_place" name="dkQuotationProductList[{{idx}}].place" type="text" value="{{row.place}}" maxlength="20"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_brandId" name="dkQuotationProductList[{{idx}}].brandId" type="text" value="{{row.brandId}}" maxlength="20"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_unit" name="dkQuotationProductList[{{idx}}].unit" type="text" value="{{row.unit}}" maxlength="10"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_price" name="dkQuotationProductList[{{idx}}].price" type="text" value="{{row.price}}"   max="100000"  min="1" class="form-control required number"/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_amount" name="dkQuotationProductList[{{idx}}].amount" type="text" value="{{row.amount}}"   max="1000"  min="1" class="form-control required digits"/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_totalPrice" name="dkQuotationProductList[{{idx}}].totalPrice" type="text" value="{{row.totalPrice}}"   max="1000000"  min="1" class="form-control  number"/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_power" name="dkQuotationProductList[{{idx}}].power" type="text" value="{{row.power}}" maxlength="10"    class="form-control  number"/>
					</td>
					
					
					<td>
						<input id="dkQuotationProductList{{idx}}_descript" name="dkQuotationProductList[{{idx}}].descript" type="text" value="{{row.descript}}" maxlength="100"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#dkQuotationProductList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>