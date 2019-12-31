package org.ore.framework.common.tree;

public class Demo implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 权限ID
	 */
	private Integer Id;
	/**
	 * 父ID
	 */
	private Integer parentId;
	/**
	 * 权限名称（菜单、按钮名称）
	 */
	private String name;

	/**
	 * 节点是否是叶子，0：不是，1：是
	 */
	private String desc;

	public Demo() {
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
        sb.append("Id=").append((this.Id)).append(line);
        sb.append("parentId=").append((this.parentId)).append(line);
        sb.append("name=").append((this.name)).append(line);
        sb.append("desc=").append((this.desc)).append(line);
        sb.append("}");
        return sb.toString();
    }

}
