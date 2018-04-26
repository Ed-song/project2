package com.atguigu.manager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.commons.pojo.EasyUIDatagrid;
import com.atguigu.dubbo.service.TbItemDubboService;
import com.atguigu.manager.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubboService;

	@Override
	public EasyUIDatagrid show(int page, int rows) {
		return tbItemDubboService.showPage(page, rows);
	}

	@Override
	public int updStatusById(String ids, byte status) {
		String[] split = ids.split(",");
		for (String id : split) {
			tbItemDubboService.updStatsById(Long.parseLong(id), status);
		}
		return 1;
	}
}
