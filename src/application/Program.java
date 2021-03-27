package application;

import java.util.List;

import entities.City;
import services.LogisticService;
import services.LogisticServiceImplement;

public class Program {

	public static void main(String[] args) {
		
		final String path = "C:\\temp\\DNIT-Distancias.csv";

		LogisticService<City> lgS = new LogisticServiceImplement();
		List<City> listCity = lgS.readDataOfFileCSV(path);
		
		System.out.println("");
		System.out.println("************* SEJA BEM VINDO AO SISTEMA DE LOGISTICA DE TRANSPORTE DA DELL *******************\n");
		System.out.println(" ==== MENU INICIAL====");
		System.out.println("1. Configurar custo por km ");
		System.out.println("2. Consultar trecho ");
		System.out.println("3. Consultar rota ");
		System.out.println("4. CTerminar o programa ");
		
		System.out.println("");
		System.out.println("Escolha uma das opçoes acima: ");
		System.out.println("");
		
		System.out.println(lgS.consultExcerpt(listCity, "PORTO ALEGRE", "BELEM"));
		System.out.println("");
		
		if(listCity != null) {
			for(City city : listCity) {
				System.out.println(city);
			}
		}		
		
	}

}
