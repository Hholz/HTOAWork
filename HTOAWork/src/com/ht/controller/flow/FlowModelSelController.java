package com.ht.controller.flow;

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
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.mapper.flow.FlowRoleMapper;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.flow.FlowModelsel;
import com.ht.popj.flow.FlowRole;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.service.flow.FlowModelselService;
import com.ht.service.flow.FlowModeltypeService;
import com.ht.service.flow.FlowRoleService;
import com.ht.service.flow.FlowsModelService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/flowModelSel")
public class FlowModelSelController {

	@Autowired
	FlowModelselService modelselService;
	@Autowired
	FlowModeltypeService modelTypeService;
	@Autowired
	FlowsModelService modelService; 
	@Autowired
	DepMapper depmapper;
	@Autowired
	EmpMapper empMapper;
	@Autowired
	FlowRoleService flowRoleService;
	@Autowired
	FlowRoleMapper roleMapper;
	
	@RequestMapping("/flowMOdelSel")
	@SystemControllerLog(description = "进入流程模板字典页面")
	public String flowMOdelSel(Model model) throws Exception{
		List<Dep> deplist = depmapper.selectDepList();
		model.addAttribute("deplist", deplist);
		List<FlowRole> flowRoles = roleMapper.selectAllByinvalid();
		model.addAttribute("flowRoles", flowRoles);
		List<Dep> deps = depmapper.selectDepList();
		model.addAttribute("deps", deps);
		return "flow/flowModelSel";
	}
	
	@RequestMapping("/findEmpIdByDepId")
	@SystemControllerLog(description = "返回所选部门的所有员工json数据")
	public @ResponseBody ResultMessage findEmpIdByDepId(Model model, Dep dep) {
		Emp emplist = new Emp();
		ResultMessage rm = new ResultMessage();
		if (dep.getId() == null) {
			emplist.setEmpname("");
			emplist.setId("");
			rm.setTotal(1);
			rm.setRows(emplist);
			return rm;
		}
		int depid = dep.getId();
		dep = depmapper.selectByPrimaryKey(depid);
		emplist = empMapper.selectByPrimaryKey(dep.getChairman());
		rm.setTotal(1);
		rm.setRows(emplist);
		return rm;
	}
	
