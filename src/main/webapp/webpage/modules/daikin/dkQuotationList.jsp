<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报价单管理</title>
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
		<h5>报价单列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkQuotation" action="${ctx}/daikin/dkQuotation/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>顾客名称：</span>
				<form:input path="memberName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>联系方式：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>商品类型：</span>
				<form:select path="productType"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select><br>
			<span>销售人员：</span>
				<sys:treeselect id="suser" name="suser.id" value="${dkContract.suser.id}" labelName="suser.name" labelValue="${dkContract.suser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
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
			<shiro:hasPermission name="daikin:dkQuotation:add">
				<table:addRow url="${ctx}/daikin/dkQuotation/form" title="报价单"></table:addRow> <!--增加按钮 -->
				
				<!--<button class="btn btn-white btn-sm"  onclick="choise();" title="添加"><i class="fa fa-plus"></i> ${label==null?'添加':label}</button>-->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkQuotation:import">
				<table:importExcel url="${ctx}/daikin/dkQuotation/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="daikin:dkQuotation:export">
	       		<table:exportExcel url="${ctx}/daikin/dkQuotation/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">名称</th>
				<th  class="sort-column memberName">顾客名称</th>
				<th  class="sort-column mobile">联系方式</th>
				<th  class="sort-column address">联系地址</th>
				<th  class="sort-column totalFee">金额</th>
				<th  class="sort-column productType">商品类型</th>
				<th  class="sort-column tuser.name">销售人员</th>
				<th  class="sort-column connectionRatio">连接率</th>
				<th  class="sort-column reviewStatus">审核状态</th>
				<th  class="sort-column ruser.name">审核者</th>
				<th  class="sort-column updateDate">update_date</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkQuotation">
			<tr>
				<!--  <td> <input type="checkbox" id="${dkQuotation.id}" class="i-checks"></td>-->
				<td><a  href="#" onclick="openDialogView('查看报价单', '${ctx}/daikin/dkQuotation/form?id=${dkQuotation.id}','800px', '500px')">
					${dkQuotation.name}
				</a></td>
				<td>
					${dkQuotation.memberName}
				</td>
				<td>
					${dkQuotation.mobile}
				</td>
				<td>
					${dkQuotation.address}
				</td>
				<td>
					${dkQuotation.totalFee}
				</td>
				<td>
					${fns:getDictLabel(dkQuotation.productType, 'product_type', '')}
				</td>
				<td>
					${dkQuotation.tuser.name}
				</td>
				<td>
					${dkQuotation.connectionRatio}
				</td>
				<td>
					${fns:getDictLabel(dkQuotation.reviewStatus, 'review_status', '')}
				</td>
				<td>
					${dkQuotation.ruser.name}
				</td>
				<td>
					<fmt:formatDate value="${dkQuotation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="daikin:dkQuotation:view">
						<a href="#" onclick="openDialogView('查看报价单', '${ctx}/daikin/dkQuotation/detail?id=${dkQuotation.id}','800px', '600px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="daikin:dkQuotation:edit">
						<c:if test="${dkQuotation.reviewStatus == '0' || dkQuotation.reviewStatus == '2'}">
    					<a href="#" onclick="openDialog('修改报价单', '${ctx}/daikin/dkQuotation/form?id=${dkQuotation.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    					</c:if>
    					<c:if test="${dkQuotation.reviewStatus == '0' }">
    					<a href="${ctx}/daikin/dkQuotation/updateReviewStatus?id=${dkQuotation.id}&reviewStatus=1" onclick="return confirmx('确定提交审核吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-edit"></i> 提交审核</a>
    					</c:if>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="daikin:dkQuotation:del">
    					<c:if test="${dkQuotation.reviewStatus == '0' && dkQuotation.isReview != 1}">
						<a href="${ctx}/daikin/dkQuotation/delete?id=${dkQuotation.id}" onclick="return confirmx('确认要删除该报价单吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
						</c:if>
					</shiro:hasPermission>
					<c:if test="${ dkQuotation.isReview == 1}">
					<a href="#" onclick="openDialogView('查看审核记录', '${ctx}/daikin/dkAuditRecord/list?recordId=${dkQuotation.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 审核记录</a>
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
<script type="text/javascript">
	function choise() {
	    //询问框
	    layer.confirm('确定提交审核？', {
	        btn: ['是','否'] //按钮
	    }, function(){
	        layer.msg('的确很重要', {icon: 1});
	        openDialog("新增报价单","${ctx}/daikin/dkQuotation/form?productType=1","${width==null?'800px':width}", "${height==null?'500px':height}","${target}");
	    }, function(){
	        layer.msg('也可以这样', {
	            time: 2000, //2s后自动关闭
	            btn: ['明白了', '知道了']
	        });
	        openDialog("新增报价单","${ctx}/daikin/dkQuotation/form?productType=2","${width==null?'800px':width}", "${height==null?'500px':height}","${target}");
	    });
	}
	
</script>
</body>

</html>