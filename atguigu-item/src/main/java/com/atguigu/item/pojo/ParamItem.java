package com.atguigu.item.pojo;

import java.util.List;

// 封装的数据库param_data字段中的group，params！
public class ParamItem {
	private String group;
	private List<MyKeyValue> params;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List<MyKeyValue> getParams() {
		return params;
	}
	public void setParams(List<MyKeyValue> params) {
		this.params = params;
	}

}
