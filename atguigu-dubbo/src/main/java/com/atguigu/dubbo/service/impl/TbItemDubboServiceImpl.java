package com.atguigu.dubbo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.commons.pojo.EasyUIDatagrid;
import com.atguigu.dubbo.service.TbItemDubboService;
import com.atguigu.mapper.TbItemMapper;
import com.atguigu.pojo.TbItem;
import com.atguigu.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemDubboServiceImpl implements TbItemDubboService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Override
	public EasyUIDatagrid showPage(int page, int rows) {
        //利用分页插件
		PageHelper.startPage(page,rows);
		//查询数据
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		//利用pageInfo进行分页显示
		PageInfo<TbItem> pi = new PageInfo<>(list);
		//方法返回值是一个EasyUIDatagrid
		EasyUIDatagrid datagrid = new EasyUIDatagrid();
		datagrid.setTotal(pi.getTotal());
		datagrid.setRows(pi.getList());
		return datagrid;
	}
	/**
	 * ps:tbItemMapper.updateByPrimaryKeySelective(item)   代表：全字段更新
	 *    tbItemMapper.updateByPrimaryKeySelective(item)   代表：有选择的更新
	 */
	@Override
	public int updStatsById(long id, byte status) {
        TbItem item = new TbItem();
        item.setId(id);
        item.setStatus(status);
        return tbItemMapper.updateByPrimaryKeySelective(item);
        
	}
	@Override
	public TbItem selById(long id) {
		
		return tbItemMapper.selectByPrimaryKey(id);
	}

}
