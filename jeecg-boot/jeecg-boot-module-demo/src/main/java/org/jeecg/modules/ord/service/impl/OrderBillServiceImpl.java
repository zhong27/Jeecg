package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.date.DateUtil;
import org.jeecg.modules.ord.OrderNo;
import org.jeecg.modules.ord.entity.OrderBill;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.mapper.OrderBillMapper;
import org.jeecg.modules.ord.service.IOrderBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 提单管理
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Service
public class OrderBillServiceImpl extends ServiceImpl<OrderBillMapper, OrderBill> implements IOrderBillService {
    @Autowired
    OrderDetServiceImpl orderDetService;
    //提单生成
    public void addBill(OrderBooking orderBooking) {
        OrderBill orderBill = new OrderBill();
        orderBill.setTotal(orderBooking.getOrderTotal());
        orderBill.setCustomerId(orderBooking.getCustomer());
        orderBill.setOrderId(orderBooking.getId());
        orderBill.setBusiness(orderBooking.getBusiness());
        orderBill.setDriver(orderBooking.getDriver());
        orderBill.setCarNo(orderBooking.getCarNo());
        orderBill.setOrderNo(orderBooking.getOrderNo());

        //查询订单主表全部明细
        List<OrderDet> orderDetList = orderDetService.selectByMainId(orderBooking.getId());
        BigDecimal totalWeight = BigDecimal.ZERO;
        //计算订单明细总价
        for (OrderDet orderDet:orderDetList){
            totalWeight = totalWeight.add(orderDet.getWeight());
        }
        orderBill.setTotalWeight(totalWeight);
        String billNo = DateUtil.format(DateUtil.date(),"yyyyMMddHHmmss");
        billNo = OrderNo.BILL_NO_PRE.getValue() + billNo;
        orderBill.setBillNo(billNo);
        save(orderBill);
    }
}
