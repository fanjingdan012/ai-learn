package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class AnalyzeData {
	public static void main(String [] args) throws Exception
	{
		//parseNumberTest();
		//own--double;sta--integer
		File own=new File("NumberLearningResult");
		File sta=new File("NumberTestOut");
		BufferedReader ownbr=new BufferedReader(new FileReader(own));
		BufferedReader stabr=new BufferedReader(new FileReader(sta));
		String line;int i=0,j=0,count=0;
		int match=0;
		while((line=ownbr.readLine())!=null)
		{
			i=0;j=0;count++;
			StringTokenizer st=new StringTokenizer(line," ",false);
			while(st.hasMoreTokens())
			{
				if(Double.parseDouble(st.nextToken())==1)
					break;
				else i++;
			}
			System.out.println("i is "+i);
			line=stabr.readLine();
			if(line==null)
				{
				System.out.println(match/1000);
				System.out.println(count);
				throw new Exception();
				}
			st=new StringTokenizer(line," ",false);
			while(st.hasMoreTokens())
			{
				if(Integer.parseInt(st.nextToken())==1)
					break;
				else j++;
			}
		//	System.out.println("j is "+j);
			if(i==j)
			{
				match++;
			//	System.out.println("==========match=========");
			}
		}
		System.out.println(match);
	}
	
	public static void parseNumberTest() throws FileNotFoundException
	{
		File file =new File("NumberTest");
		File outf =new File("NumberTestOut");
		PrintWriter pw=new PrintWriter(outf);
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line,out;int i=0;
			while((line=br.readLine()) != null)
			{
				StringTokenizer st=new StringTokenizer(line,":",false);
				out=st.nextToken();
				pw.write(out+"\n");
				
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
