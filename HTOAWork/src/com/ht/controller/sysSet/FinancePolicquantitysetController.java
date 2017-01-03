package com.ht.controller.sysSet;

import java.util.ArrayList;
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
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.dailyWork.MaterialType;
import com.ht.popj.sysSet.FinancePolicquantityset;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.dailyWork.MaterialTypeService;
import com.ht.service.sysSet.FinancePolicquantitysetService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/sysSet/finaPolicQuantitySet")
public class FinancePolicquantitysetController {

	@Autowired
	FinancePolicquantitysetService finaPolicQuantitySetService;
	@Autowired
	MaterialService materialService;
	@Autowired
	MaterialTypeService materialtypeService;

	@RequestMapping("/finaPolicQuantitySet")
	public String FinaPolicQuantitySet(Model model) throws Exception {
		
		List<MaterialType> materialtypelist = materialtypeService.selectList();
		List<Material> materialList = materialService.selectList();
		model.addAttribute("materialList", materialList);
		model.addAttribute("materialtypelist", materialtypelist);
		return "/sysSet/financePolicQuantitySet";
	}

	@RequestMapping("Json")
	public @ResponseBody ResultMessage repModelJsonList(int limit, int offset, FinancePolicquantityset score) {
		ResultMessage rm = new ResultMessage();
		List<FinancePolicquantityset> sList = new ArrayList<FinancePolicquantityset>();
		
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		sList = finaPolicQuantitySetService.selectSelective(score);
		// 取分页信息
		PageInfo<FinancePolicquantityset> pageInfo = new PageInfo<FinancePolicquantityset>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	//返回所选类别的所有物品的Json数据
	@RequestMapping("/findmaterial")
	@SystemControllerLog(description = "返回所选类别的所有物品的Json数据")
	public @ResponseBody ResultMessage findmaterial(Model model,MaterialType materialtype) {
		ResultMessage rm = new ResultMessage();
		Material material = new Material();
		material.setMaterialtypeid(materialtype.getId());
		List<Material> emplist =  materialService.selectByName(material);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	@RequestMapping("add")
	public @ResponseBody int add(FinancePolicquantityset score) throws Exception {
		// 生成随机密码
		FinancePolicquantityset f = new FinancePolicquantityset();
		f.setMaterialid(score.getMaterialid());
		if(!finaPolicQuantitySetService.selectSelective(f).isEmpty()){
			return 0;
		}
		
		if (null != score) {
			
			Material material = materialService.selectByPrimaryKey(score.getMaterialid());
			if(material.getCounts() <= score.getQuantity()){
				score.setCountstatus(1);
			}else{
				score.setCountstatus(0);
			}
			
			int count = finaPolicQuantitySetService.insertSelective(score);
			return count;
		}
		// 当student为空时，运行到这里，返回0
		return 0;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateRepModelD(FinancePolicquantityset srmd) throws Exception {
		if (null != srmd) {
			Material material = materialService.selectByPrimaryKey(srmd.getMaterialid());
			if(material.getCounts() < srmd.getQuantity()){
				srmd.setCountstatus(1);
			}else{
				srmd.setCountstatus(0);
			}
			int count = finaPolicQuantitySetService.updateByPrimaryKeySelective(srmd);
			
			return count;
		}
		return 0;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int delete(Model model, @PathVariable("id") Integer id) {
		// 实际是修改状态，调用修改方法
		int count = finaPolicQuantitySetService.deleteByPrimaryKey(id);
		return count;
	}

}
