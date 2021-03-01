package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.cash.service.impl.CashBalanceServiceImpl;
import org.jeecg.modules.ord.PayStatusEnum;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.mapper.OrderDetMapper;
import org.jeecg.modules.ord.mapper.OrderBookingMapper;
import org.jeecg.modules.ord.service.IOrderBillService;
import org.jeecg.modules.ord.service.IOrderBookingService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 订单预定
 * @Author: jeecg-boot
 * @Date: 2021-02-27
 * @Version: V1.0
 */
@Service
public class OrderBookingServiceImpl extends ServiceImpl<OrderBookingMapper, OrderBooking> implements IOrderBookingService {

    @Autowired
    private OrderBookingMapper orderBookingMapper;
    @Autowired
    private OrderDetMapper orderDetMapper;
    @Autowired
    private OrderDetServiceImpl orderDetService;
    @Autowired
    private CashBalanceServiceImpl cashBalanceService;

    @Override
    @Transactional
    public void delMain(String id) {
        //订单主表删除
/*
        OrderBooking orderBooking = getById(id);
        if (StrUtil.equals(orderBooking.getPayStatus(),PayStatusEnum.PAY.getValue())){
            throw new JeecgException("订单已支付，不可删除！");
        }
*/
        orderDetMapper.deleteByMainId(id);
        orderBookingMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            orderDetMapper.deleteByMainId(id.toString());
            orderBookingMapper.deleteById(id);
        }
    }

    public void calculateTotal(OrderDet orderDet) {
        //计算订单总价
        OrderBooking orderBooking = getById(orderDet.getOrderId());
        if (NumberUtil.equals(orderBooking.getOrderTotal(), BigDecimal.ZERO)) {
            orderBooking.setOrderTotal(orderDet.getTotal());
        } else {
            orderBooking.setOrderTotal(orderDet.getTotal().add(orderBooking.getOrderTotal()));
        }
        updateById(orderBooking);
    }

    public void subTotal(OrderDet orderDet) {


        //获取订单主表实体
        OrderBooking orderBooking = getById(orderDet.getOrderId());

        //校验订单支付状态
        if (ObjectUtil.equal(orderBooking.getPayStatus(), PayStatusEnum.PAY.getValue())) {
            throw new JeecgException("订单已支付,不可编辑！");
        }

        //计算订单明细总价
        orderDet.setTotal(orderDet.getPrice().multiply(orderDet.getWeight()));

        //查询更新前的订单明细
        OrderDet updateBeforeOrderDet = orderDetService.getById(orderDet);
        orderBooking.setOrderTotal(orderBooking.getOrderTotal().subtract(updateBeforeOrderDet.getTotal()).add(orderDet.getTotal()));
        orderDetService.updateById(orderDet);
        updateById(orderBooking);

    }

    public void changePayStatus(String orderId) {
        OrderBooking orderBooking = getById(orderId);
        //校验订单支付状态
        if (StrUtil.equals(orderBooking.getPayStatus(), PayStatusEnum.PAY.getValue())){
            throw new JeecgException("订单已经支付！");
        }
        if (StrUtil.equals(orderBooking.getPayStatus(), PayStatusEnum.UN_PAY.getValue())){

            //查询订单明细
            List<OrderDet> orderDetList = orderDetMapper.selectByMainId(orderId);
            if (CollectionUtil.isEmpty(orderDetList)){
                throw new JeecgException("请添加订单明细!");
            }
            //订单支付，扣款
            cashBalanceService.payOrder(orderBooking);
            orderBooking.setPayStatus(PayStatusEnum.PAY.getValue());

            updateById(orderBooking);

        }

    }
}
