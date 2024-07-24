package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import utilities.LoggerUtility;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void createTable() {
		String createDepartmentTable = "CREATE TABLE IF NOT EXISTS department (" + "id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "name_department VARCHAR(255) NOT NULL)";

		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(createDepartmentTable);
			LoggerUtility.info("Tabela department: Criada");
		} catch (SQLException e) {
			LoggerUtility.error("Erro na criação da tabela department\n");
			throw new DbException("Detalhes do erro: ", e);
		}
	}

	@Override
	public void insert(Department obj) {
		String sql = "INSERT INTO department (name_department) VALUES (?)";
		try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, obj.getNameDepartment());
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				try (ResultSet rs = st.getGeneratedKeys()) {
					if (rs.next()) {
						int id = rs.getInt(1);
						obj.assignId(id);
						LoggerUtility.info("Inserção de novo departamento com sucesso");
					} else {
						LoggerUtility.warn("Não foi possível obter a chave gerada para o novo departamento.");
					}
				}
			}
		} catch (SQLException e) {
			LoggerUtility.error("Erro método insert");
			throw new DbException("Detalhes erro:", e);
		}
	}

	@Override
	public void update(Department obj) {
		String sql = "UPDATE department SET name_department = ? WHERE id = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setString(1, obj.getNameDepartment());
			st.setInt(2, obj.getId());
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				LoggerUtility.info("Departamento atualizado com sucesso");
			} else {
				LoggerUtility.warn("Departamento não encontrado para atualização. ID: ", obj.getId());
			}
		} catch (SQLException e) {
			LoggerUtility.error("Erro no metodo update");
			throw new DbException("Detalhes do erro: ", e);
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM department WHERE id = ?";
		String sqlCheck = "SELECT COUNT(*) AS count FROM seller WHERE department_id = ?";

		try (PreparedStatement stCheck = conn.prepareStatement(sqlCheck);
				PreparedStatement stDelete = conn.prepareStatement(sql)) {
			stCheck.setInt(1, id);
			try (ResultSet rs = stCheck.executeQuery()) {
				if (rs.next() && rs.getInt("count") > 0) {
					LoggerUtility.warn("Existem vendedores vinculados a este departamento.");
					return;
				}
			}

			stDelete.setInt(1, id);
			int rowsAffected = stDelete.executeUpdate();

			if (rowsAffected == 0) {
				LoggerUtility.warn("Departamento não encontrado para deleção. ID: ", id);
			} else {
				LoggerUtility.info("Departamento excluído com sucesso.");
			}
		} catch (SQLException e) {
			LoggerUtility.error("Erro no método deleteById");
			throw new DbException("Detalhes do erro: ", e);
		}
	}

	@Override
	public Department findById(Integer id) {
		String sql = "SELECT * FROM department WHERE id = ?";
		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					Department dep = instantiateDepartment(rs);
					return dep;
				}
			}
			LoggerUtility.warn("Departamento não encontrado. ID: " + id);
			return null;
		} catch (SQLException e) {
			LoggerUtility.error("Erro no método findById");
			throw new DbException("Detalhes do erro: ", e);
		}
	}

	@Override
	public List<Department> findAll() {
		String sql = "SELECT * FROM department";
		List<Department> list = new ArrayList<>();
		try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				list.add(dep);
			}
			if (list.isEmpty()) {
				LoggerUtility.warn("Nenhum departamento encontrado");
			}
			return list;
		} catch (SQLException e) {
			LoggerUtility.error("Erro no método findAll");
			throw new DbException("Detalhes do erro: ", e);
		}
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.assignId(rs.getInt("id"));
		dep.setNameDepartment(rs.getString("name_department"));
		return dep;
	}
}
