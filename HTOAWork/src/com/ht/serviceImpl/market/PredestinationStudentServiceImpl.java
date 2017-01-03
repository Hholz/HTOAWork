package com.ht.serviceImpl.market;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.market.PredestinationStudentMapper;
import com.ht.popj.market.PredestinationStudent;
import com.ht.service.market.PredestinationStudentService;

public class PredestinationStudentServiceImpl implements PredestinationStudentService{
	@Autowired
	private PredestinationStudentMapper prestudentMapper;

	@Override
	public List<PredestinationStudent> findPrestudentList1(PredestinationStudent prestudent) {
		return prestudentMapper.findPrestudentList1(prestudent);
	}

	@Override
	public List<PredestinationStudent> findPrestudentList2() {
		return prestudentMapper.findPrestudentList2();
	}

	@Override
	public int addPrestudent(PredestinationStudent prestudent) {
		return prestudentMapper.addPrestudent(prestudent);
	}

	@Override
	public int updatePrestudent(PredestinationStudent prestudent) {
		return prestudentMapper.updatePrestudent(prestudent);
	}

	@Override
	public int deletePrestudnet(Integer id) {
		return prestudentMapper.deletePrestudnet(id);
	}

	@Override
	public PredestinationStudent selectPrestudentById(Integer id) {
		return prestudentMapper.selectPrestudentById(id);
	}

}
