<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ include file="/webpage/include/echarts.jsp"%>
<meta name="decorator" content="default"/>
	<div id="line_normal"  class="main000"></div>
    <echarts:line 
        id="line_normal"
		title="近一年销售曲线图" 
		subtitle=""
		xAxisData="${xLineAxisData}" 
		yAxisData="${yLineAxisData}" 
		xAxisName="年月"
		yAxisName="金额（元）" />
		
	<div id="bar_normal"  class="main000"></div>
	<echarts:bar 
	  	id="bar_normal"
		title="当月销售柱状图" 
		subtitle=""
		xAxisData="${xBarAxisData}" 
		yAxisData="${yBarAxisData}" 
		xAxisName="销售员"
		yAxisName="销售金额（元）" />