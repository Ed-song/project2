package com.atguigu.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.dubbo.service.TbItemDescDubboService;
import com.atguigu.mapper.TbItemDescMapper;
import com.atguigu.pojo.TbItemDesc;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {

	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public TbItemDesc selByItemId(long id) {
		return tbItemDescMapper.selectByPrimaryKey(id);
	}
}
 