	@RequestMapping("/flowModelSelJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "模板字典list数据")
	public @ResponseBody ResultMessage flowModelSelJsonList(int limit, int offset,FlowModelsel modelsel){
		ResultMessage rm = new ResultMessage();
		List<FlowModelsel> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = modelselService.selectAll();
		 // 取分页信息
        PageInfo<FlowModelsel> pageInfo = new PageInfo<FlowModelsel>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/flowModelselAdd")
	@SystemControllerLog(description = "新增模板字典")
	public @ResponseBody int flowModelselAdd(FlowModelsel modelsel) throws Exception{ 
		//生成随机密码
		if(null!=modelsel){
			modelsel.setInvalid(1);
			modelsel.setStatus(1);
			modelsel.setCreateTime(new Date());
			int count = modelselService.insertSelective(modelsel);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改模板字典")
	public @ResponseBody int updateRepModelD(FlowModelsel modelsel) throws Exception{
		if(null!=modelsel){
			int count = modelselService.updateByPrimaryKeySelective(modelsel);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/flowRoleJsonList")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "流程审批角色list数据")
	public @ResponseBody ResultMessage flowRoleJsonList(int limit, int offset,FlowRole flowRole){
		ResultMessage rm = new ResultMessage();
		List<FlowRole> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = flowRoleService.selectAll();
		 // 取分页信息
        PageInfo<FlowRole> pageInfo = new PageInfo<FlowRole>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/flowRoleAdd")
	@SystemControllerLog(description = "新增流程审批角色")
	public @ResponseBody int flowRoleAdd(FlowRole flowRole) throws Exception{ 
		if(null!=flowRole){
			int count = flowRoleService.insert(flowRole);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping(value = "updateFlowRole/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改模板字典")
	public @ResponseBody int updateFlowRole(FlowRole modelsel) throws Exception{
		if(null!=modelsel){
			int count = flowRoleService.updateByPrimaryKeySelective(modelsel);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/flowModelTypeJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "模板模板类型list数据")
	public @ResponseBody ResultMessage flowModelTypeJsonList(int limit, int offset,FlowsModeltype modeltype){
		ResultMessage rm = new ResultMessage();
		List<FlowsModeltype> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if (null!=modeltype) {
			if(null!=modeltype.getModelselid()){
				sList = modelTypeService.selectAll(modeltype.getModelselid());
			}else{
				modeltype.setModelselid(0);
				sList = modelTypeService.selectAll(modeltype.getModelselid());
			}
		}else{
			modeltype = new FlowsModeltype();
			modeltype.setModelselid(0);
			sList = modelTypeService.selectAll(modeltype.getModelselid());
		}
		// 取分页信息
        PageInfo<FlowsModeltype> pageInfo = new PageInfo<FlowsModeltype>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addFlowModelType")
	@SystemControllerLog(description = "新增模板类型")
	public @ResponseBody int addFlowModelType(FlowsModeltype modeltype) throws Exception{ 
		//生成随机密码
		if(null!=modeltype){
			modeltype.setCreateTime(new Date());
			int count = modelTypeService.insertSelective(modeltype);
			FlowsModel model = new FlowsModel();
			if (modeltype.getFlowType() == 1) {
				model.setEmpid("d39c7088-9413-11e6-903a-dc3b943345c4");
				model.setFlowmodeltypeid(count);
				model.setFlowmodelname("填写申请单");
				model.setPlanday(1);
				model.setSeq(1);
				model.setCreateTime(new Date());
				model.setRemark("填写申请单");
			}else{
				model.setRoleid("kong");
				model.setFlowmodeltypeid(count);
				model.setFlowmodelname("填写申请单");
				model.setPlanday(1);
				model.setSeq(1);
				model.setCreateTime(new Date());
				model.setRemark("填写申请单");
			}
			modelService.insertSelective(model);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping(value = "deleteModelType/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除模板类型")
	public @ResponseBody int deleteModelType(Model model,@PathVariable("id")Integer id){
		//实际是修改状态，调用修改方法
		int count = modelTypeService.deleteByPrimaryKey(id);
		modelService.deleteByPrimary(id);
		return count;
	}
	
	@RequestMapping(value = "/updateModelType/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改模板类型")
	public @ResponseBody int updateModelType(FlowsModeltype modeltype) throws Exception{
		if(null!=modeltype){
			int count = modelTypeService.updateByPrimaryKeySelective(modeltype);
			return count;
		}
		return 0;
	}
	
	
	@RequestMapping("/flowModelJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "模板审批节点list数据")
	public @ResponseBody ResultMessage flowModelJsonList(int limit, int offset,FlowsModel flowsModel){
		ResultMessage rm = new ResultMessage();
		List<FlowsModel> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if (null!=flowsModel) {
			if(null!=flowsModel.getFlowmodeltypeid()){
				sList = modelService.selectAll(flowsModel.getFlowmodeltypeid());
			}else{
				flowsModel.setFlowmodeltypeid(0);
				sList = modelService.selectAll(flowsModel.getFlowmodeltypeid());
			}
		}else{
			flowsModel = new FlowsModel();
			flowsModel.setFlowmodeltypeid(0);
			sList = modelService.selectAll(flowsModel.getFlowmodeltypeid());
		}
		// 取分页信息
        PageInfo<FlowsModel> pageInfo = new PageInfo<FlowsModel>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addFlowModel")
	@SystemControllerLog(description = "新增审批节点")
	public @ResponseBody int addFlowModel(FlowsModel model){ 
		//生成随机密码
		if(null!=model){
			model.setCreateTime(new Date());
			String roleid = model.getRoleid();
			int depid = 0;
			try {
				depid = Integer.parseInt(roleid);
			} catch (Exception e) {
			}
			Dep dep = depmapper.selectByPrimaryKey(depid);
			if(dep != null){
				model.setEmpid(dep.getChairman());
			}
			int count = modelService.insertSelective(model);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping(value = "deleteModel/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除模板审批节点")
	public @ResponseBody int deleteModel(Model model,@PathVariable("id")Integer id){
		//实际是修改状态，调用修改方法
		int count = modelService.deleteByPrimaryKey(id);
		return count;
	}
	
	@RequestMapping(value = "/updateModel/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改模板类型")
	public @ResponseBody int updateModel(FlowsModel model) throws Exception{
		if(null!=model){
			int count = modelService.updateByPrimaryKeySelective(model);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/flowModelJson2")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "模板审批节点list数据(审批角色)")
	public @ResponseBody ResultMessage flowModelJson2List(int limit, int offset,FlowsModel flowsModel){
		ResultMessage rm = new ResultMessage();
		List<FlowsModel> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if (null!=flowsModel) {
			if(null!=flowsModel.getFlowmodeltypeid()){
				sList = modelService.selectAll2(flowsModel.getFlowmodeltypeid());
			}else{
				flowsModel.setFlowmodeltypeid(0);
				sList = modelService.selectAll2(flowsModel.getFlowmodeltypeid());
			}
		}else{
			flowsModel = new FlowsModel();
			flowsModel.setFlowmodeltypeid(0);
			sList = modelService.selectAll2(flowsModel.getFlowmodeltypeid());
		}
		
		for (int i = 0; i < sList.size(); i++) {
			String roleid = sList.get(i).getRoleid();
			int depid = 0;
			try {
				depid = Integer.parseInt(roleid);
			} catch (Exception e) {
			}
			Dep dep = depmapper.selectByPrimaryKey(depid);
			if (dep != null) {
				sList.get(i).setDep(dep);
			}
		}
		// 取分页信息
        PageInfo<FlowsModel> pageInfo = new PageInfo<FlowsModel>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
}
