package com.ht.controller.dailyWork;

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
import com.ht.popj.dailyWork.MaterialType;
import com.ht.service.dailyWork.MaterialTypeService;
import com.ht.util.ResultMessage;
/*
 * 操作物品类别
 */
@Controller
@RequestMapping("/dailyWork/MateriaType")
public class MaterialTypeController {

	@Autowired
	MaterialTypeService materialtypeService;
	
	//进入物品类别页面
	@RequestMapping("/MTList")
	@SystemControllerLog(description = "进入物品类别页面")
	public String studentList() {
		return "/dailyWork/MaterialTypeList";
	}

	//新增物品类别
	@RequestMapping("/add")
	@SystemControllerLog(description = "新增物品类别")
	public @ResponseBody int addMaterilType(Model model, MaterialType materialtype) {
		int a = materialtypeService.addMaterialType(materialtype);

		return a;
	}

	//返回物品类别的json数据
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "返回物品类别的json数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			MaterialType materialtype) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		if(materialtype.getMaterialtypename() == null){
			materialtype.setMaterialtypename("");
		}
		if(materialtype.getMatertypeRemark() == null){
			materialtype.setMatertypeRemark("");
		}
		List<MaterialType> materialtypelist = materialtypeService
				.findListMaterialType(materialtype);
		PageInfo<MaterialType> pageInfo = new PageInfo<MaterialType>(materialtypelist);
		long total = pageInfo.getTotal(); 
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(materialtypelist);
		return rm;
	}
	
	//修改物品类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改物品类别")
	public @ResponseBody int updataMaterilType(Model model, MaterialType materialtype) {
		materialtypeService.updateMaterialType(materialtype);
		
		return 1;
	}
	
	//删除物品类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除物品类别")
	public @ResponseBody int deleteMaterilType(Model model, Integer id) {
		materialtypeService.deleteMaterialType(id);

		return 1;
	}
}
