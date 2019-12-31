package org.ore.framework.common.tree;

import java.util.ArrayList;
import java.util.List;

public class CFNode<T>
{
    private String nodeId;

    private String parentId;
    
    private String nodeName;

    private int deep;

    private T node;

    private List<CFNode<T>> childNode = new ArrayList<CFNode<T>>();

    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public T getNode()
    {
        return node;
    }

    public void setNode(T node)
    {
        this.node = node;
    }

    public List<CFNode<T>> getChildNode()
    {
        return childNode;
    }

    public void setChildNode(List<CFNode<T>> childNode)
    {
        this.childNode = childNode;
    }

    public int getDeep()
    {
        return deep;
    }

    public void setDeep(int deep)
    {
        this.deep = deep;
    }

    public String getNodeName()
    {
        return nodeName;
    }

    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    /**
     * 得到属性字符串
     * @return String 属性字符串
     */
    public String toString()
    {
        String line = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer("{");
        sb.append(line);
        sb.append("nodeId=").append((this.nodeId)).append(line);
        sb.append("parentId=").append((this.parentId)).append(line);
        sb.append("node=").append((this.node)).append(line);
        sb.append("childNode=").append((this.childNode)).append(line);
        sb.append("deep=").append((this.deep)).append(line);
        sb.append("nodeName=").append((this.nodeName)).append(line);
        sb.append("}");
        return sb.toString();
    }

}
