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
		
		if(listCity != null) {
			for(City city : listCity) {
				System.out.println(city);
			}
		}
		
		
	}

}
