package com.jeeplus.api.web;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jeeplus.api.util.CommonUtil;
import com.jeeplus.api.util.Emoji;
import com.jeeplus.common.config.Global;
import com.jeeplus.modules.daikin.entity.DkWorker;
import com.jeeplus.modules.daikin.service.DkWorkerService;

@Controller
@RequestMapping(value = "${adminPath}/api/authorize")
public class AuthorizeController {
	
	@Autowired
	private DkWorkerService workerService;
	
	@RequestMapping("/jsauthorize")
	public void jsauthorize(HttpServletRequest request, HttpServletResponse response, String callback) throws Exception {
		//共账号及商户相关参数 daikin.samehope.cn
		System.out.println("jsauthorize:cmethod==="+request.getParameter("cmethod"));
		String appid = Global.getAppId();
		String backUri = Global.getFrontPath()+"/a/api/authorize/toauthorize?cmethod="+request.getParameter("cmethod");
		System.out.println("backUri=========="+backUri);
		//String appid = "wxcfe5737777fcfd95";
		//String backUri = "http://manage.wifibao.top/daikin/a/api/authorize/toauthorize";
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=" + appid+
				"&redirect_uri=" +
				backUri+
				"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		System.out.println("***********************"+url);
		response.sendRedirect(url);
	}

	@RequestMapping(value = "/toauthorize")
	public void toauthorize(HttpServletRequest request, HttpServletResponse response, String callback) throws Exception {
		String code = request.getParameter("code");
		String cmethod = request.getParameter("cmethod");
		//商户相关资料 
		String appid = Global.getAppId();
		String appsecret = Global.getAppsecret();
		//String appid = "wxcfe5737777fcfd95";
		//String appsecret = "b6074c880d370cc2d473a50562b49f28";
		
		String openId ="";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
		System.out.println("URLURL:::"+URL);
		String jsonString = CommonUtil.httpsRequest(URL, "GET", null);
		System.out.println("jsonString:::"+jsonString);
		JsonObject returnData = new JsonParser().parse(jsonString).getAsJsonObject();
		System.out.println("returnData:::"+returnData);
		
		System.out.println(jsonString);
		String access_token="";
		if(jsonString.indexOf("openid")>-1){
			if (null != jsonString) {
				openId = returnData.get("openid").getAsString();
				access_token=returnData.get("access_token").getAsString();
				request.getSession().setAttribute("openId", openId);
			}
			String URLinfo = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openId+"&lang=zh_CN";
			String jsonString1 = CommonUtil.httpsRequest(URLinfo, "GET", null);
			JsonObject returnData1 = new JsonParser().parse(jsonString1).getAsJsonObject();
			System.out.println(returnData1.toString());
			String headimgurl="";
			String nickname="";
			String sex ="";
			String city ="";
			String province ="";
			String country ="";
			if (null != returnData1) {
				headimgurl = returnData1.get("headimgurl").getAsString();
				nickname= returnData1.get("nickname").getAsString();
				sex = returnData1.get("sex").getAsString();
				city = returnData1.get("city").getAsString();
				province = returnData1.get("province").getAsString();
				country = returnData1.get("country").getAsString();
				System.out.println(headimgurl+"###"+nickname+"###"+sex+"###"+city+"###"+province+"###"+country);
				DkWorker worker = workerService.findUniqueByProperty("open_id", openId);
				if(worker==null){
					worker = new DkWorker();
					String uuid = UUID.randomUUID().toString().replace("-", "");
					worker.setId(uuid);
					worker.setOpenId(openId);
					nickname = Emoji.filterEmoji(nickname);
					worker.setNickName(nickname);
					worker.setCity(city);
					worker.setProvince(province);
					worker.setCountry(country);
					worker.setSex(sex);
					worker.setHeadImg2(headimgurl);
					worker.setDelFlag("0");
					worker.setCreateDate(new Date());
					worker.setIsNewRecord(true);
					workerService.save(worker);
				}
				
			}
		}
		System.out.println("toauthorize:cmethod==="+request.getParameter("cmethod"));
		if("unbind".equals(cmethod)){
			response.sendRedirect(Global.getFrontPath()+"/a/api/worker/unbind");
		}else if("quotation".equals(cmethod)){
			response.sendRedirect(Global.getFrontPath()+"/a/api/quotation/index");
		}else if("member".equals(cmethod)){
			response.sendRedirect(Global.getFrontPath()+"/a/api/member/index");
		}else{
			response.sendRedirect(Global.getFrontPath()+"/a/api/worker/index");
		}
	}

}
