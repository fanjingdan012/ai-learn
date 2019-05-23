package problem.TSP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import problem.KnapSack.IndividualKnapSack;

import algorithm.genetic.GeneticAlgorithm;
import algorithm.genetic.Individual;

public class GeneticTsp implements GeneticAlgorithm{
	public Random random = new Random();
	public int generations = 10000;
	public int initPopSize = 400;
	public double pc = 0.2;//交叉概率
	public double pm = 0.2;//变异概率
	public double tf = 9500;//足够好的适应度标准（超过了即使不到代数也终止）
	public ArrayList<IndividualTsp> population = new ArrayList<IndividualTsp>();
	public ArrayList<IndividualTsp> newPopulation = new ArrayList<IndividualTsp>();
	public IndividualTsp result = null;
	
	public Place[] places;
	public double [][]distances;
	
	public GeneticTsp(Place[] places){
		this.places = places;
		getDistances();
	}
	public void getDistances(){
		this.distances = new double[places.length][places.length];
		for(int i = 0;i<places.length;i++){
			for(int j = 0;j<places.length;j++){
				distances[i][j] = Math.sqrt(Math.pow(places[i].x-places[j].x, 2)+Math.pow(places[i].x-places[j].x, 2));
			}
		}
	}
	
	
	@Override
	public void generateFirstPop() {
		for(int i = 0;i<this.initPopSize;i++){
			IndividualTsp pop = new IndividualTsp();
			pop.chromosome=new int[this.places.length];	
			HashMap<Integer,Integer> h=new HashMap<Integer,Integer>();
			pop.chromosome[0]=0;
			for(int j = 1;j<pop.chromosome.length;j++){
				boolean b = true;
				while(b){
					int k = (int) (Math.random()*(pop.chromosome.length-1)+1);
					if(k>=1&&k<pop.chromosome.length&&(h.get(k)==null)){
						pop.chromosome[j]=k;
						h.put(k,k);
						b = false;
					}
				
				}
				
			}
				
			
			this.population.add(pop);
			
			System.out.println("ini:"+pop.translate());
			
		}
	}
	@Override
	public void fitnessAll() {
		double totalFitness = 0;
		for(int i=0;i<this.population.size();i++){
			this.population.get(i).fitness=this.population.get(i).fitnessFunction(this);
			//System.out.println(this.population.get(i).fitness+"fitness");
			
			totalFitness+=this.population.get(i).fitness;
			
		}
		for(int j=0;j<this.population.size();j++){
			this.population.get(j).probability=(this.population.get(j).fitness)/totalFitness;
			//System.out.println(this.population.get(j).probability+"ppppppp"+totalFitness);
		}
		
	}

