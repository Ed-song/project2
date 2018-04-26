package com.atguigu.dubbo.service;

import com.atguigu.pojo.TbItemDesc;
 
public interface TbItemDescDubboService {
	//根据id查询商品描述信息
	TbItemDesc selByItemId(long id);
}
