package com.globalfx.trade.service;

import com.globalfx.trade.vo.KlineVO;
import com.globalfx.trade.vo.QuoteVO;

import java.util.List;

public interface QuoteService {

    List<QuoteVO> getPrices(List<String> symbols);

    List<KlineVO> getKlines(String symbol, String interval, int limit);
}
