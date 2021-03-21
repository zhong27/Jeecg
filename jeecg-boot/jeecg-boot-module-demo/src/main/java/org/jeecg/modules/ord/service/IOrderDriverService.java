package org.jeecg.modules.ord.service;

import org.jeecg.modules.ord.entity.OrderDriver;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 司机信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
public interface IOrderDriverService extends IService<OrderDriver> {

	public List<OrderDriver> selectByMainId(String mainId);
}
