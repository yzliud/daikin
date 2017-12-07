<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>派工</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		});
	</script>
	<script src="${ctxStatic}/common/PrintArea.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>派工 </h5>
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
	<form:form id="searchForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/jobList" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="reviewStatus" value="9"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
			<span>合同号：</span>
				<form:input path="contractNumber" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
			<span>商品类型：</span>
				<form:select path="productType"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<br>
			<span>顾客名称：</span>
				<form:input path="memberName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>联系方式：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>地址：</span>
				<form:input path="address" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>签订时间：</span>
				<input id="beginDate" name="beginDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkContract.beginDate}" pattern="yyyy-MM-dd"/>"/> - 
				<input id="endDate" name="endDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${dkContract.endDate}" pattern="yyyy-MM-dd"/>"/>
			<span>销售人员：</span>
				<sys:treeselect id="saleUser" name="saleUser.id" value="${dkContract.saleUser.id}" labelName="saleUser.name" labelValue="${dkContract.saleUser.name}"
					title="销售人员" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" />
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">

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
				<th  class="sort-column contractNumber">合同号</th>
				<th  class="sort-column memberName">顾客名称</th>
				<th  class="sort-column mobile">联系方式</th>
				<th  class="sort-column address">地址</th>
				<th  class="sort-column signFee">签单价</th>
				<th  class="sort-column saleUser.name">销售人员</th>
				<th  class="sort-column installUser.name">安装人员</th>
				<th  class="sort-column supervisionUser.name">工程监理</th>
				<th  class="sort-column productType">商品类型</th>
				<th  class="sort-column reviewTime">签订日期</th>
				<th  class="sort-column reviewStatus">审核状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkContract">
			<tr>
				<td>
					${dkContract.name}
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
					${dkContract.signFee}
				</td>
				<td>
					${dkContract.saleUser.name}
				</td>
				<td>
					${dkContract.installUser.name}
				</td>
				<td>
					${dkContract.supervisionUser.name}
				</td>
				<td>
					${fns:getDictLabel(dkContract.productType, 'product_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${dkContract.reviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(dkContract.reviewStatus, 'review_status', '')}
				</td>
				<td>				
					<a href="#" onclick="openDialog('设置安装人员', '${ctx}/daikin/dkContract/forwardAssign?id=${dkContract.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 安装人员</a>
					<a href="#" onclick="openDialog_print('派工单', '${ctx}/daikin/dkContract/forwardWorkOrder?id=${dkContract.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-print"></i> 下载派工单</a>
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
	
	//打开对话框(添加修改)
	function openDialog_print(title,url,width,height,target){
		
		if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
			width='auto';
			height='auto';
		}else{//如果是PC端，根据用户设置的width和height显示。
		
		}
		
		top.layer.open({
		    type: 2,  
		    area: [width, height],
		    title: title,
	        maxmin: true, //开启最大化最小化按钮
		    content: url ,
		    btn: ['下载', '关闭'],
		    btn1: function(index, layero){
		    	 
		    	 var body = top.layer.getChildFrame('body', index);
		         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         iframeWin.contentWindow.wordDiv();
				
			  },
			  cancel: function(index, layero){ 
		    	   
		       },
		       
		}); 	
		
	}
	
</script>
</body>
</html>