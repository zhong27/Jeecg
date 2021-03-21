package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.entity.OrderMater;
import org.jeecg.modules.ord.mapper.OrderBillMapper;
import org.jeecg.modules.ord.mapper.OrderMaterMapper;
import org.jeecg.modules.ord.service.IOrderMaterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 提单材料明细
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Service
public class OrderMaterServiceImpl extends ServiceImpl<OrderMaterMapper, OrderMater> implements IOrderMaterService {
	
	@Autowired
	private OrderMaterMapper orderMaterMapper;
	@Autowired
	private OrderDetServiceImpl orderDetService;
	@Autowired
	private OrderBillMapper orderBillMapper;
	
	@Override
	public List<OrderMater> selectByMainId(String mainId) {
		return orderMaterMapper.selectByMainId(mainId);
	}

    public void addOrderMater(String orderId) {
		List<OrderDet> orderDetList = orderDetService.selectByMainId(orderId);
		//非空判断
		if (CollectionUtil.isEmpty(orderDetList)){
			throw new JeecgException("订单材料明细为空！");
		}
		OrderMater selectOrderMater = orderBillMapper.selectByOrderId(orderId);

		List<OrderMater> orderMaterList = new ArrayList<>();
		for (OrderDet orderDet:orderDetList){
			OrderMater orderMater = new OrderMater();
			BeanUtil.copyProperties(orderDet,orderMater);
			orderMater.setId(null);
			orderMater.setCreateBy(null);
			orderMater.setCreateTime(null);
			orderMater.setUpdateBy(null);
			orderMater.setUpdateTime(null);
			orderMater.setSysOrgCode(null);
			orderMater.setSysOrgCode(null);
			orderMater.setBillId(selectOrderMater.getId());
			orderMaterList.add(orderMater);
		}
		saveBatch(orderMaterList);

	}
}