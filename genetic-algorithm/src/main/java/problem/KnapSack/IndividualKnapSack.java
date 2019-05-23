package problem.KnapSack;

import algorithm.genetic.GeneticAlgorithm;
import algorithm.genetic.Individual;

public class IndividualKnapSack implements Individual{
	public double fitness = 0;
	public String chromosome="";
	public double probability=0;

	@Override
	public String translate() {
		char[] bits = this.chromosome.toCharArray();
		String result = "带上第";
		for(int i = 0;i<this.chromosome.length();i++){
			if(bits[i]=='1'){
				result+=" "+(i+1)+",";
			}
		}
		result = result.substring(0, result.length()-1)+"件物品";
		return result;
	}

	@Override
	public double evaluate(GeneticAlgorithm ga) {
		double v=0.0;
		double weights = 0.0;
		GeneticKnapSack gks = (GeneticKnapSack)ga;
		char[] bits = this.chromosome.toCharArray();
		//System.out.println(bits.length+"length   "+gks.profits.length+"   "+gks.weights.length);
		for(int i = 0;i<bits.length;i++){
			fitness+=(bits[i]=='1'?gks.profits[i]:0.0);
			weights+=(bits[i]=='1'?gks.weights[i]:0.0);
		}
		if(weights>gks.maxWeight){
			v=0.0;
		}

		return v;
	}

	@Override
	public double fitnessFunction(GeneticAlgorithm ga) {
		double fitness=0.0;
		double weights = 0.0;
		GeneticKnapSack gks = (GeneticKnapSack)ga;
		char[] bits = this.chromosome.toCharArray();
		//System.out.println(bits.length+"length   "+gks.profits.length+"   "+gks.weights.length);
		for(int i = 0;i<bits.length;i++){
			fitness+=(bits[i]=='1'?gks.profits[i]:0.0);
			weights+=(bits[i]=='1'?gks.weights[i]:0.0);
		}
		if(weights>gks.maxWeight){
			fitness=0.0;
		}

		return fitness;
	}


	public double geneFitness(GeneticAlgorithm ga, int [] bitP) {
		// TODO Auto-generated method stub
		double fitness=0.0;
		double weights = 0.0;
		GeneticKnapSack gks = (GeneticKnapSack)ga;
		char[] bits = this.chromosome.toCharArray();
		//System.out.println(bits.length+"length   "+gks.profits.length+"   "+gks.weights.length);
		for(int i = 0;i<bitP.length;i++){
			fitness+=(bits[bitP[i]]=='1'?gks.profits[i]:0.0);
			weights+=(bits[bitP[i]]=='1'?gks.weights[i]:0.0);
		}
		double totalWeights = 0.0;
		for(int i = 0;i<gks.weights.length;i++){
			totalWeights+=gks.weights[i];
		}
		fitness=fitness-weights+totalWeights;

		return fitness;
	}


}
