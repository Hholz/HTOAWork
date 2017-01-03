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
	
	//���뿼��ͳ��ҳ��
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
		String y = ti[0];//��ȡ���
		String m = ti[1];//��ȡ�·�
		int mi = Integer.parseInt(m)-1;
		
		
		if(empsize==attensize && (aam==mi)){//��Ա������ȣ������·���ͬ����1
			int size = 1;
			model.addAttribute("sizes", size);
			model.addAttribute("ati", ati);
		}
		if(empsize!=attensize || (aam!=mi)){//���淴֮
			int size = 2;
			model.addAttribute("sizes", size);
			
		}
		model.addAttribute("empList", empList);
		return "/dailyWork/Attenstatis";
	}
	
	
	//���뿼����˽���
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
	@SystemControllerLog(description = "������ѡ���ŵ�����Ա��json����")
	public @ResponseBody ResultMessage findemp(Model model, Dep dep) {
		Emp e = new Emp();
		ResultMessage rm = new ResultMessage();
		e.setDepid(dep.getId());
		List<Emp> emplist = empservice.selectEmp(e);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	//��ʾ���ӱ����Ϣlist
	@RequestMapping("/AttenstatiszbList")
	public @ResponseBody ResultMessage Attenstatiszblist(int limit, int offset,Model model,Attendance_tal student) throws Exception{
		ResultMessage rm = new ResultMessage();
		String time = student.getWorkday();
		student.setWorkday(time.substring(0,10));
		List<Attendance_tal> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = attendance_talService.attendancelist(student);
		}
		System.out.println(sList.size());
		// ȡ��ҳ��Ϣ
        PageInfo<Attendance_tal> pageInfo = new PageInfo<Attendance_tal>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//ȡ�ÿ��ڵ�����listĸ��
	@RequestMapping("/AttenstatisList")
	public @ResponseBody ResultMessage Attenstatislist(int limit, int offset,Model model,Attenstatis student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<Attenstatis> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = attenstatisService.Attenstatiselect(student);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<Attenstatis> pageInfo = new PageInfo<Attenstatis>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//ȡ�ÿ��ڵ�����listĸ��
	@RequestMapping("/AttenTojiList")
	public @ResponseBody ResultMessage Attentojilist(int limit, int offset,Model model,Attenstatis student) throws Exception{
		ResultMessage rm = new ResultMessage();
		if(student.getWorkday()!=null && student.getWorkday()!=""){
			String time = student.getWorkday().replace('/','-');
			student.setWorkday(time.substring(0,7));
		}
		List<Attenstatis> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = attenstatisService.Attenstatiselandtoji(student);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<Attenstatis> pageInfo = new PageInfo<Attenstatis>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
		
	//ȡ�������ж�����
	public int monthday(){
		Date date = new Date();
		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
		String cn = ds.format(date);
		String[] ti = cn.split("-");
		String y = ti[0];//��ȡ���
		String m = ti[1];//��ȡ�·�
		Calendar cal = Calendar.getInstance();//����calendar������ȡʱ������
		cal.set(Calendar.YEAR,Integer.parseInt(y));//������
		cal.set(Calendar.MONTH,Integer.parseInt((m))-2);//Java�·ݲ�0��ʼ��-HH:ss:mm
		int dm = cal.getActualMaximum(Calendar.DATE);
		return dm;
	}
		
	//���ɿ���ͳ�Ʊ�
//	@RequestMapping("/AttenstatisList/add")
//	public @ResponseBody int AttenstatisListadd(Model model,Attenstatis student,Emp emp,Attendance_tal atten) throws Exception{ 
//		Date date = new Date();
//		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
//		String cn = ds.format(date);
//		String[] ti = cn.split("-");
//		String y = ti[0];//��ȡ���
//		String m = ti[1];//��ȡ�·�
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
//				//һ����˶��ٴο�
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
//							stat = 6;//�쳣
//						}else if(alt>1){
//							if((status[0]==status[(alt-1)])&&(status[(alt-1)]==4)){
//								stat = 6;//ǩ��
//							}
//							if((status[0]==2)&&(status[(alt-1)]==4)){
//								stat = 4;//�ٵ�
//								
//							}
//							if((status[0]==status[(alt-1)])&&(status[(alt-1)]==1)){
//								stat = 0;//����
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
	 * ͳ�ƿ���״̬��0=������1=�������죻2=��٣�3=��ٰ��죻4=�ٵ���5�쳣��6����
		���ɹ������� ���ٵ����������쳣
		?	������2��ǩ������Ч���򿨼�¼����Ĭ��������
		?	�гٵ�1�μ�¼����1��ǩ������Ч����¼����Ĭ�ϳٵ���
		?	��2�δ��򿨼�¼��������Ч�򿨼�¼����Ĭ��Ϊ������
		?	��1����Ч�򿨼�¼����Ĭ��Ϊ�쳣��
		����״̬  0���ô� 1δ�� 2�ٵ� 3��Ч 4ǩ��(��Ч)
	 * */
	//�޸İ༶��Ϣ
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
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	@RequestMapping("/deletelist")
	public @ResponseBody int deletelist(Attenstatis cls){
		if(cls!=null){
			String time =cls.getWorkday().substring(0,7);
			Attenstatis atten = new Attenstatis();
			atten.setWorkday(time);
			attenstatisService.deleteByPrimaryKey2(atten);
			
			//����
			try {
				List<Attendance_tal> absenteeism = attendance_talService.absenteeism();//����
				List<Attendance_tal> normal = attendance_talService.normalAttendance();//����
				List<Attendance_tal> late = attendance_talService.lateAttendance();//�ٵ�
				List<Attendance_tal> jiejia = attendance_talService.findidAttendance(0);//�ڼ���
				List<Attendance_tal> abnormal = attendance_talService.abnormalAttendance();//�쳣
				
				Attenstatis attenstatis = new Attenstatis();
				
				//�ڼ���
				for(int i=0;i<jiejia.size();i++){
					jiejia.get(i).setAttenstatus(7);
				}
				if(jiejia.size()>0 && jiejia!=null){
					attenstatisService.addinsertlist(jiejia);
				}
				
				//����
				for(int j=0;j<normal.size();j++){
					normal.get(j).setAttenstatus(6);
				}
				if(normal.size()>0 && normal!=null){
					attenstatisService.addinsertlist(normal);
				}
				
				//�ٵ�
				for(int k=0;k<late.size();k++){
					late.get(k).setAttenstatus(4);
				}
				if(late.size()>0 && late!=null){
					attenstatisService.addinsertlist(late);
				}
				
				//����
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
	
	//�������������
	@RequestMapping("/addjdt")
	public @ResponseBody int addjdt(Model model,Attenstatis student,Emp emp){
		List<Attenstatis> sList = new ArrayList<>();
		sList = attenstatisService.Attenstatiselect(student);
		int len = sList.size();//��ѯ��������
		int days = monthday();
		List<Emp> emplist = empservice.selectEmp(emp);
		int emplen = emplist.size();
		float alls = days*emplen;//Ӧ����������
		String bili = (len/alls)*100+"";//������ռ����
		int bl = Integer.parseInt(bili.substring(0,2));
		return bl;
	} 
	
	
}
