package com.atguigu.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.commons.utils.JsonUtils;
import com.atguigu.dubbo.service.TbItemParamItemDubboService;
import com.atguigu.item.dao.JedisPoolDao;
import com.atguigu.item.pojo.MyKeyValue;
import com.atguigu.item.pojo.ParamItem;
import com.atguigu.item.service.TbItemParamItemService;
import com.atguigu.pojo.TbItemParamItem;


@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboService;
	
	@Resource
	private JedisPoolDao jedisPoolDaoImpl;
	
	// 取得redis中要存储的key
	@Value("${redis.param.key}")
	private String key;
	
	public String showParam(long itemId) {
		// redis中要存储的key
		String mykey = key+itemId;
		if (jedisPoolDaoImpl.exists(mykey)) {
			// 存在则将redis中的数据取出来
			String json = jedisPoolDaoImpl.get(mykey);
			return json;
		}
		// 从数据库取得的值！
		TbItemParamItem itemParamItem = tbItemParamItemDubboService.selByItemId(itemId);
		// 我们要的是TbItemParamItem.paramData属性
		String paramData = itemParamItem.getParamData();
		// paramData 取出来的数据是 json对象数据，将json对象数据进行转换
		List<ParamItem> list = JsonUtils.jsonToList(paramData, ParamItem.class);
		// 准备将取出来的数据拼接成table表格的形式
		StringBuffer sf = new StringBuffer();
		sf.append("<table width='100%' style='color:#999999;'>");
		// 循环拼接数据
		for (ParamItem paramItem : list) {
			// listKV = "params":[{"k":"机身内存","v":"4"},{"k":"储存卡类型","v":"2"}]
			List<MyKeyValue> listKV = paramItem.getParams();
			// 循环遍历listKV
			for (int i = 0; i < listKV.size(); i++) {
				// 第一行要加上 group
				if (i==0) {
					sf.append("<tr>");
					sf.append("<td width='10%'>"+paramItem.getGroup()+"<td/>");
					sf.append("<td width='15%' align='right'>"+listKV.get(i).getK()+"</td>");
					sf.append("<td style='padding-left:30px;'>"+listKV.get(i).getV()+"</td>");
					sf.append("</tr>");
				}else{
					// 剩下的每一行没有大分组
					sf.append("<tr>");
					sf.append("<td width='10%'>");
					sf.append("</td>");
					sf.append("<td width='15%' align='right'>"+listKV.get(i).getK()+"</td>");
					sf.append("<td style='padding-left:30px;'>"+listKV.get(i).getV()+"</td>");
					sf.append("</tr>");
				}
			}
			// 添加一个分割线
			sf.append("<tr><td colspan='3'><hr style='color:#999999;'/></td></tr>");
		}
		sf.append("</table>");
		String string = new String(sf.toString());
		// 将数据放到redis中，现在的数据是拼接后的字符串
		jedisPoolDaoImpl.set(mykey, JsonUtils.objectToJson(string));
		return string;
	}

}
