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
 * ����ϵͳ�������ϵͳ���淢��
 */
@Controller
@RequestMapping("/dailyWork/Notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private EmpService empService;
	
	
	@RequestMapping("/noticeList")
	@SystemControllerLog(description = "����ϵͳ�������ϵͳ���淢����Ϣҳ��")
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
	//��������б�
	@RequestMapping("/noticeTypeListJson")
	@SystemControllerLog(description = "����ϵͳ��������json����")
	public @ResponseBody ResultMessage noticeTypeListJson(int limit, int offset,Noticetype noticeType){
		ResultMessage rm = new ResultMessage();
		List<Noticetype> list = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		
		list = noticeService.findNoticeTypeList2();
		
		 // ȡ��ҳ��Ϣ
        PageInfo<Noticetype> pageInfo = new PageInfo<Noticetype>(list);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	//�����������
	@RequestMapping("/addNoticeType")
	@SystemControllerLog(description = "�����������")
	public @ResponseBody int addNoticeType(Noticetype noticeType){
		noticeType.setCreateTime(new Date());
		if(noticeType != null){
			int count = noticeService.addNoticeType(noticeType);
			return count;
		}
		return 0;
	}
	//�޸Ĺ������
	@RequestMapping(value="/noticeType/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "�޸Ĺ������")
	public @ResponseBody int updateNoticeType(Noticetype noticeType){
		noticeType.setUpdateTime(new Date());
		if(noticeType != null){
			int count = noticeService.updateNoticeType(noticeType);
			return count;
		}
		return 0;
	}
	//ɾ���������
	@RequestMapping(value="/noticeType/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "ɾ���������")
	public @ResponseBody int deleteNoticeType(@PathVariable("id") Integer id){
		int count = noticeService.deleteNoticeTypeById(id);
		return count;
	}
	//-------------------------------������Ϣ-------------------------------
	//������Ϣ�б�
	@RequestMapping("/noticeListJson")
	@SystemControllerLog(description = "���ع�����Ϣ��json����")
	public @ResponseBody ResultMessage noticeListJson(int limit, int offset,Notice notice){
		ResultMessage rm = new ResultMessage();
		List<Notice> list = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(notice != null){
			list = noticeService.findNoticeList1(notice);
		}else{
			list = noticeService.findNoticeList2();
		}
		 // ȡ��ҳ��Ϣ
        PageInfo<Notice> pageInfo = new PageInfo<Notice>(list);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	//����������Ϣ
	@RequestMapping("/addNotice")
	@SystemControllerLog(description = "����������Ϣ")
	public @ResponseBody int addNotice(Notice notice){
		// 1.��ȡsession����
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		// 3.��userInfo��ȡ��ѧ����Ա����Ϣ//������һΪ�գ�ע���п�
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
	//�޸Ĺ�����Ϣ
	@RequestMapping(value="/notice/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "�޸Ĺ�����Ϣ")
	public @ResponseBody int updateNotice(Notice notice){
		notice.setUpdateTime(new Date().toLocaleString());
		if(notice != null){
			int count = noticeService.updateNotice(notice);
			return count;
		}
		return 0;
	}
	//ɾ��������Ϣ
	@RequestMapping(value="/notice/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "ɾ��������Ϣ")
	public @ResponseBody int deleteNotice(@PathVariable("id") Integer id){
		int count = noticeService.deleteNoticeById(id);
		return count;
	}
}
