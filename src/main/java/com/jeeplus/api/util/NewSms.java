package com.jeeplus.api.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class NewSms {
	
	public static void sendMsg(String mobile, String code) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23895092", "3f36af595f034634b15511c9fcc83061");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName("舜举冷暖");
		req.setSmsParamString("{\"verify_code\":\""+code+"\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_70335160");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	}
	
	public static void sendTemplateMsg(String mobile, String template) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23895092", "3f36af595f034634b15511c9fcc83061");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName("舜举冷暖");
		req.setRecNum(mobile);
		req.setSmsTemplateCode(template);
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	}
	
	
	public static void main(String[] args) throws ApiException{
		sendMsg("15298467600","1234");
		sendMsg("15298467600","SMS_70150434");
	}

}
