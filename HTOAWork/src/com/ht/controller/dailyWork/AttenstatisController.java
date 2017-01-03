package com.ht.controller.dailyWork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.dailyWork.Attendance_talMapper;
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.popj.dailyWork.Attendance_tal;
import com.ht.popj.dailyWork.Attenstatis;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.Attendance_talService;
import com.ht.service.dailyWork.AttenstatisService;
import com.ht.service.dailyWork.EmpService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork")
public class AttenstatisController {

	@Autowired
	AttenstatisService attenstatisService;
	@Autowired
	EmpService empservice;
	@Autowired
	Attendance_talService attendance_talService;
	@Autowired
	DepMapper depmapper;
	
	//进入考勤统计页面
	@RequestMapping("/Attenstatis")
	public String attenstatis(Model model,Emp emp,Attenstatis student){
		
		List<Attendance_tal> attendance_tals = attendance_talService.selectCount();
		
		List<Emp> empList = empservice.selectEmp(emp);
		int empsize = empList.size();
		List<Attenstatis> attenList = attenstatisService.Attenstatiselandtoji(student);
		int attensize = attenList.size();
		int aam = 0;
		String ati = "";
		if(attenList!=null && attenList.size()>0){
			ati = attenList.get(0).getWorkday();
			String ay = ati.substring(0,4);
			String am = ati.substring(5,7);
			aam = Integer.parseInt(am);
			
		}
		
		Date date = new Date();
		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
		String cn = ds.format(date);	
		String[] ti = cn.split("-");
		String y = ti[0];//截取年份
		String m = ti[1];//截取月份
		int mi = Integer.parseInt(m)-1;
		
		
		if(empsize==attensize && (aam==mi)){//当员工数相等，并且月份相同返回1
			int size = 1;
			model.addAttribute("sizes", size);
			model.addAttribute("ati", ati);
		}
		if(empsize!=attensize || (aam!=mi)){//上面反之
			int size = 2;
			model.addAttribute("sizes", size);
			
		}
		model.addAttribute("empList", empList);
		return "/dailyWork/Attenstatis";
	}
	
	
	//进入考勤审核界面
	@RequestMapping("/AttenShenhe")
	public String attensh(Model model,Emp emp,Attenstatis stat){
		System.out.println("==00=");
		List<Emp> empList = empservice.selectEmp(emp);
		String empname = attenstatisService.Attenstatiselect(stat).get(0).getEmpname();
		int fr = stat.getFinger();
		Emp ep = new Emp();
		ep.setFingerprint(fr);
		if(empservice.selectEmp(ep)!=null&&empservice.selectEmp(ep).size()>0){
			String empid = empservice.selectEmp(ep).get(0).getId();
			model.addAttribute("eid", empid);
		}
		model.addAttribute("ename", empname);
		model.addAttribute("stat", stat);
		model.addAttribute("empList", empList);
		return "/dailyWork/AttenShenhe";
	}
	
