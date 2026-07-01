package com.globalfx.crm.controller;

import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.Result;
import com.globalfx.crm.dto.CustomerDTO;
import com.globalfx.crm.service.CustomerService;
import com.globalfx.crm.vo.CustomerVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "客户管理")
@RestController
@RequestMapping("/api/v1/crm/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "分页查询客户")
    @GetMapping
    public Result<PageResult<CustomerVO>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {
        PageResult<CustomerVO> result = customerService.pageList(pageNum, pageSize, keyword, status);
        return Result.success(result);
    }

    @Operation(summary = "获取客户详情")
    @GetMapping("/{id}")
    public Result<CustomerVO> getDetail(@PathVariable Long id) {
        CustomerVO vo = customerService.getDetail(id);
        return Result.success(vo);
    }

    @Operation(summary = "创建客户")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody CustomerDTO dto) {
        customerService.createCustomer(dto);
        return Result.success();
    }

    @Operation(summary = "更新客户")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO dto) {
        dto.setId(id);
        customerService.updateCustomer(dto);
        return Result.success();
    }

    @Operation(summary = "删除客户")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return Result.success();
    }
}
