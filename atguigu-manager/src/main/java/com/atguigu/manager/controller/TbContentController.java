package com.atguigu.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.commons.pojo.EasyUIDatagrid;
import com.atguigu.manager.service.TbContentService;

@Controller
public class TbContentController {
	@Resource
	private TbContentService tbContentService;
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDatagrid showContent(long categoryId,int page,int rows){
		return tbContentService.showContent(categoryId, page, rows);
	}
}

