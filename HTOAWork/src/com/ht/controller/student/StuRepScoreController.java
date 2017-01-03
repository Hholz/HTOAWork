package com.ht.controller.student;

import java.lang.reflect.Type;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.student.StuRepScore;
import com.ht.popj.student.StuRepScoreD;
import com.ht.popj.student.StuReplyModel;
import com.ht.popj.student.StudentClass;
import com.ht.service.student.StuRepScoreDService;
import com.ht.service.student.StuRepScoreService;
import com.ht.service.student.StuReplyModelService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

/*
 * 项目答辩得分
 */
@Controller
@RequestMapping("/student/replyScore")
public class StuRepScoreController {

	@Autowired
	StuRepScoreDService srsdService;
	@Autowired
	StuRepScoreService srsSercice;
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	StuReplyModelService srmService;
	/*
	 * 进入项目答辩得分的页面
	 */
	@RequestMapping("/page")
	@SystemControllerLog(description = "进入学生项目答辩得分的页面")
	public String page(Model model){  
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		clsList = studentInfoService.selectallstduentclass();
		
		List<StuReplyModel> srmList = new ArrayList<StuReplyModel>();
		srmList = srmService.selectAllUnRep();//查询出未答辩的
		model.addAttribute("clsList", clsList);//所有班级
		model.addAttribute("srmList", srmList);//所有答辩模板
		return "student/stuRepScore";
	}
	/*
	 * 项目答辩得分表格json数据
	 */
	@RequestMapping("/repScoreJsonList")
	@SystemControllerLog(description = "返回项目答辩得分表json数据")
	public @ResponseBody ResultMessage repScoreJsonList(int limit, int offset,StuRepScore srs,String mbName,String clsId,String empName,String pass){
		int cls_id = 0;
		if(null!=clsId&&!"".equals(clsId)){
			cls_id = Integer.parseInt(clsId);
		}
		ResultMessage rm = new ResultMessage();
		List<StuRepScore> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		List<StuRepScore> srsList = srsSercice.selectByPJ(srs, mbName, cls_id, empName);//查询
		sList = getsList(srsList,pass);
		 // 取分页信息
        PageInfo<StuRepScore> pageInfo = new PageInfo<StuRepScore>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	public List<StuRepScore> getsList(List<StuRepScore> srsList , String pass){
		List<StuRepScore> list1 = new ArrayList<>();//不及格
		List<StuRepScore> list2 = new ArrayList<>();//及格
		List<StuRepScore> list3 = new ArrayList<>();//优秀
		List<StuRepScore> list4 = new ArrayList<>();//满分
		
		for(StuRepScore s : srsList ){
			double srsScore = s.getSrsScore();
			double allScore = s.getAllScore();
			int score = (int) Math.floor((srsScore/allScore)*100);
			if(score<60){
				list1.add(s);
			}else if(score>=60&&score<80){
				list2.add(s);
			}else if(score>=80&&score<100){
				list2.add(s);
				list3.add(s);
			}else{
				list2.add(s);
				list3.add(s);
				list4.add(s);
			}
		}
		if(null!=pass){
			if(pass.equals("1")){
				return list1;
			}else if(pass.equals("2")){
				return list2;
			}else if(pass.equals("3")){
				return list3;
			}else if(pass.equals("4")){
				return list4;
			}
		}
		return srsList;
	}
	/*
	 * 通过得分表id来获取对应的得分明细
	 */
	@RequestMapping(value = "getStuRepScoreD/{id}", method = { RequestMethod.GET })
	@SystemControllerLog(description = "通过得分表id返回得分明细表json数据")
	public @ResponseBody List<StuRepScoreD> getStuRepScoreD(@PathVariable("id")Integer id){
		List<StuRepScoreD> srsdList = new ArrayList<StuRepScoreD>();
		srsdList = srsdService.selectBysrsId(id);
		return srsdList;
	}
	
	/*
	 * 录入学生成绩
	 */
	@RequestMapping("/add")
	@SystemControllerLog(description = "录入学生成绩")
	public @ResponseBody List<scoreObj> add(StuRepScore srs,String  scoredListJson ) throws JSONException{
		List<scoreObj> list = new ArrayList<scoreObj>();
		JSONObject jsonObj;
		jsonObj = new JSONObject(scoredListJson);
		JSONArray detailArr = jsonObj.getJSONArray("scoredList");
		for (int i = 0; i < detailArr.length(); i++) {
			JSONObject jo = (JSONObject) detailArr.get(i);
			scoreObj s = new scoreObj(jo.getInt("srmdId"),jo.getInt("score"));
			list.add(s);
		}
		//新增项目答辩表
		srs.setCreateTime(new Date());
		srs.setRemark(RemarkSet.getRemark("添加"));
		srsSercice.insertByPJ(srs);
		
		for(scoreObj obj:list){
			StuRepScoreD srsd = new StuRepScoreD();
			srsd.setSrsId(srs.getId());
			srsd.setSrmId(srs.getSrmId());
			srsd.setSrmdId(obj.getSrmdId());
			srsd.setSrsdScore(obj.getScore());
			srsd.setCreateTime(new Date());
			srsd.setRemark(RemarkSet.getRemark("添加"));
			srsdService.insertByPJ(srsd);
		}
		System.out.println("新增成功");		
		
		return list;
	}
	/*
	 * 修改学生成绩
	 */
	@RequestMapping("/update")
	@SystemControllerLog(description = "修改学生成绩")
	public @ResponseBody List<scoreObj> update(String  scoredListJson ) throws JSONException{
		List<scoreObj> list = new ArrayList<scoreObj>();
		JSONObject jsonObj;
		jsonObj = new JSONObject(scoredListJson);
		JSONArray detailArr = jsonObj.getJSONArray("scoredList");
		for (int i = 0; i < detailArr.length(); i++) {
			JSONObject jo = (JSONObject) detailArr.get(i);
			scoreObj s = new scoreObj(jo.getInt("srsdId"),jo.getInt("score"));
			list.add(s);
		}
		for(scoreObj obj:list){
			StuRepScoreD srsd = new StuRepScoreD();
			srsd.setId(obj.getSrmdId());
			srsd.setSrsdScore(obj.getScore());
			srsd.setUpdateTime(new Date());
			srsd.setRemark(RemarkSet.getRemark("修改"));
			srsdService.updateByPJ(srsd);
		}
		return list;
	}
	public class scoreObj{
		private int srmdId;
		private int score;
		public scoreObj(int srmdId,int score){
			this.srmdId=srmdId;
			this.score=score;
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public int getSrmdId() {
			return srmdId;
		}
		public void setSrmdId(int srmdId) {
			this.srmdId = srmdId;
		}
	}
	
}
