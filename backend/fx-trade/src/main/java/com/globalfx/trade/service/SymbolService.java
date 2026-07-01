package com.globalfx.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.trade.entity.Symbol;
import com.globalfx.trade.vo.SymbolVO;

import java.util.List;

public interface SymbolService extends IService<Symbol> {

    List<SymbolVO> listAll(Integer status);

    SymbolVO getDetail(Long id);
}
