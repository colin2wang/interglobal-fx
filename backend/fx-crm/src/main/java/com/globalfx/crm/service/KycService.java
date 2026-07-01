package com.globalfx.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.crm.dto.KycReviewDTO;
import com.globalfx.crm.entity.KycApplication;
import com.globalfx.crm.vo.KycApplicationVO;

public interface KycService extends IService<KycApplication> {

    PageResult<KycApplicationVO> pageList(int pageNum, int pageSize, Integer status);

    void review(KycReviewDTO dto);
}
