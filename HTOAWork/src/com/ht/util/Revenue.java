package com.ht.util;


public class Revenue {
    private int salary;      // 工资薪金所得  
    private int insureHome;  // “五险一金”数额  
    private int deduct;      // 扣除数额  
//  扣税公式是：（扣除社保医保公积金后薪水-个税起征点）*税率-速算扣除数 
    public void setSalary(int salary) {  
        this.salary = salary;  
    }  
  
    public void setInsureHome(int insureHome) {  
        this.insureHome = insureHome;  
    }  
  
    public void setDeduct(int deduct) {  
        this.deduct = deduct;  
    }  
      
    public void selfValue(){ // 个人所得税具体计算  
        double sefValue;  
        if(salary>=0 && salary<1500){  
            sefValue = (double)(salary-insureHome-deduct)*0.03 - 0;  
        }else if(salary>=1500 && salary<4500){  
            sefValue = (double)(salary-insureHome-deduct)*0.1 - 105;  
  
        }else if(salary>=4500 && salary<9000){  
            sefValue = (double)(salary-insureHome-deduct)*0.2 - 555;  
  
        }else if(salary>=9000 && salary<35000){  
            sefValue = (double)(salary-insureHome-deduct)*0.25 - 1005;  
  
        }else if(salary>=35000 && salary<55000){  
            sefValue = (double)(salary-insureHome-deduct)*0.30 - 2755;  
  
        }else if(salary>=55000 && salary<80000){  
            sefValue = (double)(salary-insureHome-deduct)*0.35 - 5505;  
  
        }else{  
            sefValue = (double)(salary-insureHome-deduct)*0.45 - 13505;  
        }  
        System.out.println(sefValue);  
    }  
    public static void main(String[] args) {
    	Revenue r = new Revenue();
    	r.setInsureHome(0);
    	r.setSalary(100);
    	r.setDeduct(0);
    	r.selfValue();
	}
}  
