package services;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import utilities.LoggerUtility;

public class DepartmentService {
	private static DepartmentDao departmentDao = DaoFactory.createDepartmentDaoJDBC();

	private static Department departmentObj;

	public static void mainDepartment(Scanner sc) {
		int option = 0;
		boolean confirm = false;

		while (!confirm) {
			try {
				System.out.println(
						"Escolha uma das opções\n1 - Mostrar Departamentos\n2 - Inserir Departamentos\n3 - Procurar por ID\n4 - Deletar Departamento\n5 - Atualizar dados \n9 - Sair");
				option = Integer.parseInt(sc.nextLine());

				System.out.println();

				if (option == 1) {
					showDepartments();
					System.out.println();
					continue;

				} else if (option == 2) {
					departmentInsert(sc);
					System.out.println();
					continue;

				} else if (option == 3) {
					findById(sc);
					System.out.println();
					continue;

				} else if (option == 4) {
					deleteById(sc);
					System.out.println();
					continue;

				} else if (option == 5) {
					updateDepartment(sc);
					System.out.println();
					continue;
				} else if (option == 9) {
					System.out.println("Saindo...");
					System.out.println();
					confirm = true;

				} else {
					System.out.println("Opção invalida, tente novamente");
					System.out.println();
					continue;

				}

			} catch (NumberFormatException e) {
				LoggerUtility.error("Entrada: " + option + ". Tente novamente");
				continue;
			}
		}

	}

	private static void showDepartments() {
		System.out.println("DEPARTAMENTOS ATIVOS: ");
		System.out.println(departmentDao.findAll());

	}

	private static Department departmentInsert(Scanner sc) {

		System.out.println("Digite o nome do departamento: ");
		sc.nextLine();
		String nameDepartment = sc.nextLine();
		departmentObj = new Department(nameDepartment);
		departmentDao.insert(departmentObj);

		return departmentObj;
	}

	private static Department findById(Scanner sc) {

		int id = 0;
		try {
			System.out.println("Digite um numero ID");
			id = Integer.parseInt(sc.nextLine());
			Department objId = departmentDao.findById(id);
			if (objId != null) {
				System.out.println("Departamento encontrado:");
				System.out.println(objId);
				return objId;
			} else {
				LoggerUtility.warn("ID: " + id + " inexistente, tente novamente");
				return null;
			}
		} catch (NumberFormatException e) {
			LoggerUtility.error("ID não validado, Tente novamente");
			return null;
		}

	}

	private static void deleteById(Scanner sc) {
		int id = 0;

		if (departmentDao.findAll().isEmpty()) {
			LoggerUtility.warn("Não existem departamentos ativos para deleção");
			return;
		}

		showDepartments();
		try {
			System.out.println("Digite um id para excluir um departamento: ");
			id = Integer.parseInt(sc.nextLine());
			System.out.println();
			departmentDao.deleteById(id);
			System.out.println();
		} catch (NumberFormatException e) {
			LoggerUtility.error("ID não validado, Tente novamente");
		}

	}

	private static void updateDepartment(Scanner sc) {
		System.out.println();
		showDepartments();
		System.out.println();

		System.out.println("Escolha um id para atualização");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println();
		Department department = departmentDao.findById(id);
		if (department != null) {
			System.out.println("Digite um nome novo para o departamento para atualização");
			String nameDepartment = sc.nextLine();
			department.setNameDepartment(nameDepartment);
			departmentDao.update(department);
		}

	}

}
