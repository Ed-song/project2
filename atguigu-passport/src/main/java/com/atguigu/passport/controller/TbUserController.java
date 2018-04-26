package com.atguigu.passport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.commons.pojo.AtguiguResult;
import com.atguigu.passport.service.TbUserService;
import com.atguigu.pojo.TbUser;

@Controller
public class TbUserController {
	@Resource
	private TbUserService tbUserService;
	@RequestMapping(value = "user/showLogin")
	public String showLogin(Model model, @RequestHeader("Referer") String referer) {
		if (referer == null)
			model.addAttribute("redirect", "");
		else
			model.addAttribute("redirect", referer);
		return "login";
	}

	@RequestMapping("user/login")
	@ResponseBody
	public AtguiguResult login(TbUser user, HttpServletRequest req, HttpServletResponse res) {
		return tbUserService.login(user, req, res);
	}
	
	@RequestMapping("user/token/{token}")
	@ResponseBody
	public Object getToken(String callback, @PathVariable String token) {
		System.out.println(callback);
		AtguiguResult er = tbUserService.getToken(token);
		if (callback == null || callback.equals("")) {
			// 不是jsonp,直接AtguiguResult
			return er;
		} else {
			// 是jsonp
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
	}
	
	@RequestMapping("user/logout/{token}")
	@ResponseBody
	public Object logout(String callback,@PathVariable String token,HttpServletRequest request,HttpServletResponse response){
		AtguiguResult er = tbUserService.logout(token, request, response);
		if(callback==null||callback.equals("")){
			//System.out.println(er);
			return er;
			
		}else{
			// 是jsonp
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
	}


}

