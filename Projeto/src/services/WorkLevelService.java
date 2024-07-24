package services;
import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.WorkLevelDao;
import utilities.LoggerUtility;

public class WorkLevelService {

	private static WorkLevelDao workLevel = DaoFactory.createWorkLevelDaoJDBC();

	public static void mainWorkLevel(Scanner sc) {
		int option;

		do {

			System.out.println("Escolha uma das opções\n1 - Mostrar Niveis de trabalho\n2 - Sair");
			option = sc.nextInt();

			if (option == 1) {
				showWorkLevels();
			} else if (option == 2) {
				System.out.println("Saindo........");
				System.out.println();
			} else {
				System.out.println("Opção invalida");
				System.out.println();
			}

		} while (option != 2);

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
