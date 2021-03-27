package services;

import java.util.List;

public interface LogisticService<T>{

	public List<T> readDataOfFileCSV(String path);
}
