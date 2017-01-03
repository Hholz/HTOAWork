package com.ht.service.market;

import java.util.List;
import java.util.Map;

import com.ht.popj.market.MarketStudent;
import com.ht.popj.market.MarketStudentCount;

public interface MarketStudentService {
	int deleteById(Integer id);

    int insertByPJ(MarketStudent marketStudent);

    MarketStudent selectById(Integer id);

    int updateByPJ(MarketStudent marketStudent);
    
    List<MarketStudent> selectAll();
    
    List<MarketStudent> selectAllByPJ(MarketStudent marketStudent);

	int updateMsStatusById(Integer id,Integer msStatus);
	
	//�����ѧ��
	List<MarketStudent> selectIntentionStudent(MarketStudent marketStudent);
	//Ҫ���ٵ�ѧ��
	List<MarketStudent> selectFollowStudent(MarketStudent marketStudent);
	//Ԥ�����������list����ȫ��״̬��
	List<MarketStudent> selectPredStudentAll(MarketStudent marketStudent);
	//Ԥ�����������list��Ĭ��ֻ��Ԥ����������ʽ������
	List<MarketStudent> selectPredStudentDefault(MarketStudent marketStudent);
	//��ʽ���� �ȴ��ֵ�������
	List<MarketStudent> selectPredStudentAllRand();
	//��ѧ״̬  �ȴ��ֵ���ѧ��
	List<MarketStudent> selectTestStudentAll();
	int countByclsId(Integer classid);
	//s������12�����ж�Ӧ״̬��ѧ���ж��ٸ�Integer msStatus,String year
	MarketStudentCount countByYearStatus(Integer msStatus,Integer year);
	MarketStudentCount countIntenByYear(Integer year);//ÿ���µ�����ѧ����
	MarketStudentCount countTestByYear(Integer year);//ÿ���µ�����ѧѧ����
	
	int reportIntenYearByYear(Integer year);//һ��ĵ�����ѧ����
	int reportYearByYearStatus(Integer msStatus, Integer year);//һ��ĵ�ĳ��״̬ѧ����
	int reportTestYearByYear(Integer year);//һ��ĵ�����ѧѧ����
}
