package entities;

public class Menus {

/*
 * Este � uma class auxiliar que fornece os menus para o sistema
 * */
	public void menuInicial() {
		System.out.println("");
		System.out.println(	"************* SEJA BEM VINDO AO SISTEMA DE LOGISTICA DE TRANSPORTE DA DELL *******************\n");
		System.out.println(" ==== MENU INICIAL====");
		System.out.println("1. Configurar custo por km ");
		System.out.println("2. Consultar trecho ");
		System.out.println("3. Consultar rota ");
		System.out.println("4. Terminar o programa ");

		System.out.println("");
		System.out.println("Escolha uma das op�oes acima: ");
		System.out.println("");
		
	}
	
	public void subMenu() {

		System.out.println("1. Terminar sess�o");
		System.out.println("2. Voltar ao in�cio");
		System.out.println("3. Listar todas as cidades");

		System.out.println("");
		System.out.println("Escolha uma das op�oes acima: ");
		
	}

}
