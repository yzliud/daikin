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
		<h5>待审核到款 记录</h5>
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
	<form:form id="searchForm" modelAttribute="dkContractPay" action="${ctx}/daikin/dkContractPay/uncheck" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="reviewStatus" value="1"/>
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
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
	       <table:exportExcel url="${ctx}/daikin/dkContractPay/export"></table:exportExcel><!-- 导出按钮 -->
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
				<td>
					${dkContractPay.dkContract.name}
				</td>
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
					<c:if test="${dkContractPay.reviewStatus == '1' }">
						<a href="#" onclick="openDialog_check('审核合同到款', '${ctx}/daikin/dkContractPay/detail?id=${dkContractPay.id}&checkType=1','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 审核</a>
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
<script type="text/javascript">
	
	//打开对话框(添加修改)
	function openDialog_check(title,url,width,height,target){
		
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
		    btn: ['通过', '驳回', '关闭'],
		    btn1: function(index, layero){
		    	 
		    	 var body = top.layer.getChildFrame('body', index);
		         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         body.find('#reviewStatus').val("9");
		         var inputForm = body.find('#inputForm');
		         var top_iframe;
		         if(target){
		        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
		         }else{
		        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
		         }
		         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
		        if(iframeWin.contentWindow.doSubmit() ){
		        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
		        	  return true;
		        }else{
		        	 return false;
		         }
				
			  },
			  btn2: function(index, layero){
			    	 
			    	 var body = top.layer.getChildFrame('body', index);
			         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			         body.find('#reviewStatus').val("2");
			         var inputForm = body.find('#inputForm');
			         var top_iframe;
			         if(target){
			        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
			         }else{
			        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
			         }
			         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
			        if(iframeWin.contentWindow.doSubmit() ){
			        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
			        	  return true;
			        }else{
			        	 return false;
			         }
					
				  },
			  cancel: function(index, layero){ 
		    	   
		       },
		       
		}); 	
		
	}
	
</script>
</body>
</html>