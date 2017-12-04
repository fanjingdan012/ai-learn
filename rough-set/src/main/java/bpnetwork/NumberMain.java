package bpnetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import util.Parse;

public class NumberMain {
	static PrintWriter pw;
	
	public static void main(String [] args) throws FileNotFoundException
	{
		int train_size=10000;
		int train_times=100;
		double [][] tinput=new double[train_size][25];
		double [][] toutput=new double[train_size][10];
		Parse.parseNumber("NumberLearning", tinput, toutput);
		Network nn=new NumberNN(25,30,10);
		nn.init();
		File file=new File("NumberLearningResult");
	
		 pw=new PrintWriter(file);
		
		
		/**
		 * training process
		 */
		for(int j=0;j<train_times;j++)
		{
			for(int i=0;i<train_size;i++)
			{
				nn.output(tinput[i]);
				nn.adjust(toutput[i]);
			}
		}
		/**
		 * testing process
		 */
		int test_size=1000;
		double [][] testinput=new double[test_size][25];
		double [][] testoutput=new double[test_size][10];
		Parse.parseNumber("NumberTest", testinput, testoutput);
		for(int i=0;i<test_size;i++)
		{
			nn.output(testinput[i]);
			double [] result=nn.getOutput();
			printOutput(result);
			//System.out.println(" ");
		}
		pw.close();
		System.out.println("end!");
	}
	public static void printOutput(double [] a)
	{
		//System.out.println("===============new result==========");
		String r="";
		for(int i=0;i<a.length;i++)
			r+=a[i]+" ";
		r+="\n";
		pw.print(r);
	}

}
