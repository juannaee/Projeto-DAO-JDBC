package services;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import utilities.LoggerUtility;

public class DepartmentService {
	private static DepartmentDao department = DaoFactory.createDepartmentDaoJDBC();
	private static Scanner sc = new Scanner(System.in);
	private static Department departmentObj;
	
	
	
	public static Department departmentDataBase() {

		System.out.println("Escolha uma opção: \n1 - Ver Departamentos disponiveis\n2 - Adicionar novo Departamento");
		int optionMenu = sc.nextInt();

		switch (optionMenu) {
		case 1: {
			System.out.println("Departamentos ja existentes: ");
			System.out.println(department.findAll());
			return null;
		}

		case 2: {
			System.out.println("Digite o nome do departamento: ");
			sc.nextLine();
			String nameDepartment = sc.nextLine();
			departmentObj = new Department(nameDepartment);
			department.insert(departmentObj);
			LoggerUtility.info("Inserção de novo departamento com sucesso");
			return departmentObj;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + optionMenu);
		}

	}

}
