package com.atguigu.order.service;

import java.util.Map;

import com.atguigu.order.pojo.MyOrder;

public interface TbOrderService {
	Map<String,Object> createOrder(MyOrder order);
}
