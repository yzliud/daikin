<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
		<h5>会员列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
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
	<form:form id="searchForm" modelAttribute="dkMember" action="${ctx}/daikin/dkMember/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>姓名：</span>
				<form:input path="name" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>手机号：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>联系地址：</span>
				<form:input path="address" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="daikin:dkMember:add">
				<table:addRow url="${ctx}/daikin/dkMember/form" title="会员"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkMember:edit">
			    <table:editRow url="${ctx}/daikin/dkMember/form" title="会员" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkMember:del">
				<table:delRow url="${ctx}/daikin/dkMember/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkMember:import">
				<table:importExcel url="${ctx}/daikin/dkMember/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkMember:export">
	       		<table:exportExcel url="${ctx}/daikin/dkMember/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">姓名</th>
				<th  class="sort-column mobile">手机号</th>
				<th  class="sort-column address">联系地址</th>
				<th  class="sort-column remark">信息来源</th>
				<th  class="sort-column recordBy.name">录入者</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkMember">
			<tr>
				<td> <input type="checkbox" id="${dkMember.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看会员', '${ctx}/daikin/dkMember/form?id=${dkMember.id}','800px', '500px')">
					${dkMember.name}
				</a></td>
				<td>
					${dkMember.mobile}
				</td>
				<td>
					${dkMember.address}
				</td>
				<td>
					${dkMember.remark}
				</td>
				<td>
					${dkMember.recordBy.name}
				</td>
				<td>
					<fmt:formatDate value="${dkMember.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkMember:view">
						<a href="#" onclick="openDialogView('查看会员', '${ctx}/daikin/dkMember/form?id=${dkMember.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkMember:edit">
    					<a href="#" onclick="openDialog('修改会员', '${ctx}/daikin/dkMember/form?id=${dkMember.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkMember:del">
						<a href="${ctx}/daikin/dkMember/delete?id=${dkMember.id}" onclick="return confirmx('确认要删除该会员吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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