package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import utilities.LoggerUtility;

public class Db {

	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {
			try {

				Properties props = loadProperties();
				String url = props.getProperty("dburl")  + "?allowPublicKeyRetrieval=true&useSSL=false";
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} catch (Exception e) {
				throw new DbException(e.getMessage());
			}
		}

		return conn;
	}

	private static Properties loadProperties() {

		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}
	}

	public static void TestConnection() {
		try {
			Db.getConnection();
			LoggerUtility.info("Conexão SQL: OK");

		} catch (Exception e) {
			LoggerUtility.error("Conexão SQL: ERRO\nMotivo:  ", e.getMessage());
			throw new DbException("Detalhes: ", e);

		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} catch (Exception e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} catch (Exception e) {

				throw new DbException(e.getMessage());
			}
		}

	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
