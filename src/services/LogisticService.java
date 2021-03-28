package services;

import java.util.List;
/*
 * Essa é uma interface genérica que oferece os contratos para as suas subclasses. Ela é uma interface genérica 
 * */
public interface LogisticService<T>{

	public List<T> readDataOfFileCSV(String path);
	
	public String consultExcerpt(String... citys);
	
	public String consultRoute(String... citys);
}
