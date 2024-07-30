package application;

import java.util.Scanner;
import db.DataBaseInitializer;
import db.Db;
import services.DepartmentService;
import services.WorkLevelService;
import services.seller.SellerService;
import utilities.LoggerUtility;

public class Main {

	public static void main(String[] args) {
		Db.getConnection();
		boolean confirm = false;
		int option = 0;
		DataBaseInitializer.dataBaseSetup();
		Scanner sc = new Scanner(System.in);

		while (!confirm) {

			try

			{

				System.out.println(
						"Escolha uma das opções do menu:\n1 - Acessar serviço de departamento\n2 - Acessar serviço de Niveis de trabalho\n3 - Acessar serviço de funcionarios\n9 - Sair");
				option = Integer.parseInt(sc.nextLine());

				if (option == 1) {

					System.out.println("Serviço de departamento");
					DepartmentService.mainDepartment(sc);
					continue;

				}

				else if (option == 2) {
					System.out.println("Serviço de niveis de trabalho");
					WorkLevelService.mainWorkLevel(sc);
					continue;

				} else if (option == 3) {
					System.out.println("Serviço de funcionarios");
					SellerService.mainSeller(sc);
					continue;

				}

				else if (option == 9) {

					System.out.println("Saindo....");
					confirm = true;

				} else {

					System.out.println("Opção Invalida, tente novamente!");
					System.out.println();
					continue;
				}

			}

			catch (NumberFormatException e) {
				LoggerUtility.error("Entrada: " + option + ". invalida, tente novamente");
				continue;

			}

			catch (Exception e) {
				LoggerUtility.error("ERRO: \nMotivo: " + e.getMessage());
				continue;

			} finally {
				Db.closeConnection();
			}
		}
	}

}
