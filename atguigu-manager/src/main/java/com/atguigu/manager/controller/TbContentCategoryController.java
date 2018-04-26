package com.atguigu.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.commons.pojo.EasyUITree;
import com.atguigu.manager.service.TbContentCategoryService;

@Controller
public class TbContentCategoryController {
	@Resource
	private TbContentCategoryService tbContentCategoryService;
	
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyUITree> showCategory(@RequestParam(defaultValue="0") long id){
		return tbContentCategoryService.selByPid(id);
	}
}



