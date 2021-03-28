package services;

import java.util.List;

public interface LogisticService<T>{

	public List<T> readDataOfFileCSV(String path);
	
	public String consultExcerpt(String startCity, String destinationCity);
	
	public String consultRoute(String... citys);
}
