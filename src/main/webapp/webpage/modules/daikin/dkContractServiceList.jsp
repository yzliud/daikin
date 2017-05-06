<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>维保记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginServiceDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endServiceDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>维保记录列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkContractService" action="${ctx}/daikin/dkContractService/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>合同：</span>
				<sys:gridselect url="${ctx}/daikin/dkContractService/selectdkContract" id="dkContract" name="dkContract"  value="${dkContractService.dkContract.id}"  title="选择合同" labelName="dkContract.name" 
					labelValue="${dkContractService.dkContract.name}" cssClass="form-control required" fieldLabels="合同名称|合同号|客户|合同金额" fieldKeys="name|contractNumber|memberName|totalFee" searchLabel="合同名称" searchKey="name" ></sys:gridselect>
			<span>维保时间：</span>
				<input id="beginServiceDate" name="beginServiceDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkContractService.beginServiceDate}" pattern="yyyy-MM-dd"/>"/> - 
				<input id="endServiceDate" name="endServiceDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkContractService.endServiceDate}" pattern="yyyy-MM-dd"/>"/>
			<span>维保人员：</span>
				<form:input path="servicePerson" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
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
			<shiro:hasPermission name="daikin:dkContractService:add">
				<table:addRow url="${ctx}/daikin/dkContractService/form" title="维保记录"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractService:import">
				<table:importExcel url="${ctx}/daikin/dkContractService/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractService:export">
	       		<table:exportExcel url="${ctx}/daikin/dkContractService/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column dkContract.id">合同ID</th>
				<th  class="sort-column serviceDate">维保时间</th>
				<th  class="sort-column servicePerson">维保人员</th>
				<th  class="sort-column reviewStatus">审核状态</th>
				<th  class="sort-column reviewBy">审核者</th>
				<th  class="sort-column serviceContent">维保记录</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkContractService">
			<tr>
				<td><a  href="#" onclick="openDialogView('查看维保记录', '${ctx}/daikin/dkContractService/form?id=${dkContractService.id}','800px', '500px')">
					${dkContractService.dkContract.name}
				</a></td>
				<td>
					<fmt:formatDate value="${dkContractService.serviceDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${dkContractService.servicePerson}
				</td>
				<td>
					${fns:getDictLabel(dkContractService.reviewStatus, 'review_status', '')}
				</td>
				<td>
					${dkContractService.reviewBy}
				</td>
				<td>
					${dkContractService.serviceContent}
				</td>
				<td>
					<fmt:formatDate value="${dkContractService.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkContractService:view">
						<a href="#" onclick="openDialogView('查看维保记录', '${ctx}/daikin/dkContractService/form?id=${dkContractService.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${dkContractService.reviewStatus == '0' || dkContractService.reviewStatus == '2' }">
					<shiro:hasPermission name="daikin:dkContractService:edit">
    					<a href="#" onclick="openDialog('修改维保记录', '${ctx}/daikin/dkContractService/form?id=${dkContractService.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<c:if test="${dkContractService.isReview != '1' }">
    				<shiro:hasPermission name="daikin:dkContractService:del">
						<a href="${ctx}/daikin/dkContractService/delete?id=${dkContractService.id}" onclick="return confirmx('确认要删除该维保记录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					</c:if>
					</c:if>
					<c:if test="${ dkContractService.isReview == 1}">
						<a href="#" onclick="openDialogView('查看审核记录', '${ctx}/daikin/dkAuditRecord/list?recordId=${dkContractService.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 审核记录</a>
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
</body>
</html>