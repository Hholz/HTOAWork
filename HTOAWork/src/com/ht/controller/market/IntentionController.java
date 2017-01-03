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
import com.ht.popj.market.IntentionStudent;
import com.ht.popj.market.MarketStudent;
import com.ht.popj.market.PredestinationStudent;
import com.ht.service.market.MarketStudentService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

/*
 * 意向学生管理页面
 */
@Controller
@RequestMapping("/market/intention")
public class IntentionController {

	//市场部里意向预定学生的表service
	@Autowired
	MarketStudentService msService;
	
	@SystemControllerLog(description = "进入意向学生信息页面")
	@RequestMapping("/page")
	public String intentionStudentList() {
		return "/market/IntentionStudent";
	}

	// 意向学生列表
	@RequestMapping("/intentionStudentListJson")
	@SystemControllerLog(description = "返回意向学生表json数据")
	public @ResponseBody ResultMessage intentionStudentListJson(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (marketStudent != null) {
			//登入人id
			if(null != UserInfoUtil.getEmp()){
				marketStudent.setEmpId(UserInfoUtil.getEmp().getId());
				if(null!=marketStudent.getMsStatus()){
					if(marketStudent.getMsStatus()==100){
						//全部
						marketStudent.setMsStatus(null);
//						list = msService.selectPredStudentAll(marketStudent);
						list = msService.selectIntentionStudent(marketStudent);
					}else{
						list = msService.selectPredStudentAll(marketStudent);
					}
				}else{
					list = msService.selectIntentionStudent(marketStudent);
				}
			}
		}
		// 取分页信息
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	//新增意向学生
	@RequestMapping("/addIntentionStudent")
	@SystemControllerLog(description = "新增意向学生")
	public @ResponseBody int addIntentionStudent(MarketStudent marketStudent){
		marketStudent.setCreateTime(new Date());
		//获取当前登录用户的信息
		if(null != UserInfoUtil.getEmp()){
			marketStudent.setEmpId(UserInfoUtil.getEmp().getId());
		}
		//默认状态是0.意向
		marketStudent.setMsStatus(0);
		if(marketStudent != null){
			int count = msService.insertByPJ(marketStudent);
			return count;
		}
		return 0;
	}
	//修改意向学生信息
	@RequestMapping(value="/intentionStudent/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改意向学生")
	public @ResponseBody int updateIntentionStudent(MarketStudent marketStudent){
		marketStudent.setUpdateTime(new Date());
		if(marketStudent != null){
			int count = msService.updateByPJ(marketStudent);
			return count;
		}
		return 0;
	}
	// 删除报名学生信息
	@RequestMapping(value = "/intentionStudent/{id}", method = RequestMethod.DELETE)
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
	
	//意向学生成为预定报名学生
	@RequestMapping(value="/addPrestudent/{id}")
	@SystemControllerLog(description = "意向学生成为预定报名学生")
	public @ResponseBody int addPrestudent(@PathVariable("id") Integer id){
		//就是更改MarketStudent的学生状态(msStatus) 1=预定报名
		int count = msService.updateMsStatusById(id,1);
		//生成预定报名费收入记录
		return count;
	}
	//批量意向学生成为预定报名学生
	@RequestMapping("/addPrestudents")
	@SystemControllerLog(description = "批量意向学生成为预定报名学生")
	public @ResponseBody int addPrestudents(String ids){
		//就是更改MarketStudent的学生状态(msStatus) 1=预定报名
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			MarketStudent ms = msService.selectById(Integer.parseInt(array[i]));
			if(ms != null&&null!=ms.getApplyCost()&&!ms.getApplyCost().equals("是")){
				ms.setUpdateTime(new Date());
				ms.setMsStatus(1);
				ms.setApplyCost("是");
				count += msService.updateByPJ(ms);
			}else{
				return -1;//已经报名费
			}
		}
		return count;
	}
	// 批量变为试学状态
	@RequestMapping("/beTeststudent")
	@SystemControllerLog(description = "批量变为正式学生，等待分班")
	public @ResponseBody int beTeststudent(String ids) {
		// 就是更改MarketStudent的学生状态(msStatus) 5=试学
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			MarketStudent ms = msService.selectById(Integer.parseInt(array[i]));
			ms.setUpdateTime(new Date());
			ms.setMsStatus(5);
			ms.setIsTest("是");
			ms.setClassid(0);//如果有分了班又没有来试学的，班级重新设置为没有
			count += msService.updateByPJ(ms);
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
}
