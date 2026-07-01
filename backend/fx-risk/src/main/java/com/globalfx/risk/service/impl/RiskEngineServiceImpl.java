package com.globalfx.risk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.globalfx.common.constant.TradeConstant;
import com.globalfx.risk.dto.OrderCheckDTO;
import com.globalfx.risk.entity.RiskRule;
import com.globalfx.risk.mapper.RiskRuleMapper;
import com.globalfx.risk.service.RiskEngineService;
import com.globalfx.risk.vo.RiskCheckResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiskEngineServiceImpl implements RiskEngineService {

    private final RiskRuleMapper riskRuleMapper;

    @Override
    public RiskCheckResult checkOrder(OrderCheckDTO dto) {
        LambdaQueryWrapper<RiskRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RiskRule::getStatus, 1)
                .le(RiskRule::getEffectiveStart, LocalDateTime.now())
                .or().isNull(RiskRule::getEffectiveStart)
                .ge(RiskRule::getEffectiveEnd, LocalDateTime.now())
                .or().isNull(RiskRule::getEffectiveEnd)
                .orderByAsc(RiskRule::getPriority);
        List<RiskRule> rules = riskRuleMapper.selectList(wrapper);

        for (RiskRule rule : rules) {
            if (rule.getMinValue() != null && dto.getLotSize().compareTo(rule.getMinValue()) < 0) {
                return RiskCheckResult.builder()
                        .passed(false)
                        .action(rule.getAction())
                        .message("下单手数低于最低限制: " + rule.getMinValue())
                        .ruleCode(rule.getRuleCode())
                        .ruleId(rule.getId())
                        .build();
            }
            if (rule.getMaxValue() != null && dto.getLotSize().compareTo(rule.getMaxValue()) > 0) {
                return RiskCheckResult.builder()
                        .passed(false)
                        .action(rule.getAction())
                        .message("下单手数超过最高限制: " + rule.getMaxValue())
                        .ruleCode(rule.getRuleCode())
                        .ruleId(rule.getId())
                        .build();
            }
        }

        BigDecimal marginRequired = dto.getLotSize().multiply(new BigDecimal(TradeConstant.CONTRACT_SIZE))
                .divide(dto.getCurrentBalance(), 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100));

        if (marginRequired.compareTo(new BigDecimal(TradeConstant.MARGIN_CALL_LEVEL)) > 0) {
            return RiskCheckResult.builder()
                    .passed(false)
                    .action(1)
                    .message("保证金水平不足")
                    .ruleCode("MARGIN_CHECK")
                    .build();
        }

        return RiskCheckResult.builder()
                .passed(true)
                .action(0)
                .message("风控检查通过")
                .build();
    }
}
