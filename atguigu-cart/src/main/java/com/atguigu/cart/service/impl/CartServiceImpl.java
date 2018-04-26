package com.atguigu.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.atguigu.cart.controller.Cart;
import com.atguigu.cart.dao.JedisPoolDao;
import com.atguigu.cart.service.CartService;
import com.atguigu.commons.pojo.AtguiguResult;
import com.atguigu.commons.pojo.TbItemChild;
import com.atguigu.commons.utils.CookieUtils;
import com.atguigu.commons.utils.JsonUtils;
import com.atguigu.pojo.TbUser;

@Service
public class CartServiceImpl implements CartService {
	@Resource
	private JedisPoolDao jedisPoolDaoImpl;
	@Value("${redis.item.key}")
	private String itemKey;
	@Value("${redis.cart.key}")
	private String cartKey;
	@Value("${redis.user.key}")
	private String userKey;
	@Override
	public void addCart(long itemId,int num,HttpServletRequest request) throws Exception {
		String itemJson = jedisPoolDaoImpl.get(itemKey+itemId);
		TbItemChild child = JsonUtils.jsonToPojo(itemJson, TbItemChild.class);
			String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
			String jsonUser = jedisPoolDaoImpl.get(userKey+cookieValue);
			String cartKeyRedis = cartKey+JsonUtils.jsonToPojo(jsonUser, TbUser.class).getId();

			if(jedisPoolDaoImpl.exists(cartKeyRedis)){
				String jsonCart = jedisPoolDaoImpl.get(cartKeyRedis);
// 此处应该是转换成购物车的实体类，不是商品的实体类，因为在此是添加购物车
				List<Cart> list = JsonUtils.jsonToList(jsonCart, Cart.class);
				int index = -1;
				for (int i = 0 ;i<list.size();i++) {
					if(list.get(i).getId()==itemId){
						index = i;
break;
					}
				}
				if(index==-1){
					//购物车中没有该商品
					Cart cart = new Cart();
					cart.setId(child.getId());
					cart.setImages(child.getImages());
					cart.setNum(num);
					cart.setPrice(child.getPrice());
					cart.setTitle(child.getTitle());
					list.add(cart);
				}else{
					//购物中有该商品,让该商品数量+num
					Cart cart = list.get(index);
					cart.setNum(cart.getNum()+num);
				}
				jedisPoolDaoImpl.set(cartKeyRedis, JsonUtils.objectToJson(list));
			}else{
				//第一次添加购物车的逻辑，将reids中添加上缓存的购物车
				//转换成Cart类型
				Cart cart = new Cart();
				cart.setId(child.getId());
				cart.setImages(child.getImages());
				cart.setNum(num);
				cart.setPrice(child.getPrice());
				cart.setTitle(child.getTitle());
				List<Cart> list = new ArrayList<>();
				list.add(cart);
				jedisPoolDaoImpl.set(cartKeyRedis, JsonUtils.objectToJson(list));
			}
	}
	@Override
	public List<Cart> showCart(HttpServletRequest request) {
		//获取到登录用户的信息.
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = jedisPoolDaoImpl.get(userKey+cookieValue);
		String jsonCart = jedisPoolDaoImpl.get(cartKey+JsonUtils.jsonToPojo(jsonUser, TbUser.class).getId());
			return JsonUtils.jsonToList(jsonCart, Cart.class);
	}
	@Override
	public AtguiguResult upItemNum(long itemId, int num,HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = jedisPoolDaoImpl.get(userKey+cookieValue);
		String cartKeyRedis = cartKey+JsonUtils.jsonToPojo(jsonUser, TbUser.class).getId();
		String jsonCart = jedisPoolDaoImpl.get(cartKeyRedis);
		List<Cart> jsonToList = JsonUtils.jsonToList(jsonCart, Cart.class);
		for (Cart cart : jsonToList) {
			if(cart.getId()==itemId){
				cart.setNum(num);
				break;
			}
		}
		String result = jedisPoolDaoImpl.set(cartKeyRedis, JsonUtils.objectToJson(jsonToList));
		AtguiguResult er = new AtguiguResult();
		if(result.equals("OK")){
			er.setStatus(200);
		}else{
			er.setStatus(400);
		}
		return er;
	}
	
	public AtguiguResult delete(long id,HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = jedisPoolDaoImpl.get(userKey+cookieValue);
		String cartKeyRedis = cartKey+JsonUtils.jsonToPojo(jsonUser, TbUser.class).getId();
		String jsonCart = jedisPoolDaoImpl.get(cartKeyRedis);
		List<Cart> jsonToList = JsonUtils.jsonToList(jsonCart, Cart.class);
		Cart cartRemove = null;
		for (Cart cart : jsonToList) {
			if(cart.getId()==id){
				cartRemove= cart;
				break;
			}
		}
		jsonToList.remove(cartRemove);
		String result = jedisPoolDaoImpl.set(cartKeyRedis, JsonUtils.objectToJson(jsonToList));
		AtguiguResult er = new AtguiguResult();
		if(result.equalsIgnoreCase("OK")){
			er.setStatus(200);
			er.setMsg("OK");
			er.setData(null);
		}else{
			er.setStatus(400);
		}
		return er;
	}
	
	@Override
	public List<Cart> showCartOrder(HttpServletRequest request, List<Long> ids) {
		// 定义一个要购买商品的集合
		List<Cart> listOrder = new ArrayList<>();
		// 取得购物车的信息 cart:user.id
		String uuid = CookieUtils.getCookieValue(request, "TT_TOKEN");
		// 根据uuid 取得用户信息 user:uuid
		String userJson = jedisPoolDaoImpl.get(userKey + uuid);
		// userJson 转换成user对象
		TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
		// 定义购物车的key cart:user.id;
		String redisCartKey = cartKey + user.getId();
		// 判断
		if (jedisPoolDaoImpl.exists(redisCartKey)) {
			// 取出缓存中购物车信息
			String cartJson = jedisPoolDaoImpl.get(redisCartKey);
			// cartJson 转换成对象
			List<Cart> list = JsonUtils.jsonToList(cartJson, Cart.class);
			// 定义一个boolean类型的变量，来判断当前商品是否可以购买
			boolean flag = true;
			// 遍历前台传递过来的购物车id
			for (Long id : ids) {
				// 遍历 购物车的集合
				for (Cart cart : list) {
					if (id == cart.getId()) {
						// 则说明该商品是我想要买的商品！
						// 购买的数据，还有库存的数量
						// cart.getNum();购买的数据
						String itemJson = jedisPoolDaoImpl.get(itemKey + id);
						// 将itemJson转换成对象
						TbItemChild itemChild = JsonUtils.jsonToPojo(itemJson, TbItemChild.class);
						// 库存数量： itemChild.getNum();
						if (cart.getNum() > itemChild.getNum()) {
							// 不能购买！
							flag = false;
						}
						// 假如说：购买成功之后，库存数量要进行变更！
						/*
						 * 京东：订单完成，将你的数量锁定！买完才会进行减库存！
						 * 京东：下订单:拆单--【京东自营买个鼠标，非自营键盘，电饭煲/2订单，2个订单编号】，库存：”库存系统“
						 * 快递：库存不一样！快递公司也不一样！
						 */
					}
					if (flag) {
						listOrder.add(cart);
					}
					break;
				}
			}
		}
		// 将最终要购买的商品返回
		return listOrder;
	}




}

