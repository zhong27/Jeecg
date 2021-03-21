package org.jeecg.modules.ord.service;

import org.jeecg.modules.ord.entity.OrderDriver;
import org.jeecg.modules.ord.entity.OrderMater;
import org.jeecg.modules.ord.entity.OrderConsignee;
import org.jeecg.modules.ord.entity.OrderBill;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 提单信息管理
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
public interface IOrderBillService extends IService<OrderBill> {

	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


}
