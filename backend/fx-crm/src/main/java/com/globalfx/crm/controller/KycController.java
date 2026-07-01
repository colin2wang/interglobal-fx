package com.globalfx.crm.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.crm.dto.KycReviewDTO;
import com.globalfx.crm.service.KycService;
import com.globalfx.crm.vo.KycApplicationVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "KYC管理")
@RestController
@RequestMapping("/api/v1/crm/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    @Operation(summary = "分页查询KYC申请")
    @GetMapping
    public Result<PageResult<KycApplicationVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Integer status) {
        PageResult<KycApplicationVO> result = kycService.pageList(pageNum, pageSize, status);
        return Result.success(result);
    }

    @Operation(summary = "获取KYC申请详情")
    @GetMapping("/{id}")
    public Result<KycApplicationVO> getDetail(@PathVariable Long id) {
        KycApplicationVO vo = kycService.getById(id);
        return Result.success(vo);
    }

    @Operation(summary = "审核KYC申请")
    @PostMapping("/review")
    public Result<Void> review(@Valid @RequestBody KycReviewDTO dto) {
        kycService.review(dto);
        return Result.success();
    }
}
