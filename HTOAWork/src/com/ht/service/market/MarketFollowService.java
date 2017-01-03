package com.ht.service.market;

import java.util.List;

import com.ht.popj.market.MarketFollow;
import com.ht.popj.market.TrackStudent;

public interface MarketFollowService {
	//跟踪学生列表
	List<MarketFollow> findfllowList1(MarketFollow marketfllow);
	List<MarketFollow> findfllowList2();
	//新增跟踪学生
	int addfllow(MarketFollow marketfllow);
	//修改跟踪学生
	int updatefllow(MarketFollow marketfllow);
	//删除跟踪学生
	int deletefllow(Integer id);
}
