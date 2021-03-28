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

public class LogisticServiceImplement implements LogisticService<City> {

	private Double costPerKM;
	private List<City> listCity = new ArrayList<>();
	private Double totalDistance = 0.0;

	public LogisticServiceImplement() {
		setCostPerKM(1.85);
	}

	public Double getCostPerKM() {
		return costPerKM;
	}

	public void setCostPerKM(Double costPerKM) {
		if (costPerKM <= 0) {
			throw new InputMismatchException(
					"O valor informado não é aceite. (Provavelmente o valor informado é menor ou igual a 0, ou é um caracter)");
		}
		this.costPerKM = costPerKM;
	}

	@Override
	public List<City> readDataOfFileCSV(String path) {
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
	public String consultExcerpt(String startCity, String destinationCity) {

		boolean ex1 = false;
		boolean ex2 = false;
		int referenceIndex1 = 0;
		int referenceIndex2 = 0;
		double distance = 0;

		for (City city : listCity) {
			if (city.getName().equalsIgnoreCase(startCity)) {
				ex1 = true;
				referenceIndex1 = city.getDistances().indexOf(0.0);
			}
			if (city.getName().equalsIgnoreCase(destinationCity)) {
				ex2 = true;
				referenceIndex2 = city.getDistances().indexOf(0.0);
			}
		}

		if (referenceIndex1 < referenceIndex2) {
			for (int i = referenceIndex1; i <= referenceIndex2; i++) {
				distance += listCity.get(i).getDistances().get(referenceIndex1);
			}
		} else if (referenceIndex1 > referenceIndex2) {
			for (int i = referenceIndex1; i >= referenceIndex2; i--) {
				distance += listCity.get(i).getDistances().get(referenceIndex1);
			}
		}

		if (ex1 == true && ex2 == true) {
			return "As cidades: " + startCity + ", " + destinationCity
					+ " existem no sistema. E a distância entre elas é de: " + distance + " km";
		}
		return "As cidades: " + startCity + ", " + destinationCity + " não existem no sistema";
	}

	@Override
	public String consultRoute(String... cities) {

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
		sb.append("A distância total a ser percorrida é de: "+getTotalDistance()+" km");
		sb.append("\n");
		return sb.toString();
	}

	private int getReferenceIndex(String value) {
	int index = 0;
		for (City city : listCity) 
		{
			if (city.getName().equalsIgnoreCase(value)) {
				index = city.getDistances().indexOf(0.0);
			}		
		}
		
		return index;
	}

	private Map<String, City> getDataCityPerKey(String... cities) {

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

	public Double getTotalDistance() {
		return totalDistance;
	}
}
