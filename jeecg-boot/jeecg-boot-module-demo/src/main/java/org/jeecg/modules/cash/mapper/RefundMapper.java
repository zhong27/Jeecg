package org.jeecg.modules.cash.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cash.entity.Refund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 退款管理
 * @Author: jeecg-boot
 * @Date:   2021-03-22
 * @Version: V1.0
 */
public interface RefundMapper extends BaseMapper<Refund> {

    public List<Refund> selectByBillNo(@Param("billNo") String billNo);
}
