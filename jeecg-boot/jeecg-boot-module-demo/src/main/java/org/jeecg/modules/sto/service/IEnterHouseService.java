package org.jeecg.modules.sto.service;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.jeecg.modules.sto.entity.EnterHouse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 材料入库
 * @Author: jeecg-boot
 * @Date: 2021-02-27
 * @Version: V1.0
 */
public interface IEnterHouseService extends IService<EnterHouse> {

    @Override
    default boolean save(EnterHouse entity) {

        boolean flag =false;

        //库存根据长、宽、厚、材料号、单价、产品大类、材料名称校验、仓库
        QueryWrapper<EnterHouse> enterHouseQuery = new QueryWrapper<>();
        enterHouseQuery.lambda()
                .eq(EnterHouse::getProductClass, entity.getProductClass())
                .eq(EnterHouse::getProductName, entity.getProductName())
                .eq(EnterHouse::getMatLen, entity.getMatLen())
                .eq(EnterHouse::getMatNo, entity.getMatNo())
                .eq(EnterHouse::getMatThick, entity.getMatThick())
                .eq(EnterHouse::getMatWidth, entity.getMatWidth())
                .eq(EnterHouse::getPrice, entity.getPrice())
                .eq(EnterHouse::getWarehouse, entity.getWarehouse());
        EnterHouse enterHouse = getOne(enterHouseQuery);

        //无库存插入
        if (ObjectUtil.isNull(enterHouse)) {
            flag = IService.super.save(entity);
        }

        //已有库存更新重量，数量
        if (ObjectUtil.isNotNull(enterHouse)) {
            enterHouse.setMatNumber(entity.getMatNumber() + enterHouse.getMatNumber());
            enterHouse.setMatWeight(NumberUtil.add(entity.getMatWeight(), enterHouse.getMatWeight()));
            flag = updateById(enterHouse);
        }
        return flag;
    }
}
