package com.atguigu.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.commons.pojo.AtguiguResult;
import com.atguigu.commons.pojo.EasyUIDatagrid;
import com.atguigu.manager.service.TbItemService;

@Controller
public class TbItemController {
	@Autowired
	private TbItemService  tbItemService;
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDatagrid show(int page,int rows){
		return tbItemService.show(page, rows);
	}
	
	
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public AtguiguResult delete(String ids){
		AtguiguResult er = new AtguiguResult();
		try {
			int updStatusById = tbItemService.updStatusById(ids, (byte)3);
			
			if (updStatusById>0) {
				er.setStatus(200);
			}else{
				er.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return er;
	}
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public AtguiguResult reshelf(String ids){
		AtguiguResult er = new AtguiguResult();
		try {
			int updStatusById = tbItemService.updStatusById(ids, (byte)1);
			
			if (updStatusById>0) {
				er.setStatus(200);
			}else{
				er.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return er;
	}

	
	
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public AtguiguResult instock(String ids){
		AtguiguResult er = new AtguiguResult();
		try {
			int updStatusById = tbItemService.updStatusById(ids, (byte)2);
			
			if (updStatusById>0) {
				er.setStatus(200);
			}else{
				er.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return er;
	}

	
}
