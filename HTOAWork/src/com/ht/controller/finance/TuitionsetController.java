package com.ht.controller.finance;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.controller.student.StuRepScoreController.scoreObj;
import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;
import com.ht.popj.finance.Tuitionset;
import com.ht.popj.finance.Tuitionsetdetail;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentFall;
import com.ht.popj.student.StudentScore;
import com.ht.service.education.MajorService;
import com.ht.service.education.TermService;
import com.ht.service.finance.TuitionsetService;
import com.ht.service.finance.TuitionsetdetailService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("finance/TuitionSet")
public class TuitionsetController {

	@Autowired
	TuitionsetService tuitionsetService;
	@Autowired
	TuitionsetdetailService tuitionsetdetailService;
	@Autowired
	TermService termService;
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	MajorService majorService;
	@RequestMapping("TuitionSet")
	@SystemControllerLog(description = "进入学费设置页面")
	public String TuitionSet(Model model) throws Exception{
		List<StudentFall> fall = new ArrayList<StudentFall>();
		fall = studentInfoService.selectStudentFall();
		List<EduMajor> cls = majorService.selectMajorAll();
		//List<EduTerm> termslist = termService.selectTermAll();
		model.addAttribute("fall",fall);
		model.addAttribute("cls",cls);
		//model.addAttribute("termslist",termslist);
		return "/finance/finance_TuitionSet";
	}
	@RequestMapping("TuitionSet2")
	@SystemControllerLog(description = "进入学费生成页面")
	public String TuitionSet2(Model model) throws Exception{
		
		return "/finance/finance_TuitionSet2";
	}
	
	@RequestMapping("/Json")
	public @ResponseBody ResultMessage TuitionJson(int limit, int offset,Tuitionset stu){
		ResultMessage rm = new ResultMessage();
		List<Tuitionset> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(stu !=null){
			sList = tuitionsetService.selectByDynamic(stu);
		}else{
			sList = tuitionsetService.selectByDynamic(stu);
		}
		
		 // 取分页信息
        PageInfo<Tuitionset> pageInfo = new PageInfo<Tuitionset>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/DetailJson")
	public @ResponseBody ResultMessage TuitionsetdetailJson(int limit, int offset,Tuitionsetdetail stu){
		ResultMessage rm = new ResultMessage();
		List<Tuitionsetdetail> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(stu !=null){
			sList = tuitionsetdetailService.selectDynamic(stu);
		}else{
			sList = tuitionsetdetailService.selectDynamic(stu);
		}
		
		 // 取分页信息
        PageInfo<Tuitionsetdetail> pageInfo = new PageInfo<Tuitionsetdetail>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/findTermName")
	public @ResponseBody ResultMessage findemp(Model model,EduTerm s) {
		ResultMessage rm = new ResultMessage();
		List<EduTerm> termlist =  termService.selectByDynamic(s);
		rm.setTotal(termlist.size());
		rm.setRows(termlist);
		return rm;
	}
	
	@RequestMapping("/addTuitionset")
	public @ResponseBody int addTuitionset(Tuitionset tui,String detailJson) throws Exception{
		if(null !=tui){
			int count = tuitionsetService.insertSelective(tui);
			
			System.out.println(detailJson);
			JSONObject jsonObj=null;
			Tuitionsetdetail d=null;
			jsonObj = new JSONObject(detailJson);
			JSONArray detail = jsonObj.getJSONArray("feeNameList");
			for (int i = 0; i < detail.length(); i++) {
				JSONObject jo = (JSONObject) detail.get(i);
				d = new Tuitionsetdetail();
				d.setTuitionid(tui.getId());
				d.setTuitionname(jo.getString("feeName"));
				d.setMoney(Float.parseFloat(jo.getString("money")));
				
				tuitionsetdetailService.insertSelective(d);
			}
			
				
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateTuitionset(Tuitionset tuitionset) throws Exception{  
		if(null!=tuitionset){
			int count = tuitionsetService.updateByPrimaryKeySelective(tuitionset);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteTuitionset(Tuitionset tuitionset) throws Exception{  
		if(null!=tuitionset){
			tuitionset.setStatus(0);
			int count = tuitionsetService.updateByPrimaryKeySelective(tuitionset);
			return count;
		}
		return 0;
	}
}
