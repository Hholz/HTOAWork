package com.ht.mapper.education;

import com.ht.popj.education.EduDept;
import com.ht.popj.education.EduDeptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EduDeptMapper {
    int countByExample(EduDeptExample example);

    int deleteByExample(EduDeptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EduDept record);

    int insertSelective(EduDept record);

    List<EduDept> selectByExample(EduDeptExample example);

    EduDept selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EduDept record, @Param("example") EduDeptExample example);

    int updateByExample(@Param("record") EduDept record, @Param("example") EduDeptExample example);

    int updateByPrimaryKeySelective(EduDept record);

    int updateByPrimaryKey(EduDept record);
    
	List<EduDept> selectDeptAll();

	List<EduDept> selectByDynamic(EduDept dept);

}