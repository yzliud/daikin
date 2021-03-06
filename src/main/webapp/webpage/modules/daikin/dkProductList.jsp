<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
		<h5>商品列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkProduct" action="${ctx}/daikin/dkProduct/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>商品类型：</span>
				<form:select path="productType"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span>分类：</span>
				<form:select path="classifyId"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('classify_id')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span>名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>规格：</span>
				<form:input path="model" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>品牌：</span>
				<form:select path="brandId"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('brand_id')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="daikin:dkProduct:add">
				<table:addRow url="${ctx}/daikin/dkProduct/form" title="商品"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkProduct:edit">
			    <table:editRow url="${ctx}/daikin/dkProduct/form" title="商品" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkProduct:del">
				<table:delRow url="${ctx}/daikin/dkProduct/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkProduct:import">
				<table:importExcel url="${ctx}/daikin/dkProduct/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkProduct:export">
	       		<table:exportExcel url="${ctx}/daikin/dkProduct/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column productType">商品类型</th>
				<th  class="sort-column brandId">品牌</th>
				<th  class="sort-column classifyId">分类</th>
				<th  class="sort-column name">名称</th>
				<th  class="sort-column model">规格</th>
				<th  class="sort-column price">单价</th>
				<th  class="sort-column costPrice">成本价</th>
				<th  class="sort-column stock">库存</th>
				<th  class="sort-column power">功率</th>
				<th  class="sort-column place">产地</th>
				<th  class="sort-column unit">单位</th>
				<th  class="sort-column capacityModel">室内机容量型号</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkProduct">
			<tr>
				<td> <input type="checkbox" id="${dkProduct.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看商品', '${ctx}/daikin/dkProduct/form?id=${dkProduct.id}','800px', '500px')">
					${fns:getDictLabel(dkProduct.productType, 'product_type', '')}
				</a></td>
				<td>
					${fns:getDictLabel(dkProduct.brandId, 'brand_id', '')}
				</td>
				<td>
					${fns:getDictLabel(dkProduct.classifyId, 'classify_id', '')}
				</td>
				<td>
					${dkProduct.name}
				</td>
				<td>
					${dkProduct.model}
				</td>
				<td>
					${dkProduct.price}
				</td>
				<td>
					${dkProduct.costPrice}
				</td>
				<td>
					${dkProduct.stock}
				</td>
				<td>
					${dkProduct.power}
				</td>
				<td>
					${dkProduct.place}
				</td>
				<td>
					${dkProduct.unit}
				</td>
				<td>
					${dkProduct.capacityModel}
				</td>
				<td>
					<fmt:formatDate value="${dkProduct.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkProduct:view">
						<a href="#" onclick="openDialogView('查看商品', '${ctx}/daikin/dkProduct/form?id=${dkProduct.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkProduct:edit">
    					<a href="#" onclick="openDialog('修改商品', '${ctx}/daikin/dkProduct/form?id=${dkProduct.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkProduct:del">
						<a href="${ctx}/daikin/dkProduct/delete?id=${dkProduct.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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