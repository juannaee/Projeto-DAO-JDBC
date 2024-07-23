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

	private static Connection conn = null;
	private static Statement stmt = null;
	private static PreparedStatement st = null;
	private static ResultSet rs = null;

	@Override
	public void createTable() {
		String createDepartmentTable = "CREATE TABLE IF NOT EXISTS department (" + "id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "name_department VARCHAR(255) NOT NULL)";

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

		String sql = "INSERT INTO department (name_department) VALUES (?)";
		try {

			conn = Db.getConnection();
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
					LoggerUtility.warn("Não foi possivel inserir um novo departamento!!");
				}
			}

		} catch (SQLException e) {
			LoggerUtility.error("Erro metodo insert ( Classe: DepartmentDaoJDBC\nCausa:", e.getCause());
			throw new DbException("Detalhes erro:", e);
		}

	}

	@Override
	public void update(Department obj) {
		String sql = "UPDATE department SET name_department = ? WHERE id = ?";

		try {
			conn = Db.getConnection();
			st = conn.prepareStatement(sql);
			st.setString(1, obj.getNameDepartment());
			st.setInt(2, obj.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				LoggerUtility.info("Departamento atualizado com sucesso");
			} else {
				LoggerUtility.warn("Departamento não encontrado para atualização. ID: ", obj.getId());
			}

		} catch (SQLException e) {
			LoggerUtility.error("Erro no metodo update, classe: DepartmentDaoJDBC\nCausa: ", e.getCause());
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM department WHERE id = ?";
		String sqlCheck = "SELECT COUNT(*) AS count FROM seller WHERE department_id = ?";

		try {

			conn = Db.getConnection();
			st = conn.prepareStatement(sqlCheck);
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next() && rs.getInt("count") > 0) {
				LoggerUtility.warn("Existem vendedores vinculados a esse departamento! ");
				return;

			}

			st = conn.prepareStatement(sql);
			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				LoggerUtility.warn("Departamento para deleção não encontrado");
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
		try {
			conn = Db.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;

			}

			LoggerUtility.warn("Departamento não encontrado\nID: " + id);
			return null;

		} catch (SQLException e) {
			LoggerUtility.error("Erro no metodo findById (Classe: DepartmentDaoJDBC)");
			throw new DbException("Erro ao buscar o departamento por ID: " + e.getMessage());
		}

	}

	@Override
	public List<Department> findAll() {
		String sql = "SELECT * FROM department";
		List<Department> list = new ArrayList<Department>();

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
				LoggerUtility.warn("Não existem departamentos ativos");
			}

			return list;
		} catch (SQLException e) {
			LoggerUtility.error("Erro no metodo findAll (Classe: DepartmentJDBC)");
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
