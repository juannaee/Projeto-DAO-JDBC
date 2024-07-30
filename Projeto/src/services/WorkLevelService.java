package services;

import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.WorkLevelDao;
import utilities.LoggerUtility;

public class WorkLevelService {

	private static WorkLevelDao workLevel = DaoFactory.createWorkLevelDaoJDBC();

	public static void mainWorkLevel(Scanner sc) {
		int option = 0;
		boolean confirm = false;

		while (!confirm) {

			try {
				System.out.println("Escolha uma das opções\n1 - Mostrar Niveis de trabalho\n2 - Sair");
				option = Integer.parseInt(sc.nextLine());

				if (option == 1) {
					showWorkLevels();
					continue;
				} else if (option == 2) {
					System.out.println("Saindo........");
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

	private static void showWorkLevels() {
		System.out.println("Niveis de trabalho:");
		if (!workLevel.findAll().isEmpty()) {
			System.out.println(workLevel.findAll());
			System.out.println();
		} else {
			LoggerUtility.error("Erro ao mostrar niveis de trabalho");

		}
	}
}
