package com.ht.controller.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.popj.dailyWork.Baoxiao;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.DutyMaxTemp;
import com.ht.popj.dailyWork.Dutymodel;
import com.ht.popj.dailyWork.Dutymodeldetail;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Notice;
import com.ht.popj.education.EduFeedback;
import com.ht.popj.education.EduFeedbackStart;
import com.ht.popj.education.EduSeminar;
import com.ht.popj.education.EduSeminarDatail;
import com.ht.popj.finance.FinaceFeedbackset;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.service.dailyWork.ApplyMaterialService;
import com.ht.service.dailyWork.BaoxiaoService;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.DutyModelDService;
import com.ht.service.dailyWork.DutymodelService;
import com.ht.service.dailyWork.HolidayService;
import com.ht.service.dailyWork.NoticeService;
import com.ht.service.dailyWork.ReceiveMaterialService;
import com.ht.service.dailyWork.ReturnMaterialService;
import com.ht.service.education.FeedBackService;
import com.ht.service.education.FeedBackStartService;
import com.ht.service.education.SeminarDatailService;
import com.ht.service.education.SeminarService;
import com.ht.service.finance.FinanceFeedbacksetService;
import com.ht.service.student.AdjustClassService;
import com.ht.service.student.StuHolidayService;
import com.ht.service.student.StudentComputerService;
import com.ht.service.student.StudentFeedBackService;
import com.ht.util.DateUtil;
import com.ht.util.ResultMessage;

@Controller
public class IndexController {

	@Autowired
	SeminarService seminarService;

	@Autowired
	SeminarDatailService seminarDetailService;

	@Autowired
	FeedBackStartService feedBackStartService;

	@Autowired
	FeedBackService feedBackService;

	@Autowired
	FinanceFeedbacksetService financefeedbacksetService;

	@Autowired
	BaoxiaoService baoxiaoservice;

	@Autowired
	NoticeService noticeService;

	@Autowired
	DepService depservice;
	
	@Autowired
	ApplyMaterialService appmaterialservice;

	@Autowired
	HolidayService hservice;

	@Autowired
	ReceiveMaterialService rematerialservice;

	@Autowired
	ReturnMaterialService rms;

	@Autowired
	StudentComputerService studentComputerService;

	// ѧ�����service
	@Autowired
	StuHolidayService stuHolidayService;
	// ѧ���ķְ�����
	@Autowired
	AdjustClassService adjustClassService;
	@Autowired
	StudentFeedBackService studentFeedBackService;
	
	//ֵ��
	@Autowired
	private DutymodelService dutymodelService;
	@Autowired
	private DutyModelDService dutyModelDService;
	
