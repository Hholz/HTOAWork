package com.ht.controller.student;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.ht.popj.finance.Tuitionsetdetail;
import com.ht.popj.student.StuReplyModel;
import com.ht.popj.student.StuReplyModelD;
import com.ht.popj.student.StudentClass;
import com.ht.service.student.StuRepScoreDService;
import com.ht.service.student.StuRepScoreService;
import com.ht.service.student.StuReplyModelDService;
import com.ht.service.student.StuReplyModelService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;
/*
 * ѧ����Ŀ���ģ��
 */
@Controller
@RequestMapping("/student/reply")
public class StuRepController {

	@Autowired
	StuReplyModelService srmService;
	@Autowired
	StuReplyModelDService srmdService;
	
	@Autowired
	StuRepScoreService srsService;
	@Autowired
	StuRepScoreDService srsdService;
	//�༶
	@Autowired
	StudentInfoService studentInfoService;
	@RequestMapping("/page")
	@SystemControllerLog(description = "������Ŀ���ģ��ҳ��")
	public String page(Model model){  
		List<StuReplyModel> srmList = new ArrayList<StuReplyModel>();
		srmList = srmService.selectAll();
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		StudentClass stuCls = new StudentClass();
		if(null != UserInfoUtil.getEmp()){
			stuCls.setClteacherId(UserInfoUtil.getEmp().getId());
		}
		clsList = studentInfoService.selectStudentclass(stuCls);
		model.addAttribute("srmList", srmList);
		model.addAttribute("clsList", clsList);
		return "student/stuReply";
	}
	
	
	/*
	 * ��Ŀģ����json����
	 */
	@RequestMapping("/repModelJsonList")
	@SystemControllerLog(description = "������Ŀ���ģ����json����")
	public @ResponseBody ResultMessage repModelJsonList(int limit, int offset,StuReplyModel srm){
		ResultMessage rm = new ResultMessage();
		List<StuReplyModel> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(null!=srm){
			if(null != UserInfoUtil.getEmp()){
				srm.setTeacId(UserInfoUtil.getEmp().getId());
			}
			sList = srmService.selectByPJ(srm);
		}
		 // ȡ��ҳ��Ϣ
        PageInfo<StuReplyModel> pageInfo = new PageInfo<StuReplyModel>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * �����Ŀģ��
	 */
	@RequestMapping("/addRepModel")
	@SystemControllerLog(description = "��Ŀ���ģ������")
	public @ResponseBody int addRepModel(StuReplyModel srm,String jsonStr) throws ParseException, JSONException{ 
		//����service�㣬��������ع�
		if(null != UserInfoUtil.getEmp()){
			srm.setTeacId(UserInfoUtil.getEmp().getId());
		}else{
			return 0;//����ʧ��
		}
		return srmService.addRepModel(srm,jsonStr);
	}
	
	/*
	 * �޸���Ŀģ��
	 */
	@RequestMapping(value = "repModel/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "��Ŀ���ģ���޸�")
	public @ResponseBody int updateRepModel(StuReplyModel srm) throws ParseException{  
		srm.setRemark(RemarkSet.getRemark("����"));
		srm.setUpdateTime(new Date());
		if(null!=srm){
			int count = srmService.updateByPJ(srm);
			return count;
		}
		return 0;
	}
	/*
	 * �޸���Ŀģ��
	 */
	@RequestMapping("repModel/changeStatus")
	@SystemControllerLog(description = "��Ŀ���ģ��״̬�޸�")
	public @ResponseBody int changeStatus(StuReplyModel srm) throws ParseException{  
		srm.setRemark(RemarkSet.getRemark("����״̬"));
		srm.setUpdateTime(new Date());
		srm.setStatus(2);//�Ѵ��
		if(null!=srm){
			int count = srmService.updateByPJ(srm);
			return count;
		}
		return 0;
	}
	/*
	 * ɾ����Ŀģ��
	 */
	@RequestMapping(value = "repModel/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "��Ŀ���ģ��ɾ��")
	public @ResponseBody int deleteRepModel(Model model,@PathVariable("id")Integer id){  
		int count = 0;
		//ͨ��ģ��idȥ����ɼ������ʹ���˸�ģ�飬��ɾ��ʧ��
		if(srsService.countBysrmId(id)>0){
			count = -1;
		}else{
			count = srmService.delByUpdate(id);
		}
		return count;
	}
	
	/*
	 * ��Ŀģ����ϸ����json����
	 */
	@RequestMapping("/repModelDJsonList")
	@SystemControllerLog(description = "������Ŀ���ģ����ϸ���json����")
	public @ResponseBody ResultMessage repModelDJsonList(int limit, int offset,StuReplyModelD srmd){
		ResultMessage rm = new ResultMessage();
		List<StuReplyModelD> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(null!=srmd){
			sList = srmdService.selectByPJ(srmd);
		}else{
			sList = srmdService.selectAll();
		}
		 // ȡ��ҳ��Ϣ
        PageInfo<StuReplyModelD> pageInfo = new PageInfo<StuReplyModelD>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * �����Ŀģ����ϸ��
	 */
	@RequestMapping("/addRepModelD")
	@SystemControllerLog(description = "��Ŀ���ģ����ϸ����")
	public @ResponseBody int addRepModelD(StuReplyModelD srmd) throws ParseException{ 
		//����ʱ��
		srmd.setCreateTime(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		//�����������
		srmd.setRemark(RemarkSet.getRemark("���"));
		if(null!=srmd){
			int count = srmdService.insertByPJ(srmd);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	/*
	 * �޸���Ŀģ����ϸ��
	 */
	@RequestMapping(value = "repModelD/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "��Ŀ���ģ����ϸ�޸�")
	public @ResponseBody int updateRepModelD(StuReplyModelD srmd) throws ParseException{  
		srmd.setRemark(RemarkSet.getRemark("����"));
		srmd.setUpdateTime(new Date());
		if(null!=srmd){
			int count = srmdService.updateByPJ(srmd);
			return count;
		}
		return 0;
	}
	/*
	 * ɾ����Ŀģ����ϸ��
	 */
	@RequestMapping(value = "repModelD/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "��Ŀ���ģ����ϸɾ��")
	public @ResponseBody int deleteRepModelD(Model model,@PathVariable("id")Integer id){ 
		int count = 0;
		//ͨ��srmdIdȥ��ѯ������ϸ����û�������id,������ɾ��
		if(srsdService.countBysrmdId(id)>0){
			count = -1;
		}else{
			count = srmdService.delByUpdate(id);
		}
		return count;
	}
	/*
	 * ͨ��ģ���id����ѯģ����ϸ��
	 */
	@RequestMapping(value = "repModelDList/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "ͨ��ģ���id����ģ����ϸ��json����")
	public @ResponseBody List<StuReplyModelD> getRepModelDList(Model model,@PathVariable("id")Integer id){
		List<StuReplyModelD> srmdList = new ArrayList<StuReplyModelD>();
		srmdList = srmdService.selectBysrmId(id);
		return srmdList;
	}
}
