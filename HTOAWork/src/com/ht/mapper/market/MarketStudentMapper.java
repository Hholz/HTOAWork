package com.ht.mapper.market;

import java.util.List;
import java.util.Map;

import com.ht.popj.market.MarketStudent;
import com.ht.popj.market.MarketStudentCount;

public interface MarketStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MarketStudent record);

    int insertSelective(MarketStudent record);

    MarketStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MarketStudent record);

    int updateByPrimaryKey(MarketStudent record);

	List<MarketStudent> selectAll();

	List<MarketStudent> selectAllByPJ(MarketStudent marketStudent);

	//map���id��msStatus  (1,1)
	int updateMsStatusById(Map map);
	
	//map���msStatus��list<intenger> *ѧ��״̬
	//map���marketStudent            *��ѯ����
	//��ѯ�ü���״̬�µ��г�ѧ����
	List<MarketStudent> selectBymsStatusMap(Map map);

	List<MarketStudent> selectPredStudentAllByCount(int count);

	List<MarketStudent> selectPredStudentAllRand();

	List<MarketStudent> selectTestStudentAll();
	//��ѯ����ѧ���ѧ���ж��ٸ�
	int countByclsId(Integer classid);
	//������12�����ж�Ӧ״̬��ѧ���ж��ٸ�Integer msStatus,String year
	MarketStudentCount countByYearStatus(Map map);
	MarketStudentCount countIntenByYear(Integer year);//ÿ���µ�����ѧ����
	MarketStudentCount countTestByYear(Integer year);//ÿ���µ�����ѧѧ����
	
	int reportIntenYearByYear(Integer year);//һ��ĵ�����ѧ����
	int reportYearByYearStatus(Map map);//һ��ĵ�ĳ��״̬ѧ����
	int reportTestYearByYear(Integer year);//һ��ĵ�����ѧѧ����
}