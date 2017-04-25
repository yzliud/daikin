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
		<h5>待审核报价单</h5>
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
	<form:form id="searchForm" modelAttribute="dkQuotation" action="${ctx}/daikin/dkQuotation/uncheck" method="post" class="form-inline">
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
			<form:hidden path="reviewStatus" value="1"/>
			<span>销售人员：</span>
				<sys:treeselect id="suser" name="suser.id" value="${dkContract.suser.id}" labelName="suser.name" labelValue="${dkContract.suser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
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
				<th  class="sort-column updateDate">update_date</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dkQuotation">
			<tr>
				<!--  <td> <input type="checkbox" id="${dkQuotation.id}" class="i-checks"></td>-->
				<td>
					${dkQuotation.name}
				</td>
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
					<fmt:formatDate value="${dkQuotation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
    					<a href="#" onclick="openDialog_uncheck('审核报价单', '${ctx}/daikin/dkQuotation/detail?id=${dkQuotation.id}&checkType=1','800px', '600px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 审核</a>
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
	
	//打开对话框(添加修改)
	function openDialog_uncheck(title,url,width,height,target){
		
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
		    btn: ['通过','驳回', '关闭'],
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
			        	// top.layer.close(index);//关闭对话框。
			        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
			        	  return true;
			         }else{
			        	 return false;
			         }
		       },
		       btn3: function(index, layero){ 
		    	   
		       },
		       
		}); 	
		
	}
	
</script>
</body>

</html>