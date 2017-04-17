<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>安装进度管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginSubmitDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endSubmitDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>安装进度列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkContractSchedule" action="${ctx}/daikin/dkContractSchedule/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>合同：</span>
				<sys:gridselect url="${ctx}/daikin/dkContractSchedule/selectdkContract" id="dkContract" name="dkContract"  value="${dkContractSchedule.dkContract.id}"  title="选择合同" labelName="dkContract.name" 
					labelValue="${dkContractSchedule.dkContract.name}" cssClass="form-control required" fieldLabels="合同名称|合同号|顾客|合同金额" fieldKeys="name|contractNumber|memberName|totalFee" searchLabel="合同名称" searchKey="name" ></sys:gridselect>
			<span>提交日期：</span>
				<input id="beginSubmitDate" name="beginSubmitDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkContractSchedule.beginSubmitDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/> - 
				<input id="endSubmitDate" name="endSubmitDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkContractSchedule.endSubmitDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="daikin:dkContractSchedule:add">
				<table:addRow url="${ctx}/daikin/dkContractSchedule/form" title="安装进度"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractSchedule:edit">
			    <table:editRow url="${ctx}/daikin/dkContractSchedule/form" title="安装进度" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractSchedule:del">
				<table:delRow url="${ctx}/daikin/dkContractSchedule/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractSchedule:import">
				<table:importExcel url="${ctx}/daikin/dkContractSchedule/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractSchedule:export">
	       		<table:exportExcel url="${ctx}/daikin/dkContractSchedule/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column descript">进度描述</th>
				<th  class="sort-column submitDate">提交日期</th>
				<th  class="sort-column pic">上传图片</th>
				<th  class="sort-column percent">进度百分比</th>
				<th  class="sort-column updateDate">update_date</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkContractSchedule">
			<tr>
				<td> <input type="checkbox" id="${dkContractSchedule.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看安装进度', '${ctx}/daikin/dkContractSchedule/form?id=${dkContractSchedule.id}','800px', '500px')">
					${dkContractSchedule.dkContract.name}
				</a></td>
				<td>
					${dkContractSchedule.descript}
				</td>
				<td>
					<fmt:formatDate value="${dkContractSchedule.submitDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${dkContractSchedule.pic}
				</td>
				<td>
					${dkContractSchedule.percent}
				</td>
				<td>
					<fmt:formatDate value="${dkContractSchedule.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkContractSchedule:view">
						<a href="#" onclick="openDialogView('查看安装进度', '${ctx}/daikin/dkContractSchedule/form?id=${dkContractSchedule.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkContractSchedule:edit">
    					<a href="#" onclick="openDialog('修改安装进度', '${ctx}/daikin/dkContractSchedule/form?id=${dkContractSchedule.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkContractSchedule:del">
						<a href="${ctx}/daikin/dkContractSchedule/delete?id=${dkContractSchedule.id}" onclick="return confirmx('确认要删除该安装进度吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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