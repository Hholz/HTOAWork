package com.ht.controller.dailyWork;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.mapper.dailyWork.Attendance_talMapper;
import com.ht.popj.dailyWork.Attendance_tal;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.sysSet.FinanceAttenceset;
import com.ht.popj.sysSet.SysHoliday;
import com.ht.service.dailyWork.Attendance_talService;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.sysSet.FinanattenService;
import com.ht.service.sysSet.SetholidayService;
import com.ht.util.Msg;
import com.ht.util.MsgBuilder;
import com.ht.util.ResultMessage;
@Controller
@RequestMapping("/dailyWork")
public class Attendance_talController {
	@Autowired
	EmpService empservice;
	@Autowired 
	FinanattenService finanattenservice;
	@Autowired
	Attendance_talService attendance_talService;
	@Autowired
	SetholidayService setholidayService; 
	@Autowired
	DepService depservice;
	@Autowired
	Attendance_talMapper talMapper;
	//���뿼������ҳ��
	@RequestMapping("/attendence")
	public String studentListfall(HttpServletRequest request,Emp emp,FinanceAttenceset finan){
		
		List<Emp> empList = empservice.selectEmp(emp);
		request.setAttribute("emplist",empList);
		List<FinanceAttenceset> finanlist = finanattenservice.financeattensel(finan);
		request.setAttribute("finanlist",finanlist);
		List<Dep> sList=depservice.selectDepList();
		request.setAttribute("dep",sList);
		return "dailyWork/Attendance";
	}
	
	
	@RequestMapping("/kaoqinszs")
	//@SystemControllerLog(description = "Ա������controller���list��")
	public @ResponseBody ResultMessage kaoqinsz(int limit, int offset,Attendance_tal student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<Attendance_tal> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		
		//�жϴ�ʱ�������ʱ��Ϊ�����ȡ��ǰʱ��ȥ��ѯ��,�����Ϊ���򰴲�ѯʱ����
		String findtime = student.getWorkday();
		if(findtime!=null && !"".equals(findtime)){
			//�滻�ַ���
			String s1 = findtime.replace('/','-');
			student.setWorkday(s1);
			sList = attendance_talService.attendancelist(student);
		}else{
			sList = attendance_talService.attendancelist(student);
		}
		 // ȡ��ҳ��Ϣ
        PageInfo<Attendance_tal> pageInfo = new PageInfo<Attendance_tal>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/kaoqinadd")
	//�������ɿ���ģ���
	public @ResponseBody int addAttendances() throws Exception{
		List<SysHoliday> sysHoliday = setholidayService.setholidayselect(null);
		List<Emp> empList = empservice.selectEmp(null);
		Attendance_tal attendances  = new Attendance_tal();
		
		int y,fingerprint,depid;
		String name = "";
		String times = "";
		System.out.println("������¹��� "+sysHoliday.size()+"��");
		//ѭ��ȡ��Ա�������������
		for(int i=0;i<empList.size();i++){
			//��ȡԱ�������Ա������
			name = empList.get(i).getEmpname();
			//��ȡԱ�������Ա��ָ�Ʊ��
			fingerprint = empList.get(i).getFingerprint();
			//��ȡԱ������Ĳ���id
			depid = empList.get(i).getDepid();
			//��Ա�������ȡ������Ա��������ֵ��attendances�е�Empname��
			attendances.setEmpname(name);
			//��Ա�������ֵ��attendances�е�Empid��
			attendances.setFingerprint(fingerprint);
			//��Ա������Ĳ���id��ֵ��depid
			attendances.setDepid(depid);
			
			//ѭ��ȡ���ڼ��ձ����������
			for(int j=0;j<sysHoliday.size();j++){
				//��ȡ�ڼ���״̬
				y = sysHoliday.get(j).getHolidaystat();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				Date date = formatter.parse(sysHoliday.get(j).getHolidaytime());
				int r = date.getMonth()+1;
				int da = date.getYear()+1900;
				String q = "";
				if(j+1<10){
					q = String.valueOf(j+1);
					q = 0+q;
				}else{
					q = String.valueOf(j+1);
				}
				//�жϵ�ǰ���Ƿ�Ϊ1��,������ϸ�����Ϊ12��
				int a;
				if(r==1){
					a = 12;
				}else{
					a = r;
				}
				//��int����ת����String
				String yes = String.valueOf(da);
				String ts = String.valueOf(a);
				times = yes+"-"+ts+"-"+q;
				attendances.setWorkday(times);
				attendances.setStatus(y);//�ڼ���״̬
				attendances.setType("a");//�ϰ��
				//��������-����ģ������
				attendance_talService.addattendance(attendances);
				attendances.setType("p");//�ϰ��
				//��������-����ģ������
				attendance_talService.addattendance(attendances);
			}
		}
		return 1;
	}
	@RequestMapping("/deteleAttendances")
	public @ResponseBody int deteleAttendances(Model model)throws Exception{
		//����ɾ������--ɾ���ϸ��µ���������
		attendance_talService.deleteAttendance();
		//���������ϸ��µ�����
		List<SysHoliday> sysHoliday = setholidayService.setholidayselect(null);
		List<Emp> empList = empservice.selectEmp(null);
		Attendance_tal attendances  = new Attendance_tal();
		
		int y,fingerprint,depid;
		String name = "";
		String times = "";
		System.out.println("������¹��� "+sysHoliday.size()+"��");
		//ѭ��ȡ��Ա�������������
		for(int i=0;i<empList.size();i++){
			//��ȡԱ�������Ա������
			name = empList.get(i).getEmpname();
			//��ȡԱ�������Ա��ָ�Ʊ��
			fingerprint = empList.get(i).getFingerprint();
			//��ȡԱ������Ĳ���id
			depid = empList.get(i).getDepid();
			//��Ա�������ȡ������Ա��������ֵ��attendances�е�Empname��
			attendances.setEmpname(name);
			//��Ա�������ֵ��attendances�е�Empid��
			attendances.setFingerprint(fingerprint);
			//��Ա������Ĳ���id��ֵ��depid
			attendances.setDepid(depid);
			//ѭ��ȡ��ʱ������������
			for(int j=0;j<sysHoliday.size();j++){	
				//��ȡ�ڼ���״̬
				y = sysHoliday.get(j).getHolidaystat();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				Date date = formatter.parse(sysHoliday.get(j).getHolidaytime());
				int r = date.getMonth()+1;
				int da = date.getYear()+1900;
				String q = "";
				if(j+1<10){
					q = String.valueOf(j+1);
					q = 0+q;
				}else{
					q = String.valueOf(j+1);
				}
				//�жϵ�ǰ���Ƿ�Ϊ1��,������ϸ�����Ϊ12��
				int a;
				if(r==1){
					a = 12;
				}else{
					a = r;
				}
				//��int����ת����String
				String yes = String.valueOf(da);
				String ts = String.valueOf(a);
				times = yes+"-"+ts+"-"+q;
				attendances.setWorkday(times);
				attendances.setStatus(y);//�ڼ���״̬
				attendances.setType("a");//�ϰ��
				//��������-����ģ������
				attendance_talService.addattendance(attendances);
				attendances.setType("p");//�ϰ��
				//��������-����ģ������
				attendance_talService.addattendance(attendances);
			}
		}
		return 1;
		
	}
	/**
	 * ������Ϣ����
	 * 
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/importStudReward")
    public @ResponseBody  Msg importStudAtten(HttpServletRequest request,MultipartFile txt_file){
    	 // logger.info("�յ��������ļ�,fileName="+file.getOriginalFilename());
         JSONObject jsonObject = new JSONObject();
         if(!txt_file.isEmpty()){
        	 try {
             	jsonObject = attendance_talService.importStudAtten(txt_file);
             	Msg msg = new Msg();
             	msg.setMsg("����ɹ�");
             	msg.setOk(true);
             	msg.setData(jsonObject); 
             	return msg;
             	
             }catch (Exception e){
                 return MsgBuilder.create().setOk(false).setMsg("����ʧ�ܣ�").bind();
             }
         }
    	 return MsgBuilder.create().setOk(false).setMsg("����ʧ�ܣ�").bind();
	}
	
	/**
	 * �ر������ 
	 * @param outputStream
	 */
	@SuppressWarnings("unused")
	private void closeOutputStream(OutputStream outputStream) {
		if (null != outputStream) {
			try {
				outputStream.close();
			} catch (IOException e) {
				//logger.error("�ر����������IO�쳣:" + e.getMessage() + "");
			}
		}
	}
}
