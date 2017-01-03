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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.flow.FlowAllMapper;
import com.ht.mapper.flow.FlowsModelMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentFallMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowStudentApply;
import com.ht.popj.flow.FlowStudentApplyDetail;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.flow.FlowModeltypeService;
import com.ht.service.flow.FlowStudentApplyDetailService;
import com.ht.service.flow.FlowStudentApplyService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/flowStudApply")
public class FlowStudApplyController {
	
	@Autowired
	FlowStudentApplyService applyService;
	@Autowired
	FlowModeltypeService modelTypeService;
	@Autowired
	StudentClassMapper classMapper;
	@Autowired
	StudentFallMapper fallMapper;
	@Autowired
	FlowAllMapper allMapper;
	@Autowired
	FlowsModelMapper modelMapper;
	@Autowired
	FlowStudentApplyDetailService applyDetailService;
	@Autowired
	StudentMapper studentMapper;

	@RequestMapping("/flowStudApply")
	@SystemControllerLog(description = "����ѧ����������ҳ��")
	public String flowStudApply(Model model) throws Exception{
		List<FlowsModeltype> modeltypes = modelTypeService.selectModelTypeByModelSelId("xstb");
		model.addAttribute("modeltypes", modeltypes);
		List<StudentFall> falls = fallMapper.selectFallAllList();
		model.addAttribute("falls", falls);
		return "flow/flowStudApply";
	}
	
	@RequestMapping("/findClassListByFallid")
	@SystemControllerLog(description = "������ѡ�������а༶json����")
	public @ResponseBody ResultMessage findClassListByFallid(Model model, StudentFall fall) {
		ResultMessage rm = new ResultMessage();
		List<StudentClass> classes = new ArrayList<>();
		if (fall.getId() == null) {
			rm.setTotal(1);
			rm.setRows(classes);
			return rm;
		}
		int fallid = fall.getId();
		classes = classMapper.selectClassListByFallid(fallid);
		rm.setTotal(classes.size());
		rm.setRows(classes);
		return rm;
	}
	
