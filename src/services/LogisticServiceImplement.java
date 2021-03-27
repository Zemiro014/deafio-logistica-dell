package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import entities.City;

public class LogisticServiceImplement implements LogisticService<City>{	
	
	private Double costPerKM;
	private List<City> listCity = new ArrayList<>();
	
	public LogisticServiceImplement() {
		setCostPerKM(1.85);
	}
	

	public Double getCostPerKM() {
		return costPerKM;
	}

	public void setCostPerKM(Double costPerKM) {
		if(costPerKM <= 0) {
			throw new InputMismatchException("O valor informado não é aceite. (Provavelmente o valor informado é menor ou igual a 0, ou é um caracter)");
		}
		this.costPerKM = costPerKM;
	}	
	
	@Override
	public List<City> readDataOfFileCSV(String path) {		
		
		try(BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			String lineOfCity = br.readLine(); // Lendo os dados da primeira linha do arquivo para instanciar as cidades passando os nomes
			
			String[] vectCity = lineOfCity.split(";");
			
			for (int i = 0; i < vectCity.length; i++) {
				City city = new City(vectCity[i]);
				listCity.add(city);
			}
			
			String lineOfDistances = br.readLine(); // Lendo os dados da segunda linha do arquivo para pegar os valores das distancias
			while(lineOfDistances != null) {
				
				String[] vectDistance = lineOfDistances.split(";");
				
				for (int i = 0; i < vectDistance.length; i++) {
					listCity.get(i).addDistance(Double.parseDouble(vectDistance[i]));
				}
				
				lineOfDistances = br.readLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("Error: "+e.getMessage());		
			e.printStackTrace();
		}
		
		return listCity;
	}

	@Override
	public String consultExcerpt(List<City> list, String startCity, String destinationCity) {

		boolean ex1 = false;
		boolean ex2 = false;
		int referenceIndex1 = 0;
		int referenceIndex2 = 0;
		double totalDistance = 0;
		
		for(City city : list) {
			if(city.getName().equalsIgnoreCase(startCity)) {
				ex1 = true;
				referenceIndex1 = city.getDistances().indexOf(0.0);
			}
			if(city.getName().equalsIgnoreCase(destinationCity)) {
				ex2 = true;
				referenceIndex2 = city.getDistances().indexOf(0.0);
			}
		}
		
		if(referenceIndex1 < referenceIndex2) {
			for (int i = referenceIndex1; i <= referenceIndex2; i++) {
				totalDistance += list.get(i).getDistances().get(referenceIndex1);
			}
		}
		else if(referenceIndex1 > referenceIndex2) {
			for (int i = referenceIndex1; i >= referenceIndex2; i--) {
				totalDistance += list.get(i).getDistances().get(referenceIndex1);
			}
		}
		
		
		if(ex1 == true && ex2 == true) {
			return "As cidades: "+startCity+", "+destinationCity+" existem no sistema. E a distância entre elas é de: "+totalDistance+ " km";
		}
		return "As cidades: "+startCity+", "+destinationCity+" não existem no sistema";
	}

}
