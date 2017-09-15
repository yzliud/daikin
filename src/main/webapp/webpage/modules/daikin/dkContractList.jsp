<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/PrintArea.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>合同列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>合同类型：</span>
				<form:select path="contractFlag"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('contract_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span>合同号：</span>
				<form:input path="contractNumber" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>商品类型：</span>
				<form:select path="productType"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<br>
			<span>报价单：</span>
				<sys:gridselect url="${ctx}/daikin/dkContract/selectOwnDkQuotation" id="dkQuotation" name="dkQuotation"  value="${dkContract.dkQuotation.id}"  title="选择报价单" labelName="dkQuotation.name" 
					labelValue="${dkContract.dkQuotation.name}" cssClass="form-control required" fieldLabels="名称|顾客姓名|联系方式|联系地址|金额" fieldKeys="name|memberName|mobile|address|totalFee" searchLabel="名称" searchKey="name" ></sys:gridselect>
			<span>安装人员：</span>
				<sys:treeselect id="installUser" name="installUser.id" value="${dkContract.installUser.id}" labelName="installUser.name" labelValue="${dkContract.installUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>销售人员：</span>
				<sys:treeselect id="saleUser" name="saleUser.id" value="${dkContract.saleUser.id}" labelName="saleUser.name" labelValue="${dkContract.saleUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<br>
			<span>顾客名称：</span>
				<form:input path="memberName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>联系方式：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>审核状态：</span>
				<form:select path="reviewStatus"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('review_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="daikin:dkContract:add">
				<table:addRow url="${ctx}/daikin/dkContract/forwardAdd" title="合同"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="sort-column name">名称</th>
				<th  class="sort-column contractFlag">合同类型</th>
				<th  class="sort-column dkQuotation.id">报价单</th>
				<th  class="sort-column contractNumber">合同号</th>
				<th  class="sort-column memberName">顾客名称</th>
				<th  class="sort-column mobile">联系方式</th>
				<th  class="sort-column contractFee">签单价</th>
				<th  class="sort-column totalFee">签单总价</th>
				<th  class="sort-column arriveFee">已到账金额</th>
				<th  class="sort-column installUser.name">安装人员</th>
				<th  class="sort-column saleUser.name">销售人员</th>
				<th  class="sort-column productType">商品类型</th>
				<th  class="sort-column reviewStatus">审核状态</th>
				<th  class="sort-column reviewUser.name">审核者</th>
				<th  class="sort-column updateDate">update_date</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkContract">
			<tr>
				<td><a  href="#" onclick="openDialogView('查看合同', '${ctx}/daikin/dkContract/form?id=${dkContract.id}','800px', '500px')">
					${dkContract.name}
				</a></td>
				<td>
					${fns:getDictLabel(dkContract.contractFlag, 'contract_flag', '')}
				</td>
				<td>
					${dkContract.dkQuotation.name}
				</td>
				<td>
					${dkContract.contractNumber}
				</td>
				<td>
					${dkContract.memberName}
				</td>
				<td>
					${dkContract.mobile}
				</td>
				<td>
					${dkContract.signFee}
				</td>
				<td>
					${dkContract.totalSignFee}
				</td>
				<td>
					${dkContract.arriveFee}
				</td>
				<td>
					${dkContract.installUser.name}
				</td>
				<td>
					${dkContract.saleUser.name}
				</td>
				<td>
					${fns:getDictLabel(dkContract.productType, 'product_type', '')}
				</td>
				<td>
					${fns:getDictLabel(dkContract.reviewStatus, 'review_status', '')}
				</td>
				<td>
					${dkContract.reviewUser.name}
				</td>
				<td>
					<fmt:formatDate value="${dkContract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>				
					<shiro:hasPermission name="daikin:dkContract:view">
						<a href="#" onclick="openDialogView('查看合同', '${ctx}/daikin/dkContract/detail?id=${dkContract.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkContract:edit">
    					<c:if test="${dkContract.reviewStatus == '0' }">
						<a href="#" onclick="openDialog_check('合同提交审核', '${ctx}/daikin/dkContract/detail?id=${dkContract.id}','800px', '500px')" class="btn btn-danger btn-xs" ><i class="fa fa-edit"></i> 提交审核</a>
    					</c:if>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkContract:del">
    					<c:if test="${dkContract.reviewStatus == '0' && dkContract.isReview != 1}">
						<a href="${ctx}/daikin/dkContract/delete?id=${dkContract.id}" onclick="return confirmx('确认要删除该合同吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
						</c:if>
					</shiro:hasPermission>
					<c:if test="${ dkContract.isReview == 1}">
					<a href="#" onclick="openDialogView('查看审核记录', '${ctx}/daikin/dkAuditRecord/list?recordId=${dkContract.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 审核记录</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
<script type="text/javascript">
	
	//打开对话框(添加修改)
	function openDialog_check(title,url,width,height,target){
		
		if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
			width='auto';
			height='auto';
		}else{//如果是PC端，根据用户设置的width和height显示。
		
		}
		
		top.layer.open({
		    type: 2,  
		    area: [width, height],
		    title: title,
	        maxmin: true, //开启最大化最小化按钮
		    content: url ,
		    btn: ['提交审核', '关闭'],
		    btn1: function(index, layero){
		    	 
		    	 var body = top.layer.getChildFrame('body', index);
		         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         body.find('#reviewStatus').val("1");
		         var inputForm = body.find('#inputForm');
		         var top_iframe;
		         if(target){
		        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
		         }else{
		        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
		         }
		         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
		        if(iframeWin.contentWindow.doSubmit() ){
		        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
		        	  return true;
		        }else{
		        	 return false;
		         }
				
			  },
			  cancel: function(index, layero){ 
		    	   
		       },
		       
		}); 	
		
	}
	
</script>
</body>
</html>