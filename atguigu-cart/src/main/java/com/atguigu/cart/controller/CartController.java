package com.atguigu.cart.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.cart.service.CartService;
import com.atguigu.commons.pojo.AtguiguResult;

@Controller
public class CartController {
	@Resource
	private CartService cartService;
	// 注意：以下路径在测试的时候，有过相同的路径名称，如果不将原来的路径名称注释掉，则会报500异常！
	@RequestMapping("cart/add/{itemId}.html")
	public String addCart(@PathVariable long itemId,@RequestParam(defaultValue="1") int num,HttpServletRequest request,Model model){
		try {
			cartService.addCart(itemId, num, request);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "error/exception";
		}
		return "cartSuccess"; // cart.jsp 显示购物车用的
	

	}
	@RequestMapping("cart/cart.html")
	public String showCart(Model model,HttpServletRequest request){
		model.addAttribute("cartList", cartService.showCart(request));
		return "cart";
	}
	
	@RequestMapping("cart/update/num/{itemId}/{num}.action")
	@ResponseBody
	public AtguiguResult updNum(@PathVariable long itemId,@PathVariable int num,HttpServletRequest request){
		return cartService.upItemNum(itemId, num, request);
	}
	
	//http://localhost:8085/cart/delete/832739.action
	@RequestMapping("cart/delete/{id}.action")
	@ResponseBody
	public AtguiguResult delete(@PathVariable long id,HttpServletRequest request){
		return cartService.delete(id, request);
	}

	/**
	 * 
	 * @param id 页面传递过来的购物车中的id
	 * @param model 向前台保存数据的作用域
	 * @param request 请求域
	 * @return
	 */
	@RequestMapping("order/order-cart.html")
	public String showOrder(@RequestParam(value="id") List<Long> id,Model model,HttpServletRequest request){
		// 第一个是正常显示的页面 order-cart.jsp 在这个页面中有一个${cartList }
		List<Cart> list = cartService.showCartOrder(request, id);
		if (list.size()>0) {
			model.addAttribute("cartList", list);
			return "order-cart";
		}else{
			model.addAttribute("message", "库存不足.");
			return "info/info";
		}
	}


}
