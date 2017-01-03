package com.ht.controller.flow;

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
import com.ht.popj.flow.FlowApplyMaterialdetail;
import com.ht.popj.flow.Waitformaterial;
import com.ht.popj.sysSet.FinancePolicquantityset;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.flow.WaitformaterialService;
import com.ht.service.sysSet.FinancePolicquantitysetService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("flow/waitformaterial")
public class WaitForMaterialController {

	@Autowired
	WaitformaterialService waitformaterialService;
	@Autowired
	MaterialService materialService;
	@Autowired
	FinancePolicquantitysetService finaPolicQuantitySetService;
	@Autowired
	MaterialTypeMapper mtypeMapper;

	@RequestMapping("/waitformateriallist")
	@SystemControllerLog(description = "进入待入库用品页面")
	public String List(Model model) {
		List<MaterialType> materialtypelist = mtypeMapper.selectList();

		model.addAttribute("mlist", materialtypelist);
		return "/flow/WaitForMaterialList";
	}

	// 返回物品的json数据
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "返回物品的json数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			Waitformaterial material) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		Waitformaterial waitformaterial = new Waitformaterial();
		waitformaterial.setStatus(1);
		List<Waitformaterial> wList = waitformaterialService.selectSelective(waitformaterial);

		PageInfo<Waitformaterial> pageInfo = new PageInfo<Waitformaterial>(wList);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(wList);
		return rm;
	}

	// 修改物品
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改物品")
	public @ResponseBody int updata(Model model, Waitformaterial material) {
		
		waitformaterialService.updateByPrimaryKeySelective(material);
		count(material);
		return 1;
	}

	// 更改库存
	public void count(Waitformaterial waitformaterial) {
		Material material = materialService.selectByPrimaryKey(waitformaterial.getMaterialid());
		material.setCounts(material.getCounts() + waitformaterial.getCount());
		materialService.updateByPrimaryKeySelective(material);

		FinancePolicquantityset score = finaPolicQuantitySetService.selectByMaterialid(waitformaterial.getMaterialid());
		if (material.getCounts() <= score.getQuantity()) {
			score.setCountstatus(1);
		} else {
			score.setCountstatus(0);
		}
		int count = finaPolicQuantitySetService.updateByPrimaryKeySelective(score);

	}

	// 删除物品
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除物品")
	public @ResponseBody int delete(Model model,@PathVariable("id") Integer id) {
		waitformaterialService.deleteByPrimaryKey(id);
		return 1;
	}

}
