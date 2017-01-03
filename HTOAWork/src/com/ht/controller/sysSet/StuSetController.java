package com.ht.controller.sysSet;

import java.text.ParseException;
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
import com.ht.popj.student.StudentAttencecond;
import com.ht.popj.sysSet.Residence;
import com.ht.popj.sysSet.StuStatus;
import com.ht.service.student.StudentAttencondService;
import com.ht.service.sysSet.ResidenceService;
import com.ht.service.sysSet.StuStatusService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/sysSet")
public class StuSetController {

	@Autowired
	StuStatusService stuStatusService;
	@Autowired
	ResidenceService residenceService;
	@Autowired
	StudentAttencondService stuAttencondService;
	
	@RequestMapping("/student")
	public String index(){  
		return "sysSet/stuSet";
	}
	
	@RequestMapping("/stuStaListJson")
	public @ResponseBody ResultMessage stuStaListJson(int limit, int offset,StuStatus stuStatus){
		ResultMessage rm = new ResultMessage();
		List<StuStatus> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = stuStatusService.selectAll();
		 // ȡ��ҳ��Ϣ
        PageInfo<StuStatus> pageInfo = new PageInfo<StuStatus>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addStuSta")
	public @ResponseBody int addStuSta(StuStatus stuStatus) throws ParseException{ 
		//����ʱ��
		stuStatus.setCreateTime(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		//�����������
		stuStatus.setRemark(RemarkSet.getRemark("���ѧ��״̬"));
		if(null!=stuStatus){
			int count = stuStatusService.insertByPJ(stuStatus);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//����ѧ��״̬
	@RequestMapping(value = "/stuSta/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStuSta(StuStatus stuStatus) throws ParseException{  
		stuStatus.setRemark(RemarkSet.getRemark("����ѧ��״̬"));
		stuStatus.setUpdateTime(new Date());
		if(null!=stuStatus){
			int count = stuStatusService.updateByPJ(stuStatus);
			return count;
		}
		return 0;
	}
	//ɾ��ѧ��״̬
	@RequestMapping(value = "/stuSta/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStuSta(Model model,@PathVariable("id")Integer id){  
		int count = stuStatusService.delByUpdate(id);
		return count;
	}
	
	//��������table��json����
	@RequestMapping("/residenceListJson")
	public @ResponseBody ResultMessage residenceListJson(int limit, int offset,Residence residence){
		ResultMessage rm = new ResultMessage();
		List<Residence> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = residenceService.selectAll();
		 // ȡ��ҳ��Ϣ
        PageInfo<Residence> pageInfo = new PageInfo<Residence>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	//��ӻ�������
	@RequestMapping("/addResidence")
	public @ResponseBody int addResidence(Residence residence) throws ParseException{ 
		//����ʱ��
		residence.setCreateTime(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		//�����������
		residence.setRemark(RemarkSet.getRemark("��ӻ������"));
		if(null!=residence){
			int count = residenceService.insertByPJ(residence);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//����רҵlei'b
	@RequestMapping(value = "/residence/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateResidence(Residence residence) throws ParseException{  
		residence.setRemark(RemarkSet.getRemark("���»������"));
		residence.setUpdateTime(new Date());
		if(null!=residence){
			int count = residenceService.updateByPJ(residence);
			return count;
		}
		return 0;
	}
	//ɾ����������
	@RequestMapping(value = "/residence/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteResidence(Model model,@PathVariable("id")Integer id){  
		int count = residenceService.delByUpdate(id);
		return count;
	}
	
	//ѧ������������table��json����
	@RequestMapping("/condListJson")
	public @ResponseBody ResultMessage condListJson(int limit, int offset,StudentAttencecond cond){
		ResultMessage rm = new ResultMessage();
		List<StudentAttencecond> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = stuAttencondService.selectByPrimaryKeyall();
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentAttencecond> pageInfo = new PageInfo<StudentAttencecond>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	//���ѧ������������
	@RequestMapping("/addcond")
	public @ResponseBody int addcondition(StudentAttencecond cond) throws ParseException{ 
		if(null!=cond){
			int count = stuAttencondService.insertSelective(cond);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//����ѧ������������
	@RequestMapping(value = "/cond/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updatecond(StudentAttencecond cond) throws ParseException{  
		if(null!=cond){
			int count = stuAttencondService.updateByPrimaryKeySelective(cond);
			return count;
		}
		return 0;
	}
	//ɾ��ѧ������������
	@RequestMapping(value = "/cond/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deletecond(StudentAttencecond cond) throws ParseException{    
		if(null!=cond){
			int count = stuAttencondService.updateByPrimaryKeySelective(cond);
			return count;
		}
		return 0;
	}
	
}
