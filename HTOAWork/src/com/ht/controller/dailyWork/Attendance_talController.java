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
	//进入考勤数据页面
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
	//@SystemControllerLog(description = "员工考勤controller里的list表")
	public @ResponseBody ResultMessage kaoqinsz(int limit, int offset,Attendance_tal student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<Attendance_tal> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		
		//判断打卡时间如果打卡时间为空则获取当前时间去查询表,如果不为空则按查询时间查表
		String findtime = student.getWorkday();
		if(findtime!=null && !"".equals(findtime)){
			//替换字符串
			String s1 = findtime.replace('/','-');
			student.setWorkday(s1);
			sList = attendance_talService.attendancelist(student);
		}else{
			sList = attendance_talService.attendancelist(student);
		}
		 // 取分页信息
        PageInfo<Attendance_tal> pageInfo = new PageInfo<Attendance_tal>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/kaoqinadd")
	//按月生成考勤模板表
	public @ResponseBody int addAttendances() throws Exception{
		List<SysHoliday> sysHoliday = setholidayService.setholidayselect(null);
		List<Emp> empList = empservice.selectEmp(null);
		Attendance_tal attendances  = new Attendance_tal();
		
		int y,fingerprint,depid;
		String name = "";
		String times = "";
		System.out.println("这个本月共有 "+sysHoliday.size()+"天");
		//循环取出员工表里面的数据
		for(int i=0;i<empList.size();i++){
			//获取员工表里的员工姓名
			name = empList.get(i).getEmpname();
			//获取员工表里的员工指纹编号
			fingerprint = empList.get(i).getFingerprint();
			//获取员工表里的部门id
			depid = empList.get(i).getDepid();
			//把员工表里获取过来的员工姓名设值到attendances中的Empname里
			attendances.setEmpname(name);
			//把员工编号设值到attendances中的Empid里
			attendances.setFingerprint(fingerprint);
			//把员工表里的部门id设值到depid
			attendances.setDepid(depid);
			
			//循环取出节假日表里面的数据
			for(int j=0;j<sysHoliday.size();j++){
				//获取节假日状态
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
				//判断当前月是否为1月,如果是上个月则为12月
				int a;
				if(r==1){
					a = 12;
				}else{
					a = r;
				}
				//把int类型转化成String
				String yes = String.valueOf(da);
				String ts = String.valueOf(a);
				times = yes+"-"+ts+"-"+q;
				attendances.setWorkday(times);
				attendances.setStatus(y);//节假日状态
				attendances.setType("a");//上班打卡
				//新增函数-新增模板数据
				attendance_talService.addattendance(attendances);
				attendances.setType("p");//上班打卡
				//新增函数-新增模板数据
				attendance_talService.addattendance(attendances);
			}
		}
		return 1;
	}
	@RequestMapping("/deteleAttendances")
	public @ResponseBody int deteleAttendances(Model model)throws Exception{
		//调用删除函数--删除上个月的所有数据
		attendance_talService.deleteAttendance();
		//重新生成上个月得数据
		List<SysHoliday> sysHoliday = setholidayService.setholidayselect(null);
		List<Emp> empList = empservice.selectEmp(null);
		Attendance_tal attendances  = new Attendance_tal();
		
		int y,fingerprint,depid;
		String name = "";
		String times = "";
		System.out.println("这个本月共有 "+sysHoliday.size()+"天");
		//循环取出员工表里面的数据
		for(int i=0;i<empList.size();i++){
			//获取员工表里的员工姓名
			name = empList.get(i).getEmpname();
			//获取员工表里的员工指纹编号
			fingerprint = empList.get(i).getFingerprint();
			//获取员工表里的部门id
			depid = empList.get(i).getDepid();
			//把员工表里获取过来的员工姓名设值到attendances中的Empname里
			attendances.setEmpname(name);
			//把员工编号设值到attendances中的Empid里
			attendances.setFingerprint(fingerprint);
			//把员工表里的部门id设值到depid
			attendances.setDepid(depid);
			//循环取出时间表里面的数据
			for(int j=0;j<sysHoliday.size();j++){	
				//获取节假日状态
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
				//判断当前月是否为1月,如果是上个月则为12月
				int a;
				if(r==1){
					a = 12;
				}else{
					a = r;
				}
				//把int类型转化成String
				String yes = String.valueOf(da);
				String ts = String.valueOf(a);
				times = yes+"-"+ts+"-"+q;
				attendances.setWorkday(times);
				attendances.setStatus(y);//节假日状态
				attendances.setType("a");//上班打卡
				//新增函数-新增模板数据
				attendance_talService.addattendance(attendances);
				attendances.setType("p");//上班打卡
				//新增函数-新增模板数据
				attendance_talService.addattendance(attendances);
			}
		}
		return 1;
		
	}
	/**
	 * 考勤信息导入
	 * 
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/importStudReward")
    public @ResponseBody  Msg importStudAtten(HttpServletRequest request,MultipartFile txt_file){
    	 // logger.info("收到待处理文件,fileName="+file.getOriginalFilename());
         JSONObject jsonObject = new JSONObject();
         if(!txt_file.isEmpty()){
        	 try {
             	jsonObject = attendance_talService.importStudAtten(txt_file);
             	Msg msg = new Msg();
             	msg.setMsg("处理成功");
             	msg.setOk(true);
             	msg.setData(jsonObject); 
             	return msg;
             	
             }catch (Exception e){
                 return MsgBuilder.create().setOk(false).setMsg("处理失败！").bind();
             }
         }
    	 return MsgBuilder.create().setOk(false).setMsg("处理失败！").bind();
	}
	
	/**
	 * 关闭输出流 
	 * @param outputStream
	 */
	@SuppressWarnings("unused")
	private void closeOutputStream(OutputStream outputStream) {
		if (null != outputStream) {
			try {
				outputStream.close();
			} catch (IOException e) {
				//logger.error("关闭输出流发生IO异常:" + e.getMessage() + "");
			}
		}
	}
}
