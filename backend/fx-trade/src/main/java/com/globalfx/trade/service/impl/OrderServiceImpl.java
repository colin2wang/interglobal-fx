package com.globalfx.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.common.util.IdGenerator;
import com.globalfx.trade.dto.CreateOrderDTO;
import com.globalfx.trade.entity.Order;
import com.globalfx.trade.mapper.OrderMapper;
import com.globalfx.trade.service.OrderService;
import com.globalfx.trade.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final OrderMapper orderMapper;

    @Override
    public OrderVO createOrder(CreateOrderDTO dto) {
        Order order = new Order();
        BeanUtils.copyProperties(dto, order);
        order.setOrderNo(UUID.randomUUID());
        order.setTenantId(1L);
        order.setFilledLot(new java.math.BigDecimal("0"));
        order.setSlippagePoints(0);
        order.setSlippageType(1);
        order.setStatus(1);
        order.setExecutionType(1);
        order.setTimeInForce(dto.getTimeInForce() != null ? dto.getTimeInForce() : 1);
        order.setExecutionMode(1);
        order.setChannel(1);
        order.setRiskCheckStatus(1);
        orderMapper.insert(order);

        log.info("Order created: {}, accountId: {}, symbol: {}", order.getOrderNo(), dto.getAccountId(), dto.getSymbolId());
        return toVO(order);
    }

    @Override
    public PageResult<OrderVO> pageList(int pageNum, int pageSize, Long accountId, Integer status) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(accountId != null, Order::getAccountId, accountId)
                .eq(status != null, Order::getStatus, status)
                .orderByDesc(Order::getCreatedTime);
        Page<Order> result = orderMapper.selectPage(page, wrapper);

        List<OrderVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public OrderVO getDetail(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        return toVO(order);
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        if (order.getStatus() != 1) {
            throw new BusinessException(ResultCode.ORDER_ALREADY_FILLED);
        }
        order.setStatus(4);
        orderMapper.updateById(order);
        log.info("Order cancelled: {}", order.getOrderNo());
    }

    private OrderVO toVO(Order order) {
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        return vo;
    }
}
