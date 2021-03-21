package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.date.DateUtil;
import org.jeecg.modules.ord.OrderNo;
import org.jeecg.modules.ord.entity.*;
import org.jeecg.modules.ord.mapper.OrderDriverMapper;
import org.jeecg.modules.ord.mapper.OrderMaterMapper;
import org.jeecg.modules.ord.mapper.OrderConsigneeMapper;
import org.jeecg.modules.ord.mapper.OrderBillMapper;
import org.jeecg.modules.ord.service.IOrderBillService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 提单信息管理
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Service
public class OrderBillServiceImpl extends ServiceImpl<OrderBillMapper, OrderBill> implements IOrderBillService {

	@Autowired
	private OrderBillMapper orderBillMapper;
	@Autowired
	private OrderDriverMapper orderDriverMapper;
	@Autowired
	private OrderMaterMapper orderMaterMapper;
	@Autowired
	private OrderConsigneeMapper orderConsigneeMapper;
	@Autowired
	OrderDetServiceImpl orderDetService;
	
	@Override
	@Transactional
	public void delMain(String id) {
		orderDriverMapper.deleteByMainId(id);
		orderMaterMapper.deleteByMainId(id);
		orderConsigneeMapper.deleteByMainId(id);
		orderBillMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			orderDriverMapper.deleteByMainId(id.toString());
			orderMaterMapper.deleteByMainId(id.toString());
			orderConsigneeMapper.deleteByMainId(id.toString());
			orderBillMapper.deleteById(id);
		}
	}

	@Transactional
	//提单生成
	public void addBill(OrderBooking orderBooking) {

		OrderBill orderBill = new OrderBill();
		orderBill.setTotal(orderBooking.getOrderTotal());
		orderBill.setOrderId(orderBooking.getId());
		orderBill.setBusiness(orderBooking.getBusiness());
		orderBill.setOrderNo(orderBooking.getOrderNo());
		orderBill.setCustomerId(orderBooking.getCustomer());

		//查询订单主表全部明细
		List<OrderDet> orderDetList = orderDetService.selectByMainId(orderBooking.getId());
		BigDecimal totalWeight = BigDecimal.ZERO;
		//计算订单明细总价
		for (OrderDet orderDet : orderDetList) {
			totalWeight = totalWeight.add(orderDet.getWeight());
		}
		orderBill.setTotalWeight(totalWeight);
		//设置提单编号
		String billNo = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
		billNo = OrderNo.BILL_NO_PRE.getValue() + billNo;
		orderBill.setBillNo(billNo);
		save(orderBill);
	}
}