	@RequestMapping("/saveFlowStudentApply")
	@SystemControllerLog(description = "����������뵥Ϊ�ݸ�ģʽ")
	public @ResponseBody int saveFlowStudentApply(FlowStudentApply studentApply){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null != studentApply){
			studentApply.setStudid(student.getId());
			studentApply.setClassid1(student.getClassid());
			studentApply.setUpset(0);
			int count = applyService.insertSelective(studentApply);
			return count;
		}
		return 0;
	}
	
	//�޸����뵥״̬
	@RequestMapping(value = "/updateStudentApplyUpset", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�ύδ�ύ�����뵥")
	public @ResponseBody int updateStudentApplyUpset(FlowStudentApply studentApply) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null!=studentApply){
			studentApply.setUpset(1);
			int count = applyService.updateByPrimaryKeySelective(studentApply);//��δ�ύ״̬��Ϊ���ύ״̬
			
			studentApply = applyService.selectByPrimaryKey(studentApply.getId());//��ȡ��ǰ���뵥��������Ϣ
			
			addStudentApplyHetial(studentApply.getClassid1(),studentApply.getClassid2(),studentApply.getId(),studentApply.getFlowmodeltypeid());//�������������ڵ�ķ���
			
			StudentClass studentClass = classMapper.selectById(student.getClassid());
			StudentClass studentClass2 = classMapper.selectById(studentApply.getClassid2());
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(studentApply.getFlowmodeltypeid());
			all.setStudid(studentApply.getStudid());
			all.setStatus(0);
			all.setRemark(studentClass.getClassname()+"���"+student.getStuname()+"ͬѧ"
					+"���������"+studentClass2.getClassname()+"����Ϊ"+studentApply.getRemark());
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value = "/deleteStudentApplyById", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "ɾ����ٲݸ�")
	public @ResponseBody int deleteStudentApplyById(FlowStudentApply studentApply) throws Exception{
		if(null!=studentApply){
			int count = applyService.deleteByPrimaryKey(studentApply.getId());
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/addFlowStudentApply")
	@SystemControllerLog(description = "�ύ�������뵥")
	public @ResponseBody int addFlowStudentApply(FlowStudentApply studentApply){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null != studentApply){
			studentApply.setStudid(student.getId());
			studentApply.setClassid1(student.getClassid());
			studentApply.setUpset(1);
			int count = applyService.insertSelective(studentApply);
			addStudentApplyHetial(studentApply.getClassid1(),studentApply.getClassid2(),count,studentApply.getFlowmodeltypeid());
			
			StudentClass studentClass = classMapper.selectById(student.getClassid());
			StudentClass studentClass2 = classMapper.selectById(studentApply.getClassid2());
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(studentApply.getFlowmodeltypeid());
			all.setStudid(studentApply.getStudid());
			all.setStatus(0);
			all.setRemark(studentClass.getClassname()+"���"+student.getStuname()+"ͬѧ"
					+"���������"+studentClass2.getClassname()+"����Ϊ"+studentApply.getRemark());
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/lookStudentApplyDetial")
	@SystemControllerLog(description = "�鿴��������")
	public @ResponseBody ResultMessage lookStudentApplyDetial(FlowStudentApply studentApply){
		ResultMessage rm = new ResultMessage();
		List<FlowStudentApplyDetail> list = new ArrayList<>();
		if (null != studentApply) {
			list = applyDetailService.selectByPrimaryKey(studentApply.getId());
		}
		rm.setRows(list);
		rm.setTotal(list.size());
		return rm;
	}
	
	private void addStudentApplyHetial(int classid1,int classid2,int count,int flowModelTypeid){
		StudentClass studentClass = classMapper.selectByPrimaryKey(classid1);//��ȡд����ѧ���İ༶��Ϣ
		StudentClass studentClass2 = classMapper.selectByPrimaryKey(classid2);//��ȡд����ѧ���İ༶��Ϣ
		FlowsModel flowsModel = new FlowsModel();
		flowsModel.setFlowmodeltypeid(flowModelTypeid);
		List<FlowsModel> flowsModels = modelMapper.selectSelective(flowsModel);//��ȡģ�������ڵ��е�����
		int a=0;
		int b=0;
		//�������뵥�������ڵ�
		for (int i = 0; i < flowsModels.size(); i++) {
			FlowStudentApplyDetail applyDetail = new FlowStudentApplyDetail();
			applyDetail.setStudapplyid(count);
			applyDetail.setFlowmodelid(flowsModels.get(i).getId());
			applyDetail.setStatus(0);//������
			if(flowsModels.get(i).getRoleid().equals("kong")){
				applyDetail.setEmpid("");
				applyDetail.setStatus(1);//ͨ��
			}else if(flowsModels.get(i).getRoleid().equals("rkls")){
				if(a>0){
					applyDetail.setEmpid(studentClass2.getTeacherId());
				}else{
					applyDetail.setEmpid(studentClass.getTeacherId());
					a++;
				}
			}else if(flowsModels.get(i).getRoleid().equals("bzr")){
				if(b>0){
					applyDetail.setEmpid(studentClass2.getClteacherId());
				}else{
					applyDetail.setEmpid(studentClass.getClteacherId());
					b++;
				}
			}else {
				applyDetail.setEmpid(flowsModels.get(i).getEmpid());
			}
			applyDetailService.insertSelective(applyDetail);
		}
		
		updateApplyDetial(count);
	}
	
	//�޸������ڵ�״̬
	private void updateApplyDetial(int count){
		//�޸�
		List<FlowStudentApplyDetail> applyDetails = applyDetailService.selectAll(count);
		if(applyDetails.size()>0){
			FlowStudentApplyDetail applyDetail = applyDetails.get(0);
			applyDetail.setStatus(2);//������
			applyDetailService.updateByPrimaryKeySelective(applyDetail);
		}else{
			FlowStudentApply flowStudentApply = new FlowStudentApply();
			flowStudentApply.setId(count);
			flowStudentApply.setUpset(3);//����ͨ��
			applyService.updateByPrimaryKeySelective(flowStudentApply);
			
			flowStudentApply = applyService.selectByPrimaryKey(count);
			
			//�޸��������ж�Ӧ������״̬Ϊ��ͨ��
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(flowStudentApply.getFlowmodeltypeid());
			all.setStatus(1);
			allMapper.updateFlowAllStatus(all);
			
			//�޸�ѧ����Ϣ
			Student record = new Student();
			record.setId(flowStudentApply.getStudid());
			record.setClassid(flowStudentApply.getClassid2());
			studentMapper.updateByPrimaryKeySelective(record);
		}
	}
	
	@RequestMapping("/studentApplyYes")
	@SystemControllerLog(description = "ͬ��ѧ�����")
	public @ResponseBody int studentApplyYes(FlowStudentApplyDetail studentApplyDetail) throws Exception{ 
		if(null!=studentApplyDetail){
			studentApplyDetail.setStatus(1);//����ͨ��
			int count = applyDetailService.updateByPrimaryKeySelective(studentApplyDetail);
			updateApplyDetial(studentApplyDetail.getStudapplyid());
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	@RequestMapping("/flowStudentApplyNo")
	@SystemControllerLog(description = "��ͬ��ѧ�����")
	public @ResponseBody int flowStudentApplyNo(FlowStudentApplyDetail studentApplyDetail) throws Exception{ 
		if(null!=studentApplyDetail){
			studentApplyDetail.setStatus(3);//��ͬ������
			int count = applyDetailService.updateByPrimaryKeySelective(studentApplyDetail);
			
			//�޸����뵥��״̬
			FlowStudentApply studentApply = new FlowStudentApply();
			studentApply.setId(studentApplyDetail.getStudapplyid());
			studentApply.setUpset(2);//����ʧ��,���뵥��Ч
			applyService.updateByPrimaryKeySelective(studentApply);
			
			studentApply = applyService.selectByPrimaryKey(studentApplyDetail.getStudapplyid());
			
			//�޸��������ж�Ӧ������״̬Ϊδͨ��
			FlowAll all = new FlowAll();
			all.setApplyid(studentApply.getId());
			all.setFlowmodeltypeid(studentApply.getFlowmodeltypeid());
			all.setStatus(2);
			allMapper.updateFlowAllStatus(all);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
}
