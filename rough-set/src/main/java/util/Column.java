package util;

import java.util.ArrayList;
import java.util.HashMap;

public class Column {
	public String title = null;
	public String[] content = null;
	public double[] data = null;
	public ArrayList<MySet> partition = null;
	public Column(int rowCount){
		content = new String[rowCount];
		data = new double[rowCount];
		
	}
	public void setData(){
		for(int j = 0;j < content.length;j++)//row[0] is titles
		{
			try{
				//System.out.println(content[j].trim());
				data[j] = Double.parseDouble(content[j].trim());
			}
			catch(Exception e){
				System.out.println("doubleformat exception");
				return;
			}
			
		}
	}
	public ArrayList<MySet> partition(){
		ArrayList<MySet> result = new ArrayList<MySet>();
		//result.add(new MySet());
		boolean b = false;//b:has match
		for(int i = 0;i<data.length;i++){//for every row in this Column
			b=false;
			//System.out.println("data:"+data[i]);
			//if(!result.isEmpty()){
			//System.out.println("partition  "+this.title+result.size());
			for(int j = 0;j<result.size();j++){//for each part
				if(Math.abs((result.get(j).tag1)-(data[i]))<0.001){//find match
					//System.out.println("find match"+result.get(j).tag1+result.get(j));
					result.get(j).add(i);
					b=true;
					break;
				}
			}
			//}
			
			if(b==false){//no match
				MySet part = new MySet();
				part.add(i);
				part.tag1 = data[i];
				result.add(part);
				//System.out.println("part "+part.tag1+part);
				//b=true;
			}		
			
		}
		partition = result;
		//System.out.println(partition);
		return result;
	}
	public MySet getMatchedPartition(int i){
		//System.out.println();
		this.partition();
		//System.out.println("hellohere"+this.partition);
		if(i>=data.length){
			System.out.println("partition:data index out of bound" );
			return null;
		}
		//MySet[] parts = (MySet[])this.partition.toArray();
		//System.out.println(this.partition.size()+"size of p");
		for(MySet part:this.partition){//for each part
			//System.out.println(this.title+i+part);
			if(part.contains(i)){//find match
				return part;
				
			}
		}
		return null;
	}
	

}
