<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>合同商品列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkContractProduct" action="${ctx}/daikin/dkContractProduct/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>合同：</span>
				<form:input path="dkContract.id" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>商品：</span>
				<form:input path="dkProduct.id" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="daikin:dkContractProduct:add">
				<table:addRow url="${ctx}/daikin/dkContractProduct/form" title="合同商品"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractProduct:edit">
			    <table:editRow url="${ctx}/daikin/dkContractProduct/form" title="合同商品" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractProduct:del">
				<table:delRow url="${ctx}/daikin/dkContractProduct/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractProduct:import">
				<table:importExcel url="${ctx}/daikin/dkContractProduct/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractProduct:export">
	       		<table:exportExcel url="${ctx}/daikin/dkContractProduct/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column dkContract.id">合同</th>
				<th  class="sort-column dkProduct.id">商品</th>
				<th  class="sort-column productName">商品名称</th>
				<th  class="sort-column model">商品型号</th>
				<th  class="sort-column amount">商品数量</th>
				<th  class="sort-column unitFee">商品单价</th>
				<th  class="sort-column totalFee">商品总价</th>
				<th  class="sort-column place">安装位置</th>
				<th  class="sort-column area">需求面积</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkContractProduct">
			<tr>
				<td> <input type="checkbox" id="${dkContractProduct.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看合同商品', '${ctx}/daikin/dkContractProduct/form?id=${dkContractProduct.id}','800px', '500px')">
					${dkContractProduct.dkContract.id}
				</a></td>
				<td>
					${dkContractProduct.dkProduct.id}
				</td>
				<td>
					${dkContractProduct.productName}
				</td>
				<td>
					${dkContractProduct.model}
				</td>
				<td>
					${dkContractProduct.amount}
				</td>
				<td>
					${dkContractProduct.unitFee}
				</td>
				<td>
					${dkContractProduct.totalFee}
				</td>
				<td>
					${dkContractProduct.place}
				</td>
				<td>
					${dkContractProduct.area}
				</td>
				<td>
					<fmt:formatDate value="${dkContractProduct.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkContractProduct:view">
						<a href="#" onclick="openDialogView('查看合同商品', '${ctx}/daikin/dkContractProduct/form?id=${dkContractProduct.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkContractProduct:edit">
    					<a href="#" onclick="openDialog('修改合同商品', '${ctx}/daikin/dkContractProduct/form?id=${dkContractProduct.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkContractProduct:del">
						<a href="${ctx}/daikin/dkContractProduct/delete?id=${dkContractProduct.id}" onclick="return confirmx('确认要删除该合同商品吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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