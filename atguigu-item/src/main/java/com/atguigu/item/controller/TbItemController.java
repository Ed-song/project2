package com.atguigu.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.item.service.TbItemService;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemService;	
	
	@RequestMapping("item/{id}.html")
	public String showItem(@PathVariable long id,Model model){
		model.addAttribute("item", tbItemService.showItem(id));
		return "item";
	}
}

