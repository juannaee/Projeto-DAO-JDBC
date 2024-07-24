package model.dao;

import db.Db;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;
import model.dao.impl.WorkLevelDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDaoJDBC() {
		return new SellerDaoJDBC();

	}

	public static WorkLevelDao createWorkLevelDaoJDBC() {
		return new WorkLevelDaoJDBC();
	}

	public static DepartmentDao createDepartmentDaoJDBC() {
		return new DepartmentDaoJDBC(Db.getConnection());
	}

}
