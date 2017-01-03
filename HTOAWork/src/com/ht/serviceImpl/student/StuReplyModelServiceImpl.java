package com.ht.serviceImpl.student;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StuReplyModelDMapper;
import com.ht.mapper.student.StuReplyModelMapper;
import com.ht.popj.student.StuReplyModel;
import com.ht.popj.student.StuReplyModelD;
import com.ht.service.student.StuReplyModelService;
import com.ht.util.RemarkSet;

public class StuReplyModelServiceImpl implements StuReplyModelService{

	
	@Autowired
	StuReplyModelMapper srmMapper;
	@Autowired
	StuReplyModelDMapper srmdMapper;
	@Override
	public int deleteById(Integer id) {
		return srmMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StuReplyModel srm) {
		return srmMapper.insertSelective(srm);
	}

	@Override
	public StuReplyModel selectById(Integer id) {
		return srmMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StuReplyModel srm) {
		return srmMapper.updateByPrimaryKeySelective(srm);
	}

	@Override
	public int delByUpdate(Integer id) {
		return srmMapper.delByUpdate(id);
	}

	@Override
	public List<StuReplyModel> selectAll() {
		return srmMapper.selectAll();
	}

	@Override
	public int addRepModel(StuReplyModel srm, String jsonStr) throws JSONException {
		//创建时间
		srm.setCreateTime(new Date());// new Date()为获取当前系统时间
		//生成随机密码
		srm.setRemark(RemarkSet.getRemark("添加"));
		if(null!=srm){
			int count = srmMapper.insertSelective(srm);
			if(null!=jsonStr&&!"".equals(jsonStr)){
				JSONObject jsonObj=null;
				StuReplyModelD srmd=null;
				jsonObj = new JSONObject(jsonStr);
				JSONArray detail = jsonObj.getJSONArray("srmdNameList");
				for (int i = 0; i < detail.length(); i++) {
					JSONObject jo = (JSONObject) detail.get(i);
					srmd = new StuReplyModelD();
					srmd.setSrmId(srm.getId());
					srmd.setSrmdName(jo.getString("srmdName"));
					srmd.setSrmdScore(jo.getInt("srmdScore"));
					
					srmdMapper.insertSelective(srmd);
				}
			}
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}

	@Override
	public List<StuReplyModel> selectByPJ(StuReplyModel srm) {
		return srmMapper.selectByPJ(srm);
	}

	@Override
	public List<StuReplyModel> selectAllUnRep() {
		return srmMapper.selectAllUnRep();
	}

}
