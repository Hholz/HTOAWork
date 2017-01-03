package com.ht.controller.dailyWork;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Attendances;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentReward;
import com.ht.popj.student.StudentSayface;
import com.ht.popj.sysSet.FinanceAttenceset;
import com.ht.service.dailyWork.AttendanceService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.sysSet.FinanattenService;
import com.ht.util.Msg;
import com.ht.util.MsgBuilder;
import com.ht.util.ResultMessage;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/dailyWork/kaoqin")
public class AttendanceController {
	
	@Autowired
	AttendanceService attendanceService;
	@Autowired
	EmpService empservice;
	@Autowired 
	FinanattenService finanattenservice;
	
	//���뿼������ҳ��
	@RequestMapping("/attendencejsp")
	public String studentListfall(Model model,Emp emp,FinanceAttenceset finan){
		System.out.println("=====");
		List<Emp> empList = empservice.selectEmp(emp);
		model.addAttribute("emplist",empList);
		List<FinanceAttenceset> finanlist = finanattenservice.financeattensel(finan);
		model.addAttribute("finanlist",finanlist);
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		String name = "";
		String empid = "";
		if(userInfo!=null){
			name = userInfo.getEmp().getEmpname();
			empid = userInfo.getEmp().getId();
		}
		model.addAttribute("name",name);
		model.addAttribute("empid",empid);
		
		return "dailyWork/Attendance";
	}
	
