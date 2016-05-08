package mytrack;

import java.util.Arrays;
import java.util.Iterator;

public final class U3_Utils {

    // String s = "fred";    // do this if you want an exception

	public static float CStringToFloat(String s){
		//String s = "100.00";
		float f;
		try
		{
			f = Float.valueOf(s.trim()).floatValue();
			//2//3//99System.out.print("float f = " + f);
		}
		catch (NumberFormatException nfe)
		{
			//2//3//99System.out.print("NumberFormatException: " + nfe.getMessage());
			f = 0;
		}
		return f;
	}
	
	public static double CStringToDouble(String s){
		//String s = "100.00";
		double d;
		try
		{
			d = Float.valueOf(s.trim()).floatValue();
			//2//3//99System.out.print("float f = " + d);
		}
		catch (NumberFormatException nfe)
		{
			//2//3//99System.out.print("NumberFormatException: " + nfe.getMessage());
			d = 0;
		}
		return d;
	}
	
	public static int CStringToInt(String s){
		//String s = "100.00";
		int i;
		try
		{
			i = Integer.valueOf(s.trim()).intValue();
			//2//3//99System.out.print("float f = " + f);
		}
		catch (NumberFormatException nfe)
		{
			//3//99System.out.print("NumberFormatException: " + nfe.getMessage());
			i = 0;
		}
		return i;
	}
	
	//to join elements of a collection
	public static String join(Iterable<? extends Object> elements, CharSequence separator) {    
		StringBuilder builder = new StringBuilder();    
		if (elements != null)    {        
			Iterator<? extends Object> iter = elements.iterator();        
			if(iter.hasNext())        {            
				builder.append( String.valueOf( iter.next() ) );            
				while(iter.hasNext()){    
					builder.append( separator ).append( String.valueOf( iter.next() ) );            
				}        
			}    
		}    
		return builder.toString();
	}
	
	//concatenate any number of arrays.
	public static <T> T[] concat(T[] first, T[] second) {
		  T[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
		}

	public static <T> T[] concatAll(T[] first, T[]... rest) {
		  int totalLength = first.length;
		  for (T[] array : rest) {
		    totalLength += array.length;
		  }
		  T[] result = Arrays.copyOf(first, totalLength);
		  int offset = first.length;
		  for (T[] array : rest) {
		    System.arraycopy(array, 0, result, offset, array.length);
		    offset += array.length;
		  }
		  return result;
		}

	public static String readLeaf(String leaf, String delimeter, int i) {
		//read the ith word of string leaf split by delimeter "-"
		String[] items = leaf.split(delimeter);
		return items[i];
	}



}
