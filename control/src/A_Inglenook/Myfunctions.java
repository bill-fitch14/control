package A_Inglenook;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sm2.E1;

public final class Myfunctions {

	static int use_graphics;

	static Deque<Integer[]> st1;
	static Deque<Integer[]> st2;
	static Deque<Integer[]> st3;
	static Deque<Integer[]> sth;
	
	private static List<Deque<Integer[]>> stacks = new LinkedList<Deque<Integer[]>>()  ;
	
	static Long[] positions;

	public static boolean pause = true;
	//public static int delay = 1000;
	public static int delay = 10;
	
	public void setUpStacks(){
		 ((LinkedList<Deque<Integer[]>>) getStacks()).push(st1);
		 ((LinkedList<Deque<Integer[]>>) getStacks()).push(st2);
		 ((LinkedList<Deque<Integer[]>>) getStacks()).push(st3);
		 ((LinkedList<Deque<Integer[]>>) getStacks()).push(sth);
		 
		
	}
	
	public String getStackName(Deque<Integer[]> stack){

		if (stack==st1){
			return "stack 1";
		}else if(stack==st2){
			return "stack 2";
		}else if(stack==st3){
			return "stack 3";
		}else if(stack==sth){
			return "stack h";
		}else{
			return "error";
		}
		
	}
	
	public static long drawStacks(int[] pole) {

		int N = pole.length - 1;

		// draw 4 poles
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.002);
		for (int i = 0; i < 4; i++){
			switch(i){
			case 0:
				StdDraw.line(i, 3.5, i, 8.5);
				break;
			case 1:
				StdDraw.line(i, 5.5, i, 8.5);
				break;
			case 2:
				StdDraw.line(i, 5.5, i, 8.5);
				break;
			case 3:
				StdDraw.line(0.5, -7.5, 0.5, -10.5); 
				break;
			}
		}
		StdDraw.line(0, 3.5, 0.5, -7.5);
		StdDraw.line(1,5.5, 0.5, -7.5);
		StdDraw.line(2,5.5, 0.5, -7.5);
		//		    	  if(i<3){
			//		    		  StdDraw.line(i, 0, i, N);
			//		    	  }else{
				//		    		  StdDraw.line(0.5, -3, 0.5, -3-N); 
		//		    	  }

		Integer[] j = {0,0};
		long l_out = 0;
		int disk = 0;
		
		Iterator<Integer[]> it1 = null;
		Iterator<Integer[]> it2 = null;
		Iterator<Integer[]> it3 = null;
		Iterator<Integer[]> it4 = null;
		
