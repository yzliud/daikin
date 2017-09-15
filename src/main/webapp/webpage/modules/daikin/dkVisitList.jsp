<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginVisitDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endVisitDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>记录列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkVisit" action="${ctx}/daikin/dkVisit/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>手机号：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="32"  class=" form-control input-sm"/>
			<span>联系地址：</span>
				<form:input path="address" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>姓名：</span>
				<form:input path="name" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>录入者：</span>
				<sys:treeselect id="recordBy" name="recordBy.id" value="${dkVisit.recordBy.id}" labelName="recordBy.name" labelValue="${dkVisit.recordBy.name}"
					title="录入者" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>上门时间：</span>
				<input id="beginVisitDate" name="beginVisitDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkVisit.beginVisitDate}" pattern="yyyy-MM-dd"/>"/> - 
				<input id="endVisitDate" name="endVisitDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkVisit.endVisitDate}" pattern="yyyy-MM-dd"/>"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="daikin:dkVisit:add">
				<table:addRow url="${ctx}/daikin/dkVisit/form" title="记录"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkVisit:edit">
			    <table:editRow url="${ctx}/daikin/dkVisit/form" title="记录" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkVisit:del">
				<table:delRow url="${ctx}/daikin/dkVisit/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkVisit:import">
				<table:importExcel url="${ctx}/daikin/dkVisit/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkVisit:export">
	       		<table:exportExcel url="${ctx}/daikin/dkVisit/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column address">联系地址</th>
				<th  class="sort-column name">姓名</th>
				<th  class="sort-column recordBy.name">录入者</th>
				<th  class="sort-column content">咨询内容</th>
				<th  class="sort-column visitDate">上门时间</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkVisit">
			<tr>
				<td> <input type="checkbox" id="${dkVisit.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看记录', '${ctx}/daikin/dkVisit/form?id=${dkVisit.id}','800px', '500px')">
					${dkVisit.mobile}
				</a></td>
				<td>
					${dkVisit.address}
				</td>
				<td>
					${dkVisit.name}
				</td>
				<td>
					${dkVisit.recordBy.name}
				</td>
				<td>
					${dkVisit.content}
				</td>
				<td>
					<fmt:formatDate value="${dkVisit.visitDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${dkVisit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkVisit:view">
						<a href="#" onclick="openDialogView('查看记录', '${ctx}/daikin/dkVisit/form?id=${dkVisit.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkVisit:edit">
    					<a href="#" onclick="openDialog('修改记录', '${ctx}/daikin/dkVisit/form?id=${dkVisit.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkVisit:del">
						<a href="${ctx}/daikin/dkVisit/delete?id=${dkVisit.id}" onclick="return confirmx('确认要删除该记录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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