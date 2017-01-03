package com.ht.serviceImpl.student;

import java.util.Date;
import java.util.List;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ht.controller.student.ClassManagementController;
import com.ht.mapper.education.EduTermMapper;
import com.ht.mapper.finance.FinanceShouldchargeMapper;
import com.ht.mapper.finance.TuitionsetMapper;
import com.ht.mapper.login.ShiroUserInfoMapper;
import com.ht.mapper.login.ShiroUserMapper;
import com.ht.mapper.login.ShiroUserRoleMapper;
import com.ht.mapper.market.MarketStudentMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.education.EduTerm;
import com.ht.popj.finance.FinanceShouldcharge;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.login.ShiroUserRole;
import com.ht.popj.market.MarketStudent;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.service.education.TermService;
import com.ht.service.finance.FinanceShouldchargeService;
import com.ht.service.finance.TuitionsetService;
import com.ht.service.login.ShiroUserInfoService;
import com.ht.service.login.ShiroUserRoleService;
import com.ht.service.login.ShiroUserService;
import com.ht.service.market.MarketStudentService;
import com.ht.service.student.ClassManagementService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.util.RandomGet;

import common.Logger;

public class ClassManagementServiceImpl implements ClassManagementService{
	Logger logger = Logger.getLogger(ClassManagementServiceImpl.class);
	//�༶
	@Autowired
	private StudentClassMapper studentClassMapper;
	//�г�ѧ��
	@Autowired
	MarketStudentMapper marketStudentMapper;
	@Autowired
	StudentMapper studentMapper;//ѧ����
	@Autowired
	ShiroUserMapper userMapper;//�û���
	@Autowired
	ShiroUserRoleMapper surMapper;//�û���ɫ��
	@Autowired
	ShiroUserInfoMapper suiMapper;//�û���Ϣ��
	
	//ѧ������
	@Autowired
	FinanceShouldchargeMapper shouldchargeMapper;
	
	@Autowired
	EduTermMapper termMapper;//ѧ�ڱ�
	@Autowired
	TuitionsetMapper tuitionsetMapper;//ѧ�ѱ�
	public int Studentclassallot(String ids, Integer classid) {
		int count=0;
		if(null==ids||"".equals(ids)){
			logger.info("û��ѡ��Ҫ�ְ��ѧ��");
			return count;
		}
		String[] array = ids.split(",");
		//ͨ���༶id��ȡ�༶�����Ϣ
		StudentClass cls = studentClassMapper.selectById(classid);
		
		//��ѯ�ý죬��רҵ����һѧ��ѧ��
		EduTerm term = new EduTerm();
		term.setFall_id(Integer.parseInt(cls.getLevelId()));
		term.setMajor_id((cls.getMajorId()));
		term.setTermName("��һѧ��");
		List<EduTerm> list = termMapper.selectByDynamic(term);
		
		for(int i=0;i<array.length;i++){
			//�жϰ༶�����Ƿ�����                /*����������ˣ��Ƿ��ع�������������*/
			if(cls.getCount()<=cls.getCountstu()){
				logger.error("�༶��������");
				count = -1;
				break;
			}
			String stid = array[i];
			//ͨ��ѧ�����id��ȡ��ʽ����ѧ������Ϣ
			MarketStudent ms = marketStudentMapper.selectByPrimaryKey(Integer.parseInt(stid));
			
			//����marketStudent���ѧ��״̬��3�ѷְࣩ
			ms.setMsStatus(3);
			marketStudentMapper.updateByPrimaryKeySelective(ms);
			//����ѧ��
			Student student = new Student();
			student.setStuname(ms.getName());
			student.setSex(ms.getSex());
			student.setAge(ms.getAge());
			student.setPhone(ms.getPhone());
			student.setAddr(ms.getAddr());
			student.setMiddleschool(ms.getSchool());
			student.setIntroduretech(ms.getRecruitEmp().getEmpname());//�Ƽ���ʦ
			student.setClassid(classid);//���ð༶
			//student.setProfessional(cls.get);//����Ժϵ
			student.setProlevel(cls.getMajorId());//����רҵ
			
			//String pwd = RandomGet.getSix();
			student.setPassword("123456");//ѧ������
			//ѧ�����
			String classno = cls.getClassno();
			int no = findBigCode(classno);
			String stuno = classno + String.format("%02d",no+1);
			student.setStuno(stuno);
			
			studentMapper.insertSelective(student);//����ѧ������
			//���û���������û� stuno pwd**************************
			//����û���
			ShiroUser user = new ShiroUser();
			user.setName(student.getStuno());//�ñ��������
			if(null!=student.getPhone()){
				user.setPhone(student.getPhone());
			}
			user.setPwd(Base64.encodeToString("123456".getBytes()));
			user.setUserType(2);//2��ѧ��
			int count2 =userMapper.insertSelective(user);
			//����û���Ϣ��
			ShiroUserInfo userInfo = new ShiroUserInfo();
			userInfo.setUserId(user.getId());
			userInfo.setStuId(student.getId());
			int count3 =suiMapper.insertSelective(userInfo);
			//���û����Ĭ�Ͻ�ɫ
			ShiroUserRole sur = new ShiroUserRole();
			sur.setUserId(user.getId());
			sur.setRoleId(2);//ѧ��
			int count4 = surMapper.insertSelective(sur);
			if(count2>0&&count3>0&&count4>0){
				count++;
			}else{
				logger.error("����ѧ���û��˺�ʧ��");
				count = 0;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				break;
			}
			//����������ѧ��***************************************
			FinanceShouldcharge fsc = new FinanceShouldcharge();
			fsc.setStuid(student.getId());
			fsc.setClassid(classid);
			
			
			//list��ѧ�ڵ�
			if(list!=null&&list.size()>0){
				fsc.setTermid(list.get(0).getId());//����ѧ��
				try {
					float money = tuitionsetMapper.selCountByTermId(list.get(0).getId());
					fsc.setMoney(money);//���ý��
				} catch (NullPointerException e) {
					logger.error("��δ���øý�ð��һѧ��ѧ��");
					count = -2;
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					break;
				}
			}else{
				logger.error("δ���øý�ð�ĵ�һѧ������");
				count = -3;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				break;
			}
			fsc.setFallid(Integer.parseInt(cls.getLevelId()));
			fsc.setCreateTime(new Date());
			fsc.setRemark("�ְ��Զ�������һѧ��Ӧ��ѧ�ѣ�ѧ����"+ms.getName());
			shouldchargeMapper.insert(fsc);
			logger.info("����ѧ�������Ϣ�ɹ���"+ms.getName());
		}
		return count;
	}
	//����һ���༶�����ѧ��
	public int findBigCode(String classno) {
		//���û�÷���ֵ(�༶��һ��ѧ�������ʱ)֮�䷵��01��
		String stuno = studentMapper.findBigCode(classno);
		int num = 0;
		if(null==stuno){
			logger.info("�����༶"+classno+"�ĵ�һ��ѧ��������0");
		}else{
			try {
				String no = stuno.substring(stuno.length()-2, stuno.length());
				num = Integer.parseInt(no);
				logger.info("�༶"+classno+"����ѧ��������"+no);
			} catch (Exception e) {
				logger.error("�ַ�����ȡʧ�ܣ�ѧ�����="+stuno);
			}
		}
		return num;
	}

}
