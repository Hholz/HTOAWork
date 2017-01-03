package com.ht.controller.student;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentReward;
import com.ht.popj.student.StudentSayface;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.service.student.StudentrewardService;
import com.ht.util.Msg;
import com.ht.util.MsgBuilder;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/sreward")
public class StudentRewardController {
	
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	StudentrewardService studentrewService;
	@Autowired
	StudentService studentService;
	
	//进入奖罚页面
	@RequestMapping("/srewardStu")
	public String StudentRewardList(Model model,StudentClass stucls){
		System.out.println("=====");
		List<Student> list = studentService.selectStudentAll();
		model.addAttribute("stulist",list);
		List<StudentClass> scList = studentInfoService.selectStudentclass(stucls);
		model.addAttribute("scList",scList);
		return "student/Studentreward";
	}
	
	@RequestMapping("/stureward")
	@SystemControllerLog(description = "学生奖罚管理controller里的list表")
	public @ResponseBody ResultMessage saystudentheart(int limit, int offset,Model model,StudentReward student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<StudentReward> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(student!=null){
			sList = studentrewService.studentrewardsel(student);
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<StudentReward> pageInfo = new PageInfo<StudentReward>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//新增学生
	@RequestMapping("/reward/add")
	public @ResponseBody int addStudentreward(Model model,StudentReward student) throws Exception{ 
		if(null!=student){
			if(null==student.getReason()||student.getReason().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if(null==student.getStudentid()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if(null==student.getContent()||student.getContent().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			int i =studentrewService.insertSelective(student);
			return i;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//更新学生
	@RequestMapping(value = "/reward/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentreward(Model model,StudentReward student){  
		if(null!=student){
			if(null==student.getContent()||student.getContent().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			if(null==student.getReason()||student.getReason().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			studentrewService.updateByPrimaryKeySelective(student);
			System.out.println("p==========p");
			return 1;
		}
		return 0;
	}
	
	//删除学生
	@RequestMapping(value = "/reward/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentreward(Model model,StudentReward student){  
		if(null!=student){
			if(null==student.getId()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			studentrewService.updateByPrimaryKeySelective(student);
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
	@RequestMapping("/exportStudreward")
	public void exportStudReward(HttpSession session,HttpServletResponse response,StudentReward student) throws Exception {
		List<StudentReward> srList = studentrewService.studentrewardsel(student);
		OutputStream os = null;
		try{
			os = response.getOutputStream();
			response.setHeader(
					"Content-Disposition",
					"attachment;filename=\""
							+ URLEncoder.encode("奖罚记录.xls",
									"UTF-8") + "\"");
			response.setContentType("application/vnd.ms-excel; charset=utf-8");

			studentrewService.exportExcel(srList, os, "奖罚记录");
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
	
	
	@RequestMapping("/inreStudImport")
	public ModelAndView inreStudImport() throws Exception {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("/student/StuRewWin");
		return modelandview;
	}
	/**
	 * 学生奖罚信息导入
	 * 
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/importStudReward")
    public @ResponseBody  Msg importStudReward(HttpServletRequest request,MultipartFile txt_file){
    	 System.out.println("文件名--->"+txt_file.getOriginalFilename());	
    	 // logger.info("收到待处理文件,fileName="+file.getOriginalFilename());
         JSONObject jsonObject = new JSONObject();
         if(!txt_file.isEmpty()){
        	 try {
             	jsonObject = studentrewService.importStudReward(txt_file);
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
	
	@RequestMapping("/findreward")
	public @ResponseBody ResultMessage findemp(Model model,Student s) {
		ResultMessage rm = new ResultMessage();
		List<Student> sList = studentService.selectByDynamic(s);
		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
}
