package com.globalfx.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.trade.dto.CreateOrderDTO;
import com.globalfx.trade.entity.Order;
import com.globalfx.trade.vo.OrderVO;

public interface OrderService extends IService<Order> {

    OrderVO createOrder(CreateOrderDTO dto);

    PageResult<OrderVO> pageList(int pageNum, int pageSize, Long accountId, Integer status);

    OrderVO getDetail(Long id);

    void cancelOrder(Long id);
}
