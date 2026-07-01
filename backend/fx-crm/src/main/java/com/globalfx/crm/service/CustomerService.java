package com.globalfx.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.common.result.PageResult;
import com.globalfx.crm.dto.CustomerDTO;
import com.globalfx.crm.entity.Customer;
import com.globalfx.crm.vo.CustomerVO;

public interface CustomerService extends IService<Customer> {

    PageResult<CustomerVO> pageList(int pageNum, int pageSize, String keyword, Integer status);

    CustomerVO getDetail(Long id);

    void createCustomer(CustomerDTO dto);

    void updateCustomer(CustomerDTO dto);

    void deleteCustomer(Long id);
}
