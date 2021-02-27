package org.jeecg.modules.per.service;

import org.jeecg.modules.per.entity.Bank;
import org.jeecg.modules.per.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2021-02-26
 * @Version: V1.0
 */
public interface ICustomerService extends IService<Customer> {

	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


}
