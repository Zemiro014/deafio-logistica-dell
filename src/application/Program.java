package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import entities.City;
import entities.Menus;
import services.LogisticService;
import services.LogisticServiceImplement;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		final String path = "C:\\temp\\DNIT-Distancias.csv"; // Declarando o caminho do arquivo que contém os dados

		LogisticService<City> logisticaService = new LogisticServiceImplement(); // Declarando e instanciando o serviço LogisticService
		
		List<City> listCity = logisticaService.readDataOfFileCSV(path); // Lendo os dados do arquivo DNIT-Distancias.csv e passando para a lista de cidades
		
		LogisticServiceImplement logistica = new LogisticServiceImplement(); // Instanciando a classe concreta LogisticServiceImplement. Precisou-se porque ele possui métodos próprios.
		
		Menus menu = new Menus();
		
		boolean terminarSessao = false;
		int opcao;
		
		try {
			
			while(terminarSessao == false)
			{
				menu.menuInicial();
				opcao = sc.nextInt();
				
				switch (opcao) 
				{
				
					case 1: 
						System.out.println("Informe o custo por KM (se for número decimal digite com vírgula): ");
						double costPerKm = sc.nextDouble();
						logistica.setCostPerKM(costPerKm);
						if(costPerKm == logistica.getCostPerKM()) {
							System.out.println("O custo por KM foi configurado com sucesso: ");
							System.out.println("O valor do custo por Km é de: "+logistica.getCostPerKM());
							System.out.println("");
							System.out.println("O que deseja fazer? ");
							menu.subMenu();
							int subMenu = sc.nextInt();
							if(subMenu == 1) {
								terminarSessao = true;
							}
							else if(subMenu == 2) {
								terminarSessao = false;
							}
							else if(subMenu == 3) {
								if (listCity != null) {
									for (City city : listCity) 
									{
										System.out.println(city);
									}
								}
								terminarSessao = true;
							}
							else {
								terminarSessao = true;
							}
						}
						break;
					
					case 2:
						System.out.println("Preenchas os dados: ");
						System.out.print("Cidade de origem: "+sc.nextLine());
						String partida = sc.nextLine();
						System.out.print("Cidade de destino: ");
						String destino = sc.nextLine();
						System.out.println(logisticaService.consultExcerpt(partida.toUpperCase(), destino.toUpperCase()));
						
						System.out.println("O que deseja fazer? ");
						menu.subMenu();
						int subMenu = sc.nextInt();
						if(subMenu == 1) {
							terminarSessao = true;
						}
						else if(subMenu == 2) {
							terminarSessao = false;
						}
						
						else if(subMenu == 3) {
							if (listCity != null) {
								for (City city : listCity) 
								{
									System.out.println(city);
								}
							}
							terminarSessao = true;
						}
						else {
							terminarSessao = true;
						}
						break;
						
					case 3:
						System.out.println("Digite o nome de duas ou mais cidades separadas por virgula:");
						System.out.print("Nome das cidades separadas por vírgula: "+sc.nextLine());
						String cities = sc.nextLine();
						cities = cities.toUpperCase();
						String[] vectCities = cities.split(",");
						System.out.println(logisticaService.consultRoute(vectCities));
						System.out.println("O que deseja fazer? ");
						menu.subMenu();
						int subMenu3 = sc.nextInt();
						if(subMenu3 == 1) {
							terminarSessao = true;
						}
						else if(subMenu3 == 2) {
							terminarSessao = false;
						}
						else if(subMenu3 == 3) {
							if (listCity != null) {
								for (City city : listCity) 
								{
									System.out.println(city);
								}
							}
							terminarSessao = true;
						}
						else {
							terminarSessao = true;
						}
						break;
					case 4:
						terminarSessao = true;
						break;
						
					default:{
						System.out.println("A opção digitado não é válido. A sessão foi encerrada");
						terminarSessao = true;}
				}
			}
				}
		catch(InputMismatchException e) {
			System.out.println("Error: O valor digitado não corresponde");
		}
		
		finally {
			sc.close();
		}
		

	}

}
