package com.ht.controller.dailyWork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.ht.popj.dailyWork.Baoxiao;
import com.ht.popj.dailyWork.Baoxiaotype;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.finance.FinanceBalance;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.FlowModel;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.BaoxiaoService;
import com.ht.service.dailyWork.BaoxiaotypeService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.finance.FinanceBalanceService;
import com.ht.service.sysSet.FlowModelService;
import com.ht.service.sysSet.FlowscheduleService;
import com.ht.util.RandomGet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/Baoxiao")
public class BaoxiaoController {

	@Autowired
	BaoxiaoService baoxiaoservice;
	
	@Autowired
	BaoxiaotypeService baoxiaotypeservice;
	
	@Autowired
	EmpService empservice;
	
	@Autowired
	FlowModelService fmservice;
	
	@Autowired
	FlowscheduleService fscheduleservice;
	
	@Autowired
	FinanceBalanceService financeBalanceService;
	
	@RequestMapping("/baoxiaolist")
	@SystemControllerLog(description = "���뱨������ҳ��")
	public String seletedepList(Model model){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
			model.addAttribute("user", emp);
		}
		List<Baoxiaotype> baoxiaotypelist=baoxiaotypeservice.Baoxiaotypelist(new Baoxiaotype());
		List<Emp> emplist=empservice.selectEmp(new Emp());
		model.addAttribute("baoxiaotypelist", baoxiaotypelist);
		model.addAttribute("emplist", emplist);
		return "dailyWork/Baoxiao";
	}
	
	//��ѯ��������
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST }) 
	@SystemControllerLog(description = "��ѯ���������")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id,Integer limit, Integer offset,Baoxiao baoxiao) { 
		ResultMessage rm = new ResultMessage();
		List<Baoxiao> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = baoxiaoservice.selectList(baoxiao);
        PageInfo<Baoxiao> pageInfo = new PageInfo<Baoxiao>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("���б������룺" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm; 
	}
	
	//������������
	@RequestMapping(value = "/add") 
	@SystemControllerLog(description = "��ӱ�������")
	public @ResponseBody int addemp(Baoxiao baoxiao) {
		baoxiao.setCreateTime(new Date().toLocaleString());
		baoxiao.setUpdateTime(new Date().toLocaleString());
		String str = new SimpleDateFormat("yyyyMMdd").format(new Date());
		str = str.substring(0, 8)+RandomGet.getSix()+"BX";
		baoxiao.setApplynum(str);
		FlowModel f = new FlowModel();
		List<FlowModel> flist = fmservice.findList(f);
		for(int i=0;i<flist.size();i++){
			f=flist.get(i);
			if(f.getFlowmodelname().equals("��д������")){
				//��ȡ"��д�黹��"������id
				baoxiao.setFlowid(f.getId());
				break;
			}
		}
		int count = baoxiaoservice.insert(baoxiao);
		return count; 
	}
	
	//�޸ġ��ύ��������
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "�޸ġ��ύ��������")
	public @ResponseBody int updateemp(@PathVariable("id") String id,Baoxiao baoxiao) { 
		if(baoxiao.getFlowid()==1){
			FlowModel f = new FlowModel();
			//���̽���
			Flowschedule fsch = new Flowschedule();
			//�칫��Ʒ�黹�������idΪ6
			f.setFlowmodeltypeid(9);
			//��ѯ�칫��Ʒ�黹������������
			List<FlowModel> flist = fmservice.findList(f);
			//���̽��ȱ�    ����    �������������� ����    ������
			for(int i=0;i<flist.size();i++){
				f = flist.get(i);
				fsch.setApplynum(baoxiao.getApplynum());
				if(i==0){
					//��һ������Ϊ�,�����
					fsch.setFlowstatus(1);
				}else{
					//��������δ���
					fsch.setFlowstatus(0);
				}
				//����ʱ,idΪ��
				fsch.setId(null);
				
				fsch.setFlowtype(f.getFlowmodeltypeid());
				fsch.setFlowdot(f.getId());
				fscheduleservice.insertSelective(fsch);
			}
		}
		int count = baoxiaoservice.updateByPrimaryKey(baoxiao);
		return count;  
	}
	
	//ɾ����������
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }) 
	@SystemControllerLog(description = "ɾ����������")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) { 
		int count = baoxiaoservice.deleteByPrimaryKey(id);
		return count;  
	}
	
	//��ȡ��������
	@RequestMapping(value = "getprice/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "��ȡ��������")
	public @ResponseBody int getbaoxiao(@PathVariable("id") Integer id) { 
		Baoxiao baoxiao=baoxiaoservice.selectByPrimaryKey(id);
		baoxiao.setFlowstatus(4);
		FinanceBalance balanc=new FinanceBalance();
		balanc.setOrderId(baoxiao.getApplynum());
		List<FinanceBalance> list=financeBalanceService.selectByDynamic(balanc);
		if(null!=list && list.size()>0){
			int count = baoxiaoservice.updateByPrimaryKey(baoxiao);
			return count; 
		}else{
			return -1;
		}
	}
}
