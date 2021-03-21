package org.jeecg.modules.ord.service;

import org.jeecg.modules.ord.entity.OrderMater;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 提单材料明细
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
public interface IOrderMaterService extends IService<OrderMater> {

	public List<OrderMater> selectByMainId(String mainId);
}
