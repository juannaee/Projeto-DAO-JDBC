package model.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.Db;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import utilities.LoggerUtility;

public class DepartmentDaoJDBC implements DepartmentDao {

	@Override
	public void createTable() {
		String createDepartmentTable = "CREATE TABLE IF NOT EXISTS department (" + "id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "name_department VARCHAR(255) NOT NULL)";

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = Db.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(createDepartmentTable);
			LoggerUtility.info("tabela department: Criada");

		} catch (SQLException e) {
			LoggerUtility.error("Erro na criação da tabela department", e.getMessage(), "\n");
			throw new DbException("Detalhes do erro: ", e);
		} catch (Exception e) {
			LoggerUtility.error("Erro da na classe: DepartmentDaoJDBC", e.getMessage(), "\n");
			throw new RuntimeException("Detalhes erro: ", e.getCause());
		} 
	}

	@Override
	public void insert(Department obj) {
		
		
		

	}

	@Override
	public void update(Department obj) {

	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public Department findById(Integer id) {
		
		

		return null;
	}

	@Override
	public List<Department> findAll() {

		return null;
	}

}
