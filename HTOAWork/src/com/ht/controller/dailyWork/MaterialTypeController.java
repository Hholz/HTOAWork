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
 * ������Ʒ���
 */
@Controller
@RequestMapping("/dailyWork/MateriaType")
public class MaterialTypeController {

	@Autowired
	MaterialTypeService materialtypeService;
	
	//������Ʒ���ҳ��
	@RequestMapping("/MTList")
	@SystemControllerLog(description = "������Ʒ���ҳ��")
	public String studentList() {
		return "/dailyWork/MaterialTypeList";
	}

	//������Ʒ���
	@RequestMapping("/add")
	@SystemControllerLog(description = "������Ʒ���")
	public @ResponseBody int addMaterilType(Model model, MaterialType materialtype) {
		int a = materialtypeService.addMaterialType(materialtype);

		return a;
	}

	//������Ʒ����json����
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "������Ʒ����json����")
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
	
	//�޸���Ʒ���
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���Ʒ���")
	public @ResponseBody int updataMaterilType(Model model, MaterialType materialtype) {
		materialtypeService.updateMaterialType(materialtype);
		
		return 1;
	}
	
	//ɾ����Ʒ���
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����Ʒ���")
	public @ResponseBody int deleteMaterilType(Model model, Integer id) {
		materialtypeService.deleteMaterialType(id);

		return 1;
	}
}
