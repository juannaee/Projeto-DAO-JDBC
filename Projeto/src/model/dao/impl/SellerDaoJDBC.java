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
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import model.entities.WorkLevel;
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
					LoggerUtility.info("Inserção de novo vendedor com sucesso");
				}
			} else {
				throw new DbException("Erro na inserção de um novo vendedor");
			}

		} catch (SQLException e) {
			LoggerUtility.error("Erro metodo insert ( Classe: SellerDaoJDBC\nMotivo: ", e.getMessage(), "\n");
			throw new DbException("Detalhes erro: ", e);
		} finally {
			Db.closeStatement(st);
			Db.closeConnection();
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
		String sql = "SELECT * FROM seller WHERE id = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = Db.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Seller seller = instantiateSeller(rs);
				return seller;
			}

			return null;
		} catch (SQLException e) {
			LoggerUtility.error("Erro no metodo findById (Classe: SellerDaoJDBC)\nMotivo: ", e.getMessage());
			throw new DbException("Erro ao buscar o funcionario por ID: " + e.getMessage());
		}

	}

	@Override
	public List<Seller> findAll() {
		String sql = "SELECT * FROM seller";
		List<Seller> list = new ArrayList<Seller>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			conn = Db.getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
		}catch(SQLException e) {
			
		}

		return null;
	}

	private Seller instantiateSeller(ResultSet rs) throws SQLException {
		Seller seller = new Seller();
		seller.assignId(rs.getInt("id"));
		seller.setNameSeller(rs.getString("name_seller"));
		seller.setBaseSalary(rs.getDouble("base_salary"));
		seller.setBirthDate(rs.getDate("birth_date"));

		// Buscar e associar Department
		int departmentId = rs.getInt("department_id");
		if (departmentId > 0) {
			new DaoFactory();
			DepartmentDao departmentDao = DaoFactory.createDepartmentDaoJDBC();
			Department department = departmentDao.findById(departmentId);
			seller.setDepartment(department);
		}

		// Associar WorkLevel
		int workLevelId = rs.getInt("work_level_id");
		for (WorkLevel wl : WorkLevel.values()) {
			if (wl.getId() == workLevelId) {
				seller.setWorkLevel(wl);
				break;
			}
		}

		return seller;

	}

}
