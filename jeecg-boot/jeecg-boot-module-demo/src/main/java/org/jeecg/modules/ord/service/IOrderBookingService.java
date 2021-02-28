package org.jeecg.modules.ord.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ord.OrderNo;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.entity.OrderBooking;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Description: 订单预定
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
public interface IOrderBookingService extends IService<OrderBooking> {

	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

	@Override
	//订单add方法重写
	default boolean save(OrderBooking orderBooking) {
		//订单编号
		String orderNo = DateUtil.format(DateUtil.date(),"yyyyMMddHHmmss");
		orderNo = OrderNo.ORDER_NO_PRE.getValue() + orderNo;
		orderBooking.setOrderNo(orderNo);
		orderBooking.setOrderTotal(BigDecimal.ZERO);
		return IService.super.save(orderBooking);
	}

}
