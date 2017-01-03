package com.ht.controller.dailyWork;

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

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.EmpService;

@Controller
@RequestMapping("/dailyWork/dep")
public class DepController {
	@Autowired
	DepService depservice;
	
	@Autowired
	EmpService empservice;
	
	private String deptree;
	private List<Integer> depid=new ArrayList<>();
	
	//��ѯ����
	@RequestMapping("/deplist")
	@SystemControllerLog(description = "���벿����Ϣҳ��")
	public String seletedepList(Model model){
		deptree="";
		List<Dep> childendep = depservice.selectChildenDep(0);
		List<Dep> deplist = depservice.selectDepList();
		List<Emp> emplist = empservice.selectEmp(new Emp());
		childendep(childendep);
		deptree=deptree.replace("\'","\"");
		model.addAttribute("deptree", deptree);
		model.addAttribute("deplist", deplist);
		model.addAttribute("emplist", emplist);
		return "dailyWork/Dep";
	}
	private void childendep(List<Dep> childdep){
		if(null!=childdep && childdep.size()>0){
			deptree+="[";
			for(int i=0;i<childdep.size();i++){
				deptree+="{'id':"+childdep.get(i).getId();
				deptree+=",'text':'"+childdep.get(i).getDepname()+"'";
				deptree+=",'parent':'"+childdep.get(i).getParentid()+"'";
				deptree+=",'chairman':'"+childdep.get(i).getChairman()+"'";
				if(null!=childdep.get(i).getDepRemark() && !("").equals(childdep.get(i).getDepRemark())){
					deptree+=",'depRemark':'"+childdep.get(i).getDepRemark()+"'";
				}else{
					deptree+=",'depRemark':''";
				}
				List<Dep> childendep = depservice.selectChildenDep(childdep.get(i).getId());
				if(null!=childendep && childendep.size()>0){
					deptree+=",'nodes':";
					childendep(childendep);
				}
				deptree+="}";
				if(i!=(childdep.size()-1)){
					deptree+=",";
				}
			}
			deptree+="]";
		}
	}
	
	//��Ӳ���
	@RequestMapping(value = "/add") 
	@SystemControllerLog(description = "��Ӳ���")
	public @ResponseBody int addemp(Dep dep) { 
		dep.setCreateTime(new Date());
		dep.setUpdateTime(new Date());
		int count=depservice.insert(dep);
		return count; 
	}
	
	//�޸Ĳ���
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "�޸Ĳ���")
	public @ResponseBody int updateemp(@PathVariable("id") Integer id,Dep dep) { 
		dep.setUpdateTime(new Date());
		int count=0;
		Integer parentid=dep.getParentid();
		List<Dep> childendep = depservice.selectChildenDep(0);
		getchilddep(childendep);
		for(int i =0;i<depid.size();i++){//�жϸò����޸�ʱ�ĸ������Ƿ�Ϊ�Ӳ��ţ��ǵĻ������޸�
			if(parentid==depid.get(i)){
				count= -1;
				break;
			}
		}
		if(count==0){
			Emp emp=empservice.selectByPrimaryKey(dep.getChairman());
			emp.setDepid(dep.getId());
			empservice.updateByPrimaryKey(emp);
			count=depservice.updateByPrimaryKey(dep);
		}
		return count;
	}
	private void getchilddep(List<Dep> childdep){
		if(null!=childdep && childdep.size()>0){
			for(int i=0;i<childdep.size();i++){
				depid.add(childdep.get(i).getId());
				List<Dep> childendep = depservice.selectChildenDep(childdep.get(i).getId());
				if(null!=childendep && childendep.size()>0){
					getchilddep(childendep);
				}
			}
		}
	}
	
	//ɾ������
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }) 
	@SystemControllerLog(description = "ɾ������")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) { 
		List<Dep> childendep = depservice.selectChildenDep(id);
		Emp emp=new Emp();
		emp.setDepid(id);
		List<Emp> emplist=empservice.selectEmp(emp);
		if(null!=childendep && childendep.size()>0){//�ж��Ƿ����Ӳ��ţ��еĻ�����ɾ��
			return -1; 
		}else if(null!=emplist && emplist.size()>0){//�жϸò����Ƿ���Ա�����еĻ�����ɾ��
			return -2; 
		}else{
			int count=depservice.deleteByPrimaryKey(id);
			return count; 
		}
	}
}
