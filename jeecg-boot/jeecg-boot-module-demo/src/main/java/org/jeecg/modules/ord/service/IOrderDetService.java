package org.jeecg.modules.ord.service;

import org.jeecg.modules.ord.entity.OrderDet;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 订单明细表
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
public interface IOrderDetService extends IService<OrderDet> {

	public List<OrderDet> selectByMainId(String mainId);
}
