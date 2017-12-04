package discrete;

import java.util.ArrayList;
import java.util.Arrays;

import roughSet.RoughSet;
public class NaiveScaler {
	public static ArrayList<Double> getBreakPoint(double[] data) {
	    double breakPoint = 0;
	    ArrayList<Double> breakPoints = new ArrayList<Double>();
	    for (int i = 0; i < data.length - 1; i++) {
	        if (data[i] - data[i + 1]>0.05) {
	            breakPoint = (data[i] +data[i + 1]) / 2;
	            breakPoints.add(breakPoint);
	        }
	    }
	    return breakPoints;
	}
	public static void useBreakPoint(double[] data,ArrayList<Double> breakPoints){
		for (int j = 0; j < data.length; j++) {
			if(data[j]<=breakPoints.get(0)){
				data[j] = breakPoints.get(0)-0.5;
				continue;
			}
			if(data[j]>breakPoints.get(breakPoints.size()-1)){
				data[j] = breakPoints.get(breakPoints.size()-1)+0.5;
				continue;
			}
			for (int i = 0; i < breakPoints.size()-1; i++) {
		        if (data[j]>breakPoints.get(i)&&data[j]<=breakPoints.get(i+1)) {
		        	data[j] = (breakPoints.get(i)+breakPoints.get(i+1))/2;
		        	break;
		        }
		    }
		
		}
	}
	public static void discrete(RoughSet roughSet){
	    for(int i = 0;i<roughSet.table.data.size()-1;i++){
	    	double[] data1 = roughSet.table.data.get(i).data.clone();
	    	Arrays.sort(data1);
	    	ArrayList<Double> result = getBreakPoint(data1);
	    	//roughSet.table.data.get(i).data
	    	System.out.println("result is:" + result);
	    	useBreakPoint(roughSet.table.data.get(i).data,result);
	    }
	}
	/*
	public static void main(String args[]) {
	    discrete();
	}*/
	
}
