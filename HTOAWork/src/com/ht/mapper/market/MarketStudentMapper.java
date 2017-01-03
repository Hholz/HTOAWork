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

	//map里放id，msStatus  (1,1)
	int updateMsStatusById(Map map);
	
	//map里的msStatus放list<intenger> *学生状态
	//map里的marketStudent            *查询条件
	//查询该几个状态下的市场学生表
	List<MarketStudent> selectBymsStatusMap(Map map);

	List<MarketStudent> selectPredStudentAllByCount(int count);

	List<MarketStudent> selectPredStudentAllRand();

	List<MarketStudent> selectTestStudentAll();
	//查询该试学班的学生有多少个
	int countByclsId(Integer classid);
	//该年中12个月中对应状态的学生有多少个Integer msStatus,String year
	MarketStudentCount countByYearStatus(Map map);
	MarketStudentCount countIntenByYear(Integer year);//每个月的意向学生数
	MarketStudentCount countTestByYear(Integer year);//每个月的已试学学生数
	
	int reportIntenYearByYear(Integer year);//一年的的意向学生数
	int reportYearByYearStatus(Map map);//一年的的某个状态学生数
	int reportTestYearByYear(Integer year);//一年的的已试学学生数
}