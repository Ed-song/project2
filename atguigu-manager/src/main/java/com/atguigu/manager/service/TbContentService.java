package com.atguigu.manager.service;

import com.atguigu.commons.pojo.EasyUIDatagrid;

public interface TbContentService {
	EasyUIDatagrid showContent(long categoryId,int page,int rows);
}
