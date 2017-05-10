<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>有效会员</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
		
		function sendMsg(id){
			if(id!=''){
			}else{
				var obj=document.getElementsByName('memberId');
				for(var i=0; i<obj.length; i++){
				if(obj[i].checked) id+=obj[i].value+',';
				} 
			}
			if(id==''){
				alert('请选择需要发送的客户');
			}else{
				var msgtip = prompt("请输入需要发送的内容:","");
				if (msgtip != null){
					if(msgtip==''){
						alert("请输入信息内容！");
					}else{
						alert("将@" + msgtip + "@发送给了@"+id);	
				         $.ajax({
				             type: "post",
				             url: "http://localhost:8080/daikin/a/daikin/dkMember/sendMsg",
				             data: {
				            	 	"ids":id, 
				            	 	"msg":msgtip
				            	 },
				             dataType: "json",
				             success: function(data){
								alert("已发送");
				         	},
				         	error: function(XMLHttpRequest, textStatus, errorThrown){
				         		alert(XMLHttpRequest);
				         		alert(textStatus);
				                alert(errorThrown);
				             }
				         });
					}
				}else{
					
				}
			}
			
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>会员列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dkMember" action="${ctx}/daikin/dkMember/effective/" method="post" class="form-inline">
		<div class="form-group">
		    <span>有效时间：</span>
				<input id="beginTime" name="beginTime" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value='${beginTime}' /> - 
				<input id="endTime" name="endTime" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value='${endTime}' />
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		<div style="display: none;">
			<shiro:hasPermission name="daikin:dkMember:del" >
				<table:delRow url="" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
		</div>
		   <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sendMsg('')" title="发送短信"><i class="glyphicon glyphicon-repeat"></i> 发送短信</button>
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
				<th> <input type="checkbox" class="i-checks" ></th>
				<th  class="sort-column name">姓名</th>
				<th  class="sort-column mobile">手机号</th>
				<th  class="sort-column address">联系地址</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${efMembers}" var="efMember">
			<tr>
				<td> <input type="checkbox" id="${efMember.id}" class="i-checks" name="memberId" value= "${efMember.id}" ></td>
				<td>
					${efMember.name}
				</td>
				<td>
					${efMember.mobile}
				</td>
				<td>
					${efMember.address}
				</td>
				<td>
					<a onclick="sendMsg('${efMember.id}')"   class="btn btn-success btn-xs"><i class="fa fa-search-plus"></i> 发送短信</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>