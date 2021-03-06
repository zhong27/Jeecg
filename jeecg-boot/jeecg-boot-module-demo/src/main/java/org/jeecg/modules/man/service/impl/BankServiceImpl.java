package org.jeecg.modules.man.service.impl;

import org.jeecg.modules.man.entity.Bank;
import org.jeecg.modules.man.mapper.BankMapper;
import org.jeecg.modules.man.service.IBankService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 银行卡信息
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank> implements IBankService {
	
	@Autowired
	private BankMapper bankMapper;
	
	@Override
	public List<Bank> selectByMainId(String mainId) {
		return bankMapper.selectByMainId(mainId);
	}
}
