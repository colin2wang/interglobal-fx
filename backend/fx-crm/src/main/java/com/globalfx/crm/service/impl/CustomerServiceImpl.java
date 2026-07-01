package com.globalfx.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.PageResult;
import com.globalfx.common.result.ResultCode;
import com.globalfx.common.util.IdGenerator;
import com.globalfx.crm.dto.CustomerDTO;
import com.globalfx.crm.entity.Customer;
import com.globalfx.crm.mapper.CustomerMapper;
import com.globalfx.crm.service.CustomerService;
import com.globalfx.crm.vo.CustomerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    private final CustomerMapper customerMapper;

    @Override
    public PageResult<CustomerVO> pageList(int pageNum, int pageSize, String keyword, Integer status) {
        Page<Customer> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(keyword != null && !keyword.isBlank(), w ->
                w.like(Customer::getFullName, keyword)
                        .or().like(Customer::getEmail, keyword)
                        .or().like(Customer::getPhone, keyword)
        )
                .eq(status != null, Customer::getStatus, status)
                .orderByDesc(Customer::getCreatedTime);
        Page<Customer> result = customerMapper.selectPage(page, wrapper);

        List<CustomerVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return new PageResult<>(voList, result.getTotal(), pageNum, pageSize);
    }

    @Override
    public CustomerVO getDetail(Long id) {
        Customer customer = customerMapper.selectById(id);
        if (customer == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "客户不存在");
        }
        return toVO(customer);
    }

    @Override
    public void createCustomer(CustomerDTO dto) {
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getEmail, dto.getEmail());
        if (customerMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.DATA_CONFLICT, "邮箱已存在");
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        customer.setCustomerNo(IdGenerator.nextCustomerNo());
        customer.setTenantId(1L);
        customer.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        customerMapper.insert(customer);
    }

    @Override
    public void updateCustomer(CustomerDTO dto) {
        Customer existing = customerMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "客户不存在");
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        customerMapper.updateById(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerMapper.deleteById(id);
    }

    private CustomerVO toVO(Customer customer) {
        CustomerVO vo = new CustomerVO();
        BeanUtils.copyProperties(customer, vo);
        return vo;
    }
}
