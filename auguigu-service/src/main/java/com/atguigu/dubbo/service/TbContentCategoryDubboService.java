package com.atguigu.dubbo.service;

import java.util.List;

import com.atguigu.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {
	List<TbContentCategory> selByPid(long pid);
}
