package com.globalfx.crm.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.crm.dto.IbPartnerDTO;
import com.globalfx.crm.service.IbPartnerService;
import com.globalfx.crm.vo.IbPartnerVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "IB合作伙伴管理")
@RestController
@RequestMapping("/api/v1/crm/ib-partners")
@RequiredArgsConstructor
public class IbController {

    private final IbPartnerService ibPartnerService;

    @Operation(summary = "分页查询IB合作伙伴")
    @GetMapping
    public Result<PageResult<IbPartnerVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) Integer status) {
        PageResult<IbPartnerVO> result = ibPartnerService.pageList(pageNum, pageSize, status);
        return Result.success(result);
    }

    @Operation(summary = "获取IB合作伙伴详情")
    @GetMapping("/{id}")
    public Result<IbPartnerVO> getDetail(@PathVariable Long id) {
        IbPartnerVO vo = ibPartnerService.getDetail(id);
        return Result.success(vo);
    }

    @Operation(summary = "创建IB合作伙伴")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody IbPartnerDTO dto) {
        ibPartnerService.createPartner(dto);
        return Result.success();
    }

    @Operation(summary = "更新IB合作伙伴")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody IbPartnerDTO dto) {
        dto.setId(id);
        ibPartnerService.updatePartner(dto);
        return Result.success();
    }

    @Operation(summary = "删除IB合作伙伴")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        ibPartnerService.deletePartner(id);
        return Result.success();
    }
}
