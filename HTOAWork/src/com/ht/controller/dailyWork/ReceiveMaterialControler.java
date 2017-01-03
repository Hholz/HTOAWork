package com.ht.controller.dailyWork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.dailyWork.MaterialType;
import com.ht.popj.dailyWork.ReceiveMaterial;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.sysSet.FlowModel;
import com.ht.popj.sysSet.FlowModelType;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.MaterialService;
import com.ht.service.dailyWork.MaterialTypeService;
import com.ht.service.dailyWork.ReceiveMaterialService;
import com.ht.service.sysSet.FlowModelService;
import com.ht.service.sysSet.FlowModelTypeService;
import com.ht.service.sysSet.FlowscheduleService;
import com.ht.util.RandomGet;
import com.ht.util.ResultMessage;
/*
 * ������Ʒ����
 */
@Controller
@RequestMapping("/dailyWork/ReceiveMaterial")
public class ReceiveMaterialControler {
	
	@Autowired
	ReceiveMaterialService rematerialservice;
	@Autowired
	MaterialTypeService matypeservice;
	@Autowired
	FlowModelService fmservice;
	@Autowired
	FlowscheduleService fscheduleservice;
	@Autowired
	MaterialService maservice;
	
	//������Ʒ��������ҳ��
	@RequestMapping("/ReceiveMaterialList")
	@SystemControllerLog(description = "������Ʒ��������ҳ��")
	public String List(Model model) {
		
		List<MaterialType> materialtypelist = matypeservice.selectList();
		
		model.addAttribute("mlist", materialtypelist);
		return "/dailyWork/ReceiveMaterialList";
	}
	//������ѡ����������Ʒ��json����
	@RequestMapping("/findMaterial")
	@SystemControllerLog(description = "������ѡ����������Ʒ��json����")
	public @ResponseBody ResultMessage findemp(Model model,MaterialType matype) {
		Material material = new Material();
		ResultMessage rm = new ResultMessage();
		material.setMaterialtypeid(matype.getId());
		List<Material> emplist =  maservice.selectByName(material);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	//������Ʒ������Ϣ
	@RequestMapping("/add")
	@SystemControllerLog(description = "������Ʒ������Ϣ")
	public @ResponseBody int add(Model model, ReceiveMaterial rematerial) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		//3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		FlowModel f = new FlowModel();
		List<FlowModel> flist = fmservice.findList(f);
		for(int i=0;i<flist.size();i++){
			f=flist.get(i);
			if(f.getFlowmodelname().equals("��д���쵥")){
				//��ȡ"����д���쵥"������id
				rematerial.setFlowid(f.getId());
				break;
			}
		}
		rematerial.setEmpid(emp.getId());
		return rematerialservice.insertSelective(rematerial);
	}
	
	//������Ʒ������json����
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "������Ʒ������json����")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			ReceiveMaterial rematerial) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);

		List<ReceiveMaterial> materiallist = rematerialservice.selectByName(rematerial);

		PageInfo<ReceiveMaterial> pageInfo = new PageInfo<ReceiveMaterial>(materiallist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(materiallist);
		return rm;
	}
	
	//�޸���Ʒ������Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���Ʒ������Ϣ")
	public @ResponseBody int updata(Model model, ReceiveMaterial rematerial) {
		rematerialservice.updateByPrimaryKeySelective(rematerial);
		return 1;
	}
	
	//�ύ��Ʒ������Ϣ
	@RequestMapping(value = "/upset")
	@SystemControllerLog(description = "�ύ��Ʒ������Ϣ")
	public @ResponseBody int upset(Model model, ReceiveMaterial rematerial) {
		ReceiveMaterial r = rematerialservice.selectByPrimaryKey(rematerial.getId());
		Material m = maservice.selectByPrimaryKey(r.getMaterialid());
		if(m.getCounts()<r.getCounts()){
			
			return 0;
		}
		
		rematerial.setApprovalstatus(1);
		rematerial.setUpset(1);
		rematerial.setUnit(m.getUnit());
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(d);
		System.out.println(str);
		str = str.substring(0, 8);
		str = str+RandomGet.getSix()+"BGYPSL";
		rematerial.setApplynum(str);
		FlowModel f = new FlowModel();
		Flowschedule fsch = new Flowschedule();
		f.setFlowmodeltypeid(3);
		List<FlowModel> flist = fmservice.findList(f);
		for(int i=0;i<flist.size();i++){
			f = flist.get(i);
			fsch.setApplynum(str);
			if(i==0){
				fsch.setFlowstatus(1);
			}else{
				fsch.setFlowstatus(0);
			}
			fsch.setId(null);
			fsch.setFlowtype(f.getFlowmodeltypeid());
			fsch.setFlowdot(f.getId());
			fscheduleservice.insertSelective(fsch);
		}
		
		rematerialservice.updateByPrimaryKeySelective(rematerial);
		return 1;
	}
	
	//ɾ����Ʒ������Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����Ʒ������Ϣ")
	public @ResponseBody int delete(Model model,@PathVariable("id") Integer id) {
		rematerialservice.deleteByPrimaryKey(id);
		return 1;
	}
}