		for (int i = 1; i <= 14; i++) {


			int stack = 0;
			Iterator<Integer[]> iter = null;
			switch (i) {
			case 1: case 2: case 3: case 4: case 5:
				if (i==1) {
					disk = 5;
					it1 = st1.iterator();
				}
				stack = 0;
				if (it1.hasNext()) {
					j = it1.next();
					disk = --disk;
				}else{
					j = new Integer[] {0,0};
				}
				iter = it1;
				break;
			case 6: case 7: case 8:
				if (i==6) {
					disk = 3;
					it2 = st2.iterator();
				}
				stack = 1;
				if (it2.hasNext()) {
					j = it2.next();
					disk = --disk;
				}else{
					j = new Integer[] {0,0};
				}
				iter = it2;
				break;
			case 9: case 10: case 11:
				if (i==9) {
					disk = 3;
					it3 = st3.iterator();
				}
				stack = 2;
				if (it3.hasNext()) {
					j = it3.next();
					disk = --disk;
				}else{
					j = new Integer[] {0,0};
				}
				iter = it3;
				break;
			case 12: case 13: case 14:
				if (i==12) {
					disk = 3;
					it4 = sth.iterator();
				}
				stack = 3;
				if (it4.hasNext()) {
					j = it4.next();
					disk = --disk;
				}else{
					j = new Integer[] {0,0};
				}
				iter = it4;
				break;
			}
			Color color = Color.getHSBColor(1.0f * j[0] / N, .7f, .7f);
			StdDraw.setPenColor(color);
			StdDraw.setPenRadius(0.035);   // magic constant
			l_out += j[1] * (long) (Math.pow(10,i-1));
			//double size = 0.5 * i / 8;
			double size = 0.5 * j[1] / N;
			int[] discs = new int[4];
			int p = stack;
			float q;
			discs[p] = disk;
			if (j[1]!=0){
				if (p != 3){
					q = p;
					StdDraw.line((q-size/2), 8-discs[p], q + size/2, 8-discs[p]);
				}else{
					q = 0.5f;
					StdDraw.line((q-size/2), discs[p]-10, q + size/2, discs[p]-10);
				}
			}
		}
		if(pause ){
		StdDraw.show(Myfunctions.delay);
		}
		return l_out;
	}

	public static Long[] convertStacksToLong(Deque<Integer[]> st12, Deque<Integer[]> st22,
			Deque<Integer[]> st32, Deque<Integer[]> sth2) {

		Integer[] j = new Integer[] {0,0};
		Long l_out[] = new Long[] {0l,0l};
		Iterator<Integer[]> it1 = null;
		Iterator<Integer[]> it2 = null;
		Iterator<Integer[]> it3 = null;
		Iterator<Integer[]> it4 = null;
		for (int i = 1; i <= 14; i++) {
			switch (i) {
			case 1: case 2: case 3: case 4: case 5:
				
				if (i==1){
					it1 = st12.iterator();
				}
				if (it1.hasNext()) {
					j = it1.next();
				}else{
					j = new Integer[] {0,0};
				}
				break;
			case 6: case 7: case 8:
				if (i==6){
					it2 = st22.iterator();
				}
				if (it2.hasNext()) {
					j = it2.next();
				}else{
					j = new Integer[] {0,0};
				}
				break;
			case 9: case 10: case 11:
				if(i==9){
					it3 = st32.iterator();
				}
				if (it3.hasNext()) {
					j = it3.next();
				}else{
					j = new Integer[] {0,0};
				}
				break;
			case 12: case 13: case 14:
				if(i==12){
					it4 = sth2.iterator();
				}
				if (it4.hasNext()) {
					j = it4.next();
				}else{
					j = new Integer[] {0,0};
				}
				break;
			}
			
			l_out[0] += j[0] * (long) (Math.pow(10,i-1));
			l_out[1] += j[1] * (long) (Math.pow(10,i-1));
			
		}
		return l_out;
	}

	public static void convertLongToStacks(long l[]) {

		/*
		 * Inglenook siding problem has 3 sidings of 5, 3, 3 and one heap siding of 3 total 14
		 * There are 8 trucks numbered 1 to 8
		 * The final layout needs to be 5 of the 8 trucks in any order in the siding containing 5 trucks
		 * We have an integer for the trucks in the order 5(1),3(2),3(3),3(h)
		 */
		st1 = new ArrayDeque<Integer[]>();
		st2 = new ArrayDeque<Integer[]>();
		st3 = new ArrayDeque<Integer[]>();
		sth = new ArrayDeque<Integer[]>();
		for (int i = 14; i >= 1; i--) {
			////99System.out.print(i + "th digit " + getNthDigit(l, 10, i));
			switch (i) {
			case 1: case 2: case 3: case 4: case 5:
				if ( getNthDigit(l[0], 10, i) != 0){
					Integer[] i1 = {getNthDigit(l[0], 10, i),getNthDigit(l[1], 10, i)};
					st1.push( i1);
				}
				break;
			case 6: case 7: case 8:
				if ( getNthDigit(l[0], 10, i) != 0){
					Integer[] i1 = {getNthDigit(l[0], 10, i),getNthDigit(l[1], 10, i)};
					st2.push( i1);
				}
				break;
			case 9: case 10: case 11:
				if ( getNthDigit(l[0], 10, i) != 0){
					Integer[] i1 = {getNthDigit(l[0], 10, i),getNthDigit(l[1], 10, i)};
					st3.push( i1);
				}
				break;
			case 12: case 13: case 14:
				if ( getNthDigit(l[0], 10, i) != 0){
					Integer[] i1 = {getNthDigit(l[0], 10, i),getNthDigit(l[1], 10, i)};
					sth.push( i1);
				}
				break;
			}
		}

	}

	public static Deque<Integer[]> getSt1(){
		//		convertLongToStacks(l);
		return st1;	
	}
	public static Deque<Integer[]> getSt2(){
		//		convertLongToStacks(l);
		return st2;	
	}
	public static Deque<Integer[]> getSt3(){
		//		convertLongToStacks(l);
		return st3;	
	}
	public static Deque<Integer[]> getSth(){
		//		convertLongToStacks(l);
		return sth;	
	}


	public static int getNthDigit(long number, int base, int n) {    
		return (int) ((number / Math.pow(base, n - 1)) % base);
	}

