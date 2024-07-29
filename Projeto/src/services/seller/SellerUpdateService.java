package services.seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;
import model.entities.WorkLevel;
import utilities.LoggerUtility;

class SellerUpdateService {

	public static void updateSellerDetails(Scanner sc, Seller seller) {

		boolean updating = true;

		while (updating) {
			System.out.println("Escolha o campo para atualizar: ");
			System.out.println("1 - Nome");
			System.out.println("2 - Data de Nascimento");
			System.out.println("3 - Salário Base");
			System.out.println("4 - Departamento");
			System.out.println("5 - Nível de Trabalho");
			System.out.println("6 - Sair e Salvar");
			int option = sc.nextInt();
			sc.nextLine();

			switch (option) {
			case 1: {
				updateSellerName(sc, seller);

			}
				break;

			case 2: {
				updateSellerBirthDate(sc, seller);

			}

				break;

			case 3: {
				updateSellerBaseSalary(sc, seller);

			}
				break;

			case 4: {
				updateSellerDepartment(sc, seller);

			}
				break;

			case 5: {

				updateSellerWorkLevel(sc, seller);

			}
				break;

			case 6: {
				LoggerUtility.info("Salvando alterações no funcionario.....");
				updating = false;

			}
				break;

			default:
				LoggerUtility.error("Opção invalida!\nOpção digitada: ", option);
				continue;
			}
		}

	}

	private static void updateSellerName(Scanner sc, Seller seller) {
		boolean confirm = false;
		int choice = 0;
		String newName = null;
		while (!confirm) {
			System.out.println("Digite o novo nome: ");
			newName = sc.nextLine();
			System.out.println("O nome: " + newName + ". Está correto? (1 - Sim / Outro - Não");
			try {
				choice = Integer.parseInt(sc.nextLine());

				if (!newName.trim().isEmpty() && choice == 1) {
					LoggerUtility.info("Nome de funcionario validado: ", newName);
					seller.setNameSeller(newName);
					confirm = true;
				} else {
					LoggerUtility.warn("Nome invalidado: ", newName, ". Tente novamente");
					continue;
				}
			} catch (NumberFormatException e) {
				LoggerUtility.error("Opção: ", choice, " inválida tente novamente.");
				continue;
			}
		}
	}

	private static void updateSellerBirthDate(Scanner sc, Seller seller) {
		boolean confirm = false;
		int choice = 0;
		String dateInput = null;
		java.util.Date birthDate = null;

		while (!confirm) {

			System.out.println("Digite a nova data de nascimento (yyyy-MM-dd): ");
			dateInput = sc.nextLine();
			try {

				birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateInput);
				System.out.println("A data de nascimento: " + birthDate + ". Está correta? (1 - Sim / Outro - Não");
				choice = Integer.parseInt(sc.nextLine());
				if (choice == 1) {
					LoggerUtility.info("Data de aniversario de funcionario validada: ", birthDate);
					seller.setBirthDate(birthDate);
					confirm = true;
				} else {
					System.out.println("Ok, tente novamente.");
					continue;
				}

			} catch (NumberFormatException e) {
				LoggerUtility.error("Opção: ", choice, " inválida tente novamente.");
				continue;

			} catch (ParseException e) {
				LoggerUtility.error("Formato de data invalida: ", birthDate, "\nTente novamente.");
				continue;
			}

		}

	}

	private static void updateSellerBaseSalary(Scanner sc, Seller seller) {
		System.out.println("Digite o novo salario base: ");
		try {
			double newSalary = sc.nextDouble();
			sc.nextLine();
			if (newSalary > 0) {
				seller.setBaseSalary(newSalary);
			} else {
				LoggerUtility.warn("Salario invalido: ", newSalary);
			}
		} catch (InputMismatchException e) {
			LoggerUtility.error("Erro:\nEntrada invalidada, por gentileza insira um valor numerico\nCausa: ",
					e.getCause());
			sc.nextLine();
			return;
		}
	}

	private static void updateSellerDepartment(Scanner sc, Seller seller) {
		System.out.println("Escolha o novo departamento");
		DepartmentDao departmentDao = DaoFactory.createDepartmentDaoJDBC();
		System.out.println(departmentDao.findAll());
		int departmentId = sc.nextInt();
		sc.nextLine();
		Department department = departmentDao.findById(departmentId);
		if (department != null) {
			seller.setDepartment(department);

		} else {
			LoggerUtility.warn("Departamento não encontrado: ", department);
		}
	}

	private static void updateSellerWorkLevel(Scanner sc, Seller seller) {
		System.out.println("Escolha o novo nivel de trabalho:\n1 - Junior\n2 - Pleno\n3 - Senior");
		int workLevelChoice = sc.nextInt();
		sc.nextLine();

		switch (workLevelChoice) {
		case 1:
			seller.setWorkLevel(WorkLevel.JUNIOR);
			break;
		case 2:
			seller.setWorkLevel(WorkLevel.PLENO);
			break;
		case 3:
			seller.setWorkLevel(WorkLevel.SENIOR);
			break;
		default:
			LoggerUtility.warn("Opção inválida para nível de trabalho: ", workLevelChoice);
		}

	}

}
