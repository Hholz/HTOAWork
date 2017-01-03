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
	
	//进入考勤数据页面
	@RequestMapping("/attendencejsp")
	public String studentListfall(Model model,Emp emp,FinanceAttenceset finan){
		System.out.println("=====");
		List<Emp> empList = empservice.selectEmp(emp);
		model.addAttribute("emplist",empList);
		List<FinanceAttenceset> finanlist = finanattenservice.financeattensel(finan);
		model.addAttribute("finanlist",finanlist);
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
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
	@SystemControllerLog(description = "员工考勤controller里的list表")
	public @ResponseBody ResultMessage empattenjson(int limit, int offset,Model model,Attendances student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<Attendances> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = attendanceService.attendanceselect(student);
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<Attendances> pageInfo = new PageInfo<Attendances>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//新增考勤
	@RequestMapping("/add")
	public @ResponseBody int addattendance(Model model,Attendances atten) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		
		//3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
		if(null!=userInfo.getEmp()){
			atten.setEmpid(userInfo.getEmp().getId());
		}
		if(atten.getRemark()==null || atten.getRemark().isEmpty()){
			atten.setRemark("无。。");
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
				atten.setFlag("正常");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("早退");
			}
			if(hh>=time2&&hh<=time3){
				atten.setFlag("正常");
			}
			if(hh>time3&&hh<=(time3+1)){
				atten.setFlag("迟到");
			}
			if(hh>(time3+1)&&hh<time4){
				atten.setFlag("早退");
			}
			if(hh>=time4&&hh<=time5){
				atten.setFlag("正常");
			}
			if(hh>time5&&hh<=(time5-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time5-1)&&hh<time6){
				atten.setFlag("早退");
			}
			if(hh>=time6){
				atten.setFlag("正常");
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
				atten.setFlag("正常");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("早退");
			}
			if(hh>=time2){
				atten.setFlag("正常");
			}
	    }
	    if((time==null || time.isEmpty())&&!attenid.equals("6")){
	    	atten.setFlag("旷工");
	    } 
	    if((time!=null || !time.isEmpty())&&attenid.equals("6")){
	    	atten.setFlag("放假");
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
				atten.setFlag("正常");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("早退");
			}
			if(hh>=time2&&hh<=time3){
				atten.setFlag("正常");
			}
			if(hh>time3&&hh<=(time3+1)){
				atten.setFlag("迟到");
			}
			if(hh>(time3+1)&&hh<time4){
				atten.setFlag("早退");
			}
			if(hh>=time4&&hh<=time5){
				atten.setFlag("正常");
			}
			if(hh>time5&&hh<=(time5-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time5-1)&&hh<time6){
				atten.setFlag("早退");
			}
			if(hh>=time6){
				atten.setFlag("正常");
			}
	    }
	    
		int i =attendanceService.insertSelective(atten);
		//当student为空时，运行到这里，返回0
		return i;
	}

	
	//更新学生
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
				atten.setFlag("正常");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("早退");
			}
			if(hh>=time2&&hh<=time3){
				atten.setFlag("正常");
			}
			if(hh>time3&&hh<=(time3+1)){
				atten.setFlag("迟到");
			}
			if(hh>(time3+1)&&hh<time4){
				atten.setFlag("早退");
			}
			if(hh>=time4&&hh<=time5){
				atten.setFlag("正常");
			}
			if(hh>time5&&hh<=(time5-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time5-1)&&hh<time6){
				atten.setFlag("早退");
			}
			if(hh>=time6){
				atten.setFlag("正常");
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
				atten.setFlag("正常");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("早退");
			}
			if(hh>=time2){
				atten.setFlag("正常");
			}
	    }
	    if((time==null || time.isEmpty())&&!attenid.equals("6")){
	    	atten.setFlag("旷工");
	    } 
	    if((time!=null || !time.isEmpty())&&attenid.equals("6")){
	    	atten.setFlag("放假");
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
				atten.setFlag("正常");
			}
			if(hh>time1&&hh<=(time2-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time2-1)&&hh<time2){
				atten.setFlag("早退");
			}
			if(hh>=time2&&hh<=time3){
				atten.setFlag("正常");
			}
			if(hh>time3&&hh<=(time3+1)){
				atten.setFlag("迟到");
			}
			if(hh>(time3+1)&&hh<time4){
				atten.setFlag("早退");
			}
			if(hh>=time4&&hh<=time5){
				atten.setFlag("正常");
			}
			if(hh>time5&&hh<=(time5-1)){
				atten.setFlag("迟到");
			}
			if(hh>(time5-1)&&hh<time6){
				atten.setFlag("早退");
			}
			if(hh>=time6){
				atten.setFlag("正常");
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
	
	//删除学生
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentclass(Model model,Attendances atten){  
		if(null!=atten){
			if(null==atten.getId()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}			
			attendanceService.updateByPrimaryKeySelective(atten);
			return 1;
		}
		return 0;
	}
	
	/**
	 * 学生奖罚信息Excel导出。
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
							+ URLEncoder.encode("考勤记录表.xls",
									"UTF-8") + "\"");
			response.setContentType("application/vnd.ms-excel; charset=utf-8");

			attendanceService.exportExcel(srList, os, "奖罚记录");
		}catch(Exception e){
			//logger.error("意向学生Excel导出结果发生异常.", e);

			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			String msg = "<script>window.parent.$.messager.alert('提示', '奖罚记录Excel导出结果发生异常:请联系系统管理员.', 'info');</script>";
			try {
				os = response.getOutputStream();
				os.write(msg.getBytes("UTF-8"));
				os.flush();
			} catch (UnsupportedEncodingException e1) {
				//logger.error("关闭流发生异常.", e1);
			} catch (IOException e1) {
				//logger.error("关闭流发生异常.", e1);
			}
		}finally {
			closeOutputStream(os);
		}
		
	}
	
	/**
	 * 学生奖罚信息导入
	 * 
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/importStudReward")
    public @ResponseBody  Msg importStudAtten(HttpServletRequest request,MultipartFile txt_file){
    	 System.out.println("文件名--->"+txt_file.getOriginalFilename());	
    	 // logger.info("收到待处理文件,fileName="+file.getOriginalFilename());
         JSONObject jsonObject = new JSONObject();
         if(!txt_file.isEmpty()){
        	 try {
             	jsonObject = attendanceService.importStudAtten(txt_file);
             	Msg msg = new Msg();
             	msg.setMsg("处理成功");
             	msg.setOk(true);
             	msg.setData(jsonObject);
//             return MsgBuilder.create().setOk(true).setData(jsonObject).setMsg("处理成功！").bind();
             	return msg;
             	
             }catch (Exception e){
                 //logger.error("导入意向学生失败,fileName="+file.getOriginalFilename());
                 return MsgBuilder.create().setOk(false).setMsg("处理失败！").bind();
             }
         }
    	 return MsgBuilder.create().setOk(false).setMsg("处理失败！").bind();
	}
	
	/**
	 * 关闭输出流 
	 * @param outputStream
	 */
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
