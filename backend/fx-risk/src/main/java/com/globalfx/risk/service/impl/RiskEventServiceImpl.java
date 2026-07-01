package com.globalfx.risk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.risk.dto.HandleEventDTO;
import com.globalfx.risk.entity.RiskEvent;
import com.globalfx.risk.mapper.RiskEventMapper;
import com.globalfx.risk.service.RiskEventService;
import com.globalfx.risk.vo.RiskEventVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiskEventServiceImpl extends ServiceImpl<RiskEventMapper, RiskEvent> implements RiskEventService {

    private final RiskEventMapper riskEventMapper;

    @Override
    public PageResult<RiskEventVO> pageList(int pageNum, int pageSize, Integer status) {
        Page<RiskEvent> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<RiskEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null, RiskEvent::getStatus, status)
                .orderByDesc(RiskEvent::getCreatedTime);
        Page<RiskEvent> result = riskEventMapper.selectPage(page, wrapper);

        List<RiskEventVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public void handleEvent(HandleEventDTO dto) {
        RiskEvent event = riskEventMapper.selectById(dto.getEventId());
        if (event == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "事件不存在");
        }

        event.setOperateResult(dto.getOperateResult());
        event.setOperatorId(dto.getOperatorId());
        event.setOperateTime(LocalDateTime.now());
        event.setStatus(2);
        riskEventMapper.updateById(event);
        log.info("Risk event handled: {}, result: {}", event.getEventNo(), dto.getOperateResult());
    }

    private RiskEventVO toVO(RiskEvent event) {
        RiskEventVO vo = new RiskEventVO();
        BeanUtils.copyProperties(event, vo);
        return vo;
    }
}
