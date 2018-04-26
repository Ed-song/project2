package com.atguigu.dubbo.service;

import java.util.List;

import com.atguigu.pojo.TbOrder;
import com.atguigu.pojo.TbOrderItem;
import com.atguigu.pojo.TbOrderShipping;

public interface TbOrderDubboService {
	int insOrder(TbOrder order,List<TbOrderItem> list,TbOrderShipping shipping) throws Exception;
}
