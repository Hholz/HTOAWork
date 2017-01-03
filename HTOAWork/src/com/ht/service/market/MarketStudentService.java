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
	
	//意向表学生
	List<MarketStudent> selectIntentionStudent(MarketStudent marketStudent);
	//要跟踪的学生
	List<MarketStudent> selectFollowStudent(MarketStudent marketStudent);
	//预定报名管理的list（查全部状态）
	List<MarketStudent> selectPredStudentAll(MarketStudent marketStudent);
	//预定报名管理的list（默认只差预定报名，正式报名）
	List<MarketStudent> selectPredStudentDefault(MarketStudent marketStudent);
	//正式报名 等待分到正常班
	List<MarketStudent> selectPredStudentAllRand();
	//试学状态  等待分到试学班
	List<MarketStudent> selectTestStudentAll();
	int countByclsId(Integer classid);
	//s该年中12个月中对应状态的学生有多少个Integer msStatus,String year
	MarketStudentCount countByYearStatus(Integer msStatus,Integer year);
	MarketStudentCount countIntenByYear(Integer year);//每个月的意向学生数
	MarketStudentCount countTestByYear(Integer year);//每个月的已试学学生数
	
	int reportIntenYearByYear(Integer year);//一年的的意向学生数
	int reportYearByYearStatus(Integer msStatus, Integer year);//一年的的某个状态学生数
	int reportTestYearByYear(Integer year);//一年的的已试学学生数
}
