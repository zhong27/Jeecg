package org.jeecg.modules.man.service.impl;

import org.jeecg.modules.man.entity.Customer;
import org.jeecg.modules.man.mapper.BankMapper;
import org.jeecg.modules.man.mapper.CustomerMapper;
import org.jeecg.modules.man.service.ICustomerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private BankMapper bankMapper;
	
	@Override
	@Transactional
	public void delMain(String id) {
		bankMapper.deleteByMainId(id);
		customerMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			bankMapper.deleteByMainId(id.toString());
			customerMapper.deleteById(id);
		}
	}
	
}
