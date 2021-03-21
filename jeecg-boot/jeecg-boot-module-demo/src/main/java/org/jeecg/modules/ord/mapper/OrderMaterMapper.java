package org.jeecg.modules.ord.mapper;

import java.util.List;
import org.jeecg.modules.ord.entity.OrderMater;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 提单材料明细
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
public interface OrderMaterMapper extends BaseMapper<OrderMater> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<OrderMater> selectByMainId(@Param("mainId") String mainId);

}
