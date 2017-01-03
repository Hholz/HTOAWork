package com.ht.controller.flow;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.dailyWork.MaterialMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentFallMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.flow.FlowComputerApply;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.flow.FlowComputerApplyService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/flowComputerApply")
public class FlowComputerApplyController {
	
	@Autowired
	StudentClassMapper classMapper;
	@Autowired
	StudentFallMapper fallMapper;
	@Autowired
	StudentMapper studMapper;
	@Autowired
	FlowComputerApplyService flowComputerApplyService;
	@Autowired
	MaterialMapper materialMapper;
	
	@RequestMapping("/flowComputerApply")
	@SystemControllerLog(description = "���뷢��ѧ������ҳ��")
	public String flowComputerApply(Model model) throws Exception{
		List<StudentFall> falls = fallMapper.selectFallByComputer();
		model.addAttribute("falls", falls);
		List<Material> materials = materialMapper.selectMaterialByName();
		model.addAttribute("materials", materials);
		return "flow/flowComputerApply";
	}
	
	@RequestMapping("/findComputerApplyList")
	@SystemControllerLog(description = "�������з���ѧ�����Եļ�¼")
	public @ResponseBody ResultMessage findComputerApplyList(int limit, int offset,FlowComputerApply computerApply) {
		ResultMessage rm = new ResultMessage();
		List<FlowComputerApply> computerApplies = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		computerApplies = flowComputerApplyService.selectAllComputerApply(computerApply);
		 // ȡ��ҳ��Ϣ
        PageInfo<FlowComputerApply> pageInfo = new PageInfo<FlowComputerApply>(computerApplies);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(computerApplies);
		return rm;
	}
	
	@RequestMapping("/findComputerClassList")
	@SystemControllerLog(description = "������ѡ�������а༶json����")
	public @ResponseBody ResultMessage findComputerClassList(Model model, StudentFall fall) {
		ResultMessage rm = new ResultMessage();
		List<StudentClass> classes = new ArrayList<>();
		if (fall.getId() == null) {
			rm.setTotal(1);
			rm.setRows(classes);
			return rm;
		}
		int fallid = fall.getId();
		classes = classMapper.selectClassByComputer(fallid);
		rm.setTotal(classes.size());
		rm.setRows(classes);
		return rm;
	}
	
	@RequestMapping("/findComputerStudentList")
	@SystemControllerLog(description = "������ѡ�༶�е�����ѧ��json����")
	public @ResponseBody ResultMessage findComputerStudentList(Model model, StudentClass classes) {
		ResultMessage rm = new ResultMessage();
		List<Student> students = new ArrayList<>();
		if (classes.getId() == null) {
			rm.setTotal(1);
			rm.setRows(students);
			return rm;
		}
		int classid = classes.getId();
		students = studMapper.selectStudListComputer(classid);
		rm.setTotal(students.size());
		rm.setRows(students);
		return rm;
	}
	
	@RequestMapping("/putComputerApply")
	@SystemControllerLog(description = "����ѧ������")
	public @ResponseBody int putComputerApply(FlowComputerApply computerApply) throws Exception{ 
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		
		if(null!=computerApply){
			computerApply.setEmpid(emp.getId());
			computerApply.setStatus(1);
			int count = flowComputerApplyService.updateByPrimaryKeySelective(computerApply);
			
			Material material = materialMapper.selectByPrimaryKey(computerApply.getMaterialid());
			int num = material.getCounts();
			num -= 1;
			material.setCounts(num);
			materialMapper.updateByPrimaryKeySelective(material);
			
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
}
