package com.atguigu.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.dubbo.service.TbItemParamItemDubboService;
import com.atguigu.mapper.TbItemParamItemMapper;
import com.atguigu.pojo.TbItemParamItem;
import com.atguigu.pojo.TbItemParamItemExample;

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService{

	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Override
	public TbItemParamItem selByItemId(long id) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}

