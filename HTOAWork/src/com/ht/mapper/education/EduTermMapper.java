package com.ht.mapper.education;

import com.ht.popj.education.EduTerm;
import com.ht.popj.education.EduTermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EduTermMapper {
    int countByExample(EduTermExample example);

    int deleteByExample(EduTermExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EduTerm record);

    int insertSelective(EduTerm record);

    List<EduTerm> selectByExample(EduTermExample example);

    EduTerm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EduTerm record, @Param("example") EduTermExample example);

    int updateByExample(@Param("record") EduTerm record, @Param("example") EduTermExample example);

    int updateByPrimaryKeySelective(EduTerm record);

    int updateByPrimaryKey(EduTerm record);
    
	List<EduTerm> selectTermAll();

	List<EduTerm> selectByDynamic(EduTerm term);
	
	List<EduTerm> selectByAllMajor(EduTerm term);
}