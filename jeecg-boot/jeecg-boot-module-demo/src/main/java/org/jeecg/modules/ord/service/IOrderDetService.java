package org.jeecg.modules.ord.service;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDet;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.ord.service.impl.OrderBookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 订单明细表
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
public interface IOrderDetService extends IService<OrderDet> {

	public List<OrderDet> selectByMainId(String mainId);

}
