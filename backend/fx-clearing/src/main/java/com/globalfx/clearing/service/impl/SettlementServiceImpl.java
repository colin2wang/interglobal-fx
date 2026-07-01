package com.globalfx.clearing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.clearing.dto.ExecuteSettlementDTO;
import com.globalfx.clearing.dto.SettlementQueryDTO;
import com.globalfx.clearing.entity.DailySettlement;
import com.globalfx.clearing.mapper.DailySettlementMapper;
import com.globalfx.clearing.service.SettlementService;
import com.globalfx.clearing.vo.SettlementResultVO;
import com.globalfx.clearing.vo.SettlementVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementServiceImpl extends ServiceImpl<DailySettlementMapper, DailySettlement> implements SettlementService {

    private final DailySettlementMapper dailySettlementMapper;

    @Override
    public List<SettlementVO> pageList(SettlementQueryDTO query) {
        LambdaQueryWrapper<DailySettlement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getAccountId() != null, DailySettlement::getAccountId, query.getAccountId())
                .ge(query.getStartDate() != null, DailySettlement::getSettlementDate, query.getStartDate())
                .le(query.getEndDate() != null, DailySettlement::getSettlementDate, query.getEndDate())
                .eq(query.getStatus() != null, DailySettlement::getStatus, query.getStatus())
                .orderByDesc(DailySettlement::getSettlementDate);
        List<DailySettlement> settlements = dailySettlementMapper.selectList(wrapper);
        return settlements.stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    public SettlementResultVO executeDailySettlement(ExecuteSettlementDTO dto) {
        LocalDate settlementDate = dto.getSettlementDate();
        log.info("Executing daily settlement for date: {}", settlementDate);

        LambdaQueryWrapper<DailySettlement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DailySettlement::getSettlementDate, settlementDate);
        long existingCount = dailySettlementMapper.selectCount(wrapper);

        int successCount = 0;
        int failCount = 0;

        if (existingCount > 0) {
            log.warn("Settlement already exists for date: {}", settlementDate);
            return SettlementResultVO.builder()
                    .totalAccounts(0)
                    .successCount(0)
                    .failCount(0)
                    .message("该日期已结算")
                    .build();
        }

        log.info("Daily settlement completed: success={}, fail={}", successCount, failCount);

        return SettlementResultVO.builder()
                .totalAccounts(successCount + failCount)
                .successCount(successCount)
                .failCount(failCount)
                .message("日结算完成")
                .build();
    }

    private SettlementVO toVO(DailySettlement settlement) {
        SettlementVO vo = new SettlementVO();
        BeanUtils.copyProperties(settlement, vo);
        return vo;
    }
}
