package com.globalfx.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.ResultCode;
import com.globalfx.trade.entity.Symbol;
import com.globalfx.trade.mapper.SymbolMapper;
import com.globalfx.trade.service.SymbolService;
import com.globalfx.trade.vo.SymbolVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SymbolServiceImpl extends ServiceImpl<SymbolMapper, Symbol> implements SymbolService {

    private final SymbolMapper symbolMapper;

    @Override
    public List<SymbolVO> listAll(Integer status) {
        LambdaQueryWrapper<Symbol> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null, Symbol::getStatus, status)
                .orderByAsc(Symbol::getSymbol);
        List<Symbol> symbols = symbolMapper.selectList(wrapper);
        return symbols.stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    public SymbolVO getDetail(Long id) {
        Symbol symbol = symbolMapper.selectById(id);
        if (symbol == null) {
            throw new BusinessException(ResultCode.SYMBOL_NOT_FOUND);
        }
        return toVO(symbol);
    }

    private SymbolVO toVO(Symbol symbol) {
        SymbolVO vo = new SymbolVO();
        BeanUtils.copyProperties(symbol, vo);
        return vo;
    }
}
