package com.wande.ssy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 树形排序工具, 对JavaBean列表进行树形排序, 需要实例化该工具类
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午1:58:43
 * @param <T>
 */
public class TreeUtil<T> {

	private static List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
	private static List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	private static String sortId = "";		// 对象主键Id
	private static String sortPid = "";		// 父级ID,parentId
	private static Object sortSPid = null;	// 顶级分类parentId
	
	/**
	 * 将List<JavaBean>进行树形排序后返回,需要实例化这个工具类
	 * 
	 * @param objs			List<JavaBean>对象
	 * @param beanClass		JavaBean.class对象
	 * @param id			自定义的树形排序的ID(即JavaBean的对应ID属性名)
	 * @param pid			自定义的树形排序的父级PID(即JavaBean的对应PID属性名)
	 * @param superPid		自定义的树形顶级JavaBean的父级PID的值
	 * @return	
	 */
	public List<T> buidTree(List<T> objs, Class<T> beanClass, String id, String pid, Object superPid) {
		BeanUtil<T> beanUtil = new BeanUtil<T>();
		List<Map<String, Object>> datas = beanUtil.Bean2ListMap(objs, beanClass);
		List<Map<String, Object>> result = buildTree(datas, id, pid, superPid);
		List<T> ns2 = beanUtil.ListMap2Bean(result, beanClass);
		return ns2;
	}
	
	/**
	 * 将List<Map<String, Object>>进行树形排序后返回,需要实例化这个工具类
	 * 
	 * @param objs			List<Map<String, Object>>对象
	 * @param id			自定义的树形排序的ID(Map中作为ID的key)
	 * @param pid			自定义的树形排序的父级PID(Map中作为PID的key)
	 * @param superPid		自定义的树形顶级Map<String,Object>的父级PID的值
	 * @return
	 */
	public static List<Map<String, Object>> buildTree(List<Map<String, Object>> objs, String id, String pid, Object superPid) {
		if (id.equals("") || id == null || pid.equals("") || pid == null)
			return null;
		nodes = objs;
		result.clear();
		sortId = id;
		sortPid = pid;
		sortSPid = superPid;
		for (Map<String, Object> o : objs) {
			if (o.get(sortPid).equals(sortSPid)) {
				result.add(o);
				build(o);
			}
		}
		return result;
	}
	
	/**
	 * 内置私有方法,进行递归build树形
	 * 
	 * @param o	
	 */
	private static void build(Map<String, Object> o) {
		List<Map<String, Object>> children = getChildren(o);
		if (!children.isEmpty()) {
			for (Map<String, Object> child : children) {
				result.add(child);
				build(child);
			}
		}
	}
	
	/**
	 * 获取孩子节点并添加到该节点上
	 * 
	 * @param node
	 * @return
	 */
	private static List<Map<String, Object>> getChildren(Map<String, Object> node) {
		List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
		Object id = node.get(sortId);
		for (Map<String, Object> child : nodes) {
			if (id.equals(child.get(sortPid))) {
				children.add(child);
			}
		}
		return children;
	}
}
