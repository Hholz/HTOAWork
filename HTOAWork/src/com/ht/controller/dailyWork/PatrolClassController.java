package com.ht.controller.dailyWork;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import com.ht.popj.dailyWork.Baoxiao;
import com.ht.popj.dailyWork.Duty;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.PatrolClass;
import com.ht.popj.dailyWork.PatrolHouse;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentHourse;
import com.ht.popj.student.StudentLayer;
import com.ht.service.dailyWork.DutyService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.dailyWork.PatrolClassService;
import com.ht.service.dailyWork.PatrolHouseService;
import com.ht.service.student.StudentFloorService;
import com.ht.service.student.StudentHourseService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentLayerService;
import com.ht.service.student.StudentService;
import com.ht.util.ResultMessage;

/*
 * 操作班级巡查，宿舍巡查
 */
@Controller
@RequestMapping("/dailyWork/Patrol")
public class PatrolClassController {
	@Autowired
	private PatrolClassService patrolClassService;
	@Autowired
	private EmpService empService;
	@Autowired
	private StudentInfoService studentInfoService;
	@Autowired
	private PatrolHouseService patrolHouseService;
	@Autowired
	private StudentHourseService houseService;
	@Autowired
	private StudentFloorService floorService;
	@Autowired
	private StudentLayerService layerService;
	@Autowired
	private DutyService dutyService;
	
	@RequestMapping("/patrolClassList")
	@SystemControllerLog(description = "进入班级巡查信息页面")
	public String patrolClassList(Model model){
		List<Emp> list = new ArrayList<>();
		Emp emp = new Emp();
		list = empService.selectEmp(emp);
		model.addAttribute("emp", list);
		
		List<StudentClass> list2 = new ArrayList<>();
		StudentClass stuclass = new StudentClass();
		list2 = studentInfoService.selectStudentclass(stuclass);
		model.addAttribute("stuclass", list2);

		return "/dailyWork/PatrolClass";
	}
	//班级巡查列表
	@RequestMapping("/patrolClassListJson")
	@SystemControllerLog(description = "返回班级巡查表json数据")
	public @ResponseBody ResultMessage patrolClassListJson(int limit, int offset,PatrolClass patrolClass,Duty duty){
		ResultMessage rm = new ResultMessage();
		List<PatrolClass> list = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(patrolClass != null){
			list = patrolClassService.findPatrolClassList1(patrolClass);
		}else{
			list = patrolClassService.findPatrolClassList2();
		}
		
		List<PatrolClass> patrollist = new ArrayList<>();
		List<Duty> dutylist = new ArrayList<>();
		patrollist = patrolClassService.findPatrolClassList2();
		dutylist = dutyService.findDutyList2();
		for(int i=0;i<patrollist.size();i++){
			for(int j=0;j<dutylist.size();j++){
				if(patrollist.get(i).getPatroltime().equals(dutylist.get(j).getDutytime())){
					duty = dutyService.selectById(dutylist.get(j).getId());
					duty.setDutystatus(1);
					dutyService.updateDuty(duty);
				}
			}
		}
		// 取分页信息
        PageInfo<PatrolClass> pageInfo = new PageInfo<PatrolClass>(list);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	//新增班级巡查
	@RequestMapping("/addPatrolClass")
	@SystemControllerLog(description = "新增班级巡查")
	public @ResponseBody int addPatrolClass(PatrolClass patrolClass){
		// 1.获取session对象
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		Emp emp = null;
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
		}
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		patrolClass.setPatrolteach(emp.getId());
		patrolClass.setCreateTime(new Date().toLocaleString());
		if(patrolClass != null){
			int count = patrolClassService.addPatrolClass(patrolClass);
			return count;
		}
		return 0;
	}
	//修改班级巡查
	@RequestMapping(value="/patrolClass/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改班级巡查")
	public @ResponseBody int updatePatrolClass(PatrolClass patrolClass){
		patrolClass.setUpdateTime(new Date().toLocaleString());
		if(patrolClass != null){
			int count = patrolClassService.updatePatrolClass(patrolClass);
			return count;
		}
		return 0;
	}
	//删除
	@RequestMapping(value="/patrolClass/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除班级巡查")
	public @ResponseBody int deletePatrolClass(@PathVariable("id") Integer id){
		int count = patrolClassService.deletePatrolClassById(id);
		return count;
	}
	
