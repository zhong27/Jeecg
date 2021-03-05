package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.ord.PayStatusEnum;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.mapper.OrderDetMapper;
import org.jeecg.modules.ord.service.IOrderBookingService;
import org.jeecg.modules.ord.service.IOrderDetService;
import org.jeecg.modules.sto.service.impl.EnterHouseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * @Description: 订单明细表
 * @Author: jeecg-boot
 * @Date: 2021-02-27
 * @Version: V1.0
 */
@Service
public class OrderDetServiceImpl extends ServiceImpl<OrderDetMapper, OrderDet> implements IOrderDetService {

    @Autowired
    private OrderDetMapper orderDetMapper;
    @Autowired
    private OrderBookingServiceImpl orderBookingService;
    @Autowired
    private EnterHouseServiceImpl enterHouseService;



    @Override
    public List<OrderDet> selectByMainId(String mainId) {
        return orderDetMapper.selectByMainId(mainId);
    }
    @Transactional
    public boolean saveMain(@Param("orderDet") OrderDet orderDet) {

        OrderBooking orderBooking = orderBookingService.getById(orderDet.getOrderId());
        if (StrUtil.equals(orderBooking.getPayStatus(),PayStatusEnum.PAY.getValue())){
            throw new JeecgException("订单已支付，不可新增！");
        }
        //校验材料库存
        enterHouseService.inventoryCheck(orderDet);

        //计算订单明细总价
        orderBookingService.calculateBooking(orderDet);
        return save(orderDet);

    }


    @Transactional
    public void deleteOrderDet(String id) {

        //删除订单明细、减扣订单明细总价
        OrderDet orderDet = getById(id);
        OrderBooking orderBooking = orderBookingService.getById(orderDet.getOrderId());

        //校验订单支付状态
        if (ObjectUtil.equal(orderBooking.getPayStatus(), PayStatusEnum.PAY.getValue())) {
            throw new JeecgException("订单已支付,不可删除！");
        }
        orderBooking.setOrderTotal(orderBooking.getOrderTotal().subtract(orderDet.getTotal()));

        if (ObjectUtil.equal(orderBooking.getPayStatus(), PayStatusEnum.UN_PAY.getValue())) {

            enterHouseService.updateEnterHouse(orderDet);
            orderBookingService.updateById(orderBooking);
            removeById(id);
        }

    }
    //批量删除提单明细
    @Transactional
    public void deleteBatchById(List<String> orderDetIds) {
        List<OrderDet> orderDetList = listByIds(orderDetIds);
        OrderBooking orderBooking = orderBookingService.getById(orderDetList.get(0).getOrderId());
        if (StrUtil.equals(orderBooking.getPayStatus(),PayStatusEnum.PAY.getValue())){
            throw new JeecgException("订单已支付，不可删除！");
        }
        //删除订单明细，减扣订单总价
        BigDecimal orderTotal = BigDecimal.ZERO;
        for (OrderDet orderDet:orderDetList){
            orderTotal = orderTotal.add(orderDet.getTotal());
        }
        orderBooking.setOrderTotal(orderBooking.getOrderTotal().subtract(orderTotal));
        orderBookingService.updateById(orderBooking);
        removeByIds(orderDetIds);
    }
}
