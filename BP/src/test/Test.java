package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problem.number.BPNumber;
import problem.sin.BPSin;

import algorithm.BP.BP;

public class Test {
	public static void testSin() throws IOException{
		BP bpsin = new BPSin(1,20,1);
		bpsin.eta = 0.1;
		bpsin.trainTimes = 100000;
		bpsin.train("SinLearning.txt");
		System.out.println("训练完毕，测试，神经网络将自动输出sin值。");

		for(double i = 0;i<=1.0;i+=0.1){
			
			
			System.out.print("sin("+i+")=");
			double[] inputs = new double[2];
			inputs[0] = i;
			inputs[1] = 1.0;
			//System.out.println("here");
			double[] result = bpsin.test(inputs);
			//System.out.println("here1");
			

			for (int j = 0; j < result.length;j++) {
				System.out.println(result[j]);
			}

			
		}
	
	}
	public static void testNumber() throws IOException{
		BP bpnum = new BPNumber(25,30,10);
		bpnum.eta = 0.01;
		bpnum.trainTimes = 100;
		bpnum.train("NumberLearning.txt");
		System.out.println("训练完毕，测试，神经网络将自动输出num值。");
		bpnum.loadTrainingData("NumberTest.txt");
		
		for(int i = 0;i<1000;i++){			
			System.out.print("num"+i+":=");
			double[] inputs = new double[bpnum.trainInput[i].length];
			for(int j = 0;j<inputs.length;j++){
				inputs[j] = bpnum.trainInput[i][j];
			}
			
			
			
			double[] result = bpnum.test(inputs);
			//System.out.println("here1");
			

			for (int j = 0; j < result.length;j++) {
				if(result[j]>0.5){
					System.out.print(1);
				}else{
					System.out.print(0);
				}
				
			}
			System.out.println();

			
		}
	
	}
	public static void main(String[] args) throws IOException {
		testSin();
		//testNumber();
	}
		
		
		
	

}
