package com.atguigu.manager.service;

import com.atguigu.commons.pojo.EasyUIDatagrid;

public interface TbItemService {
	//根据前台页面使用easyui控件取数据而得出返回easyUIDatagrid
    EasyUIDatagrid show(int page,int rows);
    //更新商品状态ids，表示字符串，字符串中可能存在多个商品id
    int updStatusById(String ids,byte status);
}
