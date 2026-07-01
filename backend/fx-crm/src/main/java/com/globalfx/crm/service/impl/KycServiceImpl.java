package com.globalfx.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.crm.dto.KycReviewDTO;
import com.globalfx.crm.entity.Customer;
import com.globalfx.crm.entity.KycApplication;
import com.globalfx.crm.mapper.CustomerMapper;
import com.globalfx.crm.mapper.KycApplicationMapper;
import com.globalfx.crm.service.KycService;
import com.globalfx.crm.vo.KycApplicationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KycServiceImpl extends ServiceImpl<KycApplicationMapper, KycApplication> implements KycService {

    private final KycApplicationMapper kycApplicationMapper;
    private final CustomerMapper customerMapper;

    @Override
    public PageResult<KycApplicationVO> pageList(int pageNum, int pageSize, Integer status) {
        Page<KycApplication> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<KycApplication> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null, KycApplication::getStatus, status)
                .orderByDesc(KycApplication::getCreatedTime);
        Page<KycApplication> result = kycApplicationMapper.selectPage(page, wrapper);

        List<KycApplicationVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public void review(KycReviewDTO dto) {
        KycApplication application = kycApplicationMapper.selectById(dto.getApplicationId());
        if (application == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "KYC申请不存在");
        }

        application.setStatus(dto.getStatus());
        application.setRejectReason(dto.getRejectReason());
        application.setOperatorId(dto.getOperatorId());
        application.setOperateTime(LocalDateTime.now());
        application.setOperateRemark(dto.getOperateRemark());
        kycApplicationMapper.updateById(application);

        if (dto.getStatus() == 3) {
            log.info("KYC approved for customer: {}", application.getCustomerId());
        }

        log.info("KYC reviewed: applicationId={}, status={}", dto.getApplicationId(), dto.getStatus());
    }

    private KycApplicationVO toVO(KycApplication application) {
        KycApplicationVO vo = new KycApplicationVO();
        BeanUtils.copyProperties(application, vo);
        return vo;
    }
}
