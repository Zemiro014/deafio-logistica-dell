package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class City implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<Double> distances = new ArrayList<>();
	
	public City() {
		
	}

	public City(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getDistances() {
		return distances;
	}

	public void addDistance(Double distance) {
		distances.add(distance);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distances == null) ? 0 : distances.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (distances == null) {
			if (other.distances != null)
				return false;
		} else if (!distances.equals(other.distances))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("City name: "+ getName());
		sb.append("         Distances: ");
		for(double d: distances) {
			sb.append(d);
			sb.append(";  ");
		}
		return sb.toString();
	}	
	
}
