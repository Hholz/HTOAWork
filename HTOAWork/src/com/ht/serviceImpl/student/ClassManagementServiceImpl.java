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
	//班级
	@Autowired
	private StudentClassMapper studentClassMapper;
	//市场学生
	@Autowired
	MarketStudentMapper marketStudentMapper;
	@Autowired
	StudentMapper studentMapper;//学生表
	@Autowired
	ShiroUserMapper userMapper;//用户表
	@Autowired
	ShiroUserRoleMapper surMapper;//用户角色表
	@Autowired
	ShiroUserInfoMapper suiMapper;//用户信息表
	
	//学费设置
	@Autowired
	FinanceShouldchargeMapper shouldchargeMapper;
	
	@Autowired
	EduTermMapper termMapper;//学期表
	@Autowired
	TuitionsetMapper tuitionsetMapper;//学费表
	public int Studentclassallot(String ids, Integer classid) {
		int count=0;
		if(null==ids||"".equals(ids)){
			logger.info("没用选择要分班的学生");
			return count;
		}
		String[] array = ids.split(",");
		//通过班级id获取班级相关信息
		StudentClass cls = studentClassMapper.selectById(classid);
		
		//查询该届，该专业，第一学期学费
		EduTerm term = new EduTerm();
		term.setFall_id(Integer.parseInt(cls.getLevelId()));
		term.setMajor_id((cls.getMajorId()));
		term.setTermName("第一学期");
		List<EduTerm> list = termMapper.selectByDynamic(term);
		
		for(int i=0;i<array.length;i++){
			//判断班级人数是否满了                /*如果增着满了，是否会回滚？？？？？？*/
			if(cls.getCount()<=cls.getCountstu()){
				logger.error("班级人数满了");
				count = -1;
				break;
			}
			String stid = array[i];
			//通过学生表的id获取正式报名学生的信息
			MarketStudent ms = marketStudentMapper.selectByPrimaryKey(Integer.parseInt(stid));
			
			//更新marketStudent表的学生状态（3已分班）
			ms.setMsStatus(3);
			marketStudentMapper.updateByPrimaryKeySelective(ms);
			//新增学生
			Student student = new Student();
			student.setStuname(ms.getName());
			student.setSex(ms.getSex());
			student.setAge(ms.getAge());
			student.setPhone(ms.getPhone());
			student.setAddr(ms.getAddr());
			student.setMiddleschool(ms.getSchool());
			student.setIntroduretech(ms.getRecruitEmp().getEmpname());//推荐老师
			student.setClassid(classid);//设置班级
			//student.setProfessional(cls.get);//设置院系
			student.setProlevel(cls.getMajorId());//设置专业
			
			//String pwd = RandomGet.getSix();
			student.setPassword("123456");//学生密码
			//学生编号
			String classno = cls.getClassno();
			int no = findBigCode(classno);
			String stuno = classno + String.format("%02d",no+1);
			student.setStuno(stuno);
			
			studentMapper.insertSelective(student);//新增学生数据
			//在用户表里添加用户 stuno pwd**************************
			//添加用户表
			ShiroUser user = new ShiroUser();
			user.setName(student.getStuno());//用编号做名字
			if(null!=student.getPhone()){
				user.setPhone(student.getPhone());
			}
			user.setPwd(Base64.encodeToString("123456".getBytes()));
			user.setUserType(2);//2是学生
			int count2 =userMapper.insertSelective(user);
			//添加用户信息表
			ShiroUserInfo userInfo = new ShiroUserInfo();
			userInfo.setUserId(user.getId());
			userInfo.setStuId(student.getId());
			int count3 =suiMapper.insertSelective(userInfo);
			//给用户添加默认角色
			ShiroUserRole sur = new ShiroUserRole();
			sur.setUserId(user.getId());
			sur.setRoleId(2);//学生
			int count4 = surMapper.insertSelective(sur);
			if(count2>0&&count3>0&&count4>0){
				count++;
			}else{
				logger.error("新增学生用户账号失败");
				count = 0;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				break;
			}
			//新增待缴纳学费***************************************
			FinanceShouldcharge fsc = new FinanceShouldcharge();
			fsc.setStuid(student.getId());
			fsc.setClassid(classid);
			
			
			//list是学期的
			if(list!=null&&list.size()>0){
				fsc.setTermid(list.get(0).getId());//设置学期
				try {
					float money = tuitionsetMapper.selCountByTermId(list.get(0).getId());
					fsc.setMoney(money);//设置金额
				} catch (NullPointerException e) {
					logger.error("还未设置该届该班第一学期学费");
					count = -2;
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					break;
				}
			}else{
				logger.error("未设置该届该班的第一学期数据");
				count = -3;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				break;
			}
			fsc.setFallid(Integer.parseInt(cls.getLevelId()));
			fsc.setCreateTime(new Date());
			fsc.setRemark("分班自动新增第一学期应缴学费，学生："+ms.getName());
			shouldchargeMapper.insert(fsc);
			logger.info("新增学生相关信息成功："+ms.getName());
		}
		return count;
	}
	//返回一个班级的最大学号
	public int findBigCode(String classno) {
		//如果没用返回值(班级第一个学生的情况时)之间返回01，
		String stuno = studentMapper.findBigCode(classno);
		int num = 0;
		if(null==stuno){
			logger.info("新增班级"+classno+"的第一个学生，返回0");
		}else{
			try {
				String no = stuno.substring(stuno.length()-2, stuno.length());
				num = Integer.parseInt(no);
				logger.info("班级"+classno+"新增学生，返回"+no);
			} catch (Exception e) {
				logger.error("字符串截取失败，学生编号="+stuno);
			}
		}
		return num;
	}

}
