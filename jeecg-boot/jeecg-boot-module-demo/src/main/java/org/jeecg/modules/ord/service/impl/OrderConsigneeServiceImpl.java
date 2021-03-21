package org.jeecg.modules.ord.service.impl;

import org.jeecg.modules.ord.entity.OrderConsignee;
import org.jeecg.modules.ord.mapper.OrderConsigneeMapper;
import org.jeecg.modules.ord.service.IOrderConsigneeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 收料人信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Service
public class OrderConsigneeServiceImpl extends ServiceImpl<OrderConsigneeMapper, OrderConsignee> implements IOrderConsigneeService {
	
	@Autowired
	private OrderConsigneeMapper orderConsigneeMapper;
	
	@Override
	public List<OrderConsignee> selectByMainId(String mainId) {
		return orderConsigneeMapper.selectByMainId(mainId);
	}
}
