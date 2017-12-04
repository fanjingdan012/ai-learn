package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;



public class ReadData {	
	private String filep;
	
	
	
	
	private int RowCount = 0;
	
	private Connection con;
	private Statement stmnt;
	private ResultSet rs;
	private ResultSet rst;
	public ReadData(String filep){
		this.filep=filep;
	}
	public ArrayList<Column> readData(int columnNum){	
		ArrayList<Column> data = null;
		
		try{
			//System.out.println("hello0");
			Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
			//System.out.println("hello1"+filep);
			con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xlsx)};DBQ=" + filep);
			//System.out.println("hello2");
			stmnt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//System.out.println("hello3");
			rst=con.getMetaData().getTables(null, null, "%", null);
			//System.out.println("hello4");
			rst.next();
			String SheetName = rst.getString(3);
			String query = "Select distinct * from ["+SheetName+"]"  ;
			rs = stmnt.executeQuery( query );
			if(rs!=null)
			{
				System.out.println("result is not null");
				rs.last();
				RowCount=rs.getRow();
				rs.beforeFirst();
				if(RowCount>0)
				{
					data = new ArrayList<Column>();
					for(int i = 0;i<columnNum;i++){
						//System.out.println(i+" dddd");
						Column c = new Column(RowCount);
						data.add(c);
					}
					for(int j = 0;j < RowCount;j++)
					{
							 rs.next();
							 try
							 {
								 for(int i = 0;i<columnNum;i++){							 
									 data.get(i).content[j] = rs.getString(i+1);
									 //System.out.println(i+" "+j+" "+data.get(i).content[j]);
								 }
							 }
							 catch(Exception e)
							 {
								e.printStackTrace();
								//System.out.println(e.getMessage());
							 }
					 }
					
					
				 }
			 }
			 rs.close();
			 rst.close();
			 stmnt.close();
			 con.close();
			 

		}
		catch(Exception e)		{
			System.out.println(e.getMessage());
			System.out.println("fail to get student connection");
		}
		
		return data;
	
	}
	public static ArrayList<Column> proceedTxt(String content){
		ArrayList<Column> data = null;
		
		String[] rows = content.split("\r\n");

		if(rows.length>0)
		{
			data = new ArrayList<Column>();
			String [] columns = rows[0].split("\t");
			
			for(int i = 0;i<columns.length;i++){
				Column c = new Column(rows.length-1);
				c.title = columns[i];
				data.add(c);
			}
			for(int j = 1;j < rows.length;j++)//row[0] is titles
			{
				String [] rowContents = rows[j].split("\t");
				for(int i = 0;i<columns.length;i++){							 
					data.get(i).content[j-1] = rowContents[i];
		
				}
					
			 }
			
			
		 }
	
			 

		
		
		return data;
	}
	public static String readTxt(String fileName) {
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
	
	public int getRow() {
		return RowCount;
	}
	/*
	public static void main(String [] args){
		String content = readTxt("Data1.txt");
		ArrayList<Column> data =  proceedTxt(content);
		System.out.println(data.get(0).content[1]);
		data.remove(0);
		System.out.println(data.get(0).content[1]);
	}
*/
	
	
	
	


}

