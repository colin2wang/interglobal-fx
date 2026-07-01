package com.globalfx.trade.service.impl;

import com.globalfx.trade.service.QuoteService;
import com.globalfx.trade.vo.KlineVO;
import com.globalfx.trade.vo.QuoteVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public List<QuoteVO> getPrices(List<String> symbols) {
        List<QuoteVO> prices = new ArrayList<>();
        for (String symbol : symbols) {
            String bidStr = redisTemplate.opsForHash().get("quote:" + symbol, "bid").toString();
            String askStr = redisTemplate.opsForHash().get("quote:" + symbol, "ask").toString();
            if (bidStr != null && askStr != null) {
                BigDecimal bid = new BigDecimal(bidStr);
                BigDecimal ask = new BigDecimal(askStr);
                prices.add(QuoteVO.builder()
                        .symbol(symbol)
                        .bid(bid)
                        .ask(ask)
                        .spread(ask.subtract(bid).setScale(5, RoundingMode.HALF_UP))
                        .timestamp(Instant.now().toEpochMilli())
                        .build());
            }
        }
        return prices;
    }

    @Override
    public List<KlineVO> getKlines(String symbol, String interval, int limit) {
        String key = "kline:" + symbol + ":" + interval;
        Set<String> entries = redisTemplate.opsForZSet().reverseRange(key, 0, limit - 1);
        List<KlineVO> klines = new ArrayList<>();
        if (entries != null) {
            for (String entry : entries) {
                String[] parts = entry.split(",");
                if (parts.length >= 6) {
                    klines.add(KlineVO.builder()
                            .openTime(Long.parseLong(parts[0]))
                            .open(new BigDecimal(parts[1]))
                            .high(new BigDecimal(parts[2]))
                            .low(new BigDecimal(parts[3]))
                            .close(new BigDecimal(parts[4]))
                            .volume(new BigDecimal(parts[5]))
                            .build());
                }
            }
        }
        return klines;
    }
}
