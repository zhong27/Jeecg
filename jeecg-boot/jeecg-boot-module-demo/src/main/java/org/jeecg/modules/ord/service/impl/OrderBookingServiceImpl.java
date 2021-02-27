package org.jeecg.modules.ord.service.impl;

import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.mapper.OrderDetMapper;
import org.jeecg.modules.ord.mapper.OrderBookingMapper;
import org.jeecg.modules.ord.service.IOrderBookingService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 订单预定
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Service
public class OrderBookingServiceImpl extends ServiceImpl<OrderBookingMapper, OrderBooking> implements IOrderBookingService {

	@Autowired
	private OrderBookingMapper orderBookingMapper;
	@Autowired
	private OrderDetMapper orderDetMapper;
	
	@Override
	@Transactional
	public void delMain(String id) {
		orderDetMapper.deleteByMainId(id);
		orderBookingMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			orderDetMapper.deleteByMainId(id.toString());
			orderBookingMapper.deleteById(id);
		}
	}
	
}
