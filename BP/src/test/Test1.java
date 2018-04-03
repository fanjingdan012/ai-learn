package BP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test1 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		
		   int i=0;
		   int l=0;
	       double[]in=new double[15];
	       double[]out=new double[15];
	       double[]testin=new double[15];
	       
	       
		try 
		   { 
		    File read=new File("SinLearning.txt"); 
		    
		    BufferedReader br=new BufferedReader(new FileReader(read)); 
		    
		    String temp=null; 
		    temp=br.readLine();
		    
		    while(temp!=null) 
		    { 
		    String s[]=temp.split(":");
		    out[i]=Double.parseDouble(s[0]);
		    in[i]=Double.parseDouble(s[1]);
		    i++;
		    temp=br.readLine();
		    } 
		    
		    br.close(); 
		   } 
		   catch(FileNotFoundException e) 
		   { 
		    System.out.println("文件没有找到异常"); 
		   } 
		   catch(IOException e) 
		   { 
		    System.out.println(e.getMessage()); 
		   } 

	   System.out.println("测试数据训练中........");
	    BP bp=new BP(1,15,1);
	    for(int q=0;q!=5000;q++){
	    	for(int p=0;p<i;p++){
	    	double[] real =new double[1];
	    	double[] binary=new double[1];
	    	binary[0]=in[p];
	    	real[0]=out[p];
	    	bp.train(binary, real);
	    	}
	    }
	    System.out.println("训练完毕，输入数字，预测输出值");
	    
	    try 
		   { 
		    File read2=new File("SinLearning.txt"); 
		    
		    BufferedReader br2=new BufferedReader(new FileReader(read2)); 
		    
		    String temp2=null; 
		    temp2=br2.readLine();
		    
		    while(temp2!=null) 
		    { 
		    String s[]=temp2.split(":");
		    
		    testin[l]=Double.parseDouble(s[1]);
		   
		    l++;
		    temp2=br2.readLine();
		    } 
		    
		    br2.close(); 
		   } 
		   catch(FileNotFoundException e) 
		   { 
		    System.out.println("文件没有找到异常"); 
		   } 
		   catch(IOException e) 
		   { 
		    System.out.println(e.getMessage()); 
		   } 
		   
		   for(int s=0;s<l;s++){
			   double[]test=new double[1];
			   test[0]=testin[s];
			   double[] x=bp.test(test);
			   
			   System.out.println("预测结果为："+x[0]+"正确结果为："+out[s]);
		   }
	   
	}
	

}

