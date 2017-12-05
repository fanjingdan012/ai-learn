package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class IO {
	public static String readData(String fileName) {
		File f = new File(fileName);
		BufferedReader input = null;
		StringBuilder buff= new StringBuilder();
		String html1;
		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(f), "GB2312"));
			String line;
			while ((line=input.readLine()) != null) {
				buff.append(line);
				buff.append("\r\n");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "";
				}
		}
		
		
		html1 = buff.toString();
		//System.out.println(html1);
		return html1;
	}
	
	public void logData(String fileName,String msg){
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(new File(fileName));
			pw.append(msg);
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pw != null)
				pw.close();
				
		}
	}

}
