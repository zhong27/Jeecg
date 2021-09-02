package org.jeecg.modules.cash.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.cash.CheckStatus;
import org.jeecg.modules.cash.entity.CashBalance;
import org.jeecg.modules.cash.entity.CashIncome;
import org.jeecg.modules.cash.mapper.CashBalanceMapper;
import org.jeecg.modules.cash.mapper.CashIncomeMapper;
import org.jeecg.modules.cash.service.ICashIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * @Description: 来款管理
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Service
public class CashIncomeServiceImpl extends ServiceImpl<CashIncomeMapper, CashIncome> implements ICashIncomeService {

    @Autowired
    CashBalanceServiceImpl cashBalanceService;

    @Transactional
    //来款审核
    public void checkIncome(String id){
        CashIncome cashIncome = getById(id);
        //判断来款审核状态
        if (StrUtil.equals(cashIncome.getStatus(),CheckStatus.FINISH.getValue())){
            throw new JeecgException("来款已审核！");
        }
        cashIncome.setStatus(CheckStatus.FINISH.getValue());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        cashIncome.setChecker(sysUser.getId());
        updateById(cashIncome);
        cashBalanceService.addBlance(cashIncome);
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        CashIncome cashIncome = getById(id);
        if (StrUtil.equals(cashIncome.getStatus(),CheckStatus.FINISH.getValue())){
            throw new JeecgException("来款已审核，不可删除！");
        }
        return super.removeById(id);
    }

}
