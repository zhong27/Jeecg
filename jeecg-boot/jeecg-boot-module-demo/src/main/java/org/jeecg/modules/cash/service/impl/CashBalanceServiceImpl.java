package org.jeecg.modules.cash.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.cash.entity.CashBalance;
import org.jeecg.modules.cash.entity.CashIncome;
import org.jeecg.modules.cash.mapper.CashBalanceMapper;
import org.jeecg.modules.cash.service.ICashBalanceService;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.man.entity.Customer;
import org.jeecg.modules.man.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;

/**
 * @Description: 资金账户
 * @Author: jeecg-boot
 * @Date: 2021-02-27
 * @Version: V1.0
 */
@Service
public class CashBalanceServiceImpl extends ServiceImpl<CashBalanceMapper, CashBalance> implements ICashBalanceService {

    @Autowired
    CashBalanceMapper cashBalanceMapper;
    @Autowired
    CustomerServiceImpl customerService;

    public void addBlance(CashIncome cashIncome) {
        CashBalance cashBalanceMapperSelect = cashBalanceMapper.selectByCusotmerId(cashIncome.getCustomerName());
        //资金账户初始化
        if (ObjectUtil.isNull(cashBalanceMapperSelect)) {
            CashBalance cashBalance = new CashBalance();
            cashBalance.setCustomerName(cashIncome.getCustomerName());
            cashBalance.setTotalMoney(cashIncome.getIncome());
            cashBalance.setRemainMoney(cashIncome.getIncome());
            cashBalance.setAmountUsed(BigDecimal.ZERO);
            cashBalance.setRefundAmount(BigDecimal.ZERO);
            save(cashBalance);
        }
        //来款更新账户金额
        if (ObjectUtil.isNotNull(cashBalanceMapperSelect)) {
            cashBalanceMapperSelect.setTotalMoney(
                    NumberUtil.add(cashIncome.getIncome(), cashBalanceMapperSelect.getTotalMoney()));
            cashBalanceMapperSelect.setRemainMoney(
                    NumberUtil.add(cashIncome.getIncome(), cashBalanceMapperSelect.getRemainMoney()));
            updateById(cashBalanceMapperSelect);
        }
    }

    public void payOrder(OrderBooking orderBooking) {
        //查询资金账户
        CashBalance cashBalance = cashBalanceMapper.selectByCusotmerId(orderBooking.getCustomer());
        if (ObjectUtil.isNull(cashBalance)){
            throw new JeecgException(StrUtil.format("可用余额为0！请添加来款"));
        }
        if (cashBalance.getRemainMoney().compareTo(orderBooking.getOrderTotal()) == -1){
            Customer customer = customerService.getById(orderBooking.getCustomer());
            throw new JeecgException(StrUtil.format("支付失败！可用余额不足，超：{}元，账户：{}",
                     orderBooking.getOrderTotal().subtract(cashBalance.getRemainMoney()),customer.getCustomerName() ) );
        }
        //设置可用余额、已用金额
        cashBalance.setRemainMoney(cashBalance.getRemainMoney().subtract(orderBooking.getOrderTotal()));
        cashBalance.setAmountUsed(cashBalance.getAmountUsed().add(orderBooking.getOrderTotal()));

        updateById(cashBalance);
    }
}
