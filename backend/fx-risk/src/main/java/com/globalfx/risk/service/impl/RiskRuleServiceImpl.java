package com.globalfx.risk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.risk.dto.RiskRuleDTO;
import com.globalfx.risk.entity.RiskRule;
import com.globalfx.risk.mapper.RiskRuleMapper;
import com.globalfx.risk.service.RiskRuleService;
import com.globalfx.risk.vo.RiskRuleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiskRuleServiceImpl extends ServiceImpl<RiskRuleMapper, RiskRule> implements RiskRuleService {

    private final RiskRuleMapper riskRuleMapper;

    @Override
    public PageResult<RiskRuleVO> pageList(int pageNum, int pageSize, Integer status) {
        Page<RiskRule> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<RiskRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null, RiskRule::getStatus, status)
                .orderByAsc(RiskRule::getPriority);
        Page<RiskRule> result = riskRuleMapper.selectPage(page, wrapper);

        List<RiskRuleVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public void createRule(RiskRuleDTO dto) {
        LambdaQueryWrapper<RiskRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RiskRule::getRuleCode, dto.getRuleCode());
        if (riskRuleMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.DATA_CONFLICT, "规则编码已存在");
        }

        RiskRule rule = new RiskRule();
        BeanUtils.copyProperties(dto, rule);
        rule.setTenantId(1L);
        rule.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        rule.setActionLevel(dto.getActionLevel() != null ? dto.getActionLevel() : 1);
        rule.setPriority(dto.getPriority() != null ? dto.getPriority() : 100);
        riskRuleMapper.insert(rule);
        log.info("Risk rule created: {}", rule.getRuleCode());
    }

    @Override
    public void updateRule(RiskRuleDTO dto) {
        RiskRule existing = riskRuleMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "规则不存在");
        }

        RiskRule rule = new RiskRule();
        BeanUtils.copyProperties(dto, rule);
        riskRuleMapper.updateById(rule);
    }

    @Override
    public void deleteRule(Long id) {
        riskRuleMapper.deleteById(id);
    }

    private RiskRuleVO toVO(RiskRule rule) {
        RiskRuleVO vo = new RiskRuleVO();
        BeanUtils.copyProperties(rule, vo);
        return vo;
    }
}
