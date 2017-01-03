package com.ht.mapper.education;

import com.ht.popj.education.EduOutline;
import com.ht.popj.education.EduOutlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EduOutlineMapper {
    int countByExample(EduOutlineExample example);

    int deleteByExample(EduOutlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EduOutline record);

    int insertSelective(EduOutline record);

    List<EduOutline> selectByExample(EduOutlineExample example);

    EduOutline selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EduOutline record);

    int updateByPrimaryKey(EduOutline record);
    
  	List<EduOutline> selectOutlineAll();

  	List<EduOutline> selectByDynamic(EduOutline outline);

  	int getCount(Integer id);
  	
  	int getHourSum(EduOutline outline);
}