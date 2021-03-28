package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entities.City;

/*
 * Essa class é uma classe concreta da interface genérica LogisticService.  Ela oferece a implementação dos métodos fornecida pela interface genérica LogisiticService
 * */
public class LogisticServiceImplement implements LogisticService<City> {
	
	// Declaração das constantes
	private final double VEHICLE_CONSUMPTION = 2.57;
	private final double DAILY_DISTANCE = 283;
	
	// Variáveis de objecto
	private Double costPerKM;	
	private Double totalDistance = 0.0;
	
	private List<City> listCity = new ArrayList<>(); // Declarando e inicializando uma lista de cidades
	
	public LogisticServiceImplement() { // Construtor sem argumento. Sempre  que a class LogisticServiceImplement for instanciada, por padrão o "costPerKM" terá o seu valor como sendo de 2.10
		setCostPerKM(2.10);
	}	

	public LogisticServiceImplement(Double costPerKM, Double totalDistance) { // Construtor com argumentos
		this.costPerKM = costPerKM;
		this.totalDistance = totalDistance;
		setCostPerKM(2.10);
	}

	public Double getCostPerKM() { // Método get para obter o custo por km
		return costPerKM;
	}

	public Double getTotalDistance() { // Método get para obter a distância total de uma rota ou de um trecho. Ele não permite ser reconfigurado fora desta class
		return totalDistance;
	}
	
	public void setCostPerKM(double costPerKM) { // Método set para configurar o valor do custo por km
		if (costPerKM <= 0) {
			throw new InputMismatchException(
					"O valor informado não é aceite. (Provavelmente o valor informado é menor ou igual a 0, ou é um caracter)");
		}
		this.costPerKM = costPerKM;
	}

	@Override
	public List<City> readDataOfFileCSV(String path) { // Sobreposição do método readDataOfFileCSV que permite ler os dados do arquivo "DNIT-Distancias.csv". Esse método recebe um "path" válido
		List<City> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String lineOfCity = br.readLine(); // Lendo os dados da primeira linha do arquivo para instanciar as cidades
												// passando os nomes

			String[] vectCity = lineOfCity.split(";");

			for (int i = 0; i < vectCity.length; i++) {
				City city = new City(vectCity[i]);
				list.add(city);
			}

			String lineOfDistances = br.readLine(); // Lendo os dados da segunda linha do arquivo para pegar os valores
													// das distancias
			while (lineOfDistances != null) {

				String[] vectDistance = lineOfDistances.split(";");

				for (int i = 0; i < vectDistance.length; i++) {
					list.get(i).addDistance(Double.parseDouble(vectDistance[i]));
				}

				lineOfDistances = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		listCity = list;

		return list;
	}

	@Override
	public String consultExcerpt(String... citys) { // Método que permite consultar um trecho. Ele permite receber masi de uma variável como argumento até mesmo um array de String
		//StringBuilder sb = new StringBuilder();
		
		boolean ex1 = false;
		boolean ex2 = false;
		int referenceIndex1 = 0;
		int referenceIndex2 = 0;
		double distance = 0;

		for (City city : listCity) {
			if (city.getName().equalsIgnoreCase(citys[0])) {
				ex1 = true;
				referenceIndex1 = city.getDistances().indexOf(0.0);
			}
			if (city.getName().equalsIgnoreCase(citys[1])) {
				ex2 = true;
				referenceIndex2 = city.getDistances().indexOf(0.0);
			}
		}

		if (referenceIndex1 < referenceIndex2) { // Verifica o sentido do trecho requerido: da esquerda para direita
			for (int i = referenceIndex1; i <= referenceIndex2; i++) {
				distance += listCity.get(i).getDistances().get(referenceIndex1);
			}
		} else if (referenceIndex1 > referenceIndex2) { // Verifica o sentido do trecho requerido: da direita para esquerda
			for (int i = referenceIndex1; i >= referenceIndex2; i--) {
				distance += listCity.get(i).getDistances().get(referenceIndex1);
			}
		}

		if (ex1 == true && ex2 == true) {
			return "As cidades: " + citys[0] + ", " + citys[1]
					+ " existem no sistema. A distância entre elas é de: " + distance + " km"+ ". O custo da viagem é de: "+(distance*getCostPerKM())+ " R$";
		}
		return "Este trecho não pode ser computado porque uma das cidades: " + citys[0] + " ou " + citys[1] + " não existe no sistema";
	}

	@Override
	public String consultRoute(String... cities) { // Este método permite consultar uma rota. Pode receber duas ou mais varáveis como parâmetros, até mesmo um array de String

		Map<String, City> mapDataCities = getDataCityPerKey(cities);
		List<String> list = new ArrayList<>();
		
		int referenceIndex;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Rota: ");
		
		for(String key : mapDataCities.keySet()) {
			sb.append(mapDataCities.get(key).getName()+" -> ");
			list.add(mapDataCities.get(key).getName());
		}
		sb.append("\n");
		
		for (int i = 0; i < list.size()-1; i++) {
			referenceIndex = getReferenceIndex(list.get(i));
			if(i < list.size()-1) {				
				double distance = mapDataCities.get(list.get(i)).getDistances().get(referenceIndex) + mapDataCities.get(list.get(i+1)).getDistances().get(referenceIndex);
				sb.append("Saindo de "+list.get(i)+ " para " +list.get(i+1) + " são: " +distance+ " km de distância");
				sb.append("\n");
				totalDistance += distance;
				distance = 0.0;				
			}					
		}
		sb.append("\n");
		sb.append("A distância total a ser percorrida é de: "+getTotalDistance()+" km\n");
		sb.append("O custo da viagem é estimado a: "+(getTotalDistance()*getCostPerKM())+ "0 R$\n");
		sb.append("O total de gasolina ser consumida: "+(getTotalDistance()*VEHICLE_CONSUMPTION) +" L\n");
		sb.append("Número de dias para finalizar a viagem: "+(getTotalDistance()/DAILY_DISTANCE) + " dia(s)");
		
		sb.append("\n");
		return sb.toString();
	}

	private int getReferenceIndex(String value) { // Este é um método auxiliar que esta class usa para obter index de referência das distâncias de cada cidade. A referência é 0.0
	int index = 0;
		for (City city : listCity) 
		{
			if (city.getName().equalsIgnoreCase(value)) {
				index = city.getDistances().indexOf(0.0);
			}		
		}
		
		return index;
	}

	private Map<String, City> getDataCityPerKey(String... cities) { // Este é um método auxiliar que esta class usa para pegar na base de dados organiza-los por Key e Value. Ele retorna um Map<nomeDaCidade,ObjectDoTipoCity>

		Map<String, City> mapOfDataCities = new LinkedHashMap<>();
		
		int indexOfFirstCity = 0;
		int indexOfDistinatioCity = 0;
		
		if (cities != null) {
			indexOfFirstCity = getReferenceIndex(cities[0]);
			indexOfDistinatioCity = getReferenceIndex(cities[cities.length -1]);
		}		
		
		
		if(indexOfFirstCity < indexOfDistinatioCity) { // A rota traçada é da esquerda para direita
			for (int i = indexOfFirstCity; i <= indexOfDistinatioCity; i++) {
				mapOfDataCities.put(listCity.get(i).getName(), listCity.get(i));
			}
		}
		else if (indexOfFirstCity > indexOfDistinatioCity) { // A rota traçada é da direita para esquerda
			for (int i = indexOfFirstCity; i >= indexOfDistinatioCity; i--) {
				mapOfDataCities.put(listCity.get(i).getName(), listCity.get(i));
			}
		}	
		return mapOfDataCities;
	}


}
