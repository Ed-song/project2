package com.atguigu.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.commons.pojo.AtguiguResult;
import com.atguigu.pojo.TbUser;

public interface TbUserService {
	AtguiguResult login(TbUser user,HttpServletRequest request,HttpServletResponse response);
	
	AtguiguResult getToken(String token);
	
	AtguiguResult logout(String token,HttpServletRequest request,HttpServletResponse response);
}