//	public static boolean myTransfer1(Deque<Integer> stFrom, String stFromName, Deque<Integer> stTo, String stToName, Deque<Integer> stHeap){
//		if(isUse_graphics()==0){
//			//		//99System.out.print("hi");
//			boolean stToCondition = false;
//			if (stToName.equals("st1")){
//				stToCondition = (stTo.size() <= 4);
//			}else if(stToName.equals("st2") || stToName.equals("st3") || stToName.equals("sth")){
//				stToCondition = (stTo.size() <= 2);
//			}
//			boolean stToCondition_heapfull = false;
//			if (stFromName.equals("st1")) {
//				if(stToName.equals("st2")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st3.size()<=2)));
//				}else if( stToName.equals("st3") ){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st2.size()<=2)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st2.size()<=2)||(st3.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//			}else if (stFromName.equals("st2")) {
//				if (stToName.equals("st1")){
//					stToCondition_heapfull = ((stTo.size() <= 4) && ((st3.size()<=2)));
//				}else if( stToName.equals("st3") ){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st3.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//
//			}else if (stFromName.equals("st3")) {
//				if (stToName.equals("st1")){
//					stToCondition_heapfull = ((stTo.size() <= 4) && ((st2.size() <= 2)));
//				}else if(stToName.equals("st2")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st2.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//
//			}else{
//				stToCondition_heapfull = false;
//			}
//			
//			//boolean stToCondition_heapfull = false;
//			if (stToName.equals("st1")){
//				stToCondition_heapfull = ((stTo.size() <= 4) && ((st2.size() <= 2)||(st3.size()<=2)));
//			}else if(stToName.equals("st2")){
//				stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st3.size()<=2)));
//			}else if( stToName.equals("st3") ){
//				stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st2.size()<=2)));
//			}else if( stToName.equals("sth")){
//				stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st2.size()<=2)||(st3.size()<=2)));
//			}
//			
//			if ((stHeap.size()<=2 && !stFrom.isEmpty() && stToCondition)||(stHeap.size()==3 && !stFrom.isEmpty() && stToCondition_heapfull)){
//				Integer i = mypop(stFrom);
//				if (!(i==null)){
//					if(mypush(stTo, i)==false){
//						//put back
//						mypush(stFrom,i);
//						return false;
//					} else {
//						return true;
//					}
//				} else {
//					return false;
//				}
//			}else{
//				return false;
//			}
//		}else if(isUse_graphics()==1){
//			//		//99System.out.print("hi");
//			boolean stToCondition = false;
//			if (stToName.equals("st1")){
//				stToCondition = (stTo.size() <= 4);
//			}else if(stToName.equals("st2") || stToName.equals("st3") || stToName.equals("sth")){
//				stToCondition = (stTo.size() <= 2);
//			}
//			boolean stToCondition_heapfull = false;
//			if (stFromName.equals("st1")) {
//				if(stToName.equals("st2")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st3.size()<=2)));
//				}else if( stToName.equals("st3") ){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st2.size()<=2)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st2.size()<=2)||(st3.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//			}else if (stFromName.equals("st2")) {
//				if (stToName.equals("st1")){
//					stToCondition_heapfull = ((stTo.size() <= 4) && ((st3.size()<=2)));
//				}else if( stToName.equals("st3") ){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st3.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//
//			}else if (stFromName.equals("st3")) {
//				if (stToName.equals("st1")){
//					stToCondition_heapfull = ((stTo.size() <= 4) && ((st2.size() <= 2)));
//				}else if(stToName.equals("st2")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st2.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//
//			}else{
//				stToCondition_heapfull = false;
//			}
//			
//			//boolean stToCondition_heapfull = false;
//			if (stToName.equals("st1")){
//				stToCondition_heapfull = ((stTo.size() <= 4) && ((st2.size() <= 2)||(st3.size()<=2)));
//			}else if(stToName.equals("st2")){
//				stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st3.size()<=2)));
//			}else if( stToName.equals("st3") ){
//				stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st2.size()<=2)));
//			}else if( stToName.equals("sth")){
//				stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st2.size()<=2)||(st3.size()<=2)));
//			}
//			
//			if ((stHeap.size()<=2 && !stFrom.isEmpty() && stToCondition)||(stHeap.size()==3 && !stFrom.isEmpty() && stToCondition_heapfull)){
//				Integer i = mypop_g(stFrom, stFromName);
//				if (!(i==null)){
//					if(mypush_g(stTo,stToName, i)==false){
//						//put back
//						mypush(stFrom,i);
//						return false;
//					} else {
//						return true;
//					}
//				} else {
//					return false;
//				}
//			}else{
//				return false;
//			}
//		}else{
//			//99System.out.print("-------------");
//			//99System.out.print("Transferring from " + stFromName + " to " + stToName );
//			
//			Deque<Integer> stTemp = null;
//			boolean stToCondition = false;
//			if (stToName.equals("st1")){
//				stToCondition = (stTo.size() <= 4);
//			}else if(stToName.equals("st2") || stToName.equals("st3") || stToName.equals("sth")){
//				stToCondition = (stTo.size() <= 2);
//			}
//			
//			//99System.out.print("stToCondition " + stToCondition);
//			boolean stToCondition_heapfull = false;
//			if (stFromName.equals("st1")) {
//				if(stToName.equals("st2")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st3.size()<=2)));
//
//				}else if( stToName.equals("st3") ){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st2.size()<=2)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st2.size()<=2)||(st3.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//			}else if (stFromName.equals("st2")) {
//				if (stToName.equals("st1")){
//					stToCondition_heapfull = ((stTo.size() <= 4) && ((st3.size()<=2)));
//				}else if( stToName.equals("st3") ){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st3.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//
//			}else if (stFromName.equals("st3")) {
//				if (stToName.equals("st1")){
//					stToCondition_heapfull = ((stTo.size() <= 4) && ((st2.size() <= 2)));
//				}else if(stToName.equals("st2")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)));
//				}else if( stToName.equals("sth")){
//					stToCondition_heapfull = ((stTo.size() <= 2) && ((st1.size() <= 4)||(st2.size()<=2)));
//				}else{
//					stToCondition_heapfull = false;
//				}
//
//			}else{
//				stToCondition_heapfull = false;
//			}
//			
//			//99System.out.print("stToCondition_heapfull " + stToCondition_heapfull);
//			
//			//99System.out.print("st1.size() " + st1.size() + "st2.size() " + st2.size() + "st3.size() " + st3.size() + "sth.size() " + sth.size() );
//			Integer i = null;
//			if ((stHeap.size()<=2 && !stFrom.isEmpty() && stToCondition)){
//				i = mypop_g(stFrom, stFromName);
////				if(Myfunctions.isUse_graphics()){
////					drawDiscs();
////				}
//				if (!(i==null)){
//					if(mypush_g(stTo,stToName, i)==false){
//						//put back
//						mypush_g(stFrom,stFromName, i);
//						return false;
//					} else {
//						return true;
//					}
//				} else {
//					return false;
//				}
//			}else if(stHeap.size()==3 && !stFrom.isEmpty() && stToCondition_heapfull){
//				String stTempName = "";
//				
//
//				if (stToName.equals("st1")){
//					if((st2.size() <= 2 ) && (stFromName != "st2")){
//						stTemp = st2;
//						stTempName = "st2";
//					}else if(st3.size()<=2){
//						stTemp = st3;
//						stTempName = "st3";
//					}
//				}else if(stToName.equals("st2")){
//					if ((st1.size() <= 4)&& (stFromName != "st1")){
//						stTemp = st1;
//						stTempName = "st1";
//					}else if(st3.size()<=2){
//						stTemp = st3;
//						stTempName = "st3";
//					}
//				}else if( stToName.equals("st3")  ){
//					if ((st1.size() <= 4 ) && (stFromName != "st1")){
//						stTemp = st1;
//						stTempName = "st1";
//					}else if(st2.size()<=2){
//						stTemp = st2;
//						stTempName = "st2";
//					}
//				}else if( stToName.equals("sth")){
//					if ((st1.size() <= 4) && (stFromName != "st1")){
//						stTemp = st1;
//						stTempName = "st1";
//					}else if((st2.size()<=2) && (stFromName != "st2")){
//						stTemp = st2;
//						stTempName = "st2";
//					}else if(st3.size()<=2){
//						stTemp = st3;
//					}
//				}
//				i = mypop_g(sth,"sth");
//				mypush_g(stTemp,stTempName, i);
//				i = mypop_g(stFrom, stFromName);
//
//				mypush_g(sth,"sth",i);
//
//				i = mypop_g(sth, "sth");
//
//				mypush_g(stTo, stToName, i);				
//
//				i = mypop_g(stTemp, stTempName);
//
//				mypush_g(sth,"sth", i);
//
//				return true;
//			}else{
//				return false;
//			}
//		}
//	}
//
//	static boolean myTransfer2(Deque<Integer> stFrom, Deque<Integer> stTo, Deque<Integer> stHeap){
//		Integer i = mypop(stFrom);
//		if (!(i==null)){
//			if(mypush(stTo,i)==false){
//				//put back
//				mypush(stFrom,i);
//				return false;
//			}else{
//				Integer j = mypop(stFrom);
//				if (!(j==null)){
//					if(mypush(stTo,j)==false){
//						//put back
//						mypush(stFrom,j);
//						i =mypop (stTo);
//						mypush(stFrom,i);
//						return false;
//					}else{
//						return true;
//					}
//				} else {
//					//put back
//					i = mypop (stTo);
//					mypush(stFrom,i);
//					return false;
//				}
//			}	
//		} else {
//			return false;
//		}
//	}
//
//	static boolean myTransfer3(Deque<Integer> stFrom, Deque<Integer> stTo, Deque<Integer> stHeap){
//		Integer i = mypop(stFrom);
//		if (!(i==null)){
//			if(mypush(stTo,i)==false){
//				//put back
//				mypush(stFrom,i);
//				return false;
//			}else{
//				Integer j = mypop(stFrom);
//				if (!(j==null)){
//					if(mypush(stTo,j)==false){
//						//put back
//						mypush(stFrom,j);
//						i =mypop (stTo);
//						mypush(stFrom,i);
//						return false;
//					}else{
//						Integer k = mypop(stFrom);
//						if (!(k==null)){
//							if(mypush(stTo,k)==false){
//								//put back
//								mypush(stFrom,k);
//								j =mypop (stTo);
//								mypush(stFrom,j);
//								i =mypop (stTo);
//								mypush(stFrom,i);
//								return false;
//							}else{
//								return true;
//							}
//						} else {
//							//put back
//							j =mypop (stTo);
//							mypush(stFrom,j);
//							i = mypop (stTo);
//							mypush(stFrom,i);
//							return false;
//						}
//					}
//				} else {
//					//put back
//					i = mypop (stTo);
//					mypush(stFrom,i);
//					return false;
//				}
//			}	
//		} else {
//			return false;
//		}
//	}
//
//	static boolean myTransferSwap2(Deque<Integer> stFrom, Deque<Integer> stTo, Deque<Integer> stHeap){
//		Integer i = mypop(stFrom);
//		if (!(i==null)){
//			Integer j = mypop(stFrom);
//			if (!(j==null)){
//				if(mypush(stTo,j)){
//					if(mypush(stTo,i)){
//						return true;
//					}else{
//						//put back
//						j = mypop (stTo);
//						mypush(stFrom,j);
//						mypush(stFrom,i);
//						return false;
//					}
//				}else{
//					//put back
//					mypush(stFrom,j);
//					mypush(stFrom,i);
//					return false;
//				}
//			}else{
//				//put back
//				mypush(stFrom,i);
//				return false;
//			}
//
//		}else{
//			//nothing to put back
//			return false;
//		}
//
//	}
//
//	static boolean myTransferSwap3(Deque<Integer> stFrom, Deque<Integer> stTo, Deque<Integer> stHeap){
//		if(true){
//			Integer i = mypop(stFrom);
//			if (!(i==null)){
//				Integer j = mypop(stFrom);
//				if (!(j==null)){
//					Integer k = mypop(stFrom);
//					if (!(k==null)){
//						if(mypush(stTo,k)){
//							if(mypush(stTo,j)){
//								if(mypush(stTo,i)){
//									return true;
//								}else{
//									//put back
//									j = mypop (stTo);
//									k = mypop (stTo);
//									mypush(stFrom,k);
//									mypush(stFrom,j);
//									mypush(stFrom,i);
//									return false;
//								}
//							}else{
//								//put back
//								k = mypop (stTo);
//								mypush(stFrom,k);
//								mypush(stFrom,j);
//								mypush(stFrom,i);
//								return false;
//							}
//						}else{
//							//put back
//							mypush(stFrom,k);
//							mypush(stFrom,j);
//							mypush(stFrom,i);
//							return false;
//						}
//					}else{
//						//put back
//						mypush(stFrom,j);
//						mypush(stFrom,i);
//						return false;
//					}
//
//				}else{
//					//put back
//					mypush(stFrom,i);
//					return false;
//				}
//			}else{
//				//nothing to put back
//				return false;
//
//			}
//		}else{
//			return false;
//		}
//	}

	public static boolean mypush (Deque<Integer[]> deque, Integer[] temp){
		boolean ans;
		
		E1.threads.get_serialModel().print("mypush: " );

		
		if (deque.size() <= 5){
			deque.push(temp);
			ans = true;
		} else {
			ans = false;
		}
		
		for(Integer[] x : deque ){
			E1.threads.get_serialModel().print("deque: " + x[0] );
		}
		E1.threads.get_serialModel().print("pushed: " + temp[0] );

		E1.threads.get_serialModel().println();
//		drawStacks(pole);
		convertStacksToLong();
		return ans;
	}

	public static Integer[]  mypop (Deque<Integer[]> deque){
		E1.threads.get_serialModel().print("mypop: " );
		for(Integer[] x : deque ){
			E1.threads.get_serialModel().print("deque: " + x[0] );
		}
		
		Integer[] ans;
		if (deque.size() >= 1){
			Integer[] i = (Integer[]) deque.pop();
			ans = i;
		}
		else{
			ans = null;
		}
		E1.threads.get_serialModel().print("popped: " + ans[0] );

		E1.threads.get_serialModel().println();
//		drawStacks(pole);
		convertStacksToLong();
		return ans;
	}

	public static void convertLongToStacks(Long[] init2, int[] pole) {

		/*
		 * Inglenook siding problem has 3 sidings of 5, 3, 3 and one heap siding of 3 total 14
		 * There are 8 trucks numbered 1 to 8
		 * The final layout needs to be 5 of the 8 trucks in any order in the siding containing 5 trucks
		 * We have an integer for the trucks in the order 5(1),3(2),3(3),3(h)
		 */
		st1 = null;
		st2 = null;
		st3 = null;
		sth = null;
		st1 = new ArrayDeque<Integer[]>();
		st2 = new ArrayDeque<Integer[]>();
		st3 = new ArrayDeque<Integer[]>();
		sth = new ArrayDeque<Integer[]>();
		for (int i = 14; i >= 1; i--) {
			////99System.out.print(i + "th digit " + getNthDigit(l, 10, i));
			int n = getNthDigit(init2[0], 10, i);
			int m = getNthDigit(init2[1], 10, i);
			switch (i) {
			case 1: case 2: case 3: case 4: case 5:
				if ( n != 0){
					st1.push(new Integer[]{m, n});
					pole[m]= 3;
				}
				break;
			case 6: case 7: case 8:
				if ( getNthDigit(init2[0], 10, i) != 0){
					st2.push(new Integer[]{ getNthDigit(init2[0], 10, i),getNthDigit(init2[1], 10, i)});
					pole[getNthDigit(init2[1], 10, i)]= 2;
				}
				break;
			case 9: case 10: case 11:
				if ( getNthDigit(init2[0], 10, i) != 0){
					st3.push(new Integer[]{ getNthDigit(init2[0], 10, i),getNthDigit(init2[1], 10, i)});
					pole[getNthDigit(init2[1], 10, i)]= 1;
				}
				break;
			case 12: case 13: case 14:
				if ( getNthDigit(init2[0], 10, i) != 0){
					sth.push(new Integer[]{ getNthDigit(init2[0], 10, i),getNthDigit(init2[1], 10, i)});
					pole[getNthDigit(init2[1], 10, i)]= 0;
				}
				break;
			}
		}

	}

	
	public static int isUse_graphics() {
		return use_graphics;
	}

	static boolean mypush_g(Deque<Integer> st , String stName, Integer i){
		//99System.out.print("push " + i + " to " + stName);
		if (st.size() <= 5){
			st.push(i);
			

	        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String username;
	        try {
	            username = reader.readLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
			drawStacks(getPole());
			convertStacksToLong();
			return true;
		} else 
			return false;
	}


	static Integer  mypop_g (Deque<Integer> st, String stName){
		//99System.out.print("pop " + stName);
		if (st.size() >= 1){
			Integer i = (Integer) st.pop();
			

	        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String username;
	        try {
	            username = reader.readLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
			drawStacks(getPole());
			convertStacksToLong();
			
			return i;
		}
		else{
			return null;
		}
	}

	public static void setUse_graphics(int use_graphics) {
		Myfunctions.use_graphics = use_graphics;
	}

	private static int[] pole;
	private static long init ;

	public static void openGraphics(int N, Long[] init1) {

		// TODO Auto-generated method stub
		int WIDTH  = 200;                    // width of largest disc
		int HEIGHT = 20;                     // height of each disc

		// set size of window and sale
		StdDraw.setCanvasSize(4*WIDTH,2* (N+3)*HEIGHT);
		StdDraw.setXscale(-1, 3);
		StdDraw.setYscale(-(N+3), N+3);

		setPole(new int[N+1]);       // pole[i] = pole (0-2) that disc i is on

		Myfunctions.convertLongToStacks(init1,getPole());

		Myfunctions.drawStacks(getPole());
		positions = init1;
		init = init1[0];
	}

	public static void drawDiscs(){
		//init = Myfunctions.convertStacksToLong(Myfunctions.getSt1(), Myfunctions.getSt2(), Myfunctions.getSt3(), Myfunctions.getSth());
		//Myfunctions.convertLongToStacks(init,pole);
		Myfunctions.drawStacks(getPole());
		//Myfunctions.convertLongToStacks(init,pole);
	}

	public static void drawStacks() {
		// TODO Auto-generated method stub
		drawStacks(getPole());
	}

	public static void convertStacksToLong() {
		positions = convertStacksToLong(st1,st2,st3,sth);
		
	}

	public static Long[] getPositions() {
		return positions;
	}

	/**
	 * @return the pole
	 */
	public static int[] getPole() {
		return pole;
	}

	/**
	 * @param pole the pole to set
	 */
	public static void setPole(int[] pole) {
		Myfunctions.pole = pole;
	}

	/**
	 * @return the stacks
	 */
	public static List<Deque<Integer[]>> getStacks() {
		return stacks;
	}

	/**
	 * @param stacks the stacks to set
	 */
	public static void setStacks(List<Deque<Integer[]>> stacks) {
		Myfunctions.stacks = stacks;
	}

	/**
	 * @return the init
	 */
	public static long getInit() {
		return init;
	}

	/**
	 * @param init the init to set
	 */
	public static void setInit(long init) {
		Myfunctions.init = init;
	}


}
