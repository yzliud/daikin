<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>安装人员管理</title>
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
		<h5>安装人员列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkInstallPerson" action="${ctx}/daikin/dkInstallPerson/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>手机号：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>姓名：</span>
				<form:input path="name" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="daikin:dkInstallPerson:add">
				<table:addRow url="${ctx}/daikin/dkInstallPerson/form" title="安装人员"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkInstallPerson:edit">
			    <table:editRow url="${ctx}/daikin/dkInstallPerson/form" title="安装人员" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkInstallPerson:del">
				<table:delRow url="${ctx}/daikin/dkInstallPerson/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkInstallPerson:import">
				<table:importExcel url="${ctx}/daikin/dkInstallPerson/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkInstallPerson:export">
	       		<table:exportExcel url="${ctx}/daikin/dkInstallPerson/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column mobile">手机号</th>
				<th  class="sort-column name">姓名</th>
				<th  class="sort-column nickName">昵称</th>
				<th  class="sort-column sex">性别</th>
				<th  class="sort-column country">国家</th>
				<th  class="sort-column province">省</th>
				<th  class="sort-column city">城市</th>
				<th  class="sort-column headImg">头像</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkInstallPerson">
			<tr>
				<td> <input type="checkbox" id="${dkInstallPerson.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看安装人员', '${ctx}/daikin/dkInstallPerson/form?id=${dkInstallPerson.id}','800px', '500px')">
					${dkInstallPerson.mobile}
				</a></td>
				<td>
					${dkInstallPerson.name}
				</td>
				<td>
					${dkInstallPerson.nickName}
				</td>
				<td>
					${fns:getDictLabel(dkInstallPerson.sex, 'sex', '')}
				</td>
				<td>
					${dkInstallPerson.country}
				</td>
				<td>
					${dkInstallPerson.province}
				</td>
				<td>
					${dkInstallPerson.city}
				</td>
				<td>
					${dkInstallPerson.headImg}
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkInstallPerson:view">
						<a href="#" onclick="openDialogView('查看安装人员', '${ctx}/daikin/dkInstallPerson/form?id=${dkInstallPerson.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkInstallPerson:edit">
    					<a href="#" onclick="openDialog('修改安装人员', '${ctx}/daikin/dkInstallPerson/form?id=${dkInstallPerson.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkInstallPerson:del">
						<a href="${ctx}/daikin/dkInstallPerson/delete?id=${dkInstallPerson.id}" onclick="return confirmx('确认要删除该安装人员吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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