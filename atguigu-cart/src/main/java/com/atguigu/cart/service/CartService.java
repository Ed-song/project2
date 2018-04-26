package com.atguigu.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.atguigu.cart.controller.Cart;
import com.atguigu.commons.pojo.AtguiguResult;

public interface CartService {
	void addCart(long itemId,int num,HttpServletRequest request) throws Exception ;
	
	List<Cart> showCart(HttpServletRequest request);
	
	AtguiguResult upItemNum(long itemId,int num,HttpServletRequest request);
	
	AtguiguResult delete(long id,HttpServletRequest request);
	
	List<Cart> showCartOrder(HttpServletRequest request,List<Long> ids);
}
