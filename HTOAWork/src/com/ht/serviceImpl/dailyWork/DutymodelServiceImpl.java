package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.DutymodelMapper;
import com.ht.popj.dailyWork.Dutymodel;
import com.ht.service.dailyWork.DutymodelService;

public class DutymodelServiceImpl implements DutymodelService{
	@Autowired
	private DutymodelMapper dutymodelMapper;

	@Override
	public List<Dutymodel> findDutymodelList1(Dutymodel dutymodel) {
		return dutymodelMapper.findDutymodelList1(dutymodel);
	}

	@Override
	public List<Dutymodel> findDutymodelList2() {
		return dutymodelMapper.findDutymodelList2();
	}

	@Override
	public int addDutymodel(Dutymodel dutymodel) {
		return dutymodelMapper.addDutymodel(dutymodel);
	}

	@Override
	public int updateDutymodel(Dutymodel dutymodel) {
		return dutymodelMapper.updateDutymodel(dutymodel);
	}

	@Override
	public int deleteDutymodel(Integer id) {
		return dutymodelMapper.deleteDutymodel(id);
	}

	public int usingById(Integer id) {
		int r = 0;
		r = dutymodelMapper.usingById(id);
		r = dutymodelMapper.disableById(id);
		return r;
	}

	public int selectIdIsUsing() {
		return dutymodelMapper.selectIdIsUsing();
	}

	public Dutymodel selectIsUsing() {
		return dutymodelMapper.selectIsUsing();
	}

}
