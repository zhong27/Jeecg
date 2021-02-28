package org.jeecg.modules.ord.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.mapper.OrderDetMapper;
import org.jeecg.modules.ord.service.IOrderBookingService;
import org.jeecg.modules.ord.service.IOrderDetService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 订单明细表
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Service
public class OrderDetServiceImpl extends ServiceImpl<OrderDetMapper, OrderDet> implements IOrderDetService {
	
	@Autowired
	private OrderDetMapper orderDetMapper;

	@Autowired
	private OrderBookingServiceImpl orderBookingService;


	
	@Override
	public List<OrderDet> selectByMainId(String mainId) {
		return orderDetMapper.selectByMainId(mainId);
	}

	public boolean saveMain(@Param("entity") OrderDet entity) {
		//计算订单明细总价
		entity.setTotal(entity.getPrice().multiply(entity.getWeight() ) );
		orderBookingService.calculateTotal(entity);
		return save(entity);

	}
	public void updateOrderDet(OrderDet orderDet){
		orderDet.setTotal(orderDet.getPrice().multiply(orderDet.getWeight() ) );
		orderBookingService.subTotal(orderDet);
	}

}
