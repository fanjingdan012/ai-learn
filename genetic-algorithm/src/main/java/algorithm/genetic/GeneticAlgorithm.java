package algorithm.genetic;

import java.util.ArrayList;

public interface GeneticAlgorithm {
	public abstract void generateFirstPop(); 
	public abstract void fitnessAll();
	public abstract void select();
	public abstract void crossover();
	public abstract void mutation();
	public abstract void run();
}
