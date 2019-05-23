package util;

public class BitString {
	public static String bxor(String a,String b){
		StringBuffer   res   =   new   StringBuffer();   
        for   (int   i   =   0;   i   <   a.length();   i++)   { 
            if   (a.charAt(i)!= b.charAt(i))   res.append( '1'); 
            else   res.append( '0'); 
        } 
        return   res.toString(); 
	}
	public static String bor(String a,String b){
		StringBuffer   res   =   new   StringBuffer();   
        for   (int   i   =   0;   i   <   a.length();   i++)   { 
            if   (a.charAt(i)== '1'||b.charAt(i)== '1')   res.append( '1'); 
            else   res.append( '0'); 
        } 
        return   res.toString(); 
	}
	public static String band(String a,String b){
		StringBuffer   res   =   new   StringBuffer();   
        for   (int   i   =   0;   i   <   a.length();   i++)   { 
            if   (a.charAt(i)== '1'&&b.charAt(i)== '1')   res.append( '1'); 
            else   res.append( '0'); 
        } 
        return   res.toString(); 
	
	}

}
