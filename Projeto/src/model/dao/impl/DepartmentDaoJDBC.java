package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			LoggerUtility.error("Erro na classe: DepartmentDaoJDBC", e.getMessage(), "\n");
			throw new RuntimeException("Detalhes erro: ", e.getCause());
		}
	}

	@Override
	public void insert(Department obj) {

		PreparedStatement st = null;
		Connection conn = null;

		try {
			conn = Db.getConnection();
			String sql = "INSERT INTO department (name_department) VALUES (?)";
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNameDepartment());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.assignId(id);
					LoggerUtility.info("Inserção de novo departamento com sucesso");

				}

				else {
					throw new DbException("Erro na inserção de um novo departamento");
				}
			}

		} catch (SQLException e) {
			LoggerUtility.error("Erro metodo insert ( Classe: DepartmentDaoJDBC\nCausa:", e.getCause());
			throw new DbException("Detalhes erro:", e);
		}

	}

	@Override
	public void update(Department obj) {

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		Connection conn = null;
		String sql = "DELETE FROM department WHERE id = ?";
		String sqlCheck = "SELECT COUNT(*) AS count FROM seller WHERE department_id = ?";
		try {
			conn = Db.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				System.out.println("Departamento não encontrado");
				return;

			} else {
				LoggerUtility.info("Departamento excluido com sucesso");
			}

		} catch (SQLException e) {
			LoggerUtility.error("Erro no metodo deleteById, classe: DepartmentDaoJDBC\nCausa:\n", e.getCause());
			throw new DbException(e.getMessage());

		}

	}

	@Override
	public Department findById(Integer id) {
		String sql = "SELECT * FROM department WHERE id = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection conn = null;

		try {

			conn = Db.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;

			}

			return null;

		} catch (SQLException e) {
			LoggerUtility.error("Erro no metodo findById (Classe: DepartmentDaoJDBC)\nMotivo: ", e.getMessage());
			throw new DbException("Erro ao buscar o departamento por ID: " + e.getMessage());
		}

	}

	@Override
	public List<Department> findAll() {
		String sql = "SELECT * FROM department";
		List<Department> list = new ArrayList<Department>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = Db.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				list.add(dep);

			}

			if (list.isEmpty()) {
				System.out.println();
				System.out.println("Não existem departamentos ativos");
			}

			return list;
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar todos os departamentos\nCausa: ", e.getCause());
		}

	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.assignId(rs.getInt("id"));
		dep.setNameDepartment(rs.getString("name_department"));

		return dep;
	}

}
