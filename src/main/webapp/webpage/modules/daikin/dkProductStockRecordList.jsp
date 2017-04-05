<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品进销存管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginOperateTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endOperateTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>商品进销存列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
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
	<form:form id="searchForm" modelAttribute="dkProductStockRecord" action="${ctx}/daikin/dkProductStockRecord/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>商品ID：</span>
				<sys:gridselect url="${ctx}/daikin/dkProductStockRecord/selectdkProduct" id="dkProduct" name="dkProduct"  value="${dkProductStockRecord.dkProduct.id}"  title="选择商品ID" labelName="dkProduct.name" 
					labelValue="${dkProductStockRecord.dkProduct.name}" cssClass="form-control required" fieldLabels="名称|型号" fieldKeys="name|model" searchLabel="商品名称" searchKey="name" ></sys:gridselect>
			<span>标识(0-入库 1-出库)：</span>
				<form:select path="flag"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('stock_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span>操作时间：</span>
				<input id="beginOperateTime" name="beginOperateTime" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkProductStockRecord.beginOperateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="endOperateTime" name="endOperateTime" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkProductStockRecord.endOperateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>合同号：</span>
				<form:input path="contractNum" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="daikin:dkProductStockRecord:add">
				<table:addRow url="${ctx}/daikin/dkProductStockRecord/form" title="商品进销存"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkProductStockRecord:edit">
			    <table:editRow url="${ctx}/daikin/dkProductStockRecord/form" title="商品进销存" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkProductStockRecord:del">
				<table:delRow url="${ctx}/daikin/dkProductStockRecord/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkProductStockRecord:import">
				<table:importExcel url="${ctx}/daikin/dkProductStockRecord/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkProductStockRecord:export">
	       		<table:exportExcel url="${ctx}/daikin/dkProductStockRecord/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column dkProduct.id">商品ID</th>
				<th  class="sort-column flag">标识(0-入库 1-出库)</th>
				<th  class="sort-column operateTime">操作时间</th>
				<th  class="sort-column amount">数量</th>
				<th  class="sort-column tuser.name">操作者</th>
				<th  class="sort-column stockAmount">库存数量</th>
				<th  class="sort-column contractNum">合同号</th>
				<th  class="sort-column remark">备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkProductStockRecord">
			<tr>
				<td> <input type="checkbox" id="${dkProductStockRecord.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看商品进销存', '${ctx}/daikin/dkProductStockRecord/form?id=${dkProductStockRecord.id}','800px', '500px')">
					${dkProductStockRecord.dkProduct.name}
				</a></td>
				<td>
					${fns:getDictLabel(dkProductStockRecord.flag, 'stock_flag', '')}
				</td>
				<td>
					<fmt:formatDate value="${dkProductStockRecord.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${dkProductStockRecord.amount}
				</td>
				<td>
					${dkProductStockRecord.tuser.name}
				</td>
				<td>
					${dkProductStockRecord.stockAmount}
				</td>
				<td>
					${dkProductStockRecord.contractNum}
				</td>
				<td>
					${dkProductStockRecord.remark}
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkProductStockRecord:view">
						<a href="#" onclick="openDialogView('查看商品进销存', '${ctx}/daikin/dkProductStockRecord/form?id=${dkProductStockRecord.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkProductStockRecord:edit">
    					<a href="#" onclick="openDialog('修改商品进销存', '${ctx}/daikin/dkProductStockRecord/form?id=${dkProductStockRecord.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkProductStockRecord:del">
						<a href="${ctx}/daikin/dkProductStockRecord/delete?id=${dkProductStockRecord.id}" onclick="return confirmx('确认要删除该商品进销存吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
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
</body>
</html>