	@RequestMapping("/attenfindemp")
	@SystemControllerLog(description = "返回所选部门的所有员工json数据")
	public @ResponseBody ResultMessage findemp(Model model, Dep dep) {
		Emp e = new Emp();
		ResultMessage rm = new ResultMessage();
		e.setDepid(dep.getId());
		List<Emp> emplist = empservice.selectEmp(e);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	//显示出子表的信息list
	@RequestMapping("/AttenstatiszbList")
	public @ResponseBody ResultMessage Attenstatiszblist(int limit, int offset,Model model,Attendance_tal student) throws Exception{
		ResultMessage rm = new ResultMessage();
		String time = student.getWorkday();
		student.setWorkday(time.substring(0,10));
		List<Attendance_tal> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = attendance_talService.attendancelist(student);
		}
		System.out.println(sList.size());
		// 取分页信息
        PageInfo<Attendance_tal> pageInfo = new PageInfo<Attendance_tal>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//取用考勤的数据list母表
	@RequestMapping("/AttenstatisList")
	public @ResponseBody ResultMessage Attenstatislist(int limit, int offset,Model model,Attenstatis student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<Attenstatis> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = attenstatisService.Attenstatiselect(student);
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<Attenstatis> pageInfo = new PageInfo<Attenstatis>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//取用考勤的数据list母表
	@RequestMapping("/AttenTojiList")
	public @ResponseBody ResultMessage Attentojilist(int limit, int offset,Model model,Attenstatis student) throws Exception{
		ResultMessage rm = new ResultMessage();
		if(student.getWorkday()!=null && student.getWorkday()!=""){
			String time = student.getWorkday().replace('/','-');
			student.setWorkday(time.substring(0,7));
		}
		List<Attenstatis> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = attenstatisService.Attenstatiselandtoji(student);
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<Attenstatis> pageInfo = new PageInfo<Attenstatis>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
		
	//取得上月有都少天
	public int monthday(){
		Date date = new Date();
		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
		String cn = ds.format(date);
		String[] ti = cn.split("-");
		String y = ti[0];//截取年份
		String m = ti[1];//截取月份
		Calendar cal = Calendar.getInstance();//调用calendar函数获取时期天数
		cal.set(Calendar.YEAR,Integer.parseInt(y));//输入年
		cal.set(Calendar.MONTH,Integer.parseInt((m))-2);//Java月份才0开始算-HH:ss:mm
		int dm = cal.getActualMaximum(Calendar.DATE);
		return dm;
	}
		
	//生成考情统计表
//	@RequestMapping("/AttenstatisList/add")
//	public @ResponseBody int AttenstatisListadd(Model model,Attenstatis student,Emp emp,Attendance_tal atten) throws Exception{ 
//		Date date = new Date();
//		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
//		String cn = ds.format(date);
//		String[] ti = cn.split("-");
//		String y = ti[0];//截取年份
//		String m = ti[1];//截取月份
//		int s = Integer.parseInt(m)-1;
//		String mt = "" ;
//		if(s<10){
//			mt = "0"+s ;
//		}else {
//			mt = s+"";
//		}
//		
//		List<Emp> empList = empservice.selectEmp(emp);
//		int days = monthday();
//		for(int i=0;i<empList.size();i++){
//			int finger = empList.get(i).getFingerprint();
//			for(int j=1;j<=days;j++){
//				String dates ="";
//				if(j<10){
//					dates = y+"-"+mt+"-"+"0"+j;
//				}else{
//					dates = y+"-"+mt+"-"+j;
//				}
//				atten.setFingerprint(finger);
//				atten.setWorkday(dates);
//				//一天打了多少次卡
//				List<Attendance_tal> attenlist = attendance_talService.attendancelist(atten);
//				int alt = attenlist.size();
//				int status[] =new int[alt];
//				String empname = "";
//				int depid = 0;
//				int stat = 0;
//				int late = 0;
////				int late;
////				Float absent;
//				
//				for(int z=0;z<attenlist.size();z++){
//					Attendance_tal att =attenlist.get(0);
//					empname = att.getEmpname();
//					depid = att.getDepid();
//					if(att.getLate()!=null){
//						late = att.getLate();
//					}
//					Attendance_tal att2 =attenlist.get(z);
//					for(int a=0;a<status.length;a++){
//						status[a] = att2.getStatus();
//						if(status[a]==3){
//							stat = 6;//异常
//						}else if(alt>1){
//							if((status[0]==status[(alt-1)])&&(status[(alt-1)]==4)){
//								stat = 6;//签到
//							}
//							if((status[0]==2)&&(status[(alt-1)]==4)){
//								stat = 4;//迟到
//								
//							}
//							if((status[0]==status[(alt-1)])&&(status[(alt-1)]==1)){
//								stat = 0;//旷工
//							}
//							
//						}
//					}
//				}
//				student.setAttenstatus(stat);
//				student.setDepid(depid);
//				student.setFinger(finger);
//				student.setEmpname(empname);
//				student.setWorkday(dates);
//				student.setLate(late);
//				attenstatisService.insertSelective(student);
//				
//			}
//		}
//		return 1;
//	}
	
	/**
	 * 统计考勤状态：0=旷工；1=旷工半天；2=请假；3=请假半天；4=迟到；5异常；6正常
		生成规则：正常 》迟到》旷工》异常
		?	当天有2次签到（有效）打卡记录，则默认正常；
		?	有迟到1次记录且有1次签到（有效）记录，则默认迟到；
		?	有2次待打卡记录，且无无效打卡记录，则默认为旷工；
		?	有1次无效打卡记录，则默认为异常。
		考勤状态  0不用打卡 1未打卡 2迟到 3无效 4签到(有效)
	 * */
	//修改班级信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentclass(Model model,Attenstatis cls){
		if(null!=cls && null!=cls.getId()){
			if(cls.getAttenstatus()==1){
				cls.setAbsent((float)0.5);
				cls.setLeave((float)0);
				cls.setLate(0);
				cls.setFclockin(0);
			}
			if(cls.getAttenstatus()==3){
				cls.setLeave((float)0.5);
				cls.setAbsent((float)0);
				cls.setLate(0);
				cls.setFclockin(0);
			}
			if(cls.getAttenstatus()==0){
				cls.setAbsent((float)1);
				cls.setLeave((float)0);
				cls.setLate(0);
				cls.setFclockin(0);
			}
			if(cls.getAttenstatus()==2){
				cls.setAbsent((float)0);
				cls.setLeave((float)1);
				cls.setLate(0);
				cls.setFclockin(0);
			}
			if(cls.getAttenstatus()==6){
				cls.setAbsent((float)0);
				cls.setLeave((float)0);
				cls.setLate(0);
			}
			if(cls.getAttenstatus()==7){
				cls.setAbsent((float)0);
				cls.setLeave((float)0);
				cls.setLate(0);
				cls.setFclockin(0);
			}
			if(cls.getAttenstatus()==8){
				cls.setAbsent((float)0);
				cls.setLeave((float)0);
				cls.setLate(0);
				cls.setFclockin(1);
			}
			if(cls.getAttenstatus()==9){
				cls.setAbsent((float)0);
				cls.setLeave((float)0);
				cls.setLate(0);
				cls.setFclockin(2);
			}
			
			return	attenstatisService.updateByPrimaryKeySelective(cls);
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping("/deletelist")
	public @ResponseBody int deletelist(Attenstatis cls){
		if(cls!=null){
			String time =cls.getWorkday().substring(0,7);
			Attenstatis atten = new Attenstatis();
			atten.setWorkday(time);
			attenstatisService.deleteByPrimaryKey2(atten);
			
			//新增
			try {
				List<Attendance_tal> absenteeism = attendance_talService.absenteeism();//旷工
				List<Attendance_tal> normal = attendance_talService.normalAttendance();//正常
				List<Attendance_tal> late = attendance_talService.lateAttendance();//迟到
				List<Attendance_tal> jiejia = attendance_talService.findidAttendance(0);//节假日
				List<Attendance_tal> abnormal = attendance_talService.abnormalAttendance();//异常
				
				Attenstatis attenstatis = new Attenstatis();
				
				//节假日
				for(int i=0;i<jiejia.size();i++){
					jiejia.get(i).setAttenstatus(7);
				}
				if(jiejia.size()>0 && jiejia!=null){
					attenstatisService.addinsertlist(jiejia);
				}
				
				//正常
				for(int j=0;j<normal.size();j++){
					normal.get(j).setAttenstatus(6);
				}
				if(normal.size()>0 && normal!=null){
					attenstatisService.addinsertlist(normal);
				}
				
				//迟到
				for(int k=0;k<late.size();k++){
					late.get(k).setAttenstatus(4);
				}
				if(late.size()>0 && late!=null){
					attenstatisService.addinsertlist(late);
				}
				
				//旷工
				for(int s=0;s<absenteeism.size();s++){
					absenteeism.get(s).setAbsent(1);
					absenteeism.get(s).setAttenstatus(0);
				}
				if(absenteeism.size()>0 && absenteeism!=null){
					attenstatisService.addinsertlist(absenteeism);
				}
			
				List<Attendance_tal> abnormal2 = new ArrayList<>();
				Attendance_tal atl = new Attendance_tal();
				Attenstatis att = new Attenstatis();
				for(int i=0;i<abnormal.size();i++){
					//attenstatis = new Attenstatis();
					attenstatis.setDepid(abnormal.get(i).getDepid());
					attenstatis.setEmpname(abnormal.get(i).getEmpname());
					attenstatis.setFinger(abnormal.get(i).getFingerprint());
					attenstatis.setWorkday(abnormal.get(i).getWorkday());
					attenstatis.setLate(abnormal.get(i).getLate());
					attenstatis.setAttenstatus(5);
					att = attenstatisService.findattenstatis(attenstatis);
					if(att==null){
						atl.setDepid(attenstatis.getDepid());
						atl.setAttenstatus(5);
						atl.setFingerprint(attenstatis.getFinger());
						atl.setLate(attenstatis.getLate());
						atl.setWorkday(attenstatis.getWorkday());
						atl.setEmpname(attenstatis.getEmpname());
						abnormal2.add(atl);
					}
					
				}
				if(abnormal.size()>0 && abnormal!=null){
					attenstatisService.addinsertlist(abnormal2);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			return 1;
		}
		
		return 0;
	}
	
	//进度条结果返回
	@RequestMapping("/addjdt")
	public @ResponseBody int addjdt(Model model,Attenstatis student,Emp emp){
		List<Attenstatis> sList = new ArrayList<>();
		sList = attenstatisService.Attenstatiselect(student);
		int len = sList.size();//查询现有人数
		int days = monthday();
		List<Emp> emplist = empservice.selectEmp(emp);
		int emplen = emplist.size();
		float alls = days*emplen;//应生成总人数
		String bili = (len/alls)*100+"";//人数所占比例
		int bl = Integer.parseInt(bili.substring(0,2));
		return bl;
	} 
	
	
}
