package problem.TSP;
import algorithm.genetic.GeneticAlgorithm;
import algorithm.genetic.Individual;
public class IndividualTsp implements Individual{
	public double fitness = 0;
	public int[] chromosome =new int [100];
	public double probability=0;
	public int[]degrees = null;
	@Override
	public String translate() {
		String result = "Â·¾¶Îª£º";
		for(int i = 0;i<this.chromosome.length;i++){
			result+=this.chromosome[i]+"-";		
		}
		result += this.chromosome[0];
		return result;
	}
	@Override
	public double evaluate(GeneticAlgorithm ga) {
		GeneticTsp gt = (GeneticTsp)ga;
		double v=0.0;
		for(int i = 1;i<gt.places.length;i++){
			//System.out.println("aaa"+i);
			double l = gt.distances[i-1][i];
			v+=l;
		}
		v+=gt.distances[0][gt.places.length-1];
		if(this.chromosome.length!=gt.places.length){
			return 10000.0;
		}
		return v;
	}
	@Override
	public double fitnessFunction(GeneticAlgorithm ga) {
		double fitness = 10000.0-this.evaluate(ga);
		
		return fitness;
	}
	

	

}
