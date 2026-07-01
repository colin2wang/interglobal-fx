package com.globalfx.clearing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.clearing.dto.ExecuteSettlementDTO;
import com.globalfx.clearing.dto.SettlementQueryDTO;
import com.globalfx.clearing.entity.DailySettlement;
import com.globalfx.clearing.vo.SettlementResultVO;
import com.globalfx.clearing.vo.SettlementVO;

import java.util.List;

public interface SettlementService extends IService<DailySettlement> {

    List<SettlementVO> pageList(SettlementQueryDTO query);

    SettlementResultVO executeDailySettlement(ExecuteSettlementDTO dto);
}
