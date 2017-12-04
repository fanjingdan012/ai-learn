package roughSet;

import bpnetwork.Network;
import bpnetwork.NumberNN;
import discrete.EqualFrequency;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MySet;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import bpnetwork.DefaultNN;
import bpnetwork.Network;
import bpnetwork.NumberNN;

import discrete.EqualDistance;
import discrete.EqualFrequency;
import BP.BP;
import roughSet.RoughSet;
import util.MySet;
import util.Parse;

/**
 * Created by i312177 on 12/4/2017.
 */
public class RoughSetTest {

    double[][] trainInput = null;
    double[][] trainTarget = null;
    long beginTime;

    @Before
    public void before(){
        beginTime=System.currentTimeMillis();
    }

    @After
    public void after(){
        System.out.println("Program running for: "+ (System.currentTimeMillis()-beginTime)/1000 + "s！");
    }
    @Test
    public void testData1(){
        int []nondataColumnIdx = {0};
        RoughSet roughSet = new RoughSet("testcase/Data1.txt",nondataColumnIdx);
        roughSet.table.printTableTitles();
        roughSet.attributeReduce();
        roughSet.table.printTableTitles();
        roughSet.attributeValueReduce();
        roughSet.printRules();
        RoughSet testRS = new RoughSet("testcase/Data1.txt",nondataColumnIdx);
        for(int i = 0;i<testRS.table.data.get(0).content.length;i++){
            MySet pres = new MySet();
            for(int j = 0;j<testRS.table.data.size()-1;j++){
                String pre = "";
                pre+=testRS.table.data.get(j).title;
                pre+="=";
                pre+=testRS.table.data.get(j).data[i];
                pres.add(pre);
            }
            roughSet.getResult(pres);
        }

        //for(int i = 0;i<table.data.get(0).content.length;i++){


    }

    @Test
    public void testData2(){
        int []nondataColumnIdx = {0,0};
        RoughSet roughSet = new RoughSet("testcase/Data2.txt",nondataColumnIdx);
        EqualFrequency.discrete(roughSet);
        roughSet.table.printData();
        //System.out.println(roughSet.table.data.get(0).data[0]);
        roughSet.table.printTableTitles();
        roughSet.attributeReduce();
        roughSet.table.printTableTitles();
        roughSet.attributeValueReduce();
        roughSet.printRules();
        RoughSet testRS = new RoughSet("testcase/Data2.txt",nondataColumnIdx);
        EqualFrequency.discrete(testRS);
        for(int i = 0;i<testRS.table.data.get(0).content.length;i++){
            MySet pres = new MySet();
            for(int j = 0;j<testRS.table.data.size()-1;j++){
                String pre = "";
                pre+=testRS.table.data.get(j).title;
                pre+="=";
                pre+=testRS.table.data.get(j).data[i];
                pres.add(pre);
            }
            System.out.println("here");
            roughSet.getResult(pres);
        }


    }

    @Test
    public void testData2WithBPAfterAttrReduce(){
		/*int []nondataColumnIdx = {0,0};
		RoughSet roughSet = new RoughSet("Data2.txt",nondataColumnIdx);
		EqualFrequency.discrete(roughSet);
		roughSet.table.printData();
		roughSet.attributeReduce();
		roughSet.table.printTableTitles();
		BP bp = new BP(roughSet.table.data.size()-1,1000,6);
		this.loadTrainingData(roughSet, roughSet.table.data.size()-1, 6);


		for(int q=0;q!=500000;q++){
		    for(int p=0;p<80;p++){
		    	double[] in = this.trainInput[p];
		    	double[] target=this.trainTarget[p];

		    	bp.train(in, target);
		   	}
		}
		this.loadTrainingData(roughSet, roughSet.table.data.size()-1, 6);
		for(int p=0;p<80;p++){
		    double[] in = this.trainInput[p];
		    double[] target=this.trainTarget[p];
		    double[] out = bp.test(in);
		    for(int i = 0;i<out.length;i++){
		    	System.out.println("预测结果为："+out[0]+"正确结果为："+target[0]);
		    }
		}*/

        int []nondataColumnIdx = {0,0};
        RoughSet roughSet = new RoughSet("testcase/Data2.txt",nondataColumnIdx);
        EqualFrequency.discrete(roughSet);
        roughSet.attributeReduce();




        int train_size=80;
        int train_times=10000;
        this.loadTrainingData(roughSet, roughSet.table.data.size()-1, 6);

        Network nn=new NumberNN(roughSet.table.data.size()-1,30,6);
        nn.init();




        /**
         * training process
         */
        for(int j=0;j<train_times;j++)
        {
            for(int i=0;i<train_size;i++)
            {
                nn.output(this.trainInput[i]);
                nn.adjust(this.trainTarget[i]);
            }
        }
        /**
         * testing process
         */
        int test_size=80;
        this.loadTrainingData(roughSet, roughSet.table.data.size()-1, 6);

        for(int i=0;i<test_size;i++)
        {
            nn.output(this.trainInput[i]);
            double [] result=nn.getOutput();
            printOutput(result);
            //System.out.println(" ");
        }

        System.out.println("end!");


    }

