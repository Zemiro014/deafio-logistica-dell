package services;

import java.util.List;
/*
 * Essa � uma interface gen�rica que oferece os contratos para as suas subclasses. Ela � uma interface gen�rica 
 * */
public interface LogisticService<T>{

	public List<T> readDataOfFileCSV(String path);
	
	public String consultExcerpt(String... citys);
	
	public String consultRoute(String... citys);
}
