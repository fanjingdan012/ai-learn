package problem.KnapSack;
import java.util.ArrayList;
import java.util.Random;

import problem.TSP.IndividualTsp;
import util.BitString;

import algorithm.genetic.GeneticAlgorithm;
import algorithm.genetic.Individual;
public class GeneticKnapSack implements GeneticAlgorithm{
	public Random random = new Random();
	public int generations = 20000;
	public int initPopSize = 400;
	public double pc = 0.2;//交叉概率
	public double pm = 0.01;//变异概率
	public double tf = 300;//足够好的适应度标准（超过了即使不到代数也终止）
	public ArrayList<IndividualKnapSack> population = new ArrayList<IndividualKnapSack>();
	public ArrayList<IndividualKnapSack> newPopulation = new ArrayList<IndividualKnapSack>();
	public IndividualKnapSack result = null;
	
	double[] weights;
	double[] profits;
	double maxWeight = 0;
	
	
	public GeneticKnapSack(double[] weights,double[] profits,double maxWeight){
		this.weights = weights;
		this.profits = profits;
		this.maxWeight = maxWeight;
	}

	@Override
	public void generateFirstPop() {
		
		for(int i = 0;i<this.initPopSize;i++){
			IndividualKnapSack pop = new IndividualKnapSack();
			for(int j = 0;j<this.weights.length;j++){
				int k=Math.abs((random.nextInt())%2);
				pop.chromosome+=k+"";
			}
			this.population.add(pop);
			
		}
	}
	public void fitnessAll(){
		double totalFitness = 0;
		for(int i=0;i<this.population.size();i++){
			this.population.get(i).fitness=this.population.get(i).fitnessFunction(this);
			//System.out.println(this.population.get(i).fitness+"fitness");
			
			totalFitness+=this.population.get(i).fitness;
			
		}
		for(int j=0;j<this.population.size();j++){
			this.population.get(j).probability=(this.population.get(j).fitness)/totalFitness;
			//System.out.println(this.population.get(j).probability+" "+totalFitness);
		}
	}

	@Override
	public void select(){

		for(int i = 0;i<this.population.size();i++){//选出的个数
			double ptr = Math.random();
			//System.out.println("ptr="+ptr);
			double lowBound = 0.0;
			double highBound =0.0;
			for(int j=0;j<this.population.size();j++){
				lowBound = highBound;
				highBound=lowBound+this.population.get(j).probability;
				//System.out.println(j+" "+this.population.size()+"low:"+lowBound+"high:"+highBound);
				if(ptr>=lowBound&&ptr<highBound){
					this.newPopulation.add(this.population.get(j));
					//System.out.println(this.population.get(j).chromosome+"added");
				}
				if(ptr==1){
					this.newPopulation.add(this.population.get(this.population.size()-1));
				}
			}
		}
	}
	
	@Override
	public void crossover() {
		for(int i = 0;i<this.newPopulation.size()-1;i+=2){//选出的个数
			double ptr = Math.random();
			if(ptr<=this.pc){
				int betterIndividual = 0;
				if(this.newPopulation.get(i).fitnessFunction(this)>this.newPopulation.get(i+1).fitnessFunction(this)){
					betterIndividual = i;
				}
				else{
					betterIndividual = i+1;
				}
				String a = this.newPopulation.get(i).chromosome;
				String b = this.newPopulation.get(i+1).chromosome;
				String xor = BitString.bxor(a,b);
				String bitP = BitString.band(BitString.bxor(xor, this.newPopulation.get(betterIndividual).chromosome), xor);
				char[] bitsAdd = bitP.toCharArray();
				double weightAdd = 0.0;
				double weightO = 0.0;
				for(int j = 0;j<bitP.length();j++){
					if(bitsAdd[j]=='1'){
						weightAdd+=this.weights[j];
					}
					if(this.newPopulation.get(i).chromosome.charAt(j)=='1'){
						weightO +=this.weights[j];
					}
				}
				if(weightAdd+weightO<=this.maxWeight){
					this.newPopulation.get(i).chromosome = BitString.bor(a, bitP);
					this.newPopulation.get(i+1).chromosome = BitString.bxor(a, bitP);
				}
			}
			
			
		}
	}


	@Override
	public void mutation() {
		// TODO Auto-generated method stub
		for(int i = 0;i<this.newPopulation.size();i++){
			
			char[] bits = this.newPopulation.get(i).chromosome.toCharArray();
			for(int j = 0;j<bits.length;j++){
				double k = Math.random();
				if(k<this.pm){
					bits[j]=(bits[j]=='1')?'0':'1';
				}
				this.newPopulation.get(i).chromosome=new String(bits);
				if(this.newPopulation.get(i).fitnessFunction(this)==0.0){
					bits[j]=(bits[j]=='1')?'0':'1';
				}
			}
			this.newPopulation.get(i).chromosome=new String(bits);
			//System.out.println("mmmm"+this.newPopulation.get(i).chromosome);
			
		}
	}


	@Override
	public void run(){
		int generation = 1;
		generateFirstPop();
		
		//System.out.println(this.population.size()+"initsize");
		outer:
		while (generation<=generations){
			//System.out.println("*********************第"+generation+"代：***********************");
			//evaluate fitness of each member of the population P(t);			
			this.fitnessAll();
			//select members from population P(t) based on fitness;
			this.select();
			//produce the offspring of these pairs using genetic operators;
			this.crossover();
			//replace candidates of P(t), with these offspring;
			this.mutation();
			//set time t= t + 1
			this.result = new IndividualKnapSack();
			double avgFitness = 0;
			for(int j=0;j<this.newPopulation.size();j++){
				//System.out.println("chromo:"+this.newPopulation.get(j).chromosome+" fitness:"+this.newPopulation.get(j).fitnessFunction(this));
				avgFitness+=this.newPopulation.get(j).fitnessFunction(this);
				
				if(this.population.get(j).fitnessFunction(this)>=this.result.fitnessFunction(this)){
		    		this.result=this.population.get(j);		    		
		    	}			
				//if(this.population.get(j).fitness>=tf){
				//	this.result = this.population.get(j);
				//	break outer;
				//}    
			

			}
			avgFitness=avgFitness/this.newPopulation.size();
			//System.out.println("avgFitness is "+avgFitness+",maxFitness is "+this.result.fitness+",chromo is"+result.chromosome);
			System.out.println(avgFitness+",maxFitness"+this.result.fitnessFunction(this));
			this.population = this.newPopulation;
			this.newPopulation = new ArrayList<IndividualKnapSack>();
			generation++;
		}
		System.out.println("generation:"+(generation)+",result:"+this.result.translate());
	
		
	}

}
