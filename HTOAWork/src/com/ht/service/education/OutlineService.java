package com.ht.service.education;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ht.popj.education.EduOutline;
import com.ht.popj.education.EduOutlineExample;

public interface OutlineService {
    int countByExample(EduOutlineExample example);

    int deleteByExample(EduOutlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EduOutline record);

    int insertSelective(EduOutline record);

    List<EduOutline> selectByExample(EduOutlineExample example);

    EduOutline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EduOutline record, @Param("example") EduOutlineExample example);

    int updateByExample(@Param("record") EduOutline record, @Param("example") EduOutlineExample example);

    int updateByPrimaryKeySelective(EduOutline record);

    int updateByPrimaryKey(EduOutline record);
    
  	List<EduOutline> selectOutlineAll();

  	List<EduOutline> selectByDynamic(EduOutline outline);

  	int getCount(Integer id);
  	
	int getHourSum(EduOutline outline);
  	
}
