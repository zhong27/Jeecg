package org.jeecg.modules.sto.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.cash.RefundAgreeEnum;
import org.jeecg.modules.cash.entity.Refund;
import org.jeecg.modules.cash.service.impl.RefundServiceImpl;
import org.jeecg.modules.ord.PayStatusEnum;
import org.jeecg.modules.ord.entity.OrderBill;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.entity.OrderMater;
import org.jeecg.modules.ord.mapper.OrderMaterMapper;
import org.jeecg.modules.ord.service.impl.OrderBillServiceImpl;
import org.jeecg.modules.ord.service.impl.OrderBookingServiceImpl;
import org.jeecg.modules.ord.service.impl.OrderDetServiceImpl;
import org.jeecg.modules.sto.entity.EnterHouse;
import org.jeecg.modules.sto.mapper.EnterHouseMapper;
import org.jeecg.modules.sto.service.IEnterHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 材料入库
 * @Author: jeecg-boot
 * @Date: 2021-02-27
 * @Version: V1.0
 */
@Service
public class EnterHouseServiceImpl extends ServiceImpl<EnterHouseMapper, EnterHouse> implements IEnterHouseService {

    @Autowired
    OrderBookingServiceImpl orderBookingService;

    @Autowired
    private OrderMaterMapper orderMaterMapper;

    @Autowired
    private RefundServiceImpl refundService;

    @Autowired
    private OrderBillServiceImpl orderBillService;

    @Transactional
    public void inventoryCheck(OrderDet orderDet) {

        //通过仓库、长度、宽度、厚度、材料号、产品大类、产品名称，校验库存
        QueryWrapper<EnterHouse> queryWrapperOrderDet = new QueryWrapper<>();
        queryWrapperOrderDet.lambda().eq(EnterHouse::getMatWidth, orderDet.getMatWidth())
                .eq(EnterHouse::getMatLen, orderDet.getMatLen())
                .eq(EnterHouse::getMatThick, orderDet.getMatThick())
                .eq(EnterHouse::getMatNo, orderDet.getMatNo())
                .eq(EnterHouse::getMatType, orderDet.getMatType())
                .eq(EnterHouse::getMatName, orderDet.getMatName())
                .eq(EnterHouse::getWarehouse, orderDet.getWarehouse());
        EnterHouse selectEnterHouse = getBaseMapper().selectOne(queryWrapperOrderDet);
        //判断库存重量
        if (selectEnterHouse.getMatWeight().compareTo(orderDet.getWeight()) == -1) {
            throw new JeecgException(StrUtil.format("材料库存不足！当前可下单重量：{}", selectEnterHouse.getMatWeight()));
        }
        selectEnterHouse.setTotalWeight(selectEnterHouse.getTotalWeight().subtract(orderDet.getWeight()));
        selectEnterHouse.setMatNumber(selectEnterHouse.getTotalWeight().divide(selectEnterHouse.getMatWeight(),3, BigDecimal.ROUND_HALF_UP));
        updateById(selectEnterHouse);

    }

    @Transactional
    public void updateEnterHouse(OrderDet orderDet) {
        //通过仓库、长度、宽度、厚度、材料号、产品大类、产品名称，校验库存
        QueryWrapper<EnterHouse> queryWrapperOrderDet = new QueryWrapper<>();
        queryWrapperOrderDet.lambda().eq(EnterHouse::getMatWidth, orderDet.getMatWidth())
                .eq(EnterHouse::getMatLen, orderDet.getMatLen())
                .eq(EnterHouse::getMatThick, orderDet.getMatThick())
                .eq(EnterHouse::getMatNo, orderDet.getMatNo())
                .eq(EnterHouse::getMatType, orderDet.getMatType())
                .eq(EnterHouse::getMatName, orderDet.getMatName())
                .eq(EnterHouse::getWarehouse, orderDet.getWarehouse());
        EnterHouse enterHouse = getBaseMapper().selectOne(queryWrapperOrderDet);
        enterHouse.setTotalWeight(enterHouse.getTotalWeight().add(orderDet.getWeight()));
        enterHouse.setMatNumber(enterHouse.getTotalWeight().divide(enterHouse.getMatWeight(),3, BigDecimal.ROUND_HALF_UP));
        updateById(enterHouse);
    }

    @Transactional
    public void refundGoods(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<EnterHouse> enterHouseList = new ArrayList<>();
        for (String id : idList) {
            Refund refund = refundService.getById(id);
            if (StrUtil.equals(refund.getRefundAgree(), RefundAgreeEnum.DISAGREE.getValue())) {
                OrderBill orderBill = orderBillService.getBaseMapper().selectByBillNo(refund.getBillNo());
                List<OrderMater> orderDetList = orderMaterMapper.selectByMainId(orderBill.getId());
                OrderMater orderMater = orderDetList.get(0);
                //通过仓库、长度、宽度、厚度、材料号、产品大类、产品名称，校验库存
                QueryWrapper<EnterHouse> queryWrapperOrderDet = new QueryWrapper<>();
                queryWrapperOrderDet.lambda().eq(EnterHouse::getMatWidth, orderMater.getMatWidth())
                        .eq(EnterHouse::getMatLen, orderMater.getMatLen())
                        .eq(EnterHouse::getMatThick, orderMater.getMatThick())
                        .eq(EnterHouse::getMatNo, orderMater.getMatNo())
                        .eq(EnterHouse::getMatType, orderMater.getMatType())
                        .eq(EnterHouse::getMatName, orderMater.getMatName())
                        .eq(EnterHouse::getWarehouse, orderMater.getWarehouse());
                EnterHouse selectEnterHouse = getOne(queryWrapperOrderDet);
                EnterHouse enterHouse = selectEnterHouse.setMatWeight(selectEnterHouse.getMatWeight().add(orderMater.getWeight()));
                enterHouseList.add(enterHouse);
                refund.setRefundAgree(RefundAgreeEnum.AGREE.getValue());
                refundService.updateById(refund);
            }

        }
        if (CollectionUtil.isEmpty(enterHouseList)){
            throw new JeecgException("已全部确认，无可确认的退货！");
        }
        updateBatchById(enterHouseList);

    }
}
