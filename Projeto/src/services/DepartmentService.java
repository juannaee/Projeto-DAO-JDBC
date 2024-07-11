package services;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import utilities.LoggerUtility;

public class DepartmentService {
	private static DepartmentDao department = DaoFactory.createDepartmentDaoJDBC();
	
	private static Department departmentObj;

	public static void mainDepartment(Scanner sc) {
		int option;
		do {
			System.out
					.println("Escolha uma das opções\n1 - Mostrar Departamentos\n2 - Inserir Departamentos\n3 - Sair");
			option = sc.nextInt();
			System.out.println();

			if (option == 1) {
				showDepartments();
				System.out.println();

			} else if (option == 2) {
				departmentInsert(sc);
				System.out.println();

			} else if (option == 3) {
				System.out.println("Saindo...");
				System.out.println();

			} else {
				System.out.println("Opção invalida");
				System.out.println();

			}

		} while (option != 3);

	}

	private static void showDepartments() {
		System.out.println("DEPARTAMENTOS: ");
		System.out.println(department.findAll());
	}

	private static Department departmentInsert(Scanner sc) {

		System.out.println("Digite o nome do departamento: ");
		sc.nextLine();
		String nameDepartment = sc.nextLine();
		departmentObj = new Department(nameDepartment);
		department.insert(departmentObj);
		LoggerUtility.info("Inserção de novo departamento com sucesso");
		return departmentObj;
	}

}
