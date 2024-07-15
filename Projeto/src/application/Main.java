package application;

import java.util.Date;
import java.util.Scanner;

import db.DataBaseInitializer;
import db.Db;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import model.entities.WorkLevel;
import services.DepartmentService;
import services.WorkLevelService;

public class Main {

	public static void main(String[] args) {

		DepartmentDao departmentDao = DaoFactory.createDepartmentDaoJDBC();

		Department department = departmentDao.findById(1);
		WorkLevel wl = WorkLevel.JUNIOR;

		Date date = new Date();

		Seller seller = new Seller("Juan", date, 2000.00, department, wl);

		SellerDao sellerDao = DaoFactory.createSellerDao();

		sellerDao.insert(seller);

		System.out.println("Funcionario inserido");
		Seller achado = sellerDao.findById(1);
		System.out.println(achado);

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
