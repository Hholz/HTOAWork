package com.ht.controller.sysSet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.popj.student.StudentAttencecond;
import com.ht.popj.sysSet.Residence;
import com.ht.popj.sysSet.StuStatus;
import com.ht.service.student.StudentAttencondService;
import com.ht.service.sysSet.ResidenceService;
import com.ht.service.sysSet.StuStatusService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/sysSet")
public class StuSetController {

	@Autowired
	StuStatusService stuStatusService;
	@Autowired
	ResidenceService residenceService;
	@Autowired
	StudentAttencondService stuAttencondService;
	
	@RequestMapping("/student")
	public String index(){  
		return "sysSet/stuSet";
	}
	
	@RequestMapping("/stuStaListJson")
	public @ResponseBody ResultMessage stuStaListJson(int limit, int offset,StuStatus stuStatus){
		ResultMessage rm = new ResultMessage();
		List<StuStatus> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = stuStatusService.selectAll();
		 // 取分页信息
        PageInfo<StuStatus> pageInfo = new PageInfo<StuStatus>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addStuSta")
	public @ResponseBody int addStuSta(StuStatus stuStatus) throws ParseException{ 
		//创建时间
		stuStatus.setCreateTime(new Date());// new Date()为获取当前系统时间
		//生成随机密码
		stuStatus.setRemark(RemarkSet.getRemark("添加学生状态"));
		if(null!=stuStatus){
			int count = stuStatusService.insertByPJ(stuStatus);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//更新学生状态
	@RequestMapping(value = "/stuSta/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStuSta(StuStatus stuStatus) throws ParseException{  
		stuStatus.setRemark(RemarkSet.getRemark("更新学生状态"));
		stuStatus.setUpdateTime(new Date());
		if(null!=stuStatus){
			int count = stuStatusService.updateByPJ(stuStatus);
			return count;
		}
		return 0;
	}
	//删除学生状态
	@RequestMapping(value = "/stuSta/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStuSta(Model model,@PathVariable("id")Integer id){  
		int count = stuStatusService.delByUpdate(id);
		return count;
	}
	
	//户口类型table的json数据
	@RequestMapping("/residenceListJson")
	public @ResponseBody ResultMessage residenceListJson(int limit, int offset,Residence residence){
		ResultMessage rm = new ResultMessage();
		List<Residence> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = residenceService.selectAll();
		 // 取分页信息
        PageInfo<Residence> pageInfo = new PageInfo<Residence>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	//添加户口类型
	@RequestMapping("/addResidence")
	public @ResponseBody int addResidence(Residence residence) throws ParseException{ 
		//创建时间
		residence.setCreateTime(new Date());// new Date()为获取当前系统时间
		//生成随机密码
		residence.setRemark(RemarkSet.getRemark("添加户口类别"));
		if(null!=residence){
			int count = residenceService.insertByPJ(residence);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//更新专业lei'b
	@RequestMapping(value = "/residence/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateResidence(Residence residence) throws ParseException{  
		residence.setRemark(RemarkSet.getRemark("更新户口类别"));
		residence.setUpdateTime(new Date());
		if(null!=residence){
			int count = residenceService.updateByPJ(residence);
			return count;
		}
		return 0;
	}
	//删除户口类型
	@RequestMapping(value = "/residence/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteResidence(Model model,@PathVariable("id")Integer id){  
		int count = residenceService.delByUpdate(id);
		return count;
	}
	
	//学生考勤情况类别table的json数据
	@RequestMapping("/condListJson")
	public @ResponseBody ResultMessage condListJson(int limit, int offset,StudentAttencecond cond){
		ResultMessage rm = new ResultMessage();
		List<StudentAttencecond> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = stuAttencondService.selectByPrimaryKeyall();
		 // 取分页信息
        PageInfo<StudentAttencecond> pageInfo = new PageInfo<StudentAttencecond>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	//添加学生考勤情况类别
	@RequestMapping("/addcond")
	public @ResponseBody int addcondition(StudentAttencecond cond) throws ParseException{ 
		if(null!=cond){
			int count = stuAttencondService.insertSelective(cond);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//更新学生考勤情况类别
	@RequestMapping(value = "/cond/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updatecond(StudentAttencecond cond) throws ParseException{  
		if(null!=cond){
			int count = stuAttencondService.updateByPrimaryKeySelective(cond);
			return count;
		}
		return 0;
	}
	//删除学生考勤情况类别
	@RequestMapping(value = "/cond/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deletecond(StudentAttencecond cond) throws ParseException{    
		if(null!=cond){
			int count = stuAttencondService.updateByPrimaryKeySelective(cond);
			return count;
		}
		return 0;
	}
	
}
