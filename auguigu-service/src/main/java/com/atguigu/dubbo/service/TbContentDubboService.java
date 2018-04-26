package com.atguigu.dubbo.service;

import java.util.List;

import com.atguigu.pojo.TbContent;

public interface TbContentDubboService {
    List<TbContent> selByCidPage(long cid,int page,int rows);
	
	long selCountByCid(long cid);

}
