package com.ht.util;

import java.util.Random;

public class RandomGet {

	/** 
     * �����������λ�� 
     * @return 
     */  
    public static String getSix(){  
        Random rad=new Random();  
          
        String result  = rad.nextInt(1000000) +"";  
          
        if(result.length()!=6){  
            return getSix();  
        }  
        return result;  
    }  
}
