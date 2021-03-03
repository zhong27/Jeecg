package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.ord.OrderNo;
import org.jeecg.modules.ord.entity.OrderBill;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.mapper.OrderBillMapper;
import org.jeecg.modules.ord.service.IOrderBillService;
import org.jeecg.modules.per.entity.Bank;
import org.jeecg.modules.per.entity.Customer;
import org.jeecg.modules.per.service.impl.BankServiceImpl;
import org.jeecg.modules.per.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 提单管理
 * @Author: jeecg-boot
 * @Date: 2021-02-27
 * @Version: V1.0
 */
@Service
public class OrderBillServiceImpl extends ServiceImpl<OrderBillMapper, OrderBill> implements IOrderBillService {
    @Autowired
    OrderDetServiceImpl orderDetService;
    @Autowired
    BankServiceImpl bankService;
    @Autowired
    CustomerServiceImpl customerService;

    @Transactional
    //提单生成
    public void addBill(OrderBooking orderBooking) {

        List<Bank> bankList = bankService.selectByMainId(orderBooking.getCustomer());
        Customer customer = customerService.getById(orderBooking.getCustomer());

        if (CollectionUtil.isEmpty(bankList)){
            throw new JeecgException(StrUtil.format("客户：{} 请维护银行卡信息！",customer.getCustomerName()));
        }

        OrderBill orderBill = new OrderBill();
        orderBill.setTotal(orderBooking.getOrderTotal())
                .setCustomerId(orderBooking.getCustomer())
                .setOrderId(orderBooking.getId())
                .setBusiness(orderBooking.getBusiness())
                .setDriver(orderBooking.getDriver())
                .setCarNo(orderBooking.getCarNo())
                .setOrderNo(orderBooking.getOrderNo())
                .setBank(bankList.get(0).getBankName())
                .setBankAccount(bankList.get(0).getAccountBank());

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
