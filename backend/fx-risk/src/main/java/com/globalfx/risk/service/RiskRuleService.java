package com.globalfx.risk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.risk.dto.RiskRuleDTO;
import com.globalfx.risk.entity.RiskRule;
import com.globalfx.risk.vo.RiskRuleVO;

public interface RiskRuleService extends IService<RiskRule> {

    PageResult<RiskRuleVO> pageList(int pageNum, int pageSize, Integer status);

    void createRule(RiskRuleDTO dto);

    void updateRule(RiskRuleDTO dto);

    void deleteRule(Long id);
}
