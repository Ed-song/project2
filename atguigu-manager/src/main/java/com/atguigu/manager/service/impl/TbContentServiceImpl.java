package com.atguigu.manager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.commons.pojo.EasyUIDatagrid;
import com.atguigu.dubbo.service.TbContentDubboService;
import com.atguigu.manager.service.TbContentService;

@Service
public class TbContentServiceImpl implements TbContentService {
	@Reference
	private TbContentDubboService tbContentDubboService;

	@Override
	public EasyUIDatagrid showContent(long categoryId, int page, int rows) {
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		datagrid.setRows(tbContentDubboService.selByCidPage(categoryId, page, rows));
		datagrid.setTotal(tbContentDubboService.selCountByCid(categoryId));
		return datagrid;
	}
}

