package application;

import db.DataBaseInitializer;
import db.Db;
import services.DepartmentService;

public class Main {

	public static void main(String[] args) {
		Db.TestConnection();
		DataBaseInitializer.dataBaseSetup();
		System.out.println("Menu Data Base");
		DepartmentService.departmentDataBase();
	
	}

}
