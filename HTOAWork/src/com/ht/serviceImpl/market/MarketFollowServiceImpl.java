package com.ht.serviceImpl.market;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.market.MarketFollowMapper;
import com.ht.popj.market.MarketFollow;
import com.ht.popj.market.TrackStudent;
import com.ht.service.market.MarketFollowService;

public class MarketFollowServiceImpl implements MarketFollowService{
	@Autowired
	MarketFollowMapper marketfollowmapper;

	@Override
	public List<MarketFollow> findfllowList1(MarketFollow marketfllow) {
		return marketfollowmapper.findfllowList1(marketfllow);
	}

	@Override
	public List<MarketFollow> findfllowList2() {
		return marketfollowmapper.findfllowList2();
	}

	@Override
	public int addfllow(MarketFollow marketfllow) {
		return marketfollowmapper.addfllow(marketfllow);
	}

	@Override
	public int updatefllow(MarketFollow marketfllow) {
		return marketfollowmapper.updatefllow(marketfllow);
	}

	@Override
	public int deletefllow(Integer id) {
		return marketfollowmapper.deletefllow(id);
	}

}
