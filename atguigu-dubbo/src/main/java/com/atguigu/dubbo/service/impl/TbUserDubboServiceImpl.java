package com.atguigu.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.DigestUtils;

import com.atguigu.dubbo.service.TbUserDubboService;
import com.atguigu.mapper.TbUserMapper;
import com.atguigu.pojo.TbUser;
import com.atguigu.pojo.TbUserExample;
import com.atguigu.pojo.TbUserExample.Criteria;

public class TbUserDubboServiceImpl implements TbUserDubboService {
	@Resource
	private TbUserMapper tbUserMapper;
	@Override
	public TbUser selByUser(TbUser user) {
		TbUserExample example = new TbUserExample();
	 example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
// DigestUtils.md5DigestAsHex(user.getPassword().getBytes()) spring框架自带的MD5加密~！
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}

