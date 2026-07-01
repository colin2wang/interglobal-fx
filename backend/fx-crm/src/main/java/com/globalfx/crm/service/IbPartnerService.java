package com.globalfx.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.crm.dto.IbPartnerDTO;
import com.globalfx.crm.entity.IbPartner;
import com.globalfx.crm.vo.IbPartnerVO;

public interface IbPartnerService extends IService<IbPartner> {

    PageResult<IbPartnerVO> pageList(int pageNum, int pageSize, Integer status);

    IbPartnerVO getDetail(Long id);

    void createPartner(IbPartnerDTO dto);

    void updatePartner(IbPartnerDTO dto);

    void deletePartner(Long id);
}
