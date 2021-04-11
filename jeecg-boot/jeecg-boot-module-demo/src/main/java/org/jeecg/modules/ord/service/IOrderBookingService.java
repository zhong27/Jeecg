package org.jeecg.modules.ord.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.man.AccountStatusEnum;
import org.jeecg.modules.man.entity.Customer;
import org.jeecg.modules.man.mapper.CustomerMapper;
import org.jeecg.modules.man.service.impl.CustomerServiceImpl;
import org.jeecg.modules.ord.OrderNo;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.entity.OrderBooking;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Description: 订单预定
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
public interface IOrderBookingService extends IService<OrderBooking> {

	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

}
