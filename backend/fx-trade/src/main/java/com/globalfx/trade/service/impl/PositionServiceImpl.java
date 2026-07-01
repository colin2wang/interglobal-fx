package com.globalfx.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.trade.dto.ClosePositionDTO;
import com.globalfx.trade.entity.Position;
import com.globalfx.trade.mapper.PositionMapper;
import com.globalfx.trade.service.PositionService;
import com.globalfx.trade.vo.PositionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    private final PositionMapper positionMapper;

    @Override
    public PageResult<PositionVO> pageList(int pageNum, int pageSize, Long accountId) {
        Page<Position> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Position> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(accountId != null, Position::getAccountId, accountId)
                .orderByDesc(Position::getOpenTime);
        Page<Position> result = positionMapper.selectPage(page, wrapper);

        List<PositionVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public void closePosition(ClosePositionDTO dto) {
        Position position = positionMapper.selectById(dto.getPositionId());
        if (position == null) {
            throw new BusinessException(ResultCode.POSITION_NOT_FOUND);
        }
        if (position.getStatus() != 1) {
            throw new BusinessException(ResultCode.POSITION_LOCKED, "持仓不在开放状态");
        }

        BigDecimal closeLot = dto.getCloseLot() != null ? dto.getCloseLot() : position.getCurrentLot();
        if (closeLot.compareTo(position.getCurrentLot()) > 0) {
            throw new BusinessException("平仓手数不能大于当前手数");
        }

        if (closeLot.compareTo(position.getCurrentLot()) == 0) {
            position.setStatus(2);
            position.setCurrentLot(BigDecimal.ZERO);
        } else {
            position.setStatus(3);
            position.setCurrentLot(position.getCurrentLot().subtract(closeLot));
        }
        position.setCloseLot(closeLot);
        positionMapper.updateById(position);
        log.info("Position closed: {}, closeLot: {}", position.getPositionNo(), closeLot);
    }

    private PositionVO toVO(Position position) {
        PositionVO vo = new PositionVO();
        BeanUtils.copyProperties(position, vo);
        return vo;
    }
}
