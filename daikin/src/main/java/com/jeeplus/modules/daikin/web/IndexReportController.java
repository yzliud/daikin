package com.jeeplus.modules.daikin.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.daikin.entity.IndexReport;
import com.jeeplus.modules.daikin.service.IndexReportService;
import com.jeeplus.modules.echarts.entity.ChinaWeatherDataBean;

@Controller
@RequestMapping(value = "${adminPath}/daikin/index")
public class IndexReportController extends BaseController {

	@Autowired
	private IndexReportService indexReportService;
	
	@RequestMapping(value = {"report",""})
	public String index(ChinaWeatherDataBean chinaWeatherDataBean, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		/****************折线图*************************/
		//X轴的数据
		List<String> xLineAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yLineAxisData = new HashMap<String,List<Double>>();
		
		List<IndexReport> contractTmp = indexReportService.getContractFee();
		List<IndexReport> payTmp = indexReportService.getPayFee();
		
		List<Double> contractLs = new ArrayList<Double>();
		List<Double> payLs = new ArrayList<Double>();
		
		String[] monthStr = getLast12Months();
		boolean ishas = false;
		for(int i = 0;i<monthStr.length;i++){
			ishas = false;
			xLineAxisData.add(monthStr[i]);
			for(IndexReport ip:contractTmp){
				if(ip.getMonthStr().equals(monthStr[i])){
					contractLs.add(ip.getFee());
					ishas = true;
				}
			}
			if(!ishas){
				contractLs.add(0.00);
			}
			ishas = false;
			for(IndexReport ip:payTmp){
				if(ip.getMonthStr().equals(monthStr[i])){
					payLs.add(ip.getFee());
					ishas = true;
				}
			}
			if(!ishas){
				payLs.add(0.00);
			}
		}
		yLineAxisData.put("销售额", contractLs);
		yLineAxisData.put("合同到款", payLs);
		
		request.setAttribute("xLineAxisData", xLineAxisData);
		request.setAttribute("yLineAxisData", yLineAxisData);
		
		
		/*********************柱状图*******************************/
		//X轴的数据
		List<String> xBarAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yBarAxisData = new HashMap<String,List<Double>>();
		
		List<IndexReport> saleTmp = indexReportService.getSaleFee();
		List<Double> saleLs = new ArrayList<Double>();
		for(IndexReport ip:saleTmp){
			xBarAxisData.add(ip.getName());
			saleLs.add(ip.getFee());
		}
		yBarAxisData.put("合同额", saleLs);
		request.setAttribute("xBarAxisData", xBarAxisData);
		request.setAttribute("yBarAxisData", yBarAxisData);
		
		return "modules/daikin/indexReport";	
	}
	
	public String[] getLast12Months(){  
        
        String[] last12Months = new String[12];  
          
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去</span>  
        for(int i=0; i<12; i++){  
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月  
            String str = String.format("%02d", cal.get(Calendar.MONTH)+1);
            last12Months[11-i] = cal.get(Calendar.YEAR)+ "-" + str;  
        }  
          
        return last12Months;  
    }  
}
