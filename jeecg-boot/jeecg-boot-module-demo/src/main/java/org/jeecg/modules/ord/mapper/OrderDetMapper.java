package org.jeecg.modules.ord.mapper;

import java.util.List;
import org.jeecg.modules.ord.entity.OrderDet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 订单明细表
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
public interface OrderDetMapper extends BaseMapper<OrderDet> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<OrderDet> selectByMainId(@Param("mainId") String mainId);

}
