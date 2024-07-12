package application;
import java.util.Scanner;
import db.DataBaseInitializer;
import db.Db;
import services.DepartmentService;
import services.WorkLevelService;

public class Main {

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in);)

		{

			Db.TestConnection();
			DataBaseInitializer.dataBaseSetup();

			int option;

			do {
				System.out.println(
						"Escolha uma das opções do menu:\n1 - Acessar serviço de departamento\n2 - Acesar serviço de Niveis de trabalho\n9 - Sair");
				option = sc.nextInt();
				System.out.println();

				if (option == 1) {

					System.out.println("Serviço de departamento");
					DepartmentService.mainDepartment(sc);

				}

				else if (option == 2) {
					System.out.println("Serviço de niveis de trabalho");
					WorkLevelService.mainWorkLevel(sc);

				}

				else if (option == 9) {

					System.out.println("Saindo....");

				} else {

					System.out.println("Opção Invalida!");
					System.out.println();
				}

			} while (option != 9);

		}

		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
