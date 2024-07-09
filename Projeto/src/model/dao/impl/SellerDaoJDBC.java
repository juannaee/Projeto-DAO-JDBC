package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.Db;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Seller;
import utilities.LoggerUtility;

public class SellerDaoJDBC implements SellerDao {

	@Override
	public void createTable() {
		String createSellerTable = "CREATE TABLE IF NOT EXISTS seller (" + "id INT PRIMARY KEY AUTO_INCREMENT, "
				+ "name_seller VARCHAR(255) NOT NULL, " + "birth_date DATE," + "base_salary DOUBLE NOT NULL, "
				+ "department_id INT, " + "work_level_id INT, "
				+ "FOREIGN KEY (department_id) REFERENCES department(id), "
				+ "FOREIGN KEY (work_level_id) REFERENCES work_level(id))";

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = Db.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(createSellerTable);
			LoggerUtility.info("tabela seller: Criada");

		} catch (SQLException e) {
			LoggerUtility.error("Criação Tabela seller: ERROR\n Motivo: ", e.getMessage(), "\n");
			throw new DbException("Erro ao Criar a tabela seller", e);

		} catch (Exception e) {
			LoggerUtility.error("Erro na classe SellerDaoJDBC\nMotivo; ", e.getMessage(), "\n");
			throw new RuntimeException("Detalhes erro: ", e);
		}

	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		Connection conn = null;

		try {
			conn = Db.getConnection();
			String sql = "INSERT INTO seller (name_seller, birth_date, base_salary, department_id, work_level_id)"
					+ "VALUES (?, ? , ? , ? , ?)";
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNameSeller());
			st.setDate(2, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(3, obj.getBaseSalary());
			st.setInt(4, obj.getDepartment().getId());
			st.setInt(5, obj.getWorkLevel().getId());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.assignId(id);
				}
			} else {
				throw new DbException("Erro na inserção de um novo vendedor");
			}

		} catch (SQLException e) {
			LoggerUtility.error("Erro metodo insert ( Classe: SellerDaoJDBC\nMotivo: ", e.getMessage(), "\n");
			throw new DbException("Detalhes erro: ", e);
		}

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		return null;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
