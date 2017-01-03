package com.ht.controller.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentHourse;
import com.ht.popj.student.StudentLayer;
import com.ht.popj.student.StudentRoom;
import com.ht.service.student.StudentFloorService;
import com.ht.service.student.StudentHourseService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentLayerService;
import com.ht.service.student.StudentRoomService;
import com.ht.service.student.StudentService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

@Controller
@RequestMapping("/student/studentHourse")
public class StudentHourseController {

	@Autowired StudentInfoService studentInfoService;
	@Autowired StudentRoomService studentRoomService;
	@Autowired
	StudentService studentService;
	@Autowired
	StudentHourseService studentHourseService;
	@Autowired 
	StudentFloorService studentFloorService;
	@Autowired
	StudentLayerService studentLayerService;
	@RequestMapping("/studentHourse")
	@SystemControllerLog(description = "进入宿舍信息的页面")
	public String studentHourse(Model model) throws Exception{
		List<StudentFloor> Floorlist = studentFloorService.selectStudentFloorAll();
		List<StudentLayer> Layerlist = studentLayerService.selectStudentLayerAll();
		List<StudentHourse> Hourselist = studentHourseService.selectDynamic(null);
		model.addAttribute("Floorlist",Floorlist);
		model.addAttribute("Layerlist", Layerlist);
		model.addAttribute("Hourselist", Hourselist);
		return "student/student_hourse";
	}
	
	@RequestMapping("/studentHourseAllot")
	@SystemControllerLog(description = "进入宿舍分配的页面")
	public String studentHourseAllot(Model model) throws Exception{
		
//		Subject currentUser = SecurityUtils.getSubject();
//		Session session = currentUser.getSession();
//		// 2.从session中取得ShiroUserInfo对象
//		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null != UserInfoUtil.getEmp()) {
			List<StudentClass> cList = new ArrayList<StudentClass>();
			StudentClass cls = new StudentClass();
			Emp emp = UserInfoUtil.getEmp();
			cls.setClteacherId(emp.getId());
			cList = studentInfoService.selectStudentclass(cls);
			model.addAttribute("ClassList",cList);
		}
		
		return "student/student_hourse_allot";
	}
	
	@RequestMapping("/hourseJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "学生宿舍StudentHourseController里的list表")
	public @ResponseBody ResultMessage listJson(int limit, int offset,StudentHourse hourse){
		ResultMessage rm = new ResultMessage();
		List<StudentHourse> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		System.out.println(hourse.getHoursename()+hourse.getHsex());
		
		sList = studentHourseService.selectDynamic(hourse);
		 // 取分页信息
        PageInfo<StudentHourse> pageInfo = new PageInfo<StudentHourse>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addhourse")
	public @ResponseBody int addScore(StudentHourse hourse) throws Exception{ 
		//生成随机密码
		if(null!=hourse){
			int count = studentHourseService.insert(hourse);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	@RequestMapping("/findLayer")
	public @ResponseBody ResultMessage findFloor(Model model,StudentLayer s) {
		ResultMessage rm = new ResultMessage();
		List<StudentLayer> emplist =  studentLayerService.selectDynamic(s);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	@RequestMapping("/hoursename")
	public @ResponseBody ResultMessage findhourname(Model model,StudentHourse s,int layerid) {
		ResultMessage rm = new ResultMessage();
		List<StudentHourse> hourselist =  studentHourseService.selectDynamic(s);
		rm.setTotal(hourselist.size());
		rm.setRows(hourselist);
		return rm;
	}
	
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateRepModelD(StudentHourse hourse) throws Exception{  
		if(null!=hourse){
			int count = studentHourseService.updateByPrimaryKeySelective(hourse);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentHourse(Model model,@PathVariable("id")Integer id){
		//实际是修改状态，调用修改方法
		if(null!=id){
			StudentHourse hourse = new StudentHourse();
			hourse.setId(id);
			hourse.setStatus(0);
			int count = studentHourseService.updateByPrimaryKeySelective(hourse);
			return count;
		}else
		return 0;
	}
	
	//分宿舍===================================================================================
	
	@RequestMapping("/studentJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "分宿舍在StudentHourseController里的list表")
	public @ResponseBody ResultMessage studentJson(int limit, int offset,Student s){
		ResultMessage rm = new ResultMessage();
		List<Student> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		
		if (null != UserInfoUtil.getEmp()) {
			List<StudentClass> cList = new ArrayList<StudentClass>();
			StudentClass cls = new StudentClass();
			Emp emp = UserInfoUtil.getEmp();
			cls.setClteacherId(emp.getId());
			cList = studentInfoService.selectStudentclass(cls);
			List<Integer> ids = new ArrayList<Integer>();
			
			for (int i = 0; i < cList.size(); i++) {
				int id = cList.get(i).getId();
				ids.add(id);
			}
			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("clsIds", ids);
			map.put("student", s);
			if(s.getNum()!=0){
				sList = studentService.selectByClsIdsRandom(map);
			}else{
				sList = studentService.selectByClsIds(map);
			}
			
		}
		
		 // 取分页信息
        PageInfo<Student> pageInfo = new PageInfo<Student>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/studentHourseLot")
	public @ResponseBody int studentHourseLot(Model model,String ids,int huorid,Student student) throws Exception{ 
		String[] array = ids.split(",");
		int count=0;
		StudentRoom room=null;
		Student stu = null;
		List<Integer> idList = new ArrayList<Integer>();
		if(0!=huorid){
			
			for(int i=0;i<array.length;i++){
				int stid = Integer.parseInt(array[i]);
				idList.add(stid);
				
				stu =studentService.selectById(stid);
				room = new StudentRoom();
				room.setHourseid(huorid);
				room.setStudentid(stu.getStuname());
				room.setClassname(stu.getClassInfo().getClassname());
				//System.out.println(stu.getClassInfo().getClassname());
				//给student_room表插入数据
				studentRoomService.insertSelective(room);
			}
			Map<String,Object> map = new HashMap<>();
			map.put("ids", idList);
			map.put("huorid", huorid);
			//给学生分配宿舍，即修改学生信息的宿舍
			count = studentService.upStuHuorByIds(map);
			}
		return count;
	}
	
	
	
	//学生明细表格数据========================================================================================
	@RequestMapping("/studentRoomJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "进入学生宿舍明细在StudentHourseController里的list表")
	public @ResponseBody ResultMessage studentRoomJson(int limit, int offset,StudentRoom room){
		ResultMessage rm = new ResultMessage();
		List<StudentRoom> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList =studentRoomService.selectDynamic(room);
		 // 取分页信息
        PageInfo<StudentRoom> pageInfo = new PageInfo<StudentRoom>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
}
