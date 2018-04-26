package com.atguigu.dubbo.service;

import com.atguigu.commons.pojo.EasyUIDatagrid;
import com.atguigu.pojo.TbItem;

public interface TbItemDubboService {
    public EasyUIDatagrid showPage(int page,int rows);
    
    int updStatsById(long id,byte status);
    
    TbItem selById(long id);
    
}
