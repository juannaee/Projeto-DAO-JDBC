package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;
import model.entities.WorkLevel;
import utilities.LoggerUtility;

public class SellerInsertService {
	public static void insertSellerDetails(Scanner sc) {
		boolean continueInserting = true;

		while (continueInserting) {
			String nameSeller = insertSellerName(sc);
			Date bithDate = insertSellerBirthDate(sc);
			Double baseSalary = insertSellerBaseSalary(sc);
			Department department = insertSellerDepartment(sc);

		}

	}

	private static String insertSellerName(Scanner sc) {
		boolean confirm = false;
		int choice = 0;
		String nameSeller = null;
		while (!confirm) {
			System.out.println("Digite nome do funcionario: ");
			nameSeller = sc.nextLine();
			if (nameSeller.trim().isEmpty()) {
				System.out.println("Nome invalido tente novamente!");
				continue;
			}

			System.out.println("O nome: " + nameSeller + ". Está correto? (1 - Sim / Outro - Não");
			try {
				choice = Integer.parseInt(sc.nextLine());

				if (choice != 1) {
					System.out.println("Ok, tente novamente");
					continue;
				}
			} catch (NumberFormatException e) {

			}

			if (choice == 1) {
				LoggerUtility.info("Nome de funcionario validado: ", nameSeller);
				confirm = true;
			} else {
				System.out.println("Ok, tente novamente!");
				continue;
			}
		}

		return nameSeller;
	}

	private static Date insertSellerBirthDate(Scanner sc) {
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
				if (choice != 1) {
					System.out.println("Ok, tente novamente");
					continue;
				}

			} catch (NumberFormatException e) {
				LoggerUtility.error("Opção: ", choice, " inválida tente novamente.");
				continue;

			} catch (ParseException e) {
				LoggerUtility.error("Formato de data invalida: ", birthDate, ". Tente novamente!");
				continue;
			}

			if (choice == 1) {
				LoggerUtility.info("Data de aniversario de funcionario validada: ", birthDate);
				confirm = true;

			} else {
				System.out.println("Ok, tente novamente");
			}
		}

		return birthDate;

	}

	private static Double insertSellerBaseSalary(Scanner sc) {

		boolean confirm = false;
		int choice = 0;
		Double baseSalary = 0.0;
		while (!confirm) {
			System.out.println("Digite salario base: ");
			try {

				baseSalary = Double.parseDouble(sc.nextLine());

				System.out.println("o salário base: " + baseSalary + ". Está correto? (1 - Sim / Outro - Não");
				try {

					choice = Integer.parseInt(sc.nextLine());
					if (choice != 1) {
						System.out.println("Ok, tente novamente");
						continue;
					}
				} catch (NumberFormatException e) {
					LoggerUtility.error("Entrada:", choice, " invalida, por gentileza insira um valor numerico");
					continue;

				}

			} catch (NumberFormatException e) {
				LoggerUtility.error("Entrada:", baseSalary,
						" invalida para salário base, por gentileza insira um valor numerico");
				continue;

			}

			if (choice == 1) {
				LoggerUtility.info("Salário base validado: ", baseSalary);
				confirm = true;

			} else {
				System.out.println("Ok, tente novamente");
			}
		}
		return baseSalary;
	}

	private static Department insertSellerDepartment(Scanner sc) {
		boolean confirm = false;
		int choice = 0;
		int departmentId = 0;
		Department department = null;

		while (!confirm) {
			System.out.println("Escolha o departamento");
			DepartmentDao departmentDao = DaoFactory.createDepartmentDaoJDBC();
			System.out.println(departmentDao.findAll());
			try {
				departmentId = Integer.parseInt(sc.nextLine());
				department = departmentDao.findById(departmentId);
				if (department == null) {
					LoggerUtility.warn("Departamento não encontrado: ", department, ". Tente novamente");
					continue;
				}

				System.out.println("o departamento: " + department + ". Está correto? (1 - Sim / Outro - Não");
				try {

					choice = Integer.parseInt(sc.nextLine());
					if (choice != 1) {
						System.out.println("Ok, tente novamente");
						continue;
					}
				} catch (NumberFormatException e) {
					LoggerUtility.error("Entrada:", choice, " invalida, por gentileza insira um valor numerico");
					continue;

				}

			} catch (NumberFormatException e) {
				LoggerUtility.error("Entrada:", departmentId, " invalida, por gentileza insira um valor numerico");
				continue;
			}

			if (choice == 1) {
				LoggerUtility.info("Departamento validado: ", department);
				confirm = true;
			} else {
				System.out.println("Ok, tente novamente");
			}

		}

		return department;
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
