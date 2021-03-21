package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.jeecg.modules.man.entity.Consignee;
import org.jeecg.modules.man.service.impl.ConsigneeServiceImpl;
import org.jeecg.modules.ord.entity.OrderBill;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderConsignee;
import org.jeecg.modules.ord.mapper.OrderBillMapper;
import org.jeecg.modules.ord.mapper.OrderConsigneeMapper;
import org.jeecg.modules.ord.service.IOrderConsigneeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 收料人信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Service
public class OrderConsigneeServiceImpl extends ServiceImpl<OrderConsigneeMapper, OrderConsignee> implements IOrderConsigneeService {
	
	@Autowired
	private OrderConsigneeMapper orderConsigneeMapper;
	@Autowired
	private OrderBillMapper orderBillMapper;
	@Autowired
	private ConsigneeServiceImpl consigneeService;
	
	@Override
	public List<OrderConsignee> selectByMainId(String mainId) {
		return orderConsigneeMapper.selectByMainId(mainId);
	}

    public void addConsignee(OrderBooking orderBooking) {
		Consignee getByConsignee = consigneeService.getById(orderBooking.getConsignee());
		OrderConsignee consignee = new OrderConsignee();

		//查询提单主表id
		OrderBill selectOrderBill = orderBillMapper.selectByOrderId(orderBooking.getId());

		BeanUtil.copyProperties(getByConsignee,consignee);
		consignee.setId(null);
		consignee.setCreateBy(null);
		consignee.setCreateTime(null);
		consignee.setUpdateBy(null);
		consignee.setUpdateTime(null);
		consignee.setSysOrgCode(null);
		consignee.setBillId(selectOrderBill.getId());
		consignee.setConsigneeId(getByConsignee.getId());
		save(consignee);
	}
}
