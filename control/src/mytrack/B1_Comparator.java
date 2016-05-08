package mytrack;

import java.io.Serializable;
import java.util.Comparator;



public class B1_Comparator implements Comparator , Serializable{
	/**
	 * 
	 */


	@Override
	public int compare(Object o1, Object o2) {
		return o1.toString().compareToIgnoreCase(o2.toString());
	}

	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object obj)    {
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
	}
}