    @Test
    public void testData2WithBP(){
        int []nondataColumnIdx = {0,0};
        RoughSet roughSet = new RoughSet("testcase/Data2.txt",nondataColumnIdx);
        EqualFrequency.discrete(roughSet);




        int train_size=80;
        int train_times=10000;
        this.loadTrainingData(roughSet, roughSet.table.data.size()-1, 6);

        Network nn=new NumberNN(roughSet.table.data.size()-1,40,6);
        nn.init();




        /**
         * training process
         */
        for(int j=0;j<train_times;j++)
        {
            for(int i=0;i<train_size;i++)
            {
                nn.output(this.trainInput[i]);
                nn.adjust(this.trainTarget[i]);
            }
        }
        /**
         * testing process
         */
        int test_size=80;
        this.loadTrainingData(roughSet, roughSet.table.data.size()-1, 6);

        for(int i=0;i<test_size;i++)
        {
            nn.output(this.trainInput[i]);
            double [] result=nn.getOutput();
            printOutput(result);
            //printOutput(this.trainTarget[i]);
            //System.out.println(" ");
        }

        System.out.println("end!");









		/*
		for(int q=0;q!=10000;q++){
			System.out.println("train"+q);
		    for(int p=0;p<80;p++){
		    	double[] in = this.trainInput[p];
		    	double[] target=this.trainTarget[p];

		    	bp.train(in, target);
		   	}
		}
		this.loadTrainingData(roughSet, roughSet.table.data.size()-1, 6);
		for(int p=0;p<80;p++){
		    double[] in = this.trainInput[p];
		    System.out.println(in[0]+" "+in[1]+" "+in[2]);
		    double[] target=this.trainTarget[p];
		    double[] out = bp.test(in);
		    for(int i = 0;i<out.length;i++){
		    	System.out.print("预测："+out[i]+"正确："+target[i]);
		    }
		    System.out.println();
		}*/



    }


    public void loadTrainingData(RoughSet roughSet,int inlen,int tarlen){

        this.trainInput = new double[roughSet.table.data.get(0).content.length][inlen];
        this.trainTarget = new double [roughSet.table.data.get(0).content.length][tarlen];

        for(int r = 0;r<roughSet.table.data.get(0).content.length;r++){
            for(int c = 0;c<inlen;c++){
                trainInput[r][c] = roughSet.table.data.get(c).data[r];
            }
            int targetClass = (int)roughSet.table.data.get(roughSet.table.data.size()-1).data[r]/3-1;
            for(int c = 0;c<tarlen;c++){
                //trainTarget[r][c] = roughSet.table.data.get(roughSet.table.data.size()-1).data[r];
                if(c==targetClass){
                    this.trainTarget[r][c] = 1;
                    //System.out.print(1);
                }
                else{
                    this.trainTarget[r][c] = 0;
                    //System.out.print(0);
                }
            }
            //System.out.println();
            //System.out.print(this.trainInput[i][j]+" ");
            //this.trainInput[r][roughSet.table.data.size()-1] = 1.0;
        }
        return;






    }



    public static void printOutput(double [] a)
    {
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+"\t");
        translate(a);
    }
    public static void translate(double [] a){
        for(int i=0;i<a.length;i++){
            if(a[i]>0.5)
                System.out.println(i*3+3.5);
        }

    }

}
