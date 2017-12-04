package discrete;


import java.util.ArrayList;
import java.util.Arrays;

import roughSet.RoughSet;

public class EqualDistance{
	public static ArrayList<Double> getBreakPoint(double[] data,int numOfClasses) {
	    double breakPoint = data[0]-0.01;
	    ArrayList<Double> breakPoints = new ArrayList<Double>();
	    double totalDistance = data[data.length-1]-data[0];
	    double distance = totalDistance/numOfClasses;
	    while(breakPoint<data[data.length-1]) {
	        
	        breakPoint = breakPoint+distance;
	        breakPoints.add(breakPoint);
	        
	    }
	    return breakPoints;
	}
	public static void useBreakPoint(double[] data,ArrayList<Double> breakPoints){
		double distance = breakPoints.get(1)- breakPoints.get(0);
		for (int j = 0; j < data.length; j++) {
			if(data[j]<=breakPoints.get(0)){
				data[j] = breakPoints.get(0)-distance/2;
				continue;
			}
			if(data[j]>breakPoints.get(breakPoints.size()-1)){
				data[j] = breakPoints.get(breakPoints.size()-1)+distance/2;
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
	    	ArrayList<Double> result = getBreakPoint(data1,4);
	    	//roughSet.table.data.get(i).data
	    	System.out.println("result is:" + result);
	    	useBreakPoint(roughSet.table.data.get(i).data,result);
	    }
	}
	
}
