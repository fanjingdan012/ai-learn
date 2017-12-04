package bpnetwork;

import util.Parse;

public class SinMain {
	
	public static void main(String [] args)
	{
		int train_size=11;
		int train_times=10000;
		double [] tinput=new double[train_size];
		double [] toutput=new double[train_size];
		Parse.parse("SinLearning", tinput, toutput);
		Network nn=new DefaultNN(11,10,11);
		nn.init();
		/**
		 * training process
		 */
		for(int j=0;j<train_times;j++)
		{
			nn.output(tinput);
			nn.adjust(toutput);
			double [] result=nn.getOutput();
		//	printOutput(result);
		}
		/**
		 * testing process
		 */
		int test_size=11;
		double [] testinput=new double[test_size];
		double [] testoutput=new double[test_size];
		Parse.parse("SinLearning", testinput, testoutput);
		nn.output(testinput);
		double [] result=nn.getOutput();
		printOutput(result);
		
	}
	public static void printOutput(double [] a)
	{
		System.out.println("===============new result==========");
		for(int i=0;i<a.length;i++)
			System.out.println(a[i]);
	}

}
