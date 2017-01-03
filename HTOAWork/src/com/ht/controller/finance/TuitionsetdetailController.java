package com.ht.controller.finance;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.popj.finance.Tuitionset;
import com.ht.popj.finance.Tuitionsetdetail;
import com.ht.service.finance.TuitionsetdetailService;

@Controller
@RequestMapping("finance/TuitionsetDetail")
public class TuitionsetdetailController {
	
	@Autowired
	TuitionsetdetailService tuitionsetdetailService;

	
	@RequestMapping("/addTuitionsetDetail")
	public @ResponseBody int addTuitionsetDetail(Tuitionsetdetail tui) throws Exception{
		
		if(null !=tui){
			int count = tuitionsetdetailService.insertSelective(tui);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/findDetail")
	public @ResponseBody List<Tuitionsetdetail> getAll(Model model,Tuitionsetdetail tui){
		List<Tuitionsetdetail> list = new ArrayList<Tuitionsetdetail>();
		list = tuitionsetdetailService.selectDynamic(tui);
		return list;
	}
	
	@RequestMapping("/updateTuitionsetdetail")
	public @ResponseBody int updateTuitionsetdetail(Tuitionsetdetail tui,String detailJson) throws Exception{
		if(null !=tui){
			int count=0;
			System.out.println(detailJson);
			JSONObject jsonObj=null;
			Tuitionsetdetail d=null;
			jsonObj = new JSONObject(detailJson);
			JSONArray detail = jsonObj.getJSONArray("tuitionnameList");
			for (int i = 0; i < detail.length(); i++) {
				JSONObject jo = (JSONObject) detail.get(i);
				d = new Tuitionsetdetail();
				d.setTuitionid(tui.getTuitionid());
				d.setTuitionname(jo.getString("tuitionname"));
				d.setMoney(Float.parseFloat(jo.getString("money")));
				
				count = tuitionsetdetailService.updateByTuitionKeySelective(d);
			}
			return count;
		}
		return 0;
	}
}
