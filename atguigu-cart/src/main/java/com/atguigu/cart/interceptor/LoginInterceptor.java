package com.atguigu.cart.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.cart.dao.JedisPoolDao;
import com.atguigu.commons.utils.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor{

	@Resource
	private JedisPoolDao jedisPoolDao;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		//判断用户是否登录
		String uuid = CookieUtils.getCookieValue(arg0, "TT_TOKEN");
       //在拦截器中不能读取配置文件实现软编码。
		String mykey = "user:"+uuid;
		if(jedisPoolDao.exists(mykey)){
			String json = jedisPoolDao.get(mykey);
			if(json!=null&&!json.equals("")){
				return true;
			}
		}
		arg1.sendRedirect("http://localhost:8084/user/showLogin");
		return false;
	}

    
} 
