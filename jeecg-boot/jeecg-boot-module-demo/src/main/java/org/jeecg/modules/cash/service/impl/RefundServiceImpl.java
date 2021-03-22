package org.jeecg.modules.cash.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.cash.*;
import org.jeecg.modules.cash.entity.Refund;
import org.jeecg.modules.cash.mapper.RefundMapper;
import org.jeecg.modules.cash.service.IRefundService;
import org.jeecg.modules.ord.BillStatusEnum;
import org.jeecg.modules.ord.entity.OrderBill;
import org.jeecg.modules.ord.mapper.OrderBillMapper;
import org.jeecg.modules.ord.service.impl.OrderBillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 退款管理
 * @Author: jeecg-boot
 * @Date: 2021-03-22
 * @Version: V1.0
 */
@Service
public class RefundServiceImpl extends ServiceImpl<RefundMapper, Refund> implements IRefundService {

    @Autowired
    private OrderBillServiceImpl orderBillService;
    @Autowired
    private RefundMapper refundMapper;

    public String addRefund(String id) {
        String result = "";
        //获取提单信息
        OrderBill orderBill = orderBillService.getById(id);
        //查询退款是否存在
        List<Refund> refundList = refundMapper.selectByBillNo(orderBill.getBillNo());
        Refund refund = new Refund();
        if (CollectionUtil.isEmpty(refundList)) {
            //设置退款编号
            String refundNo = RefundPreEnum.RE.getValue();
            refundNo += DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");

            refund.setRefundNo(refundNo)
                    .setBillNo(orderBill.getBillNo())
                    .setCustomer(orderBill.getCustomerId())
                    .setRefundMoney(orderBill.getTotal())
                    .setRefundStatus(RefundCheckEnum.UN_CHECK.getValue());
            if (StrUtil.equals(orderBill.getBillStatus(), BillStatusEnum.TAKING.getValue())) {
                refund.setRefundType(RefundTypeEnum.REFUND.getValue())
                        .setRefundAgree(RefundAgreeEnum.AGREE.getValue());
            }
            if (StrUtil.equals(orderBill.getBillStatus(), BillStatusEnum.PICKED_UP.getValue())) {
                refund.setRefundType(RefundTypeEnum.REFUND_GOODS.getValue())
                        .setRefundAgree(RefundAgreeEnum.DISAGREE.getValue());
            }
        } else {
            throw new JeecgException("退单已存在，请勿重复退单！");
        }

        if (super.save(refund)) {
            result = "退单申请成功！";
        } else {
            result = "退单申请失败！";
        }
        return result;
    }
}
