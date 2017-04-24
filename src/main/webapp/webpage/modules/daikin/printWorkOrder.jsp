<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理</title>
	<meta name="decorator" content="default"/>
	
	<!--<script type="text/javascript" src="${ctxStatic}/daikin/jquery.jqprint-0.3.js"></script> 调打印-->
	<!--<script src="http://code.jquery.com/jquery-migrate-1.1.0.js"></script>-->
	<!--<script type="text/javascript" src="${ctxStatic}/daikin/FileSaver.js"></script>-->
	<!--<script type="text/javascript" src="${ctxStatic}/daikin/jquery.wordexport.js"></script>-->
	
</head>
<body class="hideScroll">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" id="tb_doc_m">
		   <tbody>
		   		<tr>
		   		<td class="width-100" colspan=4 align="center">南京舜举楼宇设备有限公司</td>
		   		</tr>
		   		<tr>
		   		<td class="width-100" colspan=4 align="center">售后服务派工单</td>
		   		</tr>
				<tr>
					<td class="width-20 active"><label class="pull-right">派工单编号：</label></td>
					<td class="width-30">
						
					</td>
					<td class="width-20 active"><label class="pull-right">派工单类型：</label></td>
					<td class="width-30">
						安装
					</td>
				</tr>
				<tr>
					<td class="width-20 active"><label class="pull-right">服务人员：</label></td>
					<td class="width-30">
						${dkContract.installUser.name}
					</td>
					<td class="width-20 active"><label class="pull-right">派工时间：</label></td>
					<td class="width-30">
						
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">客户姓名：</label></td>
					<td class="width-35">
						${dkContract.memberName}
					</td>
					<td class="width-15 active"><label class="pull-right">联系方式：</label></td>
					<td class="width-35">
						${dkContract.mobile}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">客户地址：</label></td>
					<td class="width-35" colspan=3>
						${dkContract.address}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">产品名称：</label></td>
					<td class="width-35" colspan=3>
						${dkContract.name}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">产品清单：</label></td>
					<td class="width-35" colspan=2>
						<c:forEach items="${dkContract.dkContractProductList}" var="dcpLs">
							${dcpLs.name}*${dcpLs.amount}<br>
						</c:forEach>
					</td>
					<td class="width-35" colspan=3>
						销售：${dkContract.saleUser.name}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">工程监理：</label></td>
					<td class="width-35">
						
					</td>
					<td class="width-15 active"><label class="pull-right">客户签字：</label></td>
					<td class="width-35">
						
					</td>
				</tr>
		 	</tbody>
		</table>
<script language="javascript">
    function  printDiv(){
        $("#printDiv").jqprint();
    }
    function  wordDiv(){
        //$("#printDiv").wordExport();
    	window.location="${projectName}/down/${dkContract.name}派工单.doc"; 
    }
</script>
</body>
</html>