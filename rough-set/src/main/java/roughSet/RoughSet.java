package roughSet;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import util.MySet;
import util.Table;

public class RoughSet {
	public int [] nondataColumnIdx={0};
	public String filename = "testcase/Data1.txt";
	public Table table = new Table(filename);
	//public MySet conflictSet = new MySet();
	public MySet<Rule> ruleSet = new MySet<Rule>();
	
	
	
	public RoughSet(String filename,int[] nondataColumnIdx){
		this.nondataColumnIdx=nondataColumnIdx;
		this.filename = filename;
		this.table = new Table(filename);
		for(int i = 0;i<this.nondataColumnIdx.length;i++){
			table.deleteColumn(this.nondataColumnIdx[i]);
			//System.out.println(this.nondataColumnIdx[i]+table.data.get(0).title);
		}
		for(int i = 0;i<this.table.data.size();i++){
			table.data.get(i).setData();
			table.data.get(i).partition();
		}
		
		
	}
	public void attributeReduce(){
		//get noncore attribute
		MySet reducableSet = new MySet();
		MySet testSet = new MySet();
		//System.out.println(this.table.data.size()-1+"hello");
		for(int j = 0;j<this.table.data.size()-1;j++){
			testSet.clear();
			testSet.add(j);
			//System.out.println(j);
			//System.out.println("--------------testset "+testSet+"---------------");
			if(!attributeReduceTest(testSet))	{
				reducableSet.add(j);
				//System.out.println(j+"add");
			}
			
		}
		System.out.println("reducableSet:"+reducableSet);
		//get candidate sets of arrtributes to be reduced
		ArrayList<MySet> subsets = reducableSet.getAllSubsets();
		MySet result = new MySet();
		
		//final attributes to be reduced
		if(reducableSet.isEmpty())
			return;
		result.add(reducableSet.get(0));
		
		for(MySet subset:subsets){
			if(subset.size()<2){				
				continue;
			}
			//System.out.println("subset:"+subset);
			//System.out.println(result.size()+"hhhh"+subset.size());
			if(result.size()<subset.size()&&attributeReduceTest(subset)==false)	{
				
				result = subset;	
				//System.out.println("result:"+result);
			}
		}
		System.out.println("final reduce attribute:"+result);
		//outer:	
			/*
		for(int k = 1;k<this.table.data.size()-coreNum;k++){
			for(int j = 0;j<this.table.data.size();j++){
				if(coreAttribute[j]==1){
					continue;
				}
				if(attributeReduceTest(j,restColumn))	{
					restColumn[j] = 1;
				}
				else{
					restColumn[j] = 0;
				}
				
			}
		
		}*/
		//reduce attribute
		Object[] resultArray = result.toArray();
		for(int i=0;i<resultArray.length;i++){	
			
			this.table.deleteColumn(((Integer)resultArray[i])-i);			
		}
		
		
	}
	public synchronized MySet attributeValueReduce(){
		//MySet ruleSet = new MySet();
		//for each row
		for(int r = 0;r<this.table.data.get(0).data.length;r++){
			System.out.println("------------------"+r+"------------------");
			MySet<MySet<Integer>> rowPreSet = new MySet();//every row's prerequisite samevSets
			//for each column, get samevSets
			for(int c = 0;c<this.table.data.size()-1;c++){
				//[r]c set(rows have same value)
				MySet<Integer> samevSet = this.getSamevSet(r,c);
				//System.out.println(samevSet);
				rowPreSet.add(samevSet);			
			}
			//result attribute's samevSet
			MySet rowRSet = this.getSamevSet(r,this.table.data.size()-1);
			//for all subsets of prerequisite attributes,retain each other, to see if it is subset of rowRSet
			ArrayList<MySet> subsets = rowPreSet.getRealSubsets();
			//System.out.println(subsets);
			for(MySet sub:subsets){	
				//System.out.println("sub:"+sub);
				
				MySet retainResultO = (MySet) sub.get(0);
				MySet retainResult = (MySet) retainResultO.clone();
				for(int i = 1;i<sub.size();i++){
					retainResult.retainAll((Collection) sub.get(i));	
				}
				//System.out.println("retain:"+retainResult+",rowRSet:"+rowRSet);
				if(retainResult.isSubsetOf(rowRSet)){
					//generate a rule
					Rule rule = new Rule();
					//System.out.println("sub again:"+sub);
					for(int i = 0;i<sub.size();i++){
						MySet samev = (MySet)sub.get(i);
						String s = this.table.data.get(samev.tag).title+"="+this.table.data.get(samev.tag).data[r];
						//System.out.print(s);
						rule.prerequisite.add(s);	
					}
					//System.out.println("here");
					boolean b = false;
					//System.out.println("\n"+this.ruleSet.size()+"hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
					MySet rms = new MySet();
					for(Object hasRule:this.ruleSet){
						
						//System.out.println("Checking:");
						Rule hasRule1 = (Rule)hasRule;
						//System.out.println("Checking:"+hasRule1);
						if(rule.prerequisite.isSubsetOf(hasRule1.prerequisite)){
							//System.out.println("not add because"+hasRule1);
							b = true;
							break;
						}
						if(hasRule1.prerequisite.isSubsetOf(rule.prerequisite)&&hasRule1.prerequisite.size()!=rule.prerequisite.size()){
							//ruleSet.remove(hasRule1);
							rms.add(hasRule1);
							//System.out.println("remove"+hasRule1);
						}
						
					}
					this.ruleSet.removeAll(rms);
					rms.clear();
					if(b==false&&!rule.prerequisite.isEmpty()){
						String s = "result="+this.table.data.get(this.table.data.size()-1).data[r];
						//System.out.println("->"+s);
						rule.result.add(s);
						//result is same and prerequisite contains already have rule
						//System.out.println();
						this.ruleSet.add(rule);
					}
					
					
				}
				
			}
		
		}
		return ruleSet;
		
		
		
		
	}
	public boolean attributeReduceTest(MySet testColumn) {//
		//TODO
		boolean conflict = true;
		MySet<Integer> retainResult = null;
		ArrayList<Integer> restSet = new ArrayList<Integer>();
		//MySet testedRows = new MySet();
		for(int c = 0;c<this.table.data.size()-1;c++){
			if(testColumn.contains(c)){
				continue;
			}
			restSet.add(c);
		}
		//System.out.println("restSet:"+restSet);
		if(restSet.isEmpty()){
			return true;
		}
		for(int r = 0;r<this.table.data.get(0).data.length;r++){//for each row
			conflict = true;
			retainResult = (MySet) table.data.get(restSet.get(0)).getMatchedPartition(r).clone();
			for(int c = 1;c<restSet.size();c++){
				retainResult.retainAll(table.data.get(restSet.get(c)).getMatchedPartition(r));
				//System.out.println("retainResult:"+retainResult+" column c:"+table.data.get(restSet.get(c)).getMatchedPartition(r));
				//retainResult.remove(r);
				if(retainResult.size()==1){//not all the same->no conflict with this row
					conflict = false;					
					break;
				}
			}
			if(conflict == false){
				continue;
			}
			Object[] retainArray = retainResult.toArray();//rows with same values
			
			for(int i = 0;i<retainArray.length;i++){
				int retainR = Integer.parseInt(retainArray[i].toString());
				
				if(this.table.data.get(this.table.data.size()-1).data[r]-this.table.data.get(this.table.data.size()-1).data[retainR]>0.001){
					conflict = true;
					return true;
				}
			}
			
		}
		return false;//can be reduced
		
	}
	public MySet getSamevSet(int r,int c){
		MySet result = this.table.data.get(c).getMatchedPartition(r);
		//System.out.println(result+"rc:"+r+c);
		result.tag = c;
		return result;
	}
	public void printRules(){
		for(Object r:this.ruleSet){
			Rule r1 = (Rule)r;
			System.out.println(r1.toString());
		}
	}
	public void getResult(MySet pres){
		//for(int i = 0;i<table.data.get(0).content.length;i++){
		for(Object r:this.ruleSet){//for each rule, check if satisfy
			Rule r1 = (Rule)r;
			boolean satisfyRule = true;
			outer:
			for(Object prerequisite:r1.prerequisite){//for each prerequisite, check if satisfy
				String rulePre= (String)prerequisite;
				String [] splitsRP = rulePre.split("=");
				for(Object pre:pres){//find match pre
					String givenPre = (String)pre;
					String[] splitsGP = givenPre.split("=");
					if(splitsGP[0].equals(splitsRP[0])){
						//System.out.println(splitsGP[1]+"=?"+splitsRP[1]);
						if(Math.abs(Double.parseDouble(splitsGP[1])-Double.parseDouble(splitsRP[1]))<0.1){
							break;//satisfy this prerequisite,next prerequisite
						}
						else{
							satisfyRule=false;
							break outer;//don't satisfy, next rule
						}
					}
				}
				
			}
			if (satisfyRule){
				System.out.println(r1.result);
				return;
			}
				//if(r1.)
			//System.out.println(r1.toString());
		}
		//}
		
	}
	

}
