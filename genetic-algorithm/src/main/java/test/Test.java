package test;

import problem.KnapSack.GeneticKnapSack;
import problem.TSP.GeneticTsp;
import problem.TSP.Place;


public class Test {
    public static void testKnapSack1(){
    	double[] weights = {100,77,22,29,50,90};
    	double[] profits = {5,92,22,87,46,90,};
    	double maxWeight = 200;
    	GeneticKnapSack gks = new GeneticKnapSack(weights, profits, maxWeight);
    	gks.run();
    	//max is 273
    }
    public static void testKnapSack2(){
    	double[] weights = {200,79,58,86,11,28,62,15,68};
    	double[] profits = {8,83,14,54,79,72,52,48,62};
    	double maxWeight = 200;
    	GeneticKnapSack gks = new GeneticKnapSack(weights, profits, maxWeight);
    	gks.run();
    }
    
    public static void testKnapSack3(){
    	double[] weights = {1000,75,64,68,18,83,55,60,10,18,83,53,87,80,14,92,18,67,22,64,15,80,33,79,81,43,93,74,48,74,72,70,94,98,57,75,98,4,46,9,80,18,96,78,96,24,14,37,50,52,66,63,27,30,50,88,63,100,68,50,61,55,63,47,95,52,40,65,43,69,34,46,26,45,18,94,93,67,3,34,79,60,67,48,65,4,9,28,10,49,77,98,47,56,11,23,4,70,28,95,21,};
    	double[] profits = {100,27,21,14,82,36,94,88,71,86,69,75,60,74,16,38,49,24,68,87,85,32,70,37,36,65,29,91,6,54,13,78,70,95,2,38,84,30,44,85,75,39,24,42,76,15,53,68,78,75,24,53,84,34,99,32,22,5,80,39,92,34,59,17,41,17,19,28,57,31,1,21,44,47,71,28,9,15,71,36,24,36,81,33,40,69,65,68,73,4,19,1,11,50,13,78,93,34,60,41,35,};
    	double maxWeight = 2000;
    	GeneticKnapSack gks = new GeneticKnapSack(weights, profits, maxWeight);
    	gks.run();
    }

    public static void testTsp1(){
    	Place[] places = {new Place(10,95),
    			new Place(15,35),
    			new Place(20,55),
    			new Place(35,40),
    			new Place(45,100),
    			new Place(50,75),
    			new Place(65,80),
    			new Place(70,20),
    	};
    	GeneticTsp gt = new GeneticTsp(places);
    	gt.run();
    }
    public static void testTsp3(){
    	Place[] places = {new Place(5,10),
    			new Place(5,80),
    			new Place(25,95),
    			new Place(35,30),
    			new Place(40,15),
    			new Place(40,75),
    			new Place(45,50),
    			new Place(50,35),
    			new Place(50,40),
    			new Place(60,35),
    			new Place(60,0),
    			new Place(60,75),
    			new Place(62,40),
    			new Place(65,25),
    			new Place(70,20),
    			new Place(70,25),
    			new Place(75,0),
    			new Place(80,75),
    			new Place(80,45),
    			new Place(85,55),
    			new Place(90,15),
    			new Place(95,35),
    			new Place(98,45),
    	};
    	GeneticTsp gt = new GeneticTsp(places);
    	gt.run();
    }
    public static void testTsp2(){
    	Place[] places = {new Place(0,90),
    			new Place(25,80),
    			new Place(45,90),
    			new Place(45,95),
    			new Place(50,75),
    			new Place(60,90),
    			new Place(75,40),
    			new Place(80,40),
    			new Place(90,15),
    			new Place(95,60),
    	};
    	GeneticTsp gt = new GeneticTsp(places);
    	gt.run();
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//testKnapSack1();
		//testKnapSack2();
		//testKnapSack3();
		//testTsp1();
		//testTsp2();
		testTsp3();
		
		
		
	}

}
