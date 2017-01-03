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

	// 学生请假service
	@Autowired
	StuHolidayService stuHolidayService;
	// 学生的分班申请
	@Autowired
	AdjustClassService adjustClassService;
	@Autowired
	StudentFeedBackService studentFeedBackService;
	
	//值班
	@Autowired
	private DutymodelService dutymodelService;
	@Autowired
	private DutyModelDService dutyModelDService;
	
	public static String SESSION_ID = "";
	@RequestMapping("/index2")
	public String index(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null == userInfo) {
			return "redirect:login";
		}
		Student student = null;
		Emp emp = null;
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
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
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (null == userInfo) {
			return "redirect:login";
		}
		Student student = null;
		Emp emp = null;
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
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
		//------------------------值班
		int modelid = dutymodelService.selectIdIsUsing();//查询当前正在用的模板id
		//查询该模板，某个值班人类型，最大有多少个
		List<DutyMaxTemp> dmtList = dutyModelDService.selectDutyMaxBymodelId(modelid);
		List<Dutymodeldetail> modelDList = dutyModelDService.selectByDutymodelId(modelid);//查询模板明细
		List<String> weksList = dutyModelDService.selectWeksBymodelId(modelid);//用来判断有周几
		model.addAttribute("dmtList", dmtList);
		model.addAttribute("modelDList", modelDList);
		model.addAttribute("weksList", weksList);
		model.addAttribute("weekDays", DateUtil.getWeekOfDate(new Date()));//今天是周几
		//---------------------------通知
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
		//当前使用的值班模板
		Dutymodel dutyModel = dutymodelService.selectIsUsing();
		model.addAttribute("dutyModel", dutyModel);
		
		
		Dutymodeldetail temp = new Dutymodeldetail();
		temp.setWeekends(DateUtil.getWeekOfDate(new Date()));
		temp.setModelid(modelid);
		//值班老师list
		temp.setEmpType(1);
		List<Dutymodeldetail> teacList = dutyModelDService.selectByPJ(temp);
		model.addAttribute("teacList", teacList);
		//值班班主任list
		temp.setEmpType(2);
		List<Dutymodeldetail> clteacList = dutyModelDService.selectByPJ(temp);
		model.addAttribute("clteacList", clteacList);
		//总值班list
		temp.setEmpType(3);
		List<Dutymodeldetail> leaderList = dutyModelDService.selectByPJ(temp);
		model.addAttribute("leaderList", leaderList);
		// 首页的默认页
		return "start";
	}
	@RequestMapping("/first")
	public String first(Model model) {
		// 取session实例
		// 1.获取session对象
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		Emp emp = null;
		List<Baoxiao> tasklist = new ArrayList<Baoxiao>();
		Integer count = 0;
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
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
			if(emp.getEmpname().equals("老陈")){
				count = studentFeedBackService.selectCount();
				model.addAttribute("FeedBackcount", count);
			}else{
				count = 0;
				model.addAttribute("FeedBackcount", count);
			}
			// 设置要处理的 学生请假的个数 和 分班申请的个数
			Dep dep=depservice.selectByPrimaryKey(emp.getDepid());
			emp.setDep(dep);
			setCount(model, emp);

		}
//		if (null != tasklist && tasklist.size() > 0) {
//			model.addAttribute("baoxiaotaskcount", tasklist.size());
//		} else {
			model.addAttribute("baoxiaotaskcount", 0);
//		}
		// 设置变量
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
		// 首页的默认页
		return "home";
	}

	@RequestMapping("/toLogin")
	public String toLogin(String username, String password, String checkcode, HttpServletRequest request) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// token.setRememberMe(true);
		// System.out.println("为了验证登录用户而封装的token为" +
		// ReflectionToStringBuilder.toString(token,
		// ToStringStyle.MULTI_LINE_STYLE));
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			System.out.println("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			System.out.println("对用户[" + username + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			request.setAttribute("message_login", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			request.setAttribute("message_login", "密码不正确");
		} catch (LockedAccountException lae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			request.setAttribute("message_login", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			request.setAttribute("message_login", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			request.setAttribute("message_login", "用户名或密码不正确");
		}
		String str = (String) request.getSession().getAttribute("rand");
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			if(checkcode.equals(str)){
				//设置session超时时间（默认30分钟）
				//SecurityUtils.getSubject().getSession().setTimeout(20000);  
				System.out.println("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
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
				request.setAttribute("message_login", "验证码有误");
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
			// 如果当前登录的是员工，则查看下可有会议通知
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
					// 此时可能发起了多条反馈通知，但是只反馈了其中一部分条，所以反馈时间小于反馈开始时间则认定为未进行反馈
					// 因为slist可能有多条，遍历的话可能会把时间最小的赋给开始时间，这样会出现错误，导致反馈过的再次反馈，没反馈的反馈不了
					// 但是每次只能有一条
					String feedbacktime = backList.get(0).getFeedbackTime();// 数据库按照时间大的排在前面，所以先取时间最后的反馈完成后再取次后的
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

	// 设置要处理的 学生请假的个数 和 分班申请的个数
	private void setCount(Model model, Emp emp) {
		// 学生请假处理个数
		int stuHoliCount = 0;
		// 分班申请个数
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
