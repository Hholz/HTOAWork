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
 * ��Ŀ���÷�
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
	 * ������Ŀ���÷ֵ�ҳ��
	 */
	@RequestMapping("/page")
	@SystemControllerLog(description = "����ѧ����Ŀ���÷ֵ�ҳ��")
	public String page(Model model){  
		List<StudentClass> clsList = new ArrayList<StudentClass>();
		clsList = studentInfoService.selectallstduentclass();
		
		List<StuReplyModel> srmList = new ArrayList<StuReplyModel>();
		srmList = srmService.selectAllUnRep();//��ѯ��δ����
		model.addAttribute("clsList", clsList);//���а༶
		model.addAttribute("srmList", srmList);//���д��ģ��
		return "student/stuRepScore";
	}
	/*
	 * ��Ŀ���÷ֱ��json����
	 */
	@RequestMapping("/repScoreJsonList")
	@SystemControllerLog(description = "������Ŀ���÷ֱ�json����")
	public @ResponseBody ResultMessage repScoreJsonList(int limit, int offset,StuRepScore srs,String mbName,String clsId,String empName,String pass){
		int cls_id = 0;
		if(null!=clsId&&!"".equals(clsId)){
			cls_id = Integer.parseInt(clsId);
		}
		ResultMessage rm = new ResultMessage();
		List<StuRepScore> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		List<StuRepScore> srsList = srsSercice.selectByPJ(srs, mbName, cls_id, empName);//��ѯ
		sList = getsList(srsList,pass);
		 // ȡ��ҳ��Ϣ
        PageInfo<StuRepScore> pageInfo = new PageInfo<StuRepScore>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	public List<StuRepScore> getsList(List<StuRepScore> srsList , String pass){
		List<StuRepScore> list1 = new ArrayList<>();//������
		List<StuRepScore> list2 = new ArrayList<>();//����
		List<StuRepScore> list3 = new ArrayList<>();//����
		List<StuRepScore> list4 = new ArrayList<>();//����
		
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
	 * ͨ���÷ֱ�id����ȡ��Ӧ�ĵ÷���ϸ
	 */
	@RequestMapping(value = "getStuRepScoreD/{id}", method = { RequestMethod.GET })
	@SystemControllerLog(description = "ͨ���÷ֱ�id���ص÷���ϸ��json����")
	public @ResponseBody List<StuRepScoreD> getStuRepScoreD(@PathVariable("id")Integer id){
		List<StuRepScoreD> srsdList = new ArrayList<StuRepScoreD>();
		srsdList = srsdService.selectBysrsId(id);
		return srsdList;
	}
	
	/*
	 * ¼��ѧ���ɼ�
	 */
	@RequestMapping("/add")
	@SystemControllerLog(description = "¼��ѧ���ɼ�")
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
		//������Ŀ����
		srs.setCreateTime(new Date());
		srs.setRemark(RemarkSet.getRemark("���"));
		srsSercice.insertByPJ(srs);
		
		for(scoreObj obj:list){
			StuRepScoreD srsd = new StuRepScoreD();
			srsd.setSrsId(srs.getId());
			srsd.setSrmId(srs.getSrmId());
			srsd.setSrmdId(obj.getSrmdId());
			srsd.setSrsdScore(obj.getScore());
			srsd.setCreateTime(new Date());
			srsd.setRemark(RemarkSet.getRemark("���"));
			srsdService.insertByPJ(srsd);
		}
		System.out.println("�����ɹ�");		
		
		return list;
	}
	/*
	 * �޸�ѧ���ɼ�
	 */
	@RequestMapping("/update")
	@SystemControllerLog(description = "�޸�ѧ���ɼ�")
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
			srsd.setRemark(RemarkSet.getRemark("�޸�"));
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
