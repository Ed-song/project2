package com.atguigu.order.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.commons.pojo.AtguiguResult;
import com.atguigu.commons.utils.IDUtils;
import com.atguigu.dubbo.service.TbOrderDubboService;
import com.atguigu.order.pojo.MyOrder;
import com.atguigu.order.service.TbOrderService;
import com.atguigu.pojo.TbOrder;
import com.atguigu.pojo.TbOrderItem;
import com.atguigu.pojo.TbOrderShipping;

@Service
public class TbOrderServiceImpl implements TbOrderService {

	@Reference
	private TbOrderDubboService tbOrderDubboService;

	@Override
	public Map<String,Object> createOrder(MyOrder order) {
		AtguiguResult er = new AtguiguResult();
		// 订单编号
		String orderId = IDUtils.genItemId() + "";
		Date date = new Date();
		// 订单新增
		TbOrder tbOrder = new TbOrder();
		tbOrder.setOrderId(orderId);
		tbOrder.setPayment(order.getPayment());
		tbOrder.setPaymentType(order.getPaymentType());
		tbOrder.setCreateTime(date);
		tbOrder.setUpdateTime(date);
		// 订单商品新增
		for (TbOrderItem item : order.getOrderItems()) {
			item.setOrderId(orderId);
			item.setId(IDUtils.genItemId() + "");
		}
		// 新增收货人
		TbOrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(orderId);
		shipping.setCreated(date);
		shipping.setUpdated(date);
		try {
			int index = tbOrderDubboService.insOrder(tbOrder, order.getOrderItems(), shipping);
			if (index > 0) {
				er.setStatus(200);
			} else {
				er.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("orderId", orderId);
		Calendar c =Calendar.getInstance();
		//日历类
		c.add(Calendar.DAY_OF_MONTH, 2);
		map.put("date", c.getTime());
		return map;
	}
}

