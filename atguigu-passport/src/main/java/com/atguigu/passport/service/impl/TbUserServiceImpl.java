package com.atguigu.passport.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.commons.pojo.AtguiguResult;
import com.atguigu.commons.utils.CookieUtils;
import com.atguigu.commons.utils.JsonUtils;
import com.atguigu.dubbo.service.TbUserDubboService;
import com.atguigu.passport.dao.JedisPoolDao;
import com.atguigu.passport.service.TbUserService;
import com.atguigu.pojo.TbUser;

@Service
public class TbUserServiceImpl implements TbUserService {
	@Reference
	private TbUserDubboService tbUserDubboService;
	@Resource
	private JedisPoolDao jedisPoolDaoImpl;
	@Value("${redis.user.key}")
	private String key;
	@Override
	public AtguiguResult login(TbUser user,HttpServletRequest request,HttpServletResponse response) {
		AtguiguResult er = new AtguiguResult();
		TbUser tbUser = tbUserDubboService.selByUser(user);
		if(tbUser!=null){
			String uuid = UUID.randomUUID().toString();
			//设置Cookie key--> TT_TOKEN   value--> UUID
			CookieUtils.setCookie(request, response, "TT_TOKEN", uuid);
			//把数据存储到Redis中 key—user: uuid
	jedisPoolDaoImpl.set(key+uuid,JsonUtils.objectToJson(tbUser));
			er.setStatus(200);
			er.setMsg("OK");
		}
		return er;
	}
	
	
	@Override
	public AtguiguResult getToken(String token) {
		AtguiguResult er = new AtguiguResult();
         //user+uuid登录的时候存的。Redis中取得。
		if(jedisPoolDaoImpl.exists(key+token)){
			String json = jedisPoolDaoImpl.get(key+token);
			er.setStatus(200);
			er.setData(JsonUtils.jsonToPojo(json, TbUser.class));
			er.setMsg("OK");
		}else{
			er.setStatus(400);
		}
		return er;
	}


	@Override
	public AtguiguResult logout(String token,HttpServletRequest request,HttpServletResponse response) {
		//删除redis数据 user:1233242 cookie 中删除对应的key 
		Long result = jedisPoolDaoImpl.del(key+token);
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		AtguiguResult er = new AtguiguResult();
		if(result>0){
			er.setStatus(200);
			er.setMsg("OK");
			er.setData("");
		}else{
			er.setStatus(400);
		}
		return er;
	}


}

