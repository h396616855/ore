package org.ore.framework.common.tree;

import java.util.ArrayList;
import java.util.List;

import org.ore.framework.common.tree.CFNode;
import org.ore.framework.common.tree.CFTree;

public class DemoTest {

	public static void main(String[] args) {
		CFTree<Demo> tree = new CFTree<Demo>();
		tree.setIsPrint(true);
		List<Demo> dataList = new ArrayList<Demo>();
		Demo limit = new Demo();
		limit.setId(1);
		limit.setParentId(0);
		limit.setName("组织1");
		limit.setDesc("组织1");
		dataList.add(limit);
		
		Demo limit1 = new Demo();
		limit1.setId(2);
		limit1.setParentId(1);
		limit1.setName("组织1-1");
		limit1.setDesc("组织1-1");
		dataList.add(limit1);
		
		Demo limit11 = new Demo();
		limit11.setId(3);
		limit11.setParentId(2);
		limit11.setName("组织1-1-1");
		limit11.setDesc("组织1-1-1");
		dataList.add(limit11);
		
		Demo limit111 = new Demo();
		limit111.setId(31);
		limit111.setParentId(3);
		limit111.setName("组织1-1-1");
		limit111.setDesc("组织1-1-1");
		dataList.add(limit111);
		
		Demo limit2 = new Demo();
		limit2.setId(4);
		limit2.setParentId(0);
		limit2.setName("组织2");
		limit2.setDesc("组织2");
		dataList.add(limit2);
		
		Demo limit3 = new Demo();
		limit3.setId(5);
		limit3.setParentId(0);
		limit3.setName("组织3");
		limit3.setDesc("组织3");
		dataList.add(limit3);
		tree.buildTree(dataList,"getId","getParentId","getName");
		
		System.out.println("取某个父ID的节点列表");
		CFNode<Demo> node = tree.getNodeSourceMap().get("1");
		System.out.print(node.getNodeName()+"下所以节点ID：");
		System.out.println(tree.getNodeIds(node));
		// System.out.println(tree.getNodeMap());
		// System.out.println(tree.getDataList());
		
	}
}
