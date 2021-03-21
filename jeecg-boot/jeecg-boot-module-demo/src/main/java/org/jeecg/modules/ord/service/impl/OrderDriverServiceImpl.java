package org.jeecg.modules.ord.service.impl;

import org.jeecg.modules.ord.entity.OrderDriver;
import org.jeecg.modules.ord.mapper.OrderDriverMapper;
import org.jeecg.modules.ord.service.IOrderDriverService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 司机信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Service
public class OrderDriverServiceImpl extends ServiceImpl<OrderDriverMapper, OrderDriver> implements IOrderDriverService {
	
	@Autowired
	private OrderDriverMapper orderDriverMapper;
	
	@Override
	public List<OrderDriver> selectByMainId(String mainId) {
		return orderDriverMapper.selectByMainId(mainId);
	}
}
