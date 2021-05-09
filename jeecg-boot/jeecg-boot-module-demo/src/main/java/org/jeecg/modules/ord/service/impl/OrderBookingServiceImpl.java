package org.jeecg.modules.ord.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.config.mybatis.TenantContext;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.cash.service.impl.CashBalanceServiceImpl;
import org.jeecg.modules.man.AccountStatusEnum;
import org.jeecg.modules.man.entity.Customer;
import org.jeecg.modules.man.service.impl.CustomerServiceImpl;
import org.jeecg.modules.ord.OrderNo;
import org.jeecg.modules.ord.PayStatusEnum;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.entity.OrderMater;
import org.jeecg.modules.ord.mapper.OrderDetMapper;
import org.jeecg.modules.ord.mapper.OrderBookingMapper;
import org.jeecg.modules.ord.service.IOrderBillService;
import org.jeecg.modules.ord.service.IOrderBookingService;
import org.jeecg.modules.sto.entity.EnterHouse;
import org.jeecg.modules.sto.service.impl.EnterHouseServiceImpl;
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
    @Autowired
    private OrderBillServiceImpl orderBillService;
    @Autowired
    private EnterHouseServiceImpl enterHouseService;
    @Autowired
    private OrderMaterServiceImpl orderMaterService;
    @Autowired
    private OrderDriverServiceImpl orderDriverService;
    @Autowired
    private OrderConsigneeServiceImpl orderConsigneeService;
    @Autowired
    private CustomerServiceImpl customerService;


    @Override
    @Transactional
    public void delMain(String id) {
        //订单主表删除
        OrderBooking orderBooking = getById(id);
        if (StrUtil.equals(orderBooking.getPayStatus(),PayStatusEnum.PAY.getValue())){
            throw new JeecgException("订单已支付，不可删除！");
        }
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


    //订单add方法重写
    @Transactional
    public boolean saveOrderBooking(OrderBooking orderBooking) {

        //检验账户是否冻结 
        Customer customer = customerService.getById(orderBooking.getCustomer());
        if (StrUtil.equals(customer.getAccountStatus(), AccountStatusEnum.FROZEN.getValue())){
            throw new JeecgException(StrUtil.format("不可下单！当前账户已被冻结，请联系管理员解冻账户！",customer.getCustomerName()));
        }
        //订单编号
        String orderNo = DateUtil.format(DateUtil.date(),"yyyyMMddHHmmss");
        orderNo = OrderNo.ORDER_NO_PRE.getValue() + orderNo;
        orderBooking.setOrderNo(orderNo);
        orderBooking.setOrderTotal(BigDecimal.ZERO);
        return save(orderBooking);
    }

    @Transactional
    public void calculateBooking(OrderDet orderDet) {

        orderDet.setTotal(orderDet.getPrice().multiply(orderDet.getWeight()));
        //计算订单总价
        OrderBooking orderBooking = getById(orderDet.getOrderId());
        if (NumberUtil.equals(orderBooking.getOrderTotal(), BigDecimal.ZERO)) {
            orderBooking.setOrderTotal(orderDet.getTotal());
        } else {
            orderBooking.setOrderTotal(orderDet.getTotal().add(orderBooking.getOrderTotal()));
        }
        updateById(orderBooking);
    }
    @Transactional
    public void updateOrderDetAndEnterHouse(OrderDet orderDet) {

        //获取订单主表实体
        OrderBooking orderBooking = getById(orderDet.getOrderId());

        //校验订单支付状态
        if (ObjectUtil.equal(orderBooking.getPayStatus(), PayStatusEnum.PAY.getValue())) {
            throw new JeecgException("订单已支付,不可编辑！");
        }

        //计算订单明细总价
        orderDet.setTotal(orderDet.getPrice().multiply(orderDet.getWeight()));

        //查询编辑前的订单明细
        OrderDet updateBeforeOrderDet = orderDetService.getById(orderDet);
        orderBooking.setOrderTotal(orderBooking.getOrderTotal().subtract(
                updateBeforeOrderDet.getTotal()).add(orderDet.getTotal()));

        //通过仓库、长度、宽度、厚度、材料号、产品大类、产品名称，校验库存
        QueryWrapper<EnterHouse> queryWrapperOrderDet = new QueryWrapper<>();
        queryWrapperOrderDet.lambda().eq(EnterHouse::getMatWidth,orderDet.getMatWidth())
                .eq(EnterHouse::getMatLen,orderDet.getMatLen())
                .eq(EnterHouse::getMatThick,orderDet.getMatThick())
                .eq(EnterHouse::getMatNo,orderDet.getMatNo())
                .eq(EnterHouse::getMatType,orderDet.getMatType())
                .eq(EnterHouse::getMatName,orderDet.getMatName())
                .eq(EnterHouse::getWarehouse,orderDet.getWarehouse());
        EnterHouse selectEnterHouse = enterHouseService.getOne(queryWrapperOrderDet);
        selectEnterHouse.setTotalWeight(selectEnterHouse.getTotalWeight().subtract(orderDet.getWeight())
                .add(updateBeforeOrderDet.getWeight()));

        enterHouseService.updateById(selectEnterHouse);
        orderDetService.updateById(orderDet);
        updateById(orderBooking);
    }

    @Transactional
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
            //订单生成提单
            orderBillService.addBill(orderBooking);
            //提单材料明细生成
            orderMaterService.addOrderMater(orderId);
            //提单材料司机信息赋值
            orderDriverService.addDriver(orderBooking);
            //提单材料收货人信息赋值
            orderConsigneeService.addConsignee(orderBooking);

            orderBooking.setPayStatus(PayStatusEnum.PAY.getValue());

            updateById(orderBooking);

        }

    }
    @Transactional
    public SaleInfo seleInfo() {
        SaleInfo saleInfo = new SaleInfo();
        QueryWrapper<OrderBooking> orderBookingQueryWrapper = new QueryWrapper<>();
        orderBookingQueryWrapper.lambda().eq(OrderBooking::getDelFlag,0)
                .eq(OrderBooking::getTenantId,TenantContext.getTenant());
        List<OrderBooking> orderBookingList = list(orderBookingQueryWrapper);
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal weight = BigDecimal.ZERO;
        List<OrderDet> orderDetList = new ArrayList<>();
        for(OrderBooking orderBooking:orderBookingList){
            total = total.add(orderBooking.getOrderTotal());
            orderDetList.addAll(orderDetMapper.selectByMainId(orderBooking.getId()));
        }
        for(OrderDet orderDet:orderDetList){
            weight = weight.add(orderDet.getWeight());
        }
        saleInfo.setMoney(total);
        saleInfo.setNum(orderBookingList.size());
        saleInfo.setWeight(weight);
        return saleInfo;
    }
    public class SaleInfo{
        int num;
        BigDecimal weight;
        BigDecimal Money;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public BigDecimal getWeight() {
            return weight;
        }

        public void setWeight(BigDecimal weight) {
            this.weight = weight;
        }

        public BigDecimal getMoney() {
            return Money;
        }

        public void setMoney(BigDecimal money) {
            Money = money;
        }
    }
}