	@RequestMapping("/kaoqinsz")
	@SystemControllerLog(description = "Ա������controller���list��")
	public @ResponseBody ResultMessage empattenjson(int limit, int offset,Model model,Attendances student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<Attendances> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = attendanceService.attendanceselect(student);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<Attendances> pageInfo = new PageInfo<Attendances>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//��������
	@RequestMapping("/add")
	public @ResponseBody int addattendance(Model model,Attendances atten) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		
		//3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
		if(null!=userInfo.getEmp()){
			atten.setEmpid(userInfo.getEmp().getId());
		}
		if(atten.getRemark()==null || atten.getRemark().isEmpty()){
			atten.setRemark("�ޡ���");
		}
		
		if(atten.getWorkday()==null || atten.getWorkday().isEmpty()){
			Date date = new Date();
			SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			String cellcon = ds.format(date);
			atten.setWorkday(cellcon);
		}
		
		String time = atten.getWorkday();
		String hht="";
		int hh =0;
		if(time!=null && !time.equals("")){
			String[] array = time.split(" ");
			String heat = array[1];
			String[] fhh = heat.split(":");
			hht = fhh[0];
			hh = Integer.parseInt(hht);
		}
		String attenid = atten.getAttentime();
		if(atten.getAttentime()==null || atten.getAttentime().isEmpty()){
			attenid = "7";
			atten.setAttentime("7");
		}
	    if(time!=null && !time.equals("")&&attenid.equals("7")||attenid.equals("2")){
			FinanceAttenceset finan = new FinanceAttenceset();
			finan = finanattenservice.selectByPrimaryKey(Integer.parseInt(attenid));
			String[] t1 = finan.getTime1().split(":");
			int time1 = Integer.parseInt(t1[0]);
			String[] t2 = finan.getTime2().split(":");
			int time2 = Integer.parseInt(t2[0]);
			String[] t3 = finan.getTime3().split(":");
			int time3 = Integer.parseInt(t3[0]);
			String[] t4 = finan.getTime4().split(":");
			int time4 = Integer.parseInt(t4[0]);
			String[] t5 = finan.getTime5().split(":");
			int time5 = Integer.parseInt(t5[0]);
			String[] t6 = finan.getTime6().split(":");
			int time6 = Integer.parseInt(t6[0]);
		
			if(hh<=time1){
				atten.setFlag("����");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("����");
			}
			if(hh>=time2&&hh<=time3){
				atten.setFlag("����");
			}
			if(hh>time3&&hh<=(time3+1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time3+1)&&hh<time4){
				atten.setFlag("����");
			}
			if(hh>=time4&&hh<=time5){
				atten.setFlag("����");
			}
			if(hh>time5&&hh<=(time5-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time5-1)&&hh<time6){
				atten.setFlag("����");
			}
			if(hh>=time6){
				atten.setFlag("����");
			}
	    }
	    if(time!=null && !time.equals("")&&attenid.equals("1")){
	    	FinanceAttenceset finan = new FinanceAttenceset();
			finan = finanattenservice.selectByPrimaryKey(Integer.parseInt(attenid));
			String[] t1 = finan.getTime1().split(":");
			int time1 = Integer.parseInt(t1[0]);
			String[] t2 = finan.getTime2().split(":");
			int time2 = Integer.parseInt(t2[0]);
			
			if(hh<=time1){
				atten.setFlag("����");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("����");
			}
			if(hh>=time2){
				atten.setFlag("����");
			}
	    }
	    if((time==null || time.isEmpty())&&!attenid.equals("6")){
	    	atten.setFlag("����");
	    } 
	    if((time!=null || !time.isEmpty())&&attenid.equals("6")){
	    	atten.setFlag("�ż�");
	    }
	    if(time!=null && !time.equals("")&&!attenid.equals("6")&&!attenid.equals("1")&&attenid.equals("7")&&attenid.equals("2")){
	    	FinanceAttenceset finan = new FinanceAttenceset();
			finan = finanattenservice.selectByPrimaryKey(Integer.parseInt(attenid));
			String[] t1 = finan.getTime1().split(":");
			int time1 = Integer.parseInt(t1[0]);
			String[] t2 = finan.getTime2().split(":");
			int time2 = Integer.parseInt(t2[0]);
			String[] t3 = finan.getTime3().split(":");
			int time3 = Integer.parseInt(t3[0]);
			String[] t4 = finan.getTime4().split(":");
			int time4 = Integer.parseInt(t4[0]);
			String[] t5 = finan.getTime5().split(":");
			int time5 = Integer.parseInt(t5[0]);
			String[] t6 = finan.getTime6().split(":");
			int time6 = Integer.parseInt(t6[0]);
		
			if(hh<=time1){
				atten.setFlag("����");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("����");
			}
			if(hh>=time2&&hh<=time3){
				atten.setFlag("����");
			}
			if(hh>time3&&hh<=(time3+1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time3+1)&&hh<time4){
				atten.setFlag("����");
			}
			if(hh>=time4&&hh<=time5){
				atten.setFlag("����");
			}
			if(hh>time5&&hh<=(time5-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time5-1)&&hh<time6){
				atten.setFlag("����");
			}
			if(hh>=time6){
				atten.setFlag("����");
			}
	    }
	    
		int i =attendanceService.insertSelective(atten);
		//��studentΪ��ʱ�����е��������0
		return i;
	}

	
	//����ѧ��
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int attendanceupdate(Model model,Attendances atten){
		System.out.println("=1234=====");
		String time = atten.getWorkday();
		String hht="";
		int hh =0;
		if(time!=null && !time.equals("")){
			String[] array = time.split(" ");
			String heat = array[1];
			String[] fhh = heat.split(":");
			hht = fhh[0];
			hh = Integer.parseInt(hht);
		}
		
		String attenid = atten.getAttentime();
	    if(time!=null && !time.equals("")&&attenid.equals("7")||attenid.equals("2")){
			FinanceAttenceset finan = new FinanceAttenceset();
			finan = finanattenservice.selectByPrimaryKey(Integer.parseInt(attenid));
			String[] t1 = finan.getTime1().split(":");
			int time1 = Integer.parseInt(t1[0]);
			String[] t2 = finan.getTime2().split(":");
			int time2 = Integer.parseInt(t2[0]);
			String[] t3 = finan.getTime3().split(":");
			int time3 = Integer.parseInt(t3[0]);
			String[] t4 = finan.getTime4().split(":");
			int time4 = Integer.parseInt(t4[0]);
			String[] t5 = finan.getTime5().split(":");
			int time5 = Integer.parseInt(t5[0]);
			String[] t6 = finan.getTime6().split(":");
			int time6 = Integer.parseInt(t6[0]);
		
			if(hh<=time1){
				atten.setFlag("����");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("����");
			}
			if(hh>=time2&&hh<=time3){
				atten.setFlag("����");
			}
			if(hh>time3&&hh<=(time3+1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time3+1)&&hh<time4){
				atten.setFlag("����");
			}
			if(hh>=time4&&hh<=time5){
				atten.setFlag("����");
			}
			if(hh>time5&&hh<=(time5-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time5-1)&&hh<time6){
				atten.setFlag("����");
			}
			if(hh>=time6){
				atten.setFlag("����");
			}
	    }
	    if(time!=null && !time.equals("")&&attenid.equals("1")){
	    	FinanceAttenceset finan = new FinanceAttenceset();
			finan = finanattenservice.selectByPrimaryKey(Integer.parseInt(attenid));
			String[] t1 = finan.getTime1().split(":");
			int time1 = Integer.parseInt(t1[0]);
			String[] t2 = finan.getTime2().split(":");
			int time2 = Integer.parseInt(t2[0]);
			
			if(hh<=time1){
				atten.setFlag("����");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("����");
			}
			if(hh>=time2){
				atten.setFlag("����");
			}
	    }
	    if((time==null || time.isEmpty())&&!attenid.equals("6")){
	    	atten.setFlag("����");
	    } 
	    if((time!=null || !time.isEmpty())&&attenid.equals("6")){
	    	atten.setFlag("�ż�");
	    }
	    if(time!=null && !time.equals("")&&!attenid.equals("6")&&!attenid.equals("1")&&attenid.equals("7")&&attenid.equals("2")){
	    	FinanceAttenceset finan = new FinanceAttenceset();
			finan = finanattenservice.selectByPrimaryKey(Integer.parseInt(attenid));
			String[] t1 = finan.getTime1().split(":");
			int time1 = Integer.parseInt(t1[0]);
			String[] t2 = finan.getTime2().split(":");
			int time2 = Integer.parseInt(t2[0]);
			String[] t3 = finan.getTime3().split(":");
			int time3 = Integer.parseInt(t3[0]);
			String[] t4 = finan.getTime4().split(":");
			int time4 = Integer.parseInt(t4[0]);
			String[] t5 = finan.getTime5().split(":");
			int time5 = Integer.parseInt(t5[0]);
			String[] t6 = finan.getTime6().split(":");
			int time6 = Integer.parseInt(t6[0]);
		
			if(hh<=time1){
				atten.setFlag("����");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("����");
			}
			if(hh>=time2&&hh<=time3){
				atten.setFlag("����");
			}
			if(hh>time3&&hh<=(time3+1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time3+1)&&hh<time4){
				atten.setFlag("����");
			}
			if(hh>=time4&&hh<=time5){
				atten.setFlag("����");
			}
			if(hh>time5&&hh<=(time5-1)){
				atten.setFlag("�ٵ�");
			}
			if(hh>(time5-1)&&hh<time6){
				atten.setFlag("����");
			}
			if(hh>=time6){
				atten.setFlag("����");
			}
	    }
		if(null!=atten){
			if(null==atten.getEmpid()){
				return 0;
			}
			if(null==atten.getAttentime()||atten.getAttentime().isEmpty()){
				return 0;
			}
			attendanceService.updateByPrimaryKeySelective(atten);
			System.out.println("=ererere=");
			return 1;
		}
		return 0;
	}
	
