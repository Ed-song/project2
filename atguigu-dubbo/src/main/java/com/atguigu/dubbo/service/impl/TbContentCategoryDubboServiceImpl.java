package com.atguigu.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.atguigu.dubbo.service.TbContentCategoryDubboService;
import com.atguigu.mapper.TbContentCategoryMapper;
import com.atguigu.pojo.TbContentCategory;
import com.atguigu.pojo.TbContentCategoryExample;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService{
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<TbContentCategory> selByPid(long pid) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		//排序---根据字段sort_order
		example.setOrderByClause("sort_order asc");
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}
}

