package com.globalfx.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.crm.dto.TicketDTO;
import com.globalfx.crm.dto.TicketReplyDTO;
import com.globalfx.crm.entity.Ticket;
import com.globalfx.crm.vo.TicketVO;

public interface TicketService extends IService<Ticket> {

    PageResult<TicketVO> pageList(int pageNum, int pageSize, Long customerId, Integer status);

    TicketVO getDetail(Long id);

    void createTicket(TicketDTO dto);

    void updateTicket(TicketDTO dto);

    void deleteTicket(Long id);

    void reply(TicketReplyDTO dto);
}
