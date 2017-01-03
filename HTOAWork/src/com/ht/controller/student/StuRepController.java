package com.ht.controller.student;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.ht.popj.finance.Tuitionsetdetail;
import com.ht.popj.student.StuReplyModel;
import com.ht.popj.student.StuReplyModelD;
import com.ht.popj.student.StudentClass;
import com.ht.service.student.StuRepScoreDService;
import com.ht.service.student.StuRepScoreService;
import com.ht.service.student.StuReplyModelDService;
import com.ht.service.student.StuReplyModelService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;
/*
 * 学生项目答辩模板
 */
@Controller
@RequestMapping("/student/reply")
public class StuRepController {

	@Autowired
	StuReplyModelService srmService;
	@Autowired
	StuReplyModelDService srmdService;
	
	@Autowired
	StuRepScoreService srsService;
	@Autowired
	StuRepScoreDService srsdService;
	//班级
	@Autowired
	StudentInfoService studentInfoService;
	@RequestMapping("/page")
	@SystemControllerLog(description = "进入项目答辩模板页面")
	public String page(Model model){  
		List<StuReplyModel> srmList = new ArrayList<StuReplyModel>();
		srmList = srmService.selectAll();
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		StudentClass stuCls = new StudentClass();
		if(null != UserInfoUtil.getEmp()){
			stuCls.setClteacherId(UserInfoUtil.getEmp().getId());
		}
		clsList = studentInfoService.selectStudentclass(stuCls);
		model.addAttribute("srmList", srmList);
		model.addAttribute("clsList", clsList);
		return "student/stuReply";
	}
	
	
	/*
	 * 项目模板表格json数据
	 */
	@RequestMapping("/repModelJsonList")
	@SystemControllerLog(description = "返回项目答辩模板表格json数据")
	public @ResponseBody ResultMessage repModelJsonList(int limit, int offset,StuReplyModel srm){
		ResultMessage rm = new ResultMessage();
		List<StuReplyModel> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(null!=srm){
			if(null != UserInfoUtil.getEmp()){
				srm.setTeacId(UserInfoUtil.getEmp().getId());
			}
			sList = srmService.selectByPJ(srm);
		}
		 // 取分页信息
        PageInfo<StuReplyModel> pageInfo = new PageInfo<StuReplyModel>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * 添加项目模板
	 */
	@RequestMapping("/addRepModel")
	@SystemControllerLog(description = "项目答辩模板新增")
	public @ResponseBody int addRepModel(StuReplyModel srm,String jsonStr) throws ParseException, JSONException{ 
		//放在service层，才有事务回滚
		if(null != UserInfoUtil.getEmp()){
			srm.setTeacId(UserInfoUtil.getEmp().getId());
		}else{
			return 0;//新增失败
		}
		return srmService.addRepModel(srm,jsonStr);
	}
	
	/*
	 * 修改项目模板
	 */
	@RequestMapping(value = "repModel/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "项目答辩模板修改")
	public @ResponseBody int updateRepModel(StuReplyModel srm) throws ParseException{  
		srm.setRemark(RemarkSet.getRemark("更新"));
		srm.setUpdateTime(new Date());
		if(null!=srm){
			int count = srmService.updateByPJ(srm);
			return count;
		}
		return 0;
	}
	/*
	 * 修改项目模板
	 */
	@RequestMapping("repModel/changeStatus")
	@SystemControllerLog(description = "项目答辩模板状态修改")
	public @ResponseBody int changeStatus(StuReplyModel srm) throws ParseException{  
		srm.setRemark(RemarkSet.getRemark("更新状态"));
		srm.setUpdateTime(new Date());
		srm.setStatus(2);//已答辩
		if(null!=srm){
			int count = srmService.updateByPJ(srm);
			return count;
		}
		return 0;
	}
	/*
	 * 删除项目模板
	 */
	@RequestMapping(value = "repModel/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "项目答辩模板删除")
	public @ResponseBody int deleteRepModel(Model model,@PathVariable("id")Integer id){  
		int count = 0;
		//通过模板id去查答辩成绩表，如果使用了该模块，就删除失败
		if(srsService.countBysrmId(id)>0){
			count = -1;
		}else{
			count = srmService.delByUpdate(id);
		}
		return count;
	}
	
	/*
	 * 项目模板详细表表格json数据
	 */
	@RequestMapping("/repModelDJsonList")
	@SystemControllerLog(description = "返回项目答辩模板明细表格json数据")
	public @ResponseBody ResultMessage repModelDJsonList(int limit, int offset,StuReplyModelD srmd){
		ResultMessage rm = new ResultMessage();
		List<StuReplyModelD> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(null!=srmd){
			sList = srmdService.selectByPJ(srmd);
		}else{
			sList = srmdService.selectAll();
		}
		 // 取分页信息
        PageInfo<StuReplyModelD> pageInfo = new PageInfo<StuReplyModelD>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * 添加项目模板详细表
	 */
	@RequestMapping("/addRepModelD")
	@SystemControllerLog(description = "项目答辩模板明细新增")
	public @ResponseBody int addRepModelD(StuReplyModelD srmd) throws ParseException{ 
		//创建时间
		srmd.setCreateTime(new Date());// new Date()为获取当前系统时间
		//生成随机密码
		srmd.setRemark(RemarkSet.getRemark("添加"));
		if(null!=srmd){
			int count = srmdService.insertByPJ(srmd);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	/*
	 * 修改项目模板详细表
	 */
	@RequestMapping(value = "repModelD/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "项目答辩模板明细修改")
	public @ResponseBody int updateRepModelD(StuReplyModelD srmd) throws ParseException{  
		srmd.setRemark(RemarkSet.getRemark("更新"));
		srmd.setUpdateTime(new Date());
		if(null!=srmd){
			int count = srmdService.updateByPJ(srmd);
			return count;
		}
		return 0;
	}
	/*
	 * 删除项目模板详细表
	 */
	@RequestMapping(value = "repModelD/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "项目答辩模板明细删除")
	public @ResponseBody int deleteRepModelD(Model model,@PathVariable("id")Integer id){ 
		int count = 0;
		//通过srmdId去查询分数明细表有没有用这个id,有则不能删除
		if(srsdService.countBysrmdId(id)>0){
			count = -1;
		}else{
			count = srmdService.delByUpdate(id);
		}
		return count;
	}
	/*
	 * 通过模板表id来查询模板明细表
	 */
	@RequestMapping(value = "repModelDList/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "通过模板表id返回模板明细表json数据")
	public @ResponseBody List<StuReplyModelD> getRepModelDList(Model model,@PathVariable("id")Integer id){
		List<StuReplyModelD> srmdList = new ArrayList<StuReplyModelD>();
		srmdList = srmdService.selectBysrmId(id);
		return srmdList;
	}
}
