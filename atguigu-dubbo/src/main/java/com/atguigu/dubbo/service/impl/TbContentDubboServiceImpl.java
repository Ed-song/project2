package com.atguigu.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.atguigu.dubbo.service.TbContentDubboService;
import com.atguigu.mapper.TbContentMapper;
import com.atguigu.pojo.TbContent;
import com.atguigu.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService {
	@Resource
	private TbContentMapper tbContentMapper;
	@Override
	public List<TbContent> selByCidPage(long cid, int page, int rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		if(cid!=0){
			example.createCriteria().andCategoryIdEqualTo(cid);
		}
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> pi = new PageInfo<>(list);
		return pi.getList();
	}
	@Override
	public long selCountByCid(long cid) {
		TbContentExample example = new TbContentExample();
		if(cid!=0){
			example.createCriteria().andCategoryIdEqualTo(cid);
		}
		return tbContentMapper.countByExample(example);
	}
}

