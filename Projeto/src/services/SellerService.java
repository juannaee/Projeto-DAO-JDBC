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

	private static SellerDao sellerDao = DaoFactory.createSellerDaoJDBC();

	public static void mainSeller(Scanner sc) {
		int option;

		do {
			System.out.println(
					"Escolha uma das opções\n1 - Mostrar Funcionarios\n2 - Inserir Funcionarios\n3 - Procurar por ID\n4 - Deletar Por ID\n5 - Atualizar Funcionario\n9 - Sair");
			option = sc.nextInt();
			sc.nextLine();

			if (option == 1) {
				showSeller();
				System.out.println();

			} else if (option == 2) {
				sellerInsert(sc);
				System.out.println();
			} else if (option == 3) {
				findById(sc);
				System.out.println();

			} else if (option == 4) {
				deleteById(sc);
				System.out.println();

			} else if (option == 5) {
				updateSeller(sc);
				System.out.println();
			} else if (option == 9) {
				System.out.println("Saindo...");
				System.out.println();
			} else {
				System.out.println("Opção invalida");
				System.out.println();
			}

		} while (option != 9);

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
				sc.nextLine();

				if (departmentDao.findById(departmentChoice) == null) {
					LoggerUtility.warn(
							"Departamento inexistente, assim possivel realocar o funcionario para um departamento existente");
					department = null;
				}
				department = departmentDao.findById(departmentChoice);

				System.out.println("Escolha o nivel de trabalho:\n1 - Junior\n2 - Pleno\n3 - Senior");
				System.out.println();
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
				System.out.println();
				System.out.println("Confirme os dados: ");
				System.out.println("Nome: " + nameSeller + "\n" + "Salario Base: " + salaryBase + "\n"
						+ "Departamento: " + department + "\n" + "Nivel de trabalho: " + workLevel.getDisplayName());
				System.out.println();
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
			System.out.println();
			System.out.println("Deseja inserir outro funcionario?\n1 - Sim\n2 - Não");
			int continueOption = sc.nextInt();
			sc.nextLine();
			System.out.println();

			if (continueOption != 1) {
				continueInserting = false;
			}
		}
	}

	private static Seller findById(Scanner sc) {
		System.out.println("Digite um numero ID");
		int id = sc.nextInt();
		System.out.println();
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
		System.out.println("FUNCIONARIOS ATIVOS: ");
		System.out.println(sellerDao.findAll());
	}

	private static void deleteById(Scanner sc) {

		if (sellerDao.findAll().isEmpty()) {
			return;
		}
		showSeller();
		System.out.println("Digite um id para excluir um funcionario: ");
		int id = sc.nextInt();
		System.out.println();
		sellerDao.deleteById(id);
		System.out.println();

	}

	private static void updateSeller(Scanner sc) {
		showSeller();
		System.out.println("Escolha um id para atualizar ");
		int id = sc.nextInt();
		sc.nextLine();
		Seller seller = sellerDao.findById(id);
		System.out.println();
		SellerUpdateService.updateSellerDetails(sc, seller);
		try {
			sellerDao.update(seller);
			System.out.println();

		} catch (Exception e) {
			LoggerUtility.error("Erro ao salvar atualizações ", e.getMessage());
		}

	}

}