	//宿舍巡查列表
	@RequestMapping("/patrolHouseList")
	@SystemControllerLog(description = "进入宿舍巡查信息页面")
	public String patrolHouseList(Model model){
		List<Emp> list = new ArrayList<>();
		Emp emp = new Emp();
		list = empService.selectEmp(emp);
		model.addAttribute("emp", list);
		
		List<StudentHourse> list2 = new ArrayList<>();
		StudentHourse house = new StudentHourse();
		list2 = houseService.findHouseList(house);
		model.addAttribute("house", list2);
		return "/dailyWork/PatrolHouse";
	}
	
	@RequestMapping("/patrolHouseListJson")
	@SystemControllerLog(description = "返回宿舍巡查表json数据")
	public @ResponseBody ResultMessage patrolHouseListJson(int limit, int offset,PatrolHouse patrolHouse){
		ResultMessage rm = new ResultMessage();
		List<PatrolHouse> list = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(patrolHouse != null){
			list = patrolHouseService.findPatrolHouseList1(patrolHouse);
		}else{
			list = patrolHouseService.findPatrolHouseList2();
		}
		 // 取分页信息
        PageInfo<PatrolHouse> pageInfo = new PageInfo<PatrolHouse>(list);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	
	//新增宿舍巡查
	@RequestMapping("/addPatrolHouse")
	@SystemControllerLog(description = "新增宿舍巡查")
	public @ResponseBody int addPatrolHouse(PatrolHouse patrolHouse){
		// 1.获取session对象
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		Emp emp = null;
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
		}
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		patrolHouse.setPatrolteach(emp.getId());
		patrolHouse.setCreateTime(new Date().toLocaleString());
		if(patrolHouse != null){
			int count = patrolHouseService.addPatrolHouse(patrolHouse);
			return count;
		}
		return 0;
	}
	//修改宿舍巡查
	@RequestMapping(value="/patrolHouse/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改宿舍巡查")
	public @ResponseBody int updatePatrolHouse(PatrolHouse patrolHouse){
		patrolHouse.setUpdateTime(new Date().toLocaleString());
		if(patrolHouse != null){
			int count = patrolHouseService.updatePatrolHouse(patrolHouse);
			return count;
		}
		return 0;
	}
	//删除
	@RequestMapping(value="/patrolHouse/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除宿舍巡查")
	public @ResponseBody int deletePatrolHouse(@PathVariable("id") Integer id){
		int count = patrolHouseService.deletePatrolHouseById(id);
		return count;
	}
	
	//-----------------------二级联动----------------------------
	//获取所有届别
	@RequestMapping("/allFall")
	@SystemControllerLog(description = "获取所有届别")
	public @ResponseBody List<StudentFall> allFall() throws Exception{
		List<StudentFall> fallList = new ArrayList<>();
		fallList = studentInfoService.selectStudentFall();
		return fallList;
	}
	//获取届别对应的班级
	@RequestMapping(value="/allClass/{id}")
	@SystemControllerLog(description = "获取届别对应的班级")
	public @ResponseBody List<StudentClass> allClass(@PathVariable("id") Integer id){
		List<StudentClass> classList = new ArrayList<>();
		classList = studentInfoService.selectByLevelId(id.toString());
		return classList;
	}
	//获取所有楼栋
	@RequestMapping("/allFloor")
	@SystemControllerLog(description = "获取所有楼栋")
	public @ResponseBody List<StudentFloor> allFloor(StudentFloor studentFloor){
		List<StudentFloor> floorList = new ArrayList<>();
		floorList = floorService.selectStudentFloorAll();
		return floorList;
	}
	//获取楼栋对应的楼层
	@RequestMapping(value="/allLayer/{id}")
	@SystemControllerLog(description = "获取楼栋对应的楼层")
	public @ResponseBody List<StudentLayer> allLayer(@PathVariable("id") Integer id){
		List<StudentLayer> layerList = new ArrayList<StudentLayer>();
		StudentLayer layer = new StudentLayer();
		layer.setFloorid(id);
		layerList = layerService.selectDynamic(layer);
		return layerList;
	}
}
