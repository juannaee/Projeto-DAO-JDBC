/**
 * 
 */
package model.dao;

import java.sql.SQLException;

/**
 * 
 */
public interface WorkLevelDao {

	void createTable() throws SQLException;

	void insertInititalWorkLevels() throws SQLException;

}
