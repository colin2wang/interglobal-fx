package com.globalfx.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.trade.dto.ClosePositionDTO;
import com.globalfx.trade.entity.Position;
import com.globalfx.trade.vo.PositionVO;

public interface PositionService extends IService<Position> {

    PageResult<PositionVO> pageList(int pageNum, int pageSize, Long accountId);

    void closePosition(ClosePositionDTO dto);
}
