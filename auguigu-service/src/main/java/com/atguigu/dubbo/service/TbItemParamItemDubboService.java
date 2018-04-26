package com.atguigu.dubbo.service;

import com.atguigu.pojo.TbItemParamItem;

public interface TbItemParamItemDubboService {
      //根据商品Id进行商品规格参数查询
	TbItemParamItem selByItemId(long id);
}
