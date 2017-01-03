package com.ht.controller.dailyWork;

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
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Notice;
import com.ht.popj.dailyWork.Noticetype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.dailyWork.NoticeService;
import com.ht.util.ResultMessage;

/* 
 * 操作系统公告类别，系统公告发布
 */
@Controller
@RequestMapping("/dailyWork/Notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private EmpService empService;
	
	
	@RequestMapping("/noticeList")
	@SystemControllerLog(description = "进入系统公告类别，系统公告发布信息页面")
	public String noticeList(Model model){
		List<Emp> list = new ArrayList<>();
		Emp emp = new Emp();
		list = empService.selectEmp(emp);
		model.addAttribute("emp", list);
		
		List<Noticetype> list2 = new ArrayList<>();
		list2 = noticeService.findNoticeTypeList2();
		model.addAttribute("noticeType", list2);
		return "/dailyWork/Notice";
	}
	//公告类别列表
	@RequestMapping("/noticeTypeListJson")
	@SystemControllerLog(description = "返回系统公告类别表json数据")
	public @ResponseBody ResultMessage noticeTypeListJson(int limit, int offset,Noticetype noticeType){
		ResultMessage rm = new ResultMessage();
		List<Noticetype> list = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		
		list = noticeService.findNoticeTypeList2();
		
		 // 取分页信息
        PageInfo<Noticetype> pageInfo = new PageInfo<Noticetype>(list);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	//新增公告类别
	@RequestMapping("/addNoticeType")
	@SystemControllerLog(description = "新增公告类别")
	public @ResponseBody int addNoticeType(Noticetype noticeType){
		noticeType.setCreateTime(new Date());
		if(noticeType != null){
			int count = noticeService.addNoticeType(noticeType);
			return count;
		}
		return 0;
	}
	//修改公告类别
	@RequestMapping(value="/noticeType/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改公告类别")
	public @ResponseBody int updateNoticeType(Noticetype noticeType){
		noticeType.setUpdateTime(new Date());
		if(noticeType != null){
			int count = noticeService.updateNoticeType(noticeType);
			return count;
		}
		return 0;
	}
	//删除公告类别
	@RequestMapping(value="/noticeType/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除公告类别")
	public @ResponseBody int deleteNoticeType(@PathVariable("id") Integer id){
		int count = noticeService.deleteNoticeTypeById(id);
		return count;
	}
	//-------------------------------公告信息-------------------------------
	//公告信息列表
	@RequestMapping("/noticeListJson")
	@SystemControllerLog(description = "返回公告信息表json数据")
	public @ResponseBody ResultMessage noticeListJson(int limit, int offset,Notice notice){
		ResultMessage rm = new ResultMessage();
		List<Notice> list = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(notice != null){
			list = noticeService.findNoticeList1(notice);
		}else{
			list = noticeService.findNoticeList2();
		}
		 // 取分页信息
        PageInfo<Notice> pageInfo = new PageInfo<Notice>(list);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	//新增公告信息
	@RequestMapping("/addNotice")
	@SystemControllerLog(description = "新增公告信息")
	public @ResponseBody int addNotice(Notice notice){
		// 1.获取session对象
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		notice.setEmpid(emp.getId());
		notice.setNoticeTime(new Date().toLocaleString());
		notice.setCreateTime(new Date().toLocaleString());
		if(notice != null){
			int count = noticeService.addNotice(notice);
			return count;
		}
		return 0;
	}
	//修改公告信息
	@RequestMapping(value="/notice/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改公告信息")
	public @ResponseBody int updateNotice(Notice notice){
		notice.setUpdateTime(new Date().toLocaleString());
		if(notice != null){
			int count = noticeService.updateNotice(notice);
			return count;
		}
		return 0;
	}
	//删除公告信息
	@RequestMapping(value="/notice/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除公告信息")
	public @ResponseBody int deleteNotice(@PathVariable("id") Integer id){
		int count = noticeService.deleteNoticeById(id);
		return count;
	}
}
