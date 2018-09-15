package com.wande.ssy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean工具类, 要求使用的JavaBean必须要负责规范的贫血Bean(建议实现序列化)
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午2:38:58
 * @param <T>
 */
public class BeanUtil<T> {
	
	/**
	 * 根据Map<String, Object>数据转换为JavaBean数据
	 * 
	 * @param datas			Map<String, Object>
	 * @param beanClass		Javabean.class对象
	 * @return
	 */
	public T Map2Bean(Map<String, Object> datas, Class<T> beanClass){
		// 对象字段名称
		String fieldName = "";
		// 对象方法名称
		String methodname = "";
		// 对象方法需要赋的值
		Object methodsetvalue = "";
		try {
			// 得到对象所有字段
			Field fields[] = beanClass.getDeclaredFields();
			// 创建一个泛型类型实例
			T t = beanClass.newInstance();
			// 遍历所有字段，对应配置好的字段并赋值
			for (Field field : fields) {
				// 获取字段名称
				fieldName = field.getName();
				if (fieldName.equals("serialVersionUID"))
					continue;
				// 拼接set方法
				methodname = "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
				// 获取data里的对应值
				methodsetvalue = datas.get(fieldName);
				// 赋值给字段
				Method m = beanClass.getDeclaredMethod(methodname, field.getType());
				m.invoke(t, methodsetvalue);
			}
			//返回
			return t;
		} catch (Exception e) {
			System.out.println("map转Bean失败, 请检查你的Bean是否符合规范贫血并每个字段有对应的get,set方法!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据List<Map<String, Object>>数据转换为JavaBean数据
	 * 
	 * @param datas			List<Map<String, Object>>
	 * @param beanClass		Javabean.class对象
	 * @return
	 */
	public List<T> ListMap2Bean(List<Map<String, Object>> datas, Class<T> beanClass){
		// 返回数据集合
		List<T> list = null;
		// 对象字段名称
		String fieldName = "";
		// 对象方法名称
		String methodname = "";
		// 对象方法需要赋的值
		Object methodsetvalue = "";
		try {
			list = new ArrayList<T>();
			// 得到对象所有字段
			Field fields[] = beanClass.getDeclaredFields();
			// 遍历数据
			for (Map<String, Object> mapdata : datas) {
				// 创建一个泛型类型实例
				T t = beanClass.newInstance();
				// 遍历所有字段，对应配置好的字段并赋值
				for (Field field : fields) {
					// 获取字段名称
					fieldName = field.getName();
					if (fieldName.equals("serialVersionUID"))
						continue;
					// 拼接set方法
					methodname = "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
					// 获取data里的对应值
					methodsetvalue = mapdata.get(fieldName);
					// 赋值给字段
					Method m = beanClass.getDeclaredMethod(methodname, field.getType());
					m.invoke(t, methodsetvalue);
				}
				// 存入返回列表
				list.add(t);
			}
		} catch (Exception e) {
			System.out.println("map转Bean失败, 请检查你的Bean是否符合规范贫血并每个字段有对应的get,set方法!");
			e.printStackTrace();
		}
		// 返回
		return list;
	}
	
	/**
	 * 根据JavaBean数据转换为Map<String, Object>数据
	 * 
	 * @param data			JavaBean
	 * @param beanClass		Javabean.class对象
	 * @return
	 */
	public Map<String, Object> Bean2Map(T data, Class<T> beanClass) {
		// 对象字段名称
		String fieldName = "";
		// 对象方法名称
		String methodName = "";
		// 对象方法需要赋的值
		Object methodsetvalue = "";
		try {
			// 得到对象所有字段
			Field fields[] = beanClass.getDeclaredFields();
			// 创建一个泛型类型实例
			Map<String, Object> ret = new HashMap<String, Object>();
			// 遍历所有字段，对应配置好的字段并赋值
			for (Field field : fields) {
				// 获取字段名称
				fieldName = field.getName();
				if (fieldName.equals("serialVersionUID"))
					continue;
				// 拼接get方法
				methodName = "get" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
				Method m = beanClass.getDeclaredMethod(methodName);
				// 获取t里的对应值
				methodsetvalue = m.invoke(data);
				// 放入Map中
				ret.put(fieldName, methodsetvalue);
				return ret;
			}
		} catch (Exception e) {
			System.out.println("Bean转Map失败, 请检查你的Bean是否符合规范贫血并每个字段有对应的get,set方法!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据JavaBean数据转换为List<Map<String, Object>>数据
	 * 
	 * @param 	datas		List<JavaBean>
	 * @param beanClass		Javabean.class对象
	 * @return
	 */
	public List<Map<String, Object>> Bean2ListMap(List<T> datas, Class<T> beanClass) {
		// 返回数据集合
		List<Map<String, Object>> result = null;
		// 对象字段名称
		String fieldName = "";
		// 对象方法名称
		String methodName = "";
		// 对象方法需要赋的值
		Object methodsetvalue = "";
		try {
			result = new ArrayList<Map<String, Object>>();
			// 得到对象所有字段
			Field fields[] = beanClass.getDeclaredFields();
			// 遍历数据
			for (T data : datas) {
				// 创建一个泛型类型实例
				Map<String, Object> ret = new HashMap<String, Object>();
				// 遍历所有字段，对应配置好的字段并赋值
				for (Field field : fields) {
					// 获取字段名称
					fieldName = field.getName();
					if (fieldName.equals("serialVersionUID"))
						continue;
					// 拼接get方法
					methodName = "get" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
					Method m = beanClass.getDeclaredMethod(methodName);
					// 获取t里的对应值
					methodsetvalue = m.invoke(data);
					// 放入Map中
					ret.put(fieldName, methodsetvalue);
				}
				// 存入返回列表
				result.add(ret);
			}
		} catch (Exception e) {
			System.out.println("Bean转Map失败, 请检查你的Bean是否符合规范贫血并每个字段有对应的get,set方法!");
			e.printStackTrace();
		}
		// 返回
		return result;
	}
}
