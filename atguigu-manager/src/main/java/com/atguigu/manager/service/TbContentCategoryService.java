package com.atguigu.manager.service;

import java.util.List;

import com.atguigu.commons.pojo.EasyUITree;

public interface TbContentCategoryService {
	List<EasyUITree> selByPid(long pid);
}
