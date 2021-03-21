package org.jeecg.modules.ord.service;

import org.jeecg.modules.ord.entity.OrderConsignee;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 收料人信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
public interface IOrderConsigneeService extends IService<OrderConsignee> {

	public List<OrderConsignee> selectByMainId(String mainId);
}
