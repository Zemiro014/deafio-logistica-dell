package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.City;

public class LogisticServiceImplement implements LogisticService<City>{	
	
	@Override
	public List<City> readDataOfFileCSV(String path) {
		
		List<City> listCity = new ArrayList<>();
		
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

}
