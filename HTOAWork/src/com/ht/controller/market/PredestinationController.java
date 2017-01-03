package com.ht.controller.market;

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
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.market.MarketStudent;
import com.ht.service.market.MarketStudentService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

/*
 * 预定报名学生管理页面
 */
@Controller
@RequestMapping("/market/predestination")
public class PredestinationController {

	// 市场部里意向预定学生的表service
	@Autowired
	MarketStudentService msService;

	@SystemControllerLog(description = "进入意向学生信息页面")
	@RequestMapping("/page")
	public String intentionStudentList() {
		return "/market/PredestinationStudent";
	}

	// 预定报名学生管理列表
	@RequestMapping("/predestinationStudentListJson")
	@SystemControllerLog(description = "返回MarketStudent学生表json数据")
	public @ResponseBody ResultMessage predestinationStudentListJson(int limit, int offset,
			MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (marketStudent != null) {
			//登入人id
			if(null != UserInfoUtil.getEmp()){
				marketStudent.setEmpId(UserInfoUtil.getEmp().getId());
			}
			if(null!=marketStudent.getMsStatus()){
				if(marketStudent.getMsStatus()==100){
					//全部
					marketStudent.setMsStatus(null);
					list = msService.selectPredStudentAll(marketStudent);
				}else{
					list = msService.selectPredStudentAll(marketStudent);
				}
			}else{
				list = msService.selectPredStudentDefault(marketStudent);
			}
		}
		// 取分页信息
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}

	// 新增预定报名学生（不交试学费的那种）
	@RequestMapping("/addPredestinationStudent")
	@SystemControllerLog(description = "新增预定报名学生")
	public @ResponseBody int addIntentionStudent(MarketStudent marketStudent) {
		marketStudent.setCreateTime(new Date());
		// 获取当前登录用户的信息
		if (null != UserInfoUtil.getEmp()) {
			marketStudent.setEmpId(UserInfoUtil.getEmp().getId());
		}
		// 默认状态是1.预定报名
		marketStudent.setMsStatus(1);
		if (marketStudent != null) {
			int count = msService.insertByPJ(marketStudent);
			return count;
		}
		return 0;
	}

	// 修改报名学生信息
	@RequestMapping(value = "/predestinationStudent/{id}", method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改报名学生信息")
	public @ResponseBody int updatePredestinationStudent(MarketStudent marketStudent) {
		marketStudent.setUpdateTime(new Date());
		if (marketStudent != null) {
			int count = msService.updateByPJ(marketStudent);
			return count;
		}
		return 0;
	}
	
	// 删除报名学生信息
	@RequestMapping(value = "/predestinationStudent/{id}", method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除报名学生信息")
	public @ResponseBody int delPredestinationStudent(Model model,@PathVariable("id")Integer id) {
		return msService.deleteById(id);
	}
	//批量删除报名学生信息
	@RequestMapping("/delStudents")
	@SystemControllerLog(description = "批量删除报名学生信息")
	public @ResponseBody int delStudents(String ids) {
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			int id = Integer.parseInt(array[i]);
			count += msService.deleteById(id);
		}
		return count;
	}
	// 批量变为正式报名状态
	@RequestMapping("/bePrestudent")
	@SystemControllerLog(description = "批量变为正式学生，等待分班")
	public @ResponseBody int bePrestudents(String ids) {
		// 就是更改MarketStudent的学生状态(msStatus) 2=正式报名
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			int id = Integer.parseInt(array[i]);
			count += msService.updateMsStatusById(id, 2);;
		}
		return count;
	}
	// 批量变为已经分班状态
	@RequestMapping("/beIsClass")
	@SystemControllerLog(description = "批量变为已经分班状态")
	public @ResponseBody int beIsClasses(String ids) {
		// 就是更改MarketStudent的学生状态(msStatus) 3=已经分班状态
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			int id = Integer.parseInt(array[i]);
			count += msService.updateMsStatusById(id, 3);;
		}
		return count;
	}
	// 批量变为离校状态
	@RequestMapping("/beLeave")
	@SystemControllerLog(description = "批量变为离校状态态")
	public @ResponseBody int beLeaves(String ids) {
		// 就是更改MarketStudent的学生状态(msStatus) 4=离开
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			int id = Integer.parseInt(array[i]);
			count += msService.updateMsStatusById(id, 4);;
		}
		return count;
	}

	
	
	/********************下面的方法弃用**************************************/
	// 变为正式报名状态
	@RequestMapping(value = "/bePrestudent/{id}")
	@SystemControllerLog(description = "变为正式学生，等待分班")
	public @ResponseBody int bePrestudent(@PathVariable("id") Integer id) {
		// 就是更改MarketStudent的学生状态(msStatus) 2=正式报名
		int count = msService.updateMsStatusById(id, 2);
		return count;
	}

	// 变为已经分班状态
	@RequestMapping(value = "/beIsClass/{id}")
	@SystemControllerLog(description = "分班完成")
	public @ResponseBody int beIsClass(@PathVariable("id") Integer id) {
		// 就是更改MarketStudent的学生状态(msStatus) 3=已经分班状态
		int count = msService.updateMsStatusById(id, 3);
		return count;
	}

	// 变为离校状态
	@RequestMapping(value = "/beLeave/{id}")
	@SystemControllerLog(description = "试学完，不来，离开")
	public @ResponseBody int beLeave(@PathVariable("id") Integer id) {
		// 就是更改MarketStudent的学生状态(msStatus) 4=离开
		int count = msService.updateMsStatusById(id, 4);
		// 生成预定报名费收入记录
		return count;
	}
}
