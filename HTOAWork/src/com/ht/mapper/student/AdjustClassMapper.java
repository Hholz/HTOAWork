package com.ht.mapper.student;

import java.util.List;
import java.util.Map;

import com.ht.popj.student.AdjustClass;

public interface AdjustClassMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(AdjustClass record);

    AdjustClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdjustClass record);

    List<AdjustClass> selectbyStuId(Integer stuId);
    //map���stuId����ѯ�ð���������ѧ������ٵ�
    List<AdjustClass> selectbyStuIds(Map map);
    
    //ͨ��Ҫԭ�༶��ids��ȡ������
	List<AdjustClass> selectAllbyClsId(Map map);
    //ͨ��ԭ�༶��ids��ȡ ��δ�����
    List<AdjustClass> selectbyClsIdsNodel(Map map);
    
    
    //ͨ��Ҫ�ֵ��İ༶��ids��ȡ
	List<AdjustClass> selectbyToClsIdsNodel(Map map);
}