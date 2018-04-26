package com.atguigu.order.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.order.pojo.MyOrder;
import com.atguigu.order.service.TbOrderService;

@Controller
public class TbOrderController {
	@Resource
	private TbOrderService tbOrderService;
	@RequestMapping("order/create.html")
	public String createOrder(MyOrder order,Model model){
				try {
			Map<String, Object> map = tbOrderService.createOrder(order);
			model.addAttribute("orderId", map.get("orderId"));
			model.addAttribute("date",map.get("date"));
			model.addAttribute("payment", order.getPayment());
			return "success";
		} catch (Exception e) {
			model.addAttribute("message", "服务器异常");
			return "error/exception";
		}
	}
}

