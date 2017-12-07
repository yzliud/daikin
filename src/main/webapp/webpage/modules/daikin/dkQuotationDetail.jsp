<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报价单管理</title>
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
<form:form id="inputForm" modelAttribute="dkQuotation" action="${ctx}/daikin/dkQuotation/checkQuotation" method="post" class="form-horizontal">
    <div class="wrapper wrapper-content animated fadeInRight">

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
                            <h4>报价单：</h4>
                            <h4 class="text-navy">${dkQuotation.name }</h4>
                            <address>
                                        <strong>${dkQuotation.memberName }</strong><br>
                                        ${dkQuotation.address }<br>
                                        <abbr title="Phone">联系方式：</abbr> ${dkQuotation.mobile }
                                    </address>
                            <p>
                                <span><strong>日期：</strong> ${dkQuotation.updateDate }</span>
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
									<th>销售价</th>
									<th>数量</th>
									<th>成本总价</th>
									<th>销售总价</th>
									<c:choose> 
									  <c:when test="${dkQuotation.productType == '0' }">   
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
                            	<c:forEach var="item" items="${dkQuotation.dkQuotationProductList}" varStatus="status">   
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
											  <c:when test="${dkQuotation.productType == '0' }">   
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
                                <td>&yen;${dkQuotation.totalFee }</td>
                            </tr>
                            <tr>
                                <td><strong>成本价：</strong>
                                </td>
                                <td>&yen;${dkQuotation.costFee }</td>
                            </tr>
                            <tr>
                                <td><strong>签单价：</strong>
                                </td>
                                <td>&yen;${dkQuotation.signFee }</td>
                            </tr>
                        </tbody>
                    </table>
                    <form:hidden path="id"/>
                    <form:hidden path="reviewStatus"/>
                    <div>备注： ${dkQuotation.remark } </div>
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