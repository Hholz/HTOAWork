package com.ht.controller.student;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentLayer;
import com.ht.service.student.StudentFloorService;
import com.ht.service.student.StudentLayerService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

@Controller
@RequestMapping("/student/FloorLayer")
public class Floor_Layer_Controller {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	StudentFloorService studentFloorService;
	@Autowired
	StudentLayerService studentLayerService;
	@RequestMapping("/Floor")
	@SystemControllerLog(description = "进入楼栋楼层页面")
	public String Floor(Model model) throws Exception{
		log.info(getClass()+"进入楼栋页面");
		List<StudentFloor> studentFloorList = studentFloorService.selectStudentFloorAll();
		model.addAttribute("studentFloorList", studentFloorList);
		if (null != UserInfoUtil.getEmp()) {
			
		}
		return "/student/student_Floor_Layer";
	}
	
//bootstrop table 里的url用来获取Json数据
	@RequestMapping("/listFloorJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "楼栋controller里的list表")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Model model,StudentFloor stuFloor){
		ResultMessage rm = new ResultMessage();
		List<StudentFloor> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(stuFloor.getId()!=null){
			sList = studentFloorService.selectByDynamic(stuFloor);
		}else{
			sList = studentFloorService.selectStudentFloorAll();
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<StudentFloor> pageInfo = new PageInfo<StudentFloor>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
       // System.err.println(sList.get(0).getFloorAdmin());
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addFloor")
	public @ResponseBody int addFloor(Model model,StudentFloor floor,int layerNum )throws Exception{
		int count=0;
		if(null!=floor && layerNum!=0){
			int num = layerNum;
			count = studentFloorService.insertSelective(floor);
			StudentLayer layer;
			for (int i=1;i<=num;i++) {
				layer = new StudentLayer();
				layer.setFloorid(floor.getId());
				layer.setLayername(String.valueOf(i));
				studentLayerService.insertSelective(layer);
			}
				
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
 //更新学生楼栋
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentFloor(Model model,StudentFloor studentFloor){  
		if(null!=studentFloor){
			int count = studentFloorService.updateByPrimaryKeySelective(studentFloor);
			return count;
		}
		return 0;
	}
	//删除楼栋，实际上是修改状态操作
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentFloor(Model model,@PathVariable("id")Integer id){
		//实际是修改状态，调用修改方法
		int count = studentFloorService.updateStatusPrimaryKey(id);
		return count;
	}
	
	
	//这里是楼层的controller============================================================================
	@RequestMapping("/listLayerJson")
	//此处为记录AOP拦截Controller记录用户操作    
		@SystemControllerLog(description = "楼层controller里的list表")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Model model,StudentLayer stuLayer){
		ResultMessage rm = new ResultMessage();
		List<StudentLayer> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(stuLayer!=null){
			sList = studentLayerService.selectDynamic(stuLayer);
		}else{
			sList = studentLayerService.selectDynamic(stuLayer);
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<StudentLayer> pageInfo = new PageInfo<StudentLayer>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有学生信息：" + total);
        //System.err.println(sList.get(0).getFloorname());
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	@RequestMapping("/addLayer")
	public @ResponseBody int addLayer(Model model,StudentLayer layer)throws Exception{
		if(null!=layer){
			int count = studentLayerService.insert(layer);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	} 
		
	
}
