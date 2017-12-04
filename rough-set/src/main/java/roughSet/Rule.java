package roughSet;

import java.util.ArrayList;

import util.MySet;

public class Rule {
	public MySet prerequisite = new MySet();//attribute=value
	public MySet result=new MySet();//attribute=value
	public String toString(){
		String s = "";
		for(Object str:this.prerequisite){
			String str1 = str.toString();
			s+=str1;
		}
		s+="->";
		for(Object str:this.result){
			String str1 = str.toString();
			s+=str1;
		}
		return s;
		
	}
}
