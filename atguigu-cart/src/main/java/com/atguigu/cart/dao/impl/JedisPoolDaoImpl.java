package com.atguigu.cart.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.atguigu.cart.dao.JedisPoolDao;

import redis.clients.jedis.JedisCluster;

@Repository
public class JedisPoolDaoImpl implements JedisPoolDao {

	@Resource 
	private JedisCluster jedisClusters;
	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		return jedisClusters.exists(key);
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return jedisClusters.get(key);
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return jedisClusters.del(key);
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisClusters.set(key, value);
	}

}
