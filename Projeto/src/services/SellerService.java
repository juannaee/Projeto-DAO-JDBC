package services;

import java.util.Date;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import model.entities.WorkLevel;
import utilities.LoggerUtility;

public class SellerService {

	private static SellerDao sellerDao = DaoFactory.createSellerDao();

	public static void main(Scanner sc) {
		int option;

		do {
			System.out.println(
					"Escolha uma das opções\n1 - Mostrar Funcionarios\n2 - Inserir Funcionarios\n3 - Procurar por ID\n4 - Sair");
			option = sc.nextInt();

			if (option == 1) {
				showSeller();
				System.out.println();

			}

		} while (option != 4);

	}

	private static void sellerInsert(Scanner sc) {
		Date date = new Date();
		DepartmentDao departmentDao = DaoFactory.createDepartmentDaoJDBC();

		boolean continueInserting = true;
		while (continueInserting) {
			String nameSeller = "";
			Double salaryBase = 0.0;
			Department department = null;
			WorkLevel workLevel = null;

			boolean dataConfirmed = false;

			while (!dataConfirmed) {
				System.out.println("Digite o nome do funcionario: ");
				nameSeller = sc.nextLine();
				System.out.println("Digite o salario base do funcionario: ");
				salaryBase = sc.nextDouble();
				sc.nextLine();

				System.out.println("Escolha o departamento: ");
				System.out.println("Departamentos ativos: ");
				System.out.println(departmentDao.findAll());
				int departmentChoice = sc.nextInt();

				if (departmentDao.findById(departmentChoice) == null) {
					System.out.println("Departamento Inexistente!");
					continue;
				}
				department = departmentDao.findById(departmentChoice);

				System.out.println("Escolha o nivel de trabalho:\n1 - Junior\n2 - Pleno\n3 - Senior");
				int workLevelChoice = sc.nextInt();
				sc.nextLine();

				switch (workLevelChoice) {
				case 1: {
					workLevel = WorkLevel.JUNIOR;
					break;

				}

				case 2: {
					workLevel = WorkLevel.PLENO;
					break;

				}
				case 3: {
					workLevel = WorkLevel.SENIOR;
					break;
				}
				default:
					System.out.println("Opção Invalida para WorkLevel");
					System.out.println();
					continue;
				}
				System.out.println("Confirme os dados: ");
				System.out.println("Nome: " + nameSeller + "\n" + "Salario Base: " + salaryBase + "\n"
						+ "Departamento: " + department + "\n" + "Nivel de trabalho: " + workLevel.getDisplayName());
				System.out.println("1 - Confirmar\n2 - Corrigir");
				int confirm = sc.nextInt();
				sc.nextLine();
				if (confirm == 1) {
					dataConfirmed = true;
				}
			}

			Seller seller = new Seller(nameSeller, date, salaryBase, department, workLevel);
			sellerDao.insert(seller);
			System.out.println("Novo funcionario inserido: " + seller);
			LoggerUtility.info("Inserção de funcionario com sucesso ");

			System.out.println("Deseja inserir outro funcionario?\n1 - Sim\n2 - Não");
			int continueOption = sc.nextInt();
			sc.nextLine();

			if (continueOption != 1) {
				continueInserting = false;
			}
		}
	}

	private static Seller findById(Scanner sc) {
		System.out.println("Digite um numero ID");
		int id = sc.nextInt();
		Seller objId = sellerDao.findById(id);
		if (objId != null) {
			System.out.println("Funcionario encontrado: ");
			System.out.println(objId);
			return objId;

		} else {
			System.out.println("Funcionario inexistente");
			return objId;
		}
	}

	private static void showSeller() {
		System.out.println("FUNCIONARIOS: ");
		System.out.println(sellerDao.findAll());
	}

}
