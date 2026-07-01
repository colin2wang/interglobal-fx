package com.globalfx.risk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.risk.dto.HandleEventDTO;
import com.globalfx.risk.entity.RiskEvent;
import com.globalfx.risk.vo.RiskEventVO;

public interface RiskEventService extends IService<RiskEvent> {

    PageResult<RiskEventVO> pageList(int pageNum, int pageSize, Integer status);

    void handleEvent(HandleEventDTO dto);
}
