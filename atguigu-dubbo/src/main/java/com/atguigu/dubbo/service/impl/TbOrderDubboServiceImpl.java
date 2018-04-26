package com.atguigu.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.atguigu.dubbo.service.TbOrderDubboService;
import com.atguigu.mapper.TbOrderItemMapper;
import com.atguigu.mapper.TbOrderMapper;
import com.atguigu.mapper.TbOrderShippingMapper;
import com.atguigu.pojo.TbOrder;
import com.atguigu.pojo.TbOrderItem;
import com.atguigu.pojo.TbOrderShipping;

public class TbOrderDubboServiceImpl implements TbOrderDubboService {

	@Resource 
	private TbOrderMapper tbOrderMapper;
	
	@Resource
	private TbOrderItemMapper tbOrderItemMapper;

	@Resource
	private TbOrderShippingMapper tbOrderShippingMapper;

	
	@Override
	public int insOrder(TbOrder order, List<TbOrderItem> list, TbOrderShipping shipping) throws Exception {
		
		int index = tbOrderMapper.insertSelective(order);
		
		for (TbOrderItem tbOrderItem : list) {
			index+= tbOrderItemMapper.insertSelective(tbOrderItem);
		}
		index+=tbOrderShippingMapper.insertSelective(shipping);
		if(index==2+list.size()){
			return 1;
		}else{
			throw new Exception("创建订单失败");
		}

	}

}
