package org.jeecg.modules.cash.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.cash.entity.CashBalance;
import org.jeecg.modules.cash.entity.CashIncome;
import org.jeecg.modules.cash.mapper.CashBalanceMapper;
import org.jeecg.modules.cash.service.ICashBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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

    public void addBlance(CashIncome cashIncome) {
        CashBalance cashBalanceMapperSelect = cashBalanceMapper.selectByCusotmerId(cashIncome.getCustomerName());
        //资金账户初始化
        if (ObjectUtil.isNull(cashBalanceMapperSelect)) {
            CashBalance cashBalance = new CashBalance();
            cashBalance.setCustomerName(cashIncome.getCustomerName());
            cashBalance.setTotalMoney(cashIncome.getIncome());
            cashBalance.setRemainMoney(cashIncome.getIncome());
            save(cashBalance);
        }
        if (ObjectUtil.isNotNull(cashBalanceMapperSelect)) {
            cashBalanceMapperSelect.setTotalMoney(
                    NumberUtil.add(cashIncome.getIncome(), cashBalanceMapperSelect.getTotalMoney()));
            cashBalanceMapperSelect.setRemainMoney(
                    NumberUtil.add(cashIncome.getIncome(), cashBalanceMapperSelect.getRemainMoney()));
            updateById(cashBalanceMapperSelect);
        }
    }
}
