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
			
					laydate({
			            elem: '#reviewTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
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
	<form:form id="inputForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">报价单ID：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkContract/selectdkQuotation" id="dkQuotation" name="dkQuotation.id"  value="${dkContract.dkQuotation.id}"  title="选择报价单ID" labelName="dkQuotation.name" 
						 labelValue="${dkContract.dkQuotation.name}" cssClass="form-control required" fieldLabels="名称|姓名|联系方式|地址" fieldKeys="name|memberName|mobile|address" searchLabel="报价单名称" searchKey="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">合同号：</label></td>
					<td class="width-35">
						<form:input path="contractNumber" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">顾客名称：</label></td>
					<td class="width-35">
						<form:input path="memberName" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">联系方式：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">联系地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">会员ID：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/daikin/dkContract/selectdkMember" id="dkMember" name="dkMember.id"  value="${dkContract.dkMember.id}"  title="选择会员ID" labelName="dkMember.name" 
						 labelValue="${dkContract.dkMember.name}" cssClass="form-control required" fieldLabels="姓名|联系方式|联系地址" fieldKeys="name|mobile|address" searchLabel="姓名" searchKey="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">合同总金额：</label></td>
					<td class="width-35">
						<form:input path="totalFee" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">连接率：</label></td>
					<td class="width-35">
						<form:input path="connectionRatio" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">安装人员：</label></td>
					<td class="width-35">
						<sys:treeselect id="iuser" name="iuser.id" value="${dkContract.iuser.id}" labelName="iuser.name" labelValue="${dkContract.iuser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">销售人员：</label></td>
					<td class="width-35">
						<sys:treeselect id="suser" name="suser.id" value="${dkContract.suser.id}" labelName="suser.name" labelValue="${dkContract.suser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">合同类型：</label></td>
					<td class="width-35">
						<form:select path="productType" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）：</label></td>
					<td class="width-35">
						<form:select path="reviewStatus" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('review_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">审核者：</label></td>
					<td class="width-35">
						<sys:treeselect id="ruser" name="ruser.id" value="${dkContract.ruser.id}" labelName="ruser.name" labelValue="${dkContract.ruser.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">审核日期：</label></td>
					<td class="width-35">
						<input id="reviewTime" name="reviewTime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${dkContract.reviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:input path="remark" htmlEscape="false"    class="form-control "/>
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
			<a class="btn btn-white btn-sm" onclick="addRow('#dkContractProductList', dkContractProductRowIdx, dkContractProductTpl);dkContractProductRowIdx = dkContractProductRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>商品ID</th>
						<th>名称</th>
						<th>规格</th>
						<th>单价</th>
						<th>数量</th>
						<th>总价</th>
						<th>分类</th>
						<th>功率</th>
						<th>产地</th>
						<th>品牌</th>
						<th>单位</th>
						<th>位置</th>
						<th>楼层</th>
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
						<input id="dkContractProductList{{idx}}_name" name="dkContractProductList[{{idx}}].name" type="text" value="{{row.name}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_model" name="dkContractProductList[{{idx}}].model" type="text" value="{{row.model}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_price" name="dkContractProductList[{{idx}}].price" type="text" value="{{row.price}}"   max="1000000"  min="1" class="form-control required number"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_amount" name="dkContractProductList[{{idx}}].amount" type="text" value="{{row.amount}}"   max="1000"  min="1" class="form-control required digits"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_totalPrice" name="dkContractProductList[{{idx}}].totalPrice" type="text" value="{{row.totalPrice}}"   max="10000000"  min="1" class="form-control required number"/>
					</td>
					
					
					<td>
						<select id="dkContractProductList{{idx}}_classifyId" name="dkContractProductList[{{idx}}].classifyId" data-value="{{row.classifyId}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('classify_id')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_power" name="dkContractProductList[{{idx}}].power" type="text" value="{{row.power}}" maxlength="10"    class="form-control  number"/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_place" name="dkContractProductList[{{idx}}].place" type="text" value="{{row.place}}" maxlength="10"    class="form-control "/>
					</td>
					
					
					<td>
						<select id="dkContractProductList{{idx}}_brandId" name="dkContractProductList[{{idx}}].brandId" data-value="{{row.brandId}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('brand_id')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_unit" name="dkContractProductList[{{idx}}].unit" type="text" value="{{row.unit}}" maxlength="10"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="dkContractProductList{{idx}}_position" name="dkContractProductList[{{idx}}].position" type="text" value="{{row.position}}" maxlength="10"    class="form-control "/>
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
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>