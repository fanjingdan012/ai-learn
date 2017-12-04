package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class MySet<Element> extends ArrayList{
	public int tag = 0;// for temporal use
	public double tag1 = 0.0;//foe tmp use
	public ArrayList<MySet> getRealSubsets(){
		ArrayList<MySet> result = new ArrayList<MySet>();
		Object [] data = this.toArray();
		 
        // 有a个元素的集合，一共有2^a-1个非空子集  
        int n = 1;  
        for (int i = 0; i < this.size(); i++) {  
            n = n * 2;  
        }  
        n = n - 1;  
  
        // 对于每一个数字，得到其二进制表示s。取出s中为1的位置的下标，  
        // 将data数组中该下标的内容添加到临时列表tlist中，  
        // 一次内层循环添加一个元素，一次外层循环完成一个子集  
        for (int i = 1; i < n; i++) {  
            // 取出n这个数字的位置为1的数字，组成集合  
            String s = reverse(Integer.toBinaryString(new Integer(i)));  
            MySet set = new MySet();
            for (int j = 0; j < s.length(); j++) {  
                if (s.charAt(j) == '1') {  
                    set.add(data[j]);  
                }  
            }  
            result.add(set);  
        }  
  
        // 输出子集  
        
        /*for (int i = 0; i < result.size(); i++) {    
            System.out.println(result.get(i)+ " - ");  
        } */ 
		return result;
	}
	public ArrayList<MySet> getAllSubsets(){
		
		ArrayList<MySet> result = getRealSubsets();
		result.add(this);
		return result;
	}
	/*
    public static void main(String ss[]) {  
        MySet m  = new MySet();//int a[] = { 1, 2, 3 };  
        //p(a);  
        m.add(1);
        m.add(2);
        m.add(2);
        m.add(3);
        //m.getRealSubsets();
       // m.getAllSubsets();
        System.out.println(m);
    }  
    
  */
    /** 
     * 将字符串倒转 abcd->dcba 
     *  
     * @param s 
     * @return 
     */  
    public static String reverse(String s) {  
        StringBuilder builder = new StringBuilder(s);  
        for (int i = 0; i < s.length() / 2; i++) {  
            char c_1 = s.charAt(i);  
            int end = s.length() - 1 - i;  
            char c_2 = s.charAt(end);  
            builder.setCharAt(end, c_1);  
            builder.setCharAt(i, c_2);  
        }  
        return builder.toString();  
    } 
    
    public boolean isSubsetOf(MySet set){
    	//boolean result = false;
    	MySet copySet = (MySet) set.clone();
    	copySet.retainAll(this);
    	
    	if(copySet.equals(this)){
    		return true;
    	}
    	return false;
    }
    public ArrayList<Element> toArrayList(){
    	Iterator i = this.iterator();
    	ArrayList<Element> es = new ArrayList<Element>();
    	for(int j = 0;j<this.size();j++){
    		es.add((Element) i.next());
    	}
    	for(Object e:this){
    		
    	}
    	//for(int ){
    		
    	//}
    	return null;
    }

}
