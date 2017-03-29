<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理</title>
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
		<h5>合同列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>合同号：</span>
				<form:input path="contractNumber" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>联系人手机号：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
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
				<table:addRow url="${ctx}/daikin/dkContract/form" title="合同"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContract:edit">
			    <table:editRow url="${ctx}/daikin/dkContract/form" title="合同" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContract:del">
				<table:delRow url="${ctx}/daikin/dkContract/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContract:import">
				<table:importExcel url="${ctx}/daikin/dkContract/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContract:export">
	       		<table:exportExcel url="${ctx}/daikin/dkContract/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">名称</th>
				<th  class="sort-column contractNumber">合同号</th>
				<th  class="sort-column mobile">联系人手机号</th>
				<th  class="sort-column orderDate">订购时间</th>
				<th  class="sort-column totalFee">合同总金额</th>
				<th  class="sort-column payFee">已支付金额</th>
				<th  class="sort-column contractStatus">状态(0-已签订；1-进行中；2-已完成)</th>
				<th  class="sort-column payStatus">支付状态(0-未支付；1-部分支付；2-已支付)</th>
				<th  class="sort-column installStatus">安装状态(0-未安装；1-进行中；2-已完成)</th>
				<th  class="sort-column dkInstallPerson.id">安装人员</th>
				<th  class="sort-column tuser.name">销售人员</th>
				<th  class="sort-column updateDate">update_date</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkContract">
			<tr>
				<td> <input type="checkbox" id="${dkContract.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看合同', '${ctx}/daikin/dkContract/form?id=${dkContract.id}','800px', '500px')">
					${dkContract.name}
				</a></td>
				<td>
					${dkContract.contractNumber}
				</td>
				<td>
					${dkContract.mobile}
				</td>
				<td>
					<fmt:formatDate value="${dkContract.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${dkContract.totalFee}
				</td>
				<td>
					${dkContract.payFee}
				</td>
				<td>
					${fns:getDictLabel(dkContract.contractStatus, 'contract_status', '')}
				</td>
				<td>
					${fns:getDictLabel(dkContract.payStatus, 'pay_status', '')}
				</td>
				<td>
					${fns:getDictLabel(dkContract.installStatus, 'install_status', '')}
				</td>
				<td>
					${dkContract.dkInstallPerson.name}
				</td>
				<td>
					${dkContract.tuser.name}
				</td>
				<td>
					<fmt:formatDate value="${dkContract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkContract:view">
						<a href="#" onclick="openDialogView('查看合同', '${ctx}/daikin/dkContract/form?id=${dkContract.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkContract:edit">
    					<a href="#" onclick="openDialog('修改合同', '${ctx}/daikin/dkContract/form?id=${dkContract.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkContract:del">
						<a href="${ctx}/daikin/dkContract/delete?id=${dkContract.id}" onclick="return confirmx('确认要删除该合同吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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