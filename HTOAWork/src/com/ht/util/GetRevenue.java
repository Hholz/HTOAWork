package com.ht.util;

public class GetRevenue {  
	  
    /** 
     * ���ؿ۳��籣ҽ���������нˮ 
     * @param salaryBeforeTax �۳��籣ҽ��������ǰнˮ 
     * @return 
     */  
    public int salaryAfterTax(int salaryBeforeTax)  
    {  
//      ��3W-3.5K��*25%-1005  
//      ��˰��ʽ�ǣ�  
//      ���۳��籣ҽ���������нˮ-��˰�����㣩*˰��-����۳���  
        int taxbase=salaryBeforeTax-3500;  
        int Taxrate=0;//����˰��û�г��԰ٷֱȣ�  
        int Quickdeduction=0;  
        if(taxbase <=0)//���ڸ�˰������  
        {  
            return salaryBeforeTax;  
        }else if(taxbase <=1500)  
        {  
            Taxrate=3;  
            Quickdeduction=0;  
        }else if(taxbase <=4500)  
        {  
            Taxrate=10;  
            Quickdeduction=105;  
        }else if(taxbase <=9000)  
        {  
            Taxrate=20;  
            Quickdeduction=555;  
        }else if(taxbase <=35000)  
        {  
            Taxrate=25;  
            Quickdeduction=1005;  
        }else if(taxbase <=55000)  
        {  
            Taxrate=30;  
            Quickdeduction=2755;  
        }else if(taxbase <=80000)  
        {  
            Taxrate=35;  
            Quickdeduction=5505;  
        }else  
        {  
            Taxrate=45;  
            Quickdeduction=13505;  
        }             
        return salaryBeforeTax-((salaryBeforeTax-3500)*Taxrate/100-Quickdeduction);  
    }  
      
    public void costeffectivesalary(int salaryBeforeTax)  
    {  
    	float rate = 0;
        //��1������100�Ĳ����㵽10���ҳ�˰��˰ǰ�����ġ�  
        for (;salaryBeforeTax <= 10000; salaryBeforeTax=salaryBeforeTax+100) {  
            rate=(float)salaryAfterTax(salaryBeforeTax)/salaryBeforeTax*100000;  
        }
        System.out.println(""+salaryBeforeTax+"-----------"+salaryAfterTax(salaryBeforeTax)+"-----------"+rate);
    }  
      
    public static void main(String[] args)   
    {  
        new GetRevenue().costeffectivesalary(3400);  
    }  
}  
