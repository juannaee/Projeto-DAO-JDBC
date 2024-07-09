package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import db.Db;
import db.DbException;
import model.entities.WorkLevel;
import utilities.LoggerUtility;

public class WorkLevelDaoJDBC implements model.dao.WorkLevelDao {

	@Override
	public void createTable() {

		String createWorkLevelTable = "CREATE TABLE IF NOT EXISTS work_level (" + "id INT PRIMARY KEY,"
				+ "display_name VARCHAR(255) NOT NULL," + "bonus_percentage DOUBLE NOT NULL)";

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = Db.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(createWorkLevelTable);
			LoggerUtility.info("tabela work_level: Criada");

		} catch (SQLException e) {
			LoggerUtility.error("Erro na criação da tabela work_level", e.getMessage(), "\n");
			throw new DbException("Detalhes do erro: ", e);
		} catch (Exception e) {
			LoggerUtility.error("Erro na classe: WorkLevelDaoJDCB", e.getMessage(), "\n");
			throw new RuntimeException("Detalhes erro: ", e);
		} 
	}

	@Override
	public void insertInititalWorkLevels() {

		String insertWorkLevel = "INSERT INTO work_level (id,display_name,bonus_percentage) VALUES (?,?,?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Db.getConnection();
			ps = conn.prepareStatement(insertWorkLevel);

			for (WorkLevel level : WorkLevel.values()) {
				ps.setInt(1, level.getId());
				ps.setString(2, level.getDisplayName());
				ps.setDouble(3, level.getBonusPercentage());
				ps.addBatch();
			}

			ps.executeBatch();
			LoggerUtility.info("Niveis de trabalho iniciais inseridos");

		}

		catch (SQLException e) {
			LoggerUtility.error("Erro na inserção inicial da tabela work_level", e.getMessage(), "\n");
			throw new DbException("Detalhes do erro: ", e);

		}

		catch (Exception e) {
			LoggerUtility.error("Erro na classe: WorkLevelDaoJDCB", e.getMessage(), "\n");
			throw new RuntimeException("Detalhes erro: ", e);

		}

	
			

	}

}