	@Override
	public void select() {
		for(int i = 0;i<this.population.size();i++){//选出的个数
			double ptr = Math.random();
			//System.out.println("ptr="+ptr);
			double lowBound = 0.0;
			double highBound =0.0;
			for(int j=0;j<this.population.size();j++){
				lowBound = highBound;
				highBound=lowBound+this.population.get(j).probability;
				if(highBound==Double.NaN){
					System.out.println(j+" "+this.population.size()+"low:"+lowBound+"high:"+highBound);
					
				}
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
	public int[][] findCrossover(int[] c1,int[]c2){
		int count = 0;
		int[][] result = new int[100][3];//0:start1,1:start2,2:length
		for(int i = 0;i<c1.length;i++){//标号0-c1.length-1点循环
			//c2p:c2里i的位置
			int c2p = 0;
			int c1p = 0;
			for(int a = 1;a<c1.length;a++){
				if(c2[a]==i){
					c2p = a;					
				}
				if(c1[a]==i){
					c1p = a;					
				}
				
			}	
			if(c1p==c2p||c1p==0||c2p==0){
				continue;
			}
			

			int maxL = (c1.length-c1p>c2.length-c2p)?(c2.length-c2p):(c1.length-c1p);
			HashMap<Integer,Integer> h = new HashMap<Integer,Integer>();
			
			for(int j = 0;j<maxL;j++){
				if(!h.containsKey(c1[j+c1p])){
					h.put(c1[j+c1p], c1[j+c1p]);
				}
				if(!h.containsKey(c2[j+c2p])){
					h.put(c2[j+c2p], c2[j+c2p]);
				}
				if(c1[j+c1p]==c2[j+c2p]&&h.size()==j){
					result[count][0] = c1p;
					result[count][1] = c2p;
					result[count][2] = j;
					System.out.println(count+"aaaa"+c1[c1p]+"aaaa"+c2[c2p]+"aaaa"+j+"aaaa"+c1[c1p+j]+"aaaa"+c2[c2p+j]);
					count++;
					break;
				}
			}	
		}
		if(count==0){
			return null;
		}
		int[][] result1 = new int[count][3];
		for(int i = 0;i<count;i++){
			for(int j = 0;j<3;j++){
				result1[i][j] = result[i][j];
				//System.out.println("result findco:"+result1[i][j]);
			}
		}
		return result1;
		
	}
	@Override
	public void crossover() {
		for(int i = 0;i<this.newPopulation.size()-1;i+=2){//选出的个数
			double ptr = Math.random();		
			int betterID = 0;
			if(ptr<=pc){
				if(this.newPopulation.get(i).fitnessFunction(this)>this.newPopulation.get(i+1).fitnessFunction(this)){
					betterID = i;
					
				}else{
					betterID = i+1;
				}
				//System.out.println("aaaa"+this.newPopulation.get(i).translate()+"bbbb"+this.newPopulation.get(i+1).translate());
				int[][] crossoverGene = findCrossover(this.newPopulation.get(i).chromosome,this.newPopulation.get(i+1).chromosome);
				if(crossoverGene==null){
					return;
				}
				int k = (int)(Math.random()*(crossoverGene.length-1));
				
				System.out.println(this.newPopulation.get(i).translate()+":"+crossoverGene[k][0]+this.newPopulation.get(i+1).translate()+":"+crossoverGene[k][1]+"length:"+crossoverGene[k][2]);
				System.out.println("bbbb"+this.newPopulation.get(i).translate());
				System.out.println("bbbb"+this.newPopulation.get(i+1).translate());
				
				for (int j = 1;j<crossoverGene[k][2];j++){
					int tmp = this.newPopulation.get(i).chromosome[j+crossoverGene[k][0]];
					this.newPopulation.get(i).chromosome[j+crossoverGene[k][0]]=this.newPopulation.get(i+1).chromosome[j+crossoverGene[k][1]];
					this.newPopulation.get(i+1).chromosome[j+crossoverGene[k][1]]=tmp;
				}				
				System.out.println("cccc"+this.newPopulation.get(i).translate());
				System.out.println("cccc"+this.newPopulation.get(i+1).translate());	
				
			}
		}
	}
	@Override
	public void mutation() {
		// TODO Auto-generated method stub
		for(int i = 0;i<this.newPopulation.size();i++){
			
			double k = Math.random();
			if(k<this.pm){
				int bitP1 = (int) (Math.random()*(this.newPopulation.get(i).chromosome.length-1)+1);
				int bitP2 = (int) (Math.random()*(this.newPopulation.get(i).chromosome.length-1)+1);
				
				int tmp = this.newPopulation.get(i).chromosome[bitP1];
				this.newPopulation.get(i).chromosome[bitP1] = this.newPopulation.get(i).chromosome[bitP2];
				this.newPopulation.get(i).chromosome[bitP2] = tmp;
			}
			//System.out.println("mmmm"+this.newPopulation.get(i).translate());
			
		}
				
	}
	@Override
	public void run() {
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
			this.result = new IndividualTsp();
			double avgFitness = 0.0;
			for(int j=0;j<this.newPopulation.size();j++){
				//System.out.println("chromo:"+this.newPopulation.get(j).chromosome+" fitness:"+this.newPopulation.get(j).fitnessFunction(this));
				avgFitness+=this.newPopulation.get(j).fitnessFunction(this);
				
				if(this.population.get(j).fitnessFunction(this)>this.result.fitnessFunction(this)){
		    		this.result=this.population.get(j);		    		
		    	}			
				//if(this.population.get(j).fitness>=tf){
				//	this.result = this.population.get(j);
				//	break outer;
				//}  
			

			}
			
			avgFitness=avgFitness/this.newPopulation.size();
			System.out.println("avgFitness is "+avgFitness+",maxFitness is "+this.result.fitnessFunction(this)+","+result.translate());
			//System.out.println(avgFitness+",maxFitness is "+this.result.fitness);
			
			this.population = this.newPopulation;
			this.newPopulation = new ArrayList<IndividualTsp>();
			generation++;
		}
		System.out.println("generation:"+(generation)+",result:"+this.result.translate()+",长度为:"+this.result.evaluate(this));
	
		
	}

	


}
