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
import com.ht.mapper.dailyWork.MaterialTypeMapper;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.dailyWork.MaterialType;
import com.ht.service.dailyWork.MaterialService;
import com.ht.util.ResultMessage;
/*
 * ������Ʒ
 */
@Controller
@RequestMapping("/dailyWork/Material")
public class MaterialController {
	
	@Autowired
	MaterialService MService;
	@Autowired
	MaterialTypeMapper mtypeMapper;
	
	//������Ʒҳ��
	@RequestMapping("/materiallist")
	@SystemControllerLog(description = "������Ʒҳ��")
	public String List(Model model) {
		List<MaterialType> materialtypelist = mtypeMapper.selectList();
		
		model.addAttribute("mlist", materialtypelist);
		return "/dailyWork/MaterialList";
	}
	
	//������Ʒ
	@RequestMapping("/add")
	@SystemControllerLog(description = "������Ʒ")
	public @ResponseBody int add(Model model, Material material) {
		return MService.insertSelective(material);
	}
	
	//������Ʒ��json����
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "������Ʒ��json����")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			Material material) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);

		if (material.getMaterialname() == null) {
			material.setMaterialname("");
		}
		List<Material> materiallist = MService.selectByName(material);

		PageInfo<Material> pageInfo = new PageInfo<Material>(materiallist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(materiallist);
		return rm;
	}

	//�޸���Ʒ
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���Ʒ")
	public @ResponseBody int updata(Model model, Material material) {
		MService.updateByPrimaryKeySelective(material);
		return 1;
	}

	//ɾ����Ʒ
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����Ʒ")
	public @ResponseBody int delete(Model model, Integer id) {
		MService.deleteByPrimaryKey(id);

		return 1;
	}

}
