package com.atguigu.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.commons.pojo.EasyUITree;
import com.atguigu.dubbo.service.TbContentCategoryDubboService;
import com.atguigu.manager.service.TbContentCategoryService;
import com.atguigu.pojo.TbContentCategory;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboService;

	@Override
	public List<EasyUITree> selByPid(long pid) {
		List<EasyUITree> listTree = new ArrayList<>();
		// 根据传递过来的id查询数据
		List<TbContentCategory> list = tbContentCategoryDubboService.selByPid(pid);
		// TbContentCategory类中的id 对应EasyUITree 的id
		// TbContentCategory类中的name 对应EasyUITree 的text
		// TbContentCategory类中的isParent 对应EasyUITree 的state
		// 循环将数据赋值给listTree
		for (TbContentCategory tbContentCategory : list) {
			// 创建EasyUITree 对象
			EasyUITree tree = new EasyUITree();
			tree.setId(tbContentCategory.getId());
			tree.setText(tbContentCategory.getName());
			tree.setState(tbContentCategory.getIsParent()?"closed":"open");
			// 将所有的tree对象添加到集合
			listTree.add(tree);
		}
		// 将最终的数据返回！
		return listTree;
	}

}
