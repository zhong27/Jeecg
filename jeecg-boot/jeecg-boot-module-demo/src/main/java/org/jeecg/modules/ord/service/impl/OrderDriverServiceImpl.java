package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.jeecg.modules.man.entity.Driver;
import org.jeecg.modules.man.service.impl.DriverServiceImpl;
import org.jeecg.modules.ord.entity.OrderBill;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDriver;
import org.jeecg.modules.ord.mapper.OrderBillMapper;
import org.jeecg.modules.ord.mapper.OrderDriverMapper;
import org.jeecg.modules.ord.service.IOrderDriverService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 司机信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Service
public class OrderDriverServiceImpl extends ServiceImpl<OrderDriverMapper, OrderDriver> implements IOrderDriverService {
	
	@Autowired
	private OrderDriverMapper orderDriverMapper;
	@Autowired
	private DriverServiceImpl driverService;
	@Autowired
	private OrderBillMapper orderBillMapper;
	
	@Override
	public List<OrderDriver> selectByMainId(String mainId) {
		return orderDriverMapper.selectByMainId(mainId);
	}

    public void addDriver(OrderBooking orderBooking) {
		OrderDriver orderDriver = new OrderDriver();
		//获取司机信息
		Driver driver = driverService.getById(orderBooking.getDriver());
		BeanUtil.copyProperties(driver,orderDriver);

		//查询提单主表id
		OrderBill selectOrderBill = orderBillMapper.selectByOrderId(orderBooking.getId());

		orderDriver.setId(null);
		orderDriver.setCreateBy(null);
		orderDriver.setCreateTime(null);
		orderDriver.setUpdateBy(null);
		orderDriver.setUpdateTime(null);
		orderDriver.setSysOrgCode(null);
		orderDriver.setBillId(selectOrderBill.getId());
		orderDriver.setDriverId(driver.getId());
		save(orderDriver);
	}
}
