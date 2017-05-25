<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>审核记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="">
    <div class="ibox-content">
    
    <form:form id="searchForm" modelAttribute="dkAuditRecord" action="${ctx}/daikin/dkAuditRecord/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="recordId" name="recordId" type="hidden" value="${dkAuditRecord.recordId}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>	
	</form:form>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="sort-column reviewStatus">审核状态</th>
				<th  class="sort-column tuser.name">审核者</th>
				<th  class="sort-column remark">审核意见</th>
				<th  class="sort-column updateDate">update_date</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkAuditRecord">
			<tr>
				<td>
					${fns:getDictLabel(dkAuditRecord.reviewStatus, 'review_status', '')}
				</td>
				<td>
					${dkAuditRecord.tuser.name}
				</td>
				<td>
					${dkAuditRecord.remark}
				</td>
				<td>
					<fmt:formatDate value="${dkAuditRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
</body>
</html>