	public static String SESSION_ID = "";
	@RequestMapping("/index2")
	public String index(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null == userInfo) {
			return "redirect:login";
		}
		Student student = null;
		Emp emp = null;
		// 3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
		}
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		if (null == student && null == emp) {
			return "redirect:login";
		}

		getNotification(request);
		getFeedBack(request);

		return "index_shiro";
	}
	@RequestMapping("/index")
	public String index2(HttpServletRequest request,Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null == userInfo) {
			return "redirect:login";
		}
		Student student = null;
		Emp emp = null;
		// 3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
		}
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		if (null == student && null == emp) {
			return "redirect:login";
		}
		
		model.addAttribute("student", student);
		model.addAttribute("emp", emp);

		getNotification(request);
		getFeedBack(request);

		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		SESSION_ID = (String) SecurityUtils.getSubject().getSession().getId();
		System.out.println(SESSION_ID);
		return "login";
	}

	@RequestMapping("/start")
	public String start(Model model) {
		//------------------------ֵ��
		int modelid = dutymodelService.selectIdIsUsing();//��ѯ��ǰ�����õ�ģ��id
		//��ѯ��ģ�壬ĳ��ֵ�������ͣ�����ж��ٸ�
		List<DutyMaxTemp> dmtList = dutyModelDService.selectDutyMaxBymodelId(modelid);
		List<Dutymodeldetail> modelDList = dutyModelDService.selectByDutymodelId(modelid);//��ѯģ����ϸ
		List<String> weksList = dutyModelDService.selectWeksBymodelId(modelid);//�����ж����ܼ�
		model.addAttribute("dmtList", dmtList);
		model.addAttribute("modelDList", modelDList);
		model.addAttribute("weksList", weksList);
		model.addAttribute("weekDays", DateUtil.getWeekOfDate(new Date()));//�������ܼ�
		//---------------------------֪ͨ
		List<Notice> noticelist = new ArrayList<>();
		noticelist = noticeService.selectBynoticetime();
		for (int i = 0; i < noticelist.size(); i++) {
			if (i == 0) {
				Notice notice1 = noticeService.selectById(noticelist.get(i).getId());
				model.addAttribute("notice1", notice1);
			} else if (i == 1) {
				Notice notice2 = noticeService.selectById(noticelist.get(i).getId());
				model.addAttribute("notice2", notice2);
			} else if (i == 2) {
				Notice notice3 = noticeService.selectById(noticelist.get(i).getId());
				model.addAttribute("notice3", notice3);
			}
		}
		//��ǰʹ�õ�ֵ��ģ��
		Dutymodel dutyModel = dutymodelService.selectIsUsing();
		model.addAttribute("dutyModel", dutyModel);
		
		
		Dutymodeldetail temp = new Dutymodeldetail();
		temp.setWeekends(DateUtil.getWeekOfDate(new Date()));
		temp.setModelid(modelid);
		//ֵ����ʦlist
		temp.setEmpType(1);
		List<Dutymodeldetail> teacList = dutyModelDService.selectByPJ(temp);
		model.addAttribute("teacList", teacList);
		//ֵ�������list
		temp.setEmpType(2);
		List<Dutymodeldetail> clteacList = dutyModelDService.selectByPJ(temp);
		model.addAttribute("clteacList", clteacList);
		//��ֵ��list
		temp.setEmpType(3);
		List<Dutymodeldetail> leaderList = dutyModelDService.selectByPJ(temp);
		model.addAttribute("leaderList", leaderList);
		// ��ҳ��Ĭ��ҳ
		return "start";
	}
	@RequestMapping("/first")
	public String first(Model model) {
		// ȡsessionʵ��
		// 1.��ȡsession����
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		Emp emp = null;
		List<Baoxiao> tasklist = new ArrayList<Baoxiao>();
		Integer count = 0;
		// 3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
		}
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
//			tasklist = baoxiaoservice.selectBaoxiaoTask(emp);
//			count = appmaterialservice.selectTask(emp);
			model.addAttribute("applymaterialcount", count);
//			count = hservice.selectTask(emp);
			model.addAttribute("applyholidaycount", count);
//			count = hservice.selectOverTask(emp);
			model.addAttribute("applyovercount", count);
//			count = hservice.selectChangeTask(emp);
			model.addAttribute("applychangecount", count);
//			count = rematerialservice.selectTask(emp);
			model.addAttribute("rematerialcount", count);
//			count = rms.selectTask(emp);
			model.addAttribute("returnmaterialcount", count);
//			count = studentComputerService.selectNewTask(emp);
			model.addAttribute("newcomputercount", count);
//			count = studentComputerService.selectRepairTask(emp);
			model.addAttribute("repaircount", count);
//			count = studentComputerService.selectReviceTask(emp);
			model.addAttribute("revicecount", count);
//			count = studentComputerService.selectReturnTask(emp);
			model.addAttribute("returncount", count);
			if(emp.getEmpname().equals("�ϳ�")){
				count = studentFeedBackService.selectCount();
				model.addAttribute("FeedBackcount", count);
			}else{
				count = 0;
				model.addAttribute("FeedBackcount", count);
			}
			// ����Ҫ����� ѧ����ٵĸ��� �� �ְ�����ĸ���
			Dep dep=depservice.selectByPrimaryKey(emp.getDepid());
			emp.setDep(dep);
			setCount(model, emp);

		}
//		if (null != tasklist && tasklist.size() > 0) {
//			model.addAttribute("baoxiaotaskcount", tasklist.size());
//		} else {
			model.addAttribute("baoxiaotaskcount", 0);
//		}
		// ���ñ���
		model.addAttribute("student", student);
		model.addAttribute("emp", emp);

