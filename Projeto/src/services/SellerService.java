package services;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {


	private static SellerDao seller = DaoFactory.createSellerDao();

	public static Seller findById(Scanner sc) {
		System.out.println("Digite um numero ID");
		int id = sc.nextInt();
		Seller objId = seller.findById(id);
		if (objId != null) {
			System.out.println("Funcionario encontrado: ");
			System.out.println(objId);
			return objId;

		} else {
			System.out.println("Funcionario inexistente");
			return objId;
		}
	}

}
