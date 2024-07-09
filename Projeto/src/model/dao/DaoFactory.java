package model.dao;

import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;
import model.dao.impl.WorkLevelDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC();

	}

	public static WorkLevelDao createWorkLevelDaoJDBC() {
		return new WorkLevelDaoJDBC();
	}

	public static DepartmentDao createDepartmentDaoJDBC() {
		return new DepartmentDaoJDBC();
	}

}
