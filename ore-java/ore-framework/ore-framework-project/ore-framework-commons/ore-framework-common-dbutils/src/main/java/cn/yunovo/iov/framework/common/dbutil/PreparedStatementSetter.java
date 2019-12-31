package cn.yunovo.iov.framework.common.dbutil;

import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * 设置PrePareStament的值
 * 
 * @author huangzz
 * @version
 */
public interface PreparedStatementSetter {
	void setValues(PreparedStatement ps) throws SQLException;
}
