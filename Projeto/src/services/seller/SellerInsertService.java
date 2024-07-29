package services.seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
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
			WorkLevel workLevel = insertSellerWorkLevel(sc);
			Seller seller = new Seller(nameSeller, bithDate, baseSalary, department, workLevel);

			System.out.println("O funcionario: " + seller + "\n Está correto? (1 - Sim / Outro - Não");
			int choice = 0;
			try {
				choice = Integer.parseInt(sc.nextLine());

				if (choice != 1) {
					System.out.println("Ok, tente novamente.");
					continue;
				}

				System.out.println("Deseja inserir mais um funcionario? (1 - Sim / Outro - Não");
				int choice_continue = 0;
				try {
					choice_continue = Integer.parseInt(sc.nextLine());
					if (choice_continue == 1) {
						System.out.println("Ok.");
						continue;
					}

					if (choice_continue != 1) {
						System.out.println("Funcionario validado.");
						SellerDao sellerDao = DaoFactory.createSellerDaoJDBC();
						sellerDao.insert(seller);
						continueInserting = false;

					}

				} catch (NumberFormatException e) {
					LoggerUtility.error("Opção: ", choice_continue, " inválida tente novamente.");
					continue;

				}

			} catch (NumberFormatException e) {
				LoggerUtility.error("Opção: ", choice, " inválida tente novamente.");
				continue;

			}

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
					System.out.println("Ok, tente novamente.");
					continue;
				}
			} catch (NumberFormatException e) {
				LoggerUtility.error("Opção: ", choice, " inválida tente novamente.");
				continue;

			}

			if (choice == 1) {
				LoggerUtility.info("Nome de funcionario validado: ", nameSeller);
				confirm = true;
			} else {
				System.out.println("Ok, tente novamente.");
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
				if (choice == 1) {
					LoggerUtility.info("Data de aniversario de funcionario validada: ", birthDate);
					confirm = true;
				} else {
					System.out.println("Ok, tente novamente.");
					continue;
				}

			} catch (NumberFormatException e) {
				LoggerUtility.error("Opção: ", choice, " inválida tente novamente.");
				continue;

			} catch (ParseException e) {
				LoggerUtility.error("Formato de data invalida: ", birthDate, ". Tente novamente!");
				continue;
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
					if (choice == 1) {
						LoggerUtility.info("Salário base validado: ", baseSalary);
						confirm = true;

					} else {
						System.out.println("Ok, tente novamente.");
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

				System.out.println("o departamento: " + department + "Está correto? (1 - Sim / Outro - Não");
				try {

					choice = Integer.parseInt(sc.nextLine());
					if (choice == 1) {
						LoggerUtility.info("Departamento validado: ", department);
						confirm = true;

					} else {
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

		}

		return department;
	}

	private static WorkLevel insertSellerWorkLevel(Scanner sc) {
		boolean confirm = false;
		int choice = 0;
		int workLevelChoice = 0;
		WorkLevel workLevel = null;

		while (!confirm) {
			System.out.println("Escolha o novo nivel de trabalho:\n1 - Junior\n2 - Pleno\n3 - Senior");
			try {
				workLevelChoice = Integer.parseInt(sc.nextLine());
				switch (workLevelChoice) {
				case 1:
					workLevel = WorkLevel.JUNIOR;
					break;
				case 2:
					workLevel = WorkLevel.PLENO;
					break;
				case 3:
					workLevel = WorkLevel.SENIOR;
					break;
				default:
					LoggerUtility.warn("Opção inválida para nível de trabalho: ", workLevelChoice, ". Tente novamente");
					continue;
				}

				System.out.println("O nivel de trabalho : " + workLevel + "Está correto? (1 - Sim / Outro - Não");
				try {

					choice = Integer.parseInt(sc.nextLine());
					if (choice == 1) {
						LoggerUtility.info("Nivel de trabalho validado: ", workLevel);
						confirm = true;
					} else {
						System.out.println("Ok, tente novamente");
						continue;
					}
				} catch (NumberFormatException e) {
					LoggerUtility.error("Entrada:", choice, " invalida, por gentileza insira um valor numerico");
					continue;

				}

			} catch (NumberFormatException e) {
				LoggerUtility.error("Entrada:", workLevelChoice, " invalida, por gentileza insira um valor numerico");
				continue;

			}

		}

		return workLevel;
	}

}