//		appmaterialservice.selectTask(emp);

		List<Notice> noticelist = new ArrayList<>();
		noticelist = noticeService.selectBynoticetime();
		for (int i = 0; i < noticelist.size(); i++) {
			if (i == 0) {
				Notice notice1 = noticeService.selectById(noticelist.get(i).getId());
				model.addAttribute("notice1", notice1);
			} else if (i == 1) {
				Notice notice2 = noticeService.selectById(noticelist.get(i).getId());
				model.addAttribute("notice2", notice2);
			} else if (i == 2) {
				Notice notice3 = noticeService.selectById(noticelist.get(i).getId());
				model.addAttribute("notice3", notice3);
			}
		}
		// ��ҳ��Ĭ��ҳ
		return "home";
	}

	@RequestMapping("/toLogin")
	public String toLogin(String username, String password, String checkcode, HttpServletRequest request) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// token.setRememberMe(true);
		// System.out.println("Ϊ����֤��¼�û�����װ��tokenΪ" +
		// ReflectionToStringBuilder.toString(token,
		// ToStringStyle.MULTI_LINE_STYLE));
		// ��ȡ��ǰ��Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// �ڵ�����login������,SecurityManager���յ�AuthenticationToken,�����䷢�͸������õ�Realmִ�б������֤���
			// ÿ��Realm�����ڱ�Ҫʱ���ύ��AuthenticationTokens������Ӧ
			// ������һ���ڵ���login(token)����ʱ,�����ߵ�MyRealm.doGetAuthenticationInfo()������,������֤��ʽ����˷���
			System.out.println("���û�[" + username + "]���е�¼��֤..��֤��ʼ");
			currentUser.login(token);
			System.out.println("���û�[" + username + "]���е�¼��֤..��֤ͨ��");
		} catch (UnknownAccountException uae) {
			System.out.println("���û�[" + username + "]���е�¼��֤..��֤δͨ��,δ֪�˻�");
			request.setAttribute("message_login", "δ֪�˻�");
		} catch (IncorrectCredentialsException ice) {
			System.out.println("���û�[" + username + "]���е�¼��֤..��֤δͨ��,�����ƾ֤");
			request.setAttribute("message_login", "���벻��ȷ");
		} catch (LockedAccountException lae) {
			System.out.println("���û�[" + username + "]���е�¼��֤..��֤δͨ��,�˻�������");
			request.setAttribute("message_login", "�˻�������");
		} catch (ExcessiveAttemptsException eae) {
			System.out.println("���û�[" + username + "]���е�¼��֤..��֤δͨ��,�����������");
			request.setAttribute("message_login", "�û�������������������");
		} catch (AuthenticationException ae) {
			// ͨ������Shiro������ʱAuthenticationException�Ϳ��Կ����û���¼ʧ�ܻ��������ʱ���龰
			System.out.println("���û�[" + username + "]���е�¼��֤..��֤δͨ��,��ջ�켣����");
			ae.printStackTrace();
			request.setAttribute("message_login", "�û��������벻��ȷ");
		}
		String str = (String) request.getSession().getAttribute("rand");
		// ��֤�Ƿ��¼�ɹ�
		if (currentUser.isAuthenticated()) {
			if(checkcode.equals(str)){
				//����session��ʱʱ�䣨Ĭ��30���ӣ�
				//SecurityUtils.getSubject().getSession().setTimeout(20000);  
				System.out.println("�û�[" + username + "]��¼��֤ͨ��(������Խ���һЩ��֤ͨ�����һЩϵͳ������ʼ������)");
				//return "redirect:index";
				//return org.apache.shiro.web.filter.authc.AuthenticationFilter.DEFAULT_SUCCESS_URL;
				String url = "redirect:index";
				if(null!=WebUtils.getSavedRequest(request)&&null!=WebUtils.getSavedRequest(request).getRequestUrl()){
					url = WebUtils.getSavedRequest(request).getRequestUrl();
					url = "redirect:"+url.substring(9, url.length());
				}
				return url;
			}
			if(!checkcode.equals(str)){
				token.clear();
				request.setAttribute("message_login", "��֤������");
				return "login";
			}
		} else {
			token.clear();
			return "login";
		}
		return "login";
	}

	@RequestMapping("/loginOut")
	public String loginOut() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:login";
	}

	private void getNotification(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if (null != userInfo.getEmp()) {
			// �����ǰ��¼����Ա������鿴�¿��л���֪ͨ
			emp = userInfo.getEmp();
			String nowdate = new Date().toLocaleString();
			EduSeminar seminar = new EduSeminar();
			seminar.setStartTime(nowdate);
			List<EduSeminar> list = new ArrayList<EduSeminar>();
			list = seminarService.getSomeSeminar(seminar);
			List ids = new ArrayList();
			if (list.size() > 0) {
				for (EduSeminar l : list) {
					ids.add(l.getId());
				}
			}
			EduSeminarDatail datail = new EduSeminarDatail();
			datail.setIds(ids);
			List<EduSeminarDatail> detailList = seminarDetailService.getSomeSiminarDetail(datail);
			List seminarids = new ArrayList();
			if (detailList.size() > 0) {
				for (EduSeminarDatail eduSeminarDatail : detailList) {
					String empIds = eduSeminarDatail.getEmpId();
					String arr[] = empIds.split(",");
					for (int i = 0; i < arr.length; i++) {
						if (emp.getId().equals(arr[i])) {
							seminarids.add(eduSeminarDatail.getSeminarId());
						}
					}
				}
			}
			if (seminarids.size() == 0 && seminarids.isEmpty()) {
				list.clear();
				request.setAttribute("list", list);
			} else {
				seminar.setSeminarIds(seminarids);
				list.clear();
				list = seminarService.getSomeSeminar(seminar);
				request.setAttribute("list", list);
			}

		}
	}

	private void getFeedBack(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = new Student();
		if (null != userInfo.getStudent()) {
			student = userInfo.getStudent();
			String nowdate = new Date().toLocaleString();
			EduFeedbackStart start = new EduFeedbackStart();
			start.setEndTime(nowdate);
			List<EduFeedbackStart> blist = feedBackStartService.getSomeFeedBackStart(start);
			List<Integer> ids = new ArrayList();
			int classId = 0;
			if (blist.size() > 0) {
				for (EduFeedbackStart eduFeedbackStart : blist) {
					ids.add(eduFeedbackStart.getClassId());
				}
			}
			if (ids.size() > 0) {
				for (Integer i : ids) {
					if (i == student.getClassid()) {
						classId = i;
					}
				}
			}
			if (classId != 0) {
				start.setClassId(classId);
				EduFeedback back = new EduFeedback();
				back.setStuId(student.getId());
				List<EduFeedback> backList = feedBackService.getSomeFeedBack(back);
				if (backList.size() == 0) {
					blist.clear();
					blist = feedBackStartService.getSomeFeedBackStart(start);
					request.setAttribute("blist", blist);
					request.setAttribute("student", student);
				} else {
					// ��ʱ���ܷ����˶�������֪ͨ������ֻ����������һ�����������Է���ʱ��С�ڷ�����ʼʱ�����϶�Ϊδ���з���
					// ��Ϊslist�����ж����������Ļ����ܻ��ʱ����С�ĸ�����ʼʱ�䣬��������ִ��󣬵��·��������ٴη�����û�����ķ�������
					// ����ÿ��ֻ����һ��
					String feedbacktime = backList.get(0).getFeedbackTime();// ���ݿⰴ��ʱ��������ǰ�棬������ȡʱ�����ķ�����ɺ���ȡ�κ��
					start.setEndTime(null);
					start.setStartTime(feedbacktime);
					// System.out.println(start);
					blist.clear();
					blist = feedBackStartService.getSomeFeedBackStart(start);
					request.setAttribute("blist", blist);
					request.setAttribute("student", student);
				}
			}
		}
	}

	@RequestMapping(value = "/getAllTemplate", method = { RequestMethod.PUT })
	public @ResponseBody ResultMessage getAllTemplate() {
		ResultMessage rm = new ResultMessage();
		List<FinaceFeedbackset> set = financefeedbacksetService.selectFinanceFeedbacksetAll();
		rm.setTotal(set.size());
		rm.setRows(set);
		return rm;
	}

	// ����Ҫ����� ѧ����ٵĸ��� �� �ְ�����ĸ���
	private void setCount(Model model, Emp emp) {
		// ѧ����ٴ������
		int stuHoliCount = 0;
		// �ְ��������
		int adjustClsCount = 0;
		try {
			stuHoliCount = stuHolidayService.selectByEmpIdNodel(emp.getId()).size();
			adjustClsCount = adjustClassService.selectbyTeacIdNodel(emp.getId()).size();
			adjustClsCount += adjustClassService.selectbyTeacIdNodel2(emp.getId()).size();
		} catch (Exception e) {

		}
		model.addAttribute("stuHoliCount", stuHoliCount);
		model.addAttribute("adjustClsCount", adjustClsCount);
	}

}
