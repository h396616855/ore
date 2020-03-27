package org.ore.framework.common.tree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.TreeMap;

public class CFTree<T> {

	// 节点数据源
	private List<CFNode<T>> dataList = new ArrayList<CFNode<T>>();

	// 节点Map,和 dataList 数据节点数量一致
	public Map<String, CFNode<T>> nodeSourceMap = new TreeMap<String, CFNode<T>>();

	// 生成树后的Map
	private Map<String, CFNode<T>> treeTargetMap = new TreeMap<String, CFNode<T>>();

	// 被过滤的Map
	private Map<String, String> fiterMap = new TreeMap<String, String>();

	private List<String> fiterList = new ArrayList<String>();

	// 获取某个节点下所以ID字符串
	private String nodeIds;

	private Boolean isPrint = false;

	/**
	 * 查找父节点
	 * 
	 * @param node
	 */
	private void findParentNode(CFNode<T> node) {
		CFNode<T> parentNode = null;
		if ("0".equals(node.getParentId())) {
			node.setDeep(1);
			treeTargetMap.put(node.getNodeId(), node);
			fiterList.add(node.getNodeId());
			return;
		} else {
			parentNode = nodeSourceMap.get(node.getParentId());
			//if (null != treeTargetMap.get(parentNode.getNodeId())) {
				if (parentNode.getNodeId().equals(node.getParentId())) {
					node.setDeep(parentNode.getDeep() + 1);
					parentNode.getChildNode().add(node);
					fiterList.add(node.getNodeId());
					return;
				}
			//}
		}
	}

	/**
	 * 打印节点信息
	 * 
	 * @param tree
	 */
	public void printNode(CFNode<T> tree) {
		List<CFNode<T>> list = tree.getChildNode();
		for (CFNode<T> node : list) {
			for (int i = 0; i < node.getDeep(); i++) {
				if (isPrint) {
					System.out.print(" . ");
				}
			}
			if (isPrint) {
				System.out.println(node.getNodeName());
			}
			printNode(node);
		}
	}

	public String getNodeIds(CFNode<T> tree) {
		if (null == nodeIds) {
			nodeIds = tree.getNodeId();
		} else {
			nodeIds = nodeIds + "," + tree.getNodeId();
		}

		List<CFNode<T>> list = tree.getChildNode();
		for (CFNode<T> node : list) {
			getNodeIds(node);
		}
		return nodeIds;
	}

	/**
	 * 构建一个树节点
	 * 
	 * @param dataList
	 */
	@SuppressWarnings("unchecked")
	public void buildTree(List<T> list, String methodId, String methodParentId,String methodName) {
		try {
			for (T t : list) {
				
				Method getIdMethod = t.getClass().getDeclaredMethod(methodId);
				Object id = getIdMethod.invoke(t);
				Method getMethodParentId = t.getClass().getDeclaredMethod(methodParentId);
				Object parentId = getMethodParentId.invoke(t);
				
				Method getMethodName = t.getClass().getDeclaredMethod(methodName);
				Object nodeName = getMethodName.invoke(t);

				CFNode<T> node = new CFNode<T>();
				node.setNodeId(id.toString());
				node.setParentId(parentId.toString());
				if(null != nodeName){
					node.setNodeName(nodeName.toString());
				}
				node.setNode(t);
				this.getDataList().add(node);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return;
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		for (CFNode<T> node : dataList) {
			nodeSourceMap.put(node.getNodeId(), node);
			fiterMap.put(node.getNodeId(), node.getNodeId());
		}

		// 进行树节点处理
		while (true) {
			Iterator<String> iterator = fiterMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				CFNode<T> node = nodeSourceMap.get(key);
				findParentNode(node);
			}

			for (String key : fiterList) {
				fiterMap.remove(key);
			}

			fiterList.clear();

			if (0 == fiterMap.size()) {
				break;
			}
		}

		for (Object obj : treeTargetMap.entrySet().toArray()) {
			Map.Entry<Integer, CFNode<T>> entry = (Entry<Integer, CFNode<T>>) obj;
			CFNode<T> tree = entry.getValue();
			if (isPrint) {
				System.out.print(" . ");
				System.out.println(tree.getNodeName());
			}

			printNode(tree);
		}
	}

	public Map<String, CFNode<T>> getTreeTargetMap() {
		return treeTargetMap;
	}

	public Map<String, CFNode<T>> getNodeSourceMap() {
		return nodeSourceMap;
	}

	public void setDataList(List<CFNode<T>> dataList) {
		this.dataList = dataList;
	}

	public List<CFNode<T>> getDataList() {
		return dataList;
	}

	public void setIsPrint(Boolean isPrint) {
		this.isPrint = isPrint;
	}

}
