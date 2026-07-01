package com.globalfx.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.common.util.IdGenerator;
import com.globalfx.crm.dto.IbPartnerDTO;
import com.globalfx.crm.entity.IbPartner;
import com.globalfx.crm.mapper.IbPartnerMapper;
import com.globalfx.crm.service.IbPartnerService;
import com.globalfx.crm.vo.IbPartnerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IbPartnerServiceImpl extends ServiceImpl<IbPartnerMapper, IbPartner> implements IbPartnerService {

    private final IbPartnerMapper ibPartnerMapper;

    @Override
    public PageResult<IbPartnerVO> pageList(int pageNum, int pageSize, Integer status) {
        Page<IbPartner> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<IbPartner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null, IbPartner::getStatus, status)
                .orderByDesc(IbPartner::getCreatedTime);
        Page<IbPartner> result = ibPartnerMapper.selectPage(page, wrapper);

        List<IbPartnerVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public IbPartnerVO getDetail(Long id) {
        IbPartner partner = ibPartnerMapper.selectById(id);
        if (partner == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "IB合作伙伴不存在");
        }
        return toVO(partner);
    }

    @Override
    public void createPartner(IbPartnerDTO dto) {
        IbPartner partner = new IbPartner();
        BeanUtils.copyProperties(dto, partner);
        partner.setPartnerNo(IdGenerator.nextAccountNo());
        partner.setTenantId(1L);
        partner.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        ibPartnerMapper.insert(partner);
    }

    @Override
    public void updatePartner(IbPartnerDTO dto) {
        IbPartner existing = ibPartnerMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "IB合作伙伴不存在");
        }

        IbPartner partner = new IbPartner();
        BeanUtils.copyProperties(dto, partner);
        ibPartnerMapper.updateById(partner);
    }

    @Override
    public void deletePartner(Long id) {
        ibPartnerMapper.deleteById(id);
    }

    private IbPartnerVO toVO(IbPartner partner) {
        IbPartnerVO vo = new IbPartnerVO();
        BeanUtils.copyProperties(partner, vo);
        return vo;
    }
}