	//ɾ��ѧ��
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentclass(Model model,Attendances atten){  
		if(null!=atten){
			if(null==atten.getId()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}			
			attendanceService.updateByPrimaryKeySelective(atten);
			return 1;
		}
		return 0;
	}
	
	/**
	 * ѧ��������ϢExcel������
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportAttendance")
	public void exportAttendance(HttpSession session,HttpServletResponse response,Attendances student) throws Exception {
		List<Attendances> srList = attendanceService.attendanceselect(student);
		OutputStream os = null;
		try{
			os = response.getOutputStream();
			response.setHeader(
					"Content-Disposition",
					"attachment;filename=\""
							+ URLEncoder.encode("���ڼ�¼��.xls",
									"UTF-8") + "\"");
			response.setContentType("application/vnd.ms-excel; charset=utf-8");

			attendanceService.exportExcel(srList, os, "������¼");
		}catch(Exception e){
			//logger.error("����ѧ��Excel������������쳣.", e);

			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			String msg = "<script>window.parent.$.messager.alert('��ʾ', '������¼Excel������������쳣:����ϵϵͳ����Ա.', 'info');</script>";
			try {
				os = response.getOutputStream();
				os.write(msg.getBytes("UTF-8"));
				os.flush();
			} catch (UnsupportedEncodingException e1) {
				//logger.error("�ر��������쳣.", e1);
			} catch (IOException e1) {
				//logger.error("�ر��������쳣.", e1);
			}
		}finally {
			closeOutputStream(os);
		}
		
	}
	
	/**
	 * ѧ��������Ϣ����
	 * 
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/importStudReward")
    public @ResponseBody  Msg importStudAtten(HttpServletRequest request,MultipartFile txt_file){
    	 System.out.println("�ļ���--->"+txt_file.getOriginalFilename());	
    	 // logger.info("�յ��������ļ�,fileName="+file.getOriginalFilename());
         JSONObject jsonObject = new JSONObject();
         if(!txt_file.isEmpty()){
        	 try {
             	jsonObject = attendanceService.importStudAtten(txt_file);
             	Msg msg = new Msg();
             	msg.setMsg("����ɹ�");
             	msg.setOk(true);
             	msg.setData(jsonObject);
//             return MsgBuilder.create().setOk(true).setData(jsonObject).setMsg("����ɹ���").bind();
             	return msg;
             	
             }catch (Exception e){
                 //logger.error("��������ѧ��ʧ��,fileName="+file.getOriginalFilename());
                 return MsgBuilder.create().setOk(false).setMsg("����ʧ�ܣ�").bind();
             }
         }
    	 return MsgBuilder.create().setOk(false).setMsg("����ʧ�ܣ�").bind();
	}
	
	/**
	 * �ر������ 
	 * @param outputStream
	 */
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
