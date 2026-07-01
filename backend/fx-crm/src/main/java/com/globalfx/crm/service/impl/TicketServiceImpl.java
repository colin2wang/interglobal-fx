package com.globalfx.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.common.util.IdGenerator;
import com.globalfx.crm.dto.TicketDTO;
import com.globalfx.crm.dto.TicketReplyDTO;
import com.globalfx.crm.entity.Ticket;
import com.globalfx.crm.entity.TicketReply;
import com.globalfx.crm.mapper.TicketMapper;
import com.globalfx.crm.mapper.TicketReplyMapper;
import com.globalfx.crm.service.TicketService;
import com.globalfx.crm.vo.TicketVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketService {

    private final TicketMapper ticketMapper;
    private final TicketReplyMapper ticketReplyMapper;

    @Override
    public PageResult<TicketVO> pageList(int pageNum, int pageSize, Long customerId, Integer status) {
        Page<Ticket> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Ticket> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(customerId != null, Ticket::getCustomerId, customerId)
                .eq(status != null, Ticket::getStatus, status)
                .orderByDesc(Ticket::getCreatedTime);
        Page<Ticket> result = ticketMapper.selectPage(page, wrapper);

        List<TicketVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public TicketVO getDetail(Long id) {
        Ticket ticket = ticketMapper.selectById(id);
        if (ticket == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "工单不存在");
        }
        return toVO(ticket);
    }

    @Override
    public void createTicket(TicketDTO dto) {
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(dto, ticket);
        ticket.setTicketNo(IdGenerator.nextTicketNo());
        ticket.setTenantId(1L);
        ticket.setPriority(dto.getPriority() != null ? dto.getPriority() : 2);
        ticket.setStatus(1);
        ticketMapper.insert(ticket);
    }

    @Override
    public void updateTicket(TicketDTO dto) {
        Ticket existing = ticketMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "工单不存在");
        }

        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(dto, ticket);
        ticketMapper.updateById(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reply(TicketReplyDTO dto) {
        Ticket ticket = ticketMapper.selectById(dto.getTicketId());
        if (ticket == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "工单不存在");
        }

        TicketReply reply = new TicketReply();
        reply.setTicketId(dto.getTicketId());
        reply.setTenantId(ticket.getTenantId());
        reply.setContent(dto.getContent());
        reply.setReplyType(dto.getReplyType() != null ? dto.getReplyType() : 1);
        reply.setCreatedBy(dto.getCreatedBy());
        reply.setCreatedTime(LocalDateTime.now());
        ticketReplyMapper.insert(reply);

        if (ticket.getFirstReplyTime() == null) {
            ticket.setFirstReplyTime(LocalDateTime.now());
        }
        ticket.setStatus(2);
        ticketMapper.updateById(ticket);
    }

    private TicketVO toVO(Ticket ticket) {
        TicketVO vo = new TicketVO();
        BeanUtils.copyProperties(ticket, vo);
        return vo;
    }
}
