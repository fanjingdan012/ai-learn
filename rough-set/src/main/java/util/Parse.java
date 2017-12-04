package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Parse {
	
	public static void parse(String filename,double [] input,double output[])
	{
		File file =new File(filename);
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line;int i=0;
			while((line=br.readLine()) != null)
			{
				StringTokenizer st=new StringTokenizer(line," :",false);
				output[i]=Double.parseDouble(st.nextToken());
				input[i]=Double.parseDouble(st.nextToken());
			//	System.out.println("input is "+input[i]+" output is "+output[i]);
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void parseNumber(String filename,double [][] input, double [][] output)
	{
		File file =new File(filename);
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line,in,out;int i=0;
			while((line=br.readLine()) != null)
			{
				StringTokenizer st=new StringTokenizer(line,":",false);
				out=st.nextToken();
				in=st.nextToken();
		//		System.out.println("in is "+in+" and out is "+out);
				StringTokenizer sti=new StringTokenizer(in," ",false);
				int j=0;
				while(sti.hasMoreElements())
				{
					input[i][j]=Integer.parseInt(sti.nextToken());
					j++;				
				}
				if(j!=25)
					System.err.println("in parsing input j is "+j);
				StringTokenizer sto=new StringTokenizer(out," ",false);
				j=0;
				while(sto.hasMoreElements())
				{
					output[i][j]=Integer.parseInt(sto.nextToken());
					j++;
				}
				if(j!=10)
					System.err.println("in parsing output j is "+j);
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
