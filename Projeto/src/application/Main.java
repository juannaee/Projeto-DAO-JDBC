package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import db.Db;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.dao.WorkLevelDao;
import utilities.LoggerUtility;

public class Main {

	private static WorkLevelDao workLevel = DaoFactory.createWorkLevelDaoJDBC();
	private static DepartmentDao department = DaoFactory.createDepartmentDaoJDBC();
	private static SellerDao seller = DaoFactory.createSellerDao();

	public static void main(String[] args) {

		// Inicio Campo de LOG
		System.out.println("Inicio Campo de LOG");
		System.out.println("----------------------------");
		Db.TestConnection();
		dataBaseSetup();
		System.out.println("----------------------------");
		System.out.println("Fim Campo de LOG");
		// Fim Campo de LOG

	}

	// Inicio Configuração das tabelas.
	public static void dataBaseSetup() {
		Connection conn = null;
	
		try {

			conn = Db.getConnection();
			conn.setAutoCommit(false);

			if (!tableExists(conn, "department")) {
				department.createTable();
			}
			if (!tableExists(conn, "work_level")) {
				workLevel.createTable();
				workLevel.insertInititalWorkLevels();
			}

			if (!tableExists(conn, "seller")) {
				seller.createTable();
			}

			conn.commit();
			LoggerUtility.info("Tabelas criadas");

		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException rollbackEx) {
				LoggerUtility.error("Erro ao reveter a transação", rollbackEx.getMessage());
			}
			LoggerUtility.error("Configuração do banco de dados: ERROR\nCausa: ", e.getCause());
			throw new RuntimeException("\nDetalhes: ", e);

		} finally {
			Db.closeConnection();
		}
	}
	// Fim Configuração das tabelas

	// Metodo para verificar se existe a tabela no banco de dados
	private static boolean tableExists(Connection conn, String tableName) {
		ResultSet rs = null;

		try {
			rs = conn.getMetaData().getTables(null, null, tableName.toUpperCase(), null);
			return rs.next();
		} catch (SQLException e) {
			LoggerUtility.error("Erro no metodo tableExist\nCausa: ", e.getCause());
			throw new RuntimeException("\nDetalhes: ", e);

		}

	}

}
