package services;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	private static DepartmentDao department = DaoFactory.createDepartmentDaoJDBC();

	private static Department departmentObj;

	public static void mainDepartment(Scanner sc) {
		int option;
		do {
			System.out.println(
					"Escolha uma das opções\n1 - Mostrar Departamentos\n2 - Inserir Departamentos\n3 - Procurar por ID\n4 - Deletar Departamento\n9 - Sair");
			option = sc.nextInt();
			System.out.println();

			if (option == 1) {
				showDepartments();
				System.out.println();

			} else if (option == 2) {
				departmentInsert(sc);
				System.out.println();

			} else if (option == 3) {
				findById(sc);
				System.out.println();

			} else if (option == 4) {
				deleteById(sc);
				System.out.println();

			}

			else if (option == 9) {
				System.out.println("Saindo...");
				System.out.println();

			}

			else {
				System.out.println("Opção invalida");
				System.out.println();

			}

		} while (option != 9);

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

		return departmentObj;
	}

	private static Department findById(Scanner sc) {

		System.out.println("Digite um numero ID");
		int id = sc.nextInt();
		Department objId = department.findById(id);
		if (objId != null) {
			System.out.println("Departamento encontrado:");
			System.out.println(objId);
			return objId;
		} else {
			System.out.println("Departamento inexistente");
			return objId;
		}

	}

	private static void deleteById(Scanner sc) {

		if (department.findAll().isEmpty()) {
			return;
		}

		showDepartments();
		System.out.println("Digite um id para excluir um departamento: ");
		int id = sc.nextInt();
		System.out.println();
		department.deleteById(id);
		System.out.println();

	}

}
