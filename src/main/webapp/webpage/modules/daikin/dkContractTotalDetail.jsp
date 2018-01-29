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
<body class="gray-bg" style="overflow-x:hidden;">
<div class="row">
        <div class="col-sm-12">
            <div class="wrapper wrapper-content animated fadeInUp">
                <div class="ibox">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="m-b-md">
                                    <h2>${dkContract.name }</h2>
                                </div>
                                <dl class="dl-horizontal">
                                    <dt>状态：</dt>
                                    <dd><span class="label label-primary">进行中</span>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-5">
                                <dl class="dl-horizontal">

                               		<dt>合同号：</dt>
                                    <dd>${dkContract.contractNumber }</dd>
                                    <dt>客户：</dt>
                                    <dd>${dkContract.memberName }</dd>
                                    <dt>联系方式：</dt>
                                    <dd>${dkContract.mobile }</dd>
                                    <dt>联系地址：</dt>
                                    <dd>${dkContract.address }</dd>
                                 
                                </dl>
                            </div>
                            <div class="col-sm-7" id="cluster_info">
                                <dl class="dl-horizontal">

                                    <dt>合同签单总价：</dt>
                                    <dd>${dkContract.totalSignFee }</dd>
                                    <dt>已到账金额：</dt>
                                    <dd>${dkContract.arriveFee }</dd>
                                    <dt>销售人员：</dt>
                                    <dd>${dkContract.saleUser.name }</dd>
                                    <dt>监理人员：</dt>
                                    <dd>${dkContract.supervisionUser.name }</dd>
                                    <dt>安装人员：</dt>
                                    <dd>${dkContract.installUser.name }</dd>
                                </dl>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <dl class="dl-horizontal">
                                    <dt>当前安装进度</dt>
                                    <dd>
                                        <div class="progress progress-striped active m-b-sm">
                                            <div style="width: ${scheduleCent }%;" class="progress-bar"></div>
                                        </div>
                                        <small>当前已完成安装项目总进度的 <strong>${scheduleCent }%</strong></small>
                                    </dd>
                                </dl>
                            </div>
                        </div>
						<div class="row">
                            <div class="col-sm-12">
                                <dl class="dl-horizontal">
                                    <dt>当前回款进度</dt>
                                    <dd>
                                        <div class="progress progress-striped active m-b-sm">
                                            <div style="width: ${payCent }%;" class="progress-bar"></div>
                                        </div>
                                        <small>当前已完成项目回款总进度的 <strong>${payCent }%</strong></small>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <dl class="dl-horizontal">
                                    <dt>备注</dt>
                                    <dd>
                                        ${dkContract.remark }
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="row m-t-sm">
                            <div class="col-sm-12">
                                <div class="panel blank-panel">
                                    <div class="panel-heading">
                                        <div class="panel-options">
                                            <ul class="nav nav-tabs">
                                            	<li><a href="#tab-100" data-toggle="tab">主合同</a>
                                            	<c:forEach items="${contractSubList}" var="subdc" varStatus="status">
                                            		<li><a href="#tab-${status.count}" data-toggle="tab">${subdc.name}</a></li>
                                            	</c:forEach>
                                                <li><a href="#tab-101" data-toggle="tab">回款记录</a>
                                                </li>
                                                <li><a href="#tab-102" data-toggle="tab">安装记录</a>
                                                </li>
                                                <li><a href="#tab-103" data-toggle="tab">安装付款</a>
                                                </li>
                                                <li><a href="#tab-104" data-toggle="tab">维保记录</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="panel-body">

                                        <div class="tab-content">
                                     
                                            <div class="tab-pane active" id="tab-100">

                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
						                                    <th>商品</th>
						                                    <th>规格</th>
						                                    <th>成本价</th>
															<th>销售价</th>
															<th>数量</th>
															<th>已出库数量</th>
															<th>成本总价</th>
															<th>销售总价</th>
															<c:choose> 
															  <c:when test="${dkContract.productType == '0' }">   
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
                                                        <c:forEach var="item" items="${dkContract.dkContractProductList}">   
														      <tr>
															      <td>
							                                        <div><strong>${item.name }</strong>
							                                        </div>
							                                      </td>  
							                                      <td>${item.model }</td>
							                                      <td>&yen;${item.costPrice }</td>
							                                      <td>&yen;${item.price }</td>
							                                      <td>${item.amount }</td>
							                                      <td>${item.stockOut }</td>
							                                      <td>&yen;${item.totalCostPrice }</td>
							                                      <td>&yen;${item.totalPrice }</td>
							                                      <c:choose> 
																	  <c:when test="${dkContract.productType == '0' }">   
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

                                            </div>
                                            
                                            <c:forEach items="${contractSubList}" var="subitem" varStatus="substatus">
                                            <div class="tab-pane" id="tab-${substatus.count}">

                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
						                                    <th>商品</th>
						                                    <th>规格</th>
						                                    <th>成本价</th>
															<th>销售价</th>
															<th>数量</th>
															<th>已出库数量</th>
															<th>成本总价</th>
															<th>销售总价</th>
															<c:choose> 
															  <c:when test="${dkContract.productType == '0' }">   
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
                                                        <c:forEach var="subitemPro" items="${subitem.dkContractProductList}">   
														      <tr>
															      <td>
							                                        <div><strong>${subitemPro.name }</strong>
							                                        </div>
							                                      </td>  
							                                      <td>${subitemPro.model }</td>
							                                      <td>&yen;${subitemPro.costPrice }</td>
							                                      <td>&yen;${subitemPro.price }</td>
							                                      <td>${subitemPro.amount }</td>
							                                      <td>${subitemPro.stockOut }</td>
							                                      <td>&yen;${subitemPro.totalCostPrice }</td>
							                                      <td>&yen;${subitemPro.totalPrice }</td>
							                                      <c:choose> 
																	  <c:when test="${dkContract.productType == '0' }">   
									                                      <td>
									                                        <c:if test="${subitemPro.capacityModel != null }">
																				<c:forTokens items="${subitemPro.capacityModel}" delims="," var="itemCapacityModel">
																				   ${itemCapacityModel}<br>
																				</c:forTokens>
																			</c:if>
									                                      </td>  
																	  </c:when> 
																	  <c:otherwise>   
																	      <td>${subitemPro.place }</td>
									                                      <td>${fns:getDictLabel(subitemPro.brandId, 'brand_id', '')}</td>
									                                      <td>${subitemPro.unit }</td>
																	  </c:otherwise> 
																	</c:choose> 
																	
							                                      <td>${subitemPro.descript }</td>  
														      </tr>   
														</c:forEach> 


                                                    </tbody>
                                                </table>
                                                
                                                
                                                <table class="table invoice-total">
							                        <tbody>
							                            <tr>
							                                <td><strong>销售价：</strong>
							                                </td>
							                                <td>&yen;${subitem.contractFee }</td>
							                            </tr>
							                            <tr>
							                                <td><strong>成本价：</strong>
							                                </td>
							                                <td>&yen;${subitem.costFee }</td>
							                            </tr>
							                            <tr>
							                                <td><strong>签单价：</strong>
							                                </td>
							                                <td>&yen;${subitem.signFee }</td>
							                            </tr>
							                        </tbody>
							                    </table>

                                            </div>
                                            </c:forEach>
                                            
                                            
                                            <div class="tab-pane" id="tab-101">

                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
						                                    <th>日期</th>
						                                    <th>金额</th>
															<th>备注</th>
						                                </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="itemPay" items="${contractPayList}">   
														      <tr>
															      <td>
							                                        <fmt:formatDate value="${itemPay.payDate}" pattern="yyyy-MM-dd"/>
							                                      </td>  
							                                      <td>&yen;${itemPay.payFee }</td>
							                                      <td>${itemPay.remark }</td>
														      </tr>   
														</c:forEach> 


                                                    </tbody>
                                                </table>

                                            </div>
                                            
                                            
                                            
                                            
                                            <div class="tab-pane" id="tab-102">
                                                <div class="feed-activity-list">
                                                	<c:forEach var="itemSchedule" items="${contractScheduleList}">   
	                                                    <div class="feed-element">
	                                                        <div class="media-body ">
	                                                            <small class="pull-right"><fmt:formatDate value="${itemSchedule.submitDate }" pattern="yyyy-MM-dd HH:mm:ss"/> </small>
	                                                            	${itemSchedule.descript }
	                                                            <br>
	                                                            <small class="text-muted">进度:${itemSchedule.percent }%</small>
	                                                            <div class="photos">
	                                                                
	                                                                <c:if test="${itemSchedule.pic != null }">
																	<c:forTokens items="${itemSchedule.pic}" delims="," var="itemSchedulePic">
																	   <img src="${itemSchedulePic}" class="feed-photo">
																	</c:forTokens>
																	</c:if>
	                                                            </div>
	                                                        </div>
                                                    </div>
                                                    </c:forEach>

                                                    
                                                </div>

                                            </div>
                                            
                                            
                                            <div class="tab-pane" id="tab-103">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
						                                    <th>日期</th>
						                                    <th>付款金额</th>
						                                    <th>安装队</th>
															<th>备注</th>
						                                </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="itemInstallCost" items="${dkContractInstallCostList}">   
														      <tr>
															      <td>
							                                        <fmt:formatDate value="${itemInstallCost.payDate}" pattern="yyyy-MM-dd"/>
							                                      </td>  
							                                      <td>&yen;${itemInstallCost.payFee }</td>
							                                      <td>${itemInstallCost.installTeam }</td>
							                                      <td>${itemInstallCost.remark }</td>
														      </tr>   
														</c:forEach> 


                                                    </tbody>
                                                </table>

                                            </div>
                                            
                                            
                                            <div class="tab-pane" id="tab-104">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
						                                    <th>维保日期</th>
						                                    <th>维保人员</th>
															<th>维保记录</th>
						                                </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="itemService" items="${dkContractServiceEntityList}">   
														      <tr>
															      <td>
							                                        <fmt:formatDate value="${itemInstallCost.serviceDate}" pattern="yyyy-MM-dd"/>
							                                      </td>  
							                                      <td>${itemInstallCost.servicePerson }</td>
							                                      <td>${itemInstallCost.serviceContent }</td>
														      </tr>   
														</c:forEach> 


                                                    </tbody>
                                                </table>

                                            </div>
                                            
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
       
    </div>
</body>
</html>