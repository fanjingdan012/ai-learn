package util;

import java.util.ArrayList;

public class Table {
	public ArrayList<Column> data = new ArrayList<Column>();
	public Table(String filename){
		String content = ReadData.readTxt(filename);
		this.data =  ReadData.proceedTxt(content);		
	}
	
	public void deleteColumn(int columnIdx){
		if(columnIdx < this.data.size()){
			//System.out.println("delete"+columnIdx+this.data.size());
			this.data.remove(columnIdx);
			//System.out.println(this.data.get(0).title+this.data.size());
			//this.printTableTitles();
		}
	}
	public void printTableTitles(){
		for(Column c:data){
			System.out.print(c.title+"\t");
			
		}
		System.out.println();
	}
	public void printData(){
		for(Column c:data){
			System.out.print(c.title+"\t");					

			
		}
		System.out.println();
		for(int i = 0;i<this.data.get(0).data.length;i++){
			for(Column c:data){
				System.out.print((int)c.data[i]+"\t");					
				
				
			}
			System.out.println();

		}
		
	}
	


}
