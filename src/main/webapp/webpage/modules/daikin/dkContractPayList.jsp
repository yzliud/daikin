<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同到款管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginPayDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endPayDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>合同到款列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkContractPay" action="${ctx}/daikin/dkContractPay/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>合同：</span>
				<sys:gridselect url="${ctx}/daikin/dkContractPay/selectdkContract" id="dkContract" name="dkContract"  value="${dkContractPay.dkContract.id}"  title="选择合同ID" labelName="dkContract.name" 
					labelValue="${dkContractPay.dkContract.name}" cssClass="form-control required" fieldLabels="合同名称|合同号|客户|合同金额" fieldKeys="name|contractNumber|memberName|totalFee" searchLabel="合同名称" searchKey="name" ></sys:gridselect>
			<span>支付时间：</span>
				<input id="beginPayDate" name="beginPayDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkContractPay.beginPayDate}" pattern="yyyy-MM-dd"/>"/> - 
				<input id="endPayDate" name="endPayDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkContractPay.endPayDate}" pattern="yyyy-MM-dd"/>"/>
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
			<shiro:hasPermission name="daikin:dkContractPay:add">
				<table:addRow url="${ctx}/daikin/dkContractPay/form" title="合同到款"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractPay:import">
				<table:importExcel url="${ctx}/daikin/dkContractPay/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkContractPay:export">
	       		<table:exportExcel url="${ctx}/daikin/dkContractPay/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column payDate">支付时间</th>
				<th  class="sort-column payFee">支付金额</th>
				<th  class="sort-column remark">支付方式</th>
				<th  class="sort-column reviewStatus">审核状态</th>
				<th  class="sort-column reviewBy">审核者</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkContractPay">
			<tr>
				<td> <input type="checkbox" id="${dkContractPay.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看合同到款', '${ctx}/daikin/dkContractPay/form?id=${dkContractPay.id}','800px', '500px')">
					${dkContractPay.dkContract.name}
				</a></td>
				<td>
					<fmt:formatDate value="${dkContractPay.payDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${dkContractPay.payFee}
				</td>
				<td>
					${dkContractPay.remark}
				</td>
				<td>
					${fns:getDictLabel(dkContractPay.reviewStatus, 'review_status', '')}
				</td>
				<td>
					${dkContractPay.reviewName}
				</td>
				<td>
					<fmt:formatDate value="${dkContractPay.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkContractPay:view">
						<a href="#" onclick="openDialogView('查看合同到款', '${ctx}/daikin/dkContractPay/detail?id=${dkContractPay.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${dkContractPay.reviewStatus == '0' || dkContractPay.reviewStatus == '1' }">
					<shiro:hasPermission name="daikin:dkContractPay:edit">
    					<a href="#" onclick="openDialog('修改合同到款', '${ctx}/daikin/dkContractPay/form?id=${dkContractPay.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkContractPay:del">
    					<c:if test="${dkContractPay.isReview != '1' }">
							<a href="${ctx}/daikin/dkContractPay/delete?id=${dkContractPay.id}" onclick="return confirmx('确认要删除该合同到款吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
						</c:if>
					</shiro:hasPermission>
					</c:if>
					<c:if test="${ dkContractPay.isReview == 1}">
						<a href="#" onclick="openDialogView('查看审核记录', '${ctx}/daikin/dkAuditRecord/list?recordId=${dkContractPay.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 审核记录</a>
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