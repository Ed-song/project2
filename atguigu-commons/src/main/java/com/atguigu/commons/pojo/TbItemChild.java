package com.atguigu.commons.pojo;


import com.atguigu.pojo.TbItem;

// TbItemChild 跟页面所要的数据对应！
public class TbItemChild extends TbItem{
	
	private String [] images;

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
}
