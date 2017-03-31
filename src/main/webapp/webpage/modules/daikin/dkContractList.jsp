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
			<span>报价单ID：</span>
				<sys:gridselect url="${ctx}/daikin/dkContract/selectdkQuotation" id="dkQuotation" name="dkQuotation"  value="${dkContract.dkQuotation.id}"  title="选择报价单ID" labelName="dkQuotation.name" 
					labelValue="${dkContract.dkQuotation.name}" cssClass="form-control required" fieldLabels="名称|姓名|联系方式|地址" fieldKeys="name|memberName|mobile|address" searchLabel="报价单名称" searchKey="name" ></sys:gridselect>
			<span>合同号：</span>
				<form:input path="contractNumber" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>联系方式：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>会员ID：</span>
				<sys:gridselect url="${ctx}/daikin/dkContract/selectdkMember" id="dkMember" name="dkMember"  value="${dkContract.dkMember.id}"  title="选择会员ID" labelName="dkMember.name" 
					labelValue="${dkContract.dkMember.name}" cssClass="form-control required" fieldLabels="姓名|联系方式|联系地址" fieldKeys="name|mobile|address" searchLabel="姓名" searchKey="name" ></sys:gridselect>
			<span>安装人员：</span>
				<sys:treeselect id="iuser" name="iuser.id" value="${dkContract.iuser.id}" labelName="iuser.name" labelValue="${dkContract.iuser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>销售人员：</span>
				<sys:treeselect id="suser" name="suser.id" value="${dkContract.suser.id}" labelName="suser.name" labelValue="${dkContract.suser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>合同类型：</span>
				<form:select path="productType"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span>审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）：</span>
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
				<th  class="sort-column dkQuotation.id">报价单ID</th>
				<th  class="sort-column contractNumber">合同号</th>
				<th  class="sort-column memberName">顾客名称</th>
				<th  class="sort-column mobile">联系方式</th>
				<th  class="sort-column address">联系地址</th>
				<th  class="sort-column dkMember.id">会员ID</th>
				<th  class="sort-column totalFee">合同总金额</th>
				<th  class="sort-column connectionRatio">连接率</th>
				<th  class="sort-column iuser.name">安装人员</th>
				<th  class="sort-column suser.name">销售人员</th>
				<th  class="sort-column productType">合同类型</th>
				<th  class="sort-column reviewStatus">审核状态（0-未提交 1-待审核  2-审核不通过 9-审核通过）</th>
				<th  class="sort-column ruser.name">审核者</th>
				<th  class="sort-column reviewTime">审核日期</th>
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
					${dkContract.dkQuotation.name}
				</td>
				<td>
					${dkContract.contractNumber}
				</td>
				<td>
					${dkContract.memberName}
				</td>
				<td>
					${dkContract.mobile}
				</td>
				<td>
					${dkContract.address}
				</td>
				<td>
					${dkContract.dkMember.name}
				</td>
				<td>
					${dkContract.totalFee}
				</td>
				<td>
					${dkContract.connectionRatio}
				</td>
				<td>
					${dkContract.iuser.name}
				</td>
				<td>
					${dkContract.suser.name}
				</td>
				<td>
					${fns:getDictLabel(dkContract.productType, 'product_type', '')}
				</td>
				<td>
					${fns:getDictLabel(dkContract.reviewStatus, 'review_status', '')}
				</td>
				<td>
					${dkContract.ruser.name}
				</td>
				<td>
					${dkContract.reviewTime}
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