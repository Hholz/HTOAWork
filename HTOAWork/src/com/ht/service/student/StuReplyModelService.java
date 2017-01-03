package com.ht.service.student;

import java.util.List;

import org.json.JSONException;

import com.ht.popj.student.StuReplyModel;

public interface StuReplyModelService {

	int deleteById(Integer id);

    int insertByPJ(StuReplyModel srm);

    StuReplyModel selectById(Integer id);

    int updateByPJ(StuReplyModel srm);
    
    int delByUpdate(Integer id);
    
    List<StuReplyModel> selectAll();
    //²éÑ¯³öÎ´´ð±çµÄ
    List<StuReplyModel> selectAllUnRep();
    
    List<StuReplyModel> selectByPJ(StuReplyModel srm);
    
    int addRepModel(StuReplyModel srm,String jsonStr) throws JSONException;
}
