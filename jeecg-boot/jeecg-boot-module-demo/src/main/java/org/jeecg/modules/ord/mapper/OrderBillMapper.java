package org.jeecg.modules.ord.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ord.entity.OrderBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.ord.entity.OrderMater;

/**
 * @Description: 提单信息管理
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
public interface OrderBillMapper extends BaseMapper<OrderBill> {

    public OrderMater selectByOrderId(@Param("orderId") String orderId);

}
