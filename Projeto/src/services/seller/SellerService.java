package services.seller;

import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;
import utilities.LoggerUtility;

public class SellerService {

	private static SellerDao sellerDao = DaoFactory.createSellerDaoJDBC();

	public static void mainSeller(Scanner sc) {
		int option = 0;
		boolean confirm = false;

		while (!confirm) {
			try {
				System.out.println(
						"Escolha uma das opções\n1 - Mostrar Funcionarios\n2 - Inserir Funcionarios\n3 - Procurar por ID\n4 - Deletar Por ID\n5 - Atualizar Funcionario\n9 - Sair");
				option = Integer.parseInt(sc.nextLine());

				if (option == 1) {
					showSeller();
					System.out.println();
					continue;

				} else if (option == 2) {
					sellerInsert(sc);
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
					updateSeller(sc);
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

	private static void sellerInsert(Scanner sc) {
		SellerInsertService.insertSellerDetails(sc);
	}

	private static Seller findById(Scanner sc) {
		System.out.println("Digite um numero ID");
		int id = 0;
		try {
			id = Integer.parseInt(sc.nextLine());
			System.out.println();
			Seller objId = sellerDao.findById(id);
			if (objId != null) {
				System.out.println("Funcionario encontrado: ");
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

	private static void showSeller() {
		System.out.println("FUNCIONARIOS ATIVOS: ");
		System.out.println(sellerDao.findAll());
	}

	private static void deleteById(Scanner sc) {

		int id = 0;
		if (sellerDao.findAll().isEmpty()) {
			LoggerUtility.warn("Não existem funcionarios ativos para deleção");
			return;
		}
		showSeller();

		try {
			System.out.println("Digite um id para excluir um funcionario: ");
			id = Integer.parseInt(sc.nextLine());
			System.out.println();
			sellerDao.deleteById(id);
			System.out.println();
		} catch (NumberFormatException e) {
			LoggerUtility.error("ID não validado, Tente novamente");
		}

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
