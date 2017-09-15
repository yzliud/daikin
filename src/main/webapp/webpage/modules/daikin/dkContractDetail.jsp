<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同明细</title>
	<meta name="decorator" content="default"/>
<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="gray-bg">
<form:form id="inputForm" modelAttribute="dkContract" action="${ctx}/daikin/dkContract/reviewContract" method="post" class="form-horizontal">
    <div class="wrapper wrapper-content animated fadeInRight" id="printDiv">

        <div class="row">
            <div class="col-sm-12">
                <div class="ibox-content p-xl">
                    <div class="row">
                        <div class="col-sm-6">
                            <address>
                                        <strong>南京舜举楼宇设备有限公司</strong><br>
                                        		江苏省南京市雨花南路51-6号大金空调旗舰店<br>
                                        <abbr title="Phone">总机：</abbr> 025-51800052
                                    </address>
                        </div>

                        <div class="col-sm-6 text-right">
                            <h4>合同：</h4>
                            <h4 class="text-navy">${dkContract.name }(${dkContract.contractNumber })</h4><br>
                            <address>
                                <strong>${dkContract.memberName }</strong><br>
                                ${dkContract.address }<br>
                                <abbr title="Phone">联系方式：</abbr> ${dkContract.mobile }<br>
                                <abbr title="Phone">销售人员：</abbr> ${dkContract.saleUser.name }<br>
                                <abbr title="Phone">监理人员：</abbr> ${dkContract.supervisionUser.name }<br>
                                <abbr title="Phone">安装人员：</abbr> ${dkContract.installUser.name }
                            </address>
                            <p>
                                <span><strong>日期：</strong> ${dkContract.reviewTime }</span>
                            </p>
                        </div>
                    </div>

                    <div class="table-responsive m-t">
                        <table class="table invoice-table">
                            <thead>
                                <tr>
                                    <th>商品</th>
                                    <th>规格</th>
                                    <th>成本价</th>
									<th>单价</th>
									<th>数量</th>
									<th>成本总价</th>
									<th>销售总价</th>
									<c:choose> 
									  <c:when test="${dkContract.productType == '0' }">   
									    <th>功率</th>
										<th>楼层</th>
										<th>位置</th>
										<th>需求面积</th>
										<th>室内机容量型号</th>
									  </c:when> 
									  <c:otherwise>   
									    <th>产地</th>
										<th>品牌</th>
										<th>单位</th>
									  </c:otherwise> 
									</c:choose> 
									<th>描述</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach var="item" items="${dkContract.dkContractProductList}" varStatus="status">   
								      <tr>
									      <td>
	                                        <div><strong>${item.name }</strong>
	                                        </div>
	                                      </td>  
	                                      <td>${item.model }</td>
	                                      <td>&yen;${item.costPrice }</td>
	                                      <td>&yen;${item.price }</td>
	                                      <td>${item.amount }</td>
	                                      <td>&yen;${item.totalCostPrice }</td>
	                                      <td>&yen;${item.totalPrice }</td>
	                                      <c:choose> 
											  <c:when test="${dkContract.productType == '0' }">   
											      <td>${item.power }</td>
			                                      <td>${fns:getDictLabel(item.floor,'floor','')}</td>
			                                      <td>${item.position }</td>
			                                      <td>${item.demandArea }</td>
			                                      <td>
						                            <c:if test="${item.capacityModel != null }">
														<c:forTokens items="${item.capacityModel}" delims="," var="itemCapacityModel">
														   ${itemCapacityModel}<br>
														</c:forTokens>
													</c:if>
													</td>
											  </c:when> 
											  <c:otherwise>   
											      <td>${item.place }</td>
			                                      <td>${fns:getDictLabel(item.brandId, 'brand_id', '')}</td>
			                                      <td>${item.unit }</td>
											  </c:otherwise> 
											</c:choose> 
	                                      <td>${item.descript }</td>  
								      </tr>   
								</c:forEach> 

                          

                            </tbody>
                        </table>
                    </div>
                    <!-- /table-responsive -->

                    <table class="table invoice-total">
                        <tbody>
                            <tr>
                                <td><strong>销售价：</strong>
                                </td>
                                <td>&yen;${dkContract.contractFee }</td>
                            </tr>
                            <tr>
                                <td><strong>成本价：</strong>
                                </td>
                                <td>&yen;${dkContract.costFee }</td>
                            </tr>
                            <tr>
                                <td><strong>签单价：</strong>
                                </td>
                                <td>&yen;${dkContract.signFee }</td>
                            </tr>
                        </tbody>
                    </table>
                    <form:hidden path="id"/>
                    <form:hidden path="parent.id"/>
                    <form:hidden path="reviewStatus"/>
                    <c:if test="${checkType == '1' }">
					<div><strong>审核意见:</strong><input name="remark" id="remark"   value=""  class="form-control required"/>
                    </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    </form:form>
</body>
</html>