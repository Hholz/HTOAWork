package com.ht.service.market;

import java.util.List;

import com.ht.popj.market.MarketFollow;
import com.ht.popj.market.TrackStudent;

public interface MarketFollowService {
	//����ѧ���б�
	List<MarketFollow> findfllowList1(MarketFollow marketfllow);
	List<MarketFollow> findfllowList2();
	//��������ѧ��
	int addfllow(MarketFollow marketfllow);
	//�޸ĸ���ѧ��
	int updatefllow(MarketFollow marketfllow);
	//ɾ������ѧ��
	int deletefllow(Integer id);
}
