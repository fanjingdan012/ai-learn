package algorithm.genetic;

public interface Individual {
	public abstract String translate();
	public abstract double evaluate(GeneticAlgorithm ga) ;
	public abstract double fitnessFunction(GeneticAlgorithm ga);
	//public abstract double geneFitness(GeneticAlgorithm ga,int start,int end);
}
