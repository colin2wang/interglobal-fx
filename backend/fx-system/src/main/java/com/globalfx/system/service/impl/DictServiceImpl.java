package com.globalfx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.globalfx.common.exception.BusinessException;
import com.globalfx.common.result.ResultCode;
import com.globalfx.system.dto.DictDataDTO;
import com.globalfx.system.dto.DictTypeDTO;
import com.globalfx.system.entity.DictData;
import com.globalfx.system.entity.DictType;
import com.globalfx.system.mapper.DictDataMapper;
import com.globalfx.system.mapper.DictTypeMapper;
import com.globalfx.system.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictService {

    private final DictTypeMapper dictTypeMapper;
    private final DictDataMapper dictDataMapper;

    @Override
    public List<DictType> listDictTypes(Integer status) {
        LambdaQueryWrapper<DictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(status != null, DictType::getStatus, status)
                .orderByAsc(DictType::getDictType);
        return dictTypeMapper.selectList(wrapper);
    }

    @Override
    public void createDictType(DictTypeDTO dto) {
        LambdaQueryWrapper<DictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictType::getDictType, dto.getDictType());
        if (dictTypeMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ResultCode.DATA_CONFLICT, "字典类型已存在");
        }

        DictType dictType = new DictType();
        BeanUtils.copyProperties(dto, dictType);
        dictType.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        dictTypeMapper.insert(dictType);
    }

    @Override
    public void updateDictType(DictTypeDTO dto) {
        DictType existing = dictTypeMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "字典类型不存在");
        }

        DictType dictType = new DictType();
        BeanUtils.copyProperties(dto, dictType);
        dictTypeMapper.updateById(dictType);
    }

    @Override
    public void deleteDictType(Long id) {
        dictTypeMapper.deleteById(id);
    }

    @Override
    public List<DictData> listDictDataByType(String dictType) {
        LambdaQueryWrapper<DictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictData::getDictType, dictType)
                .orderByAsc(DictData::getSortOrder);
        return dictDataMapper.selectList(wrapper);
    }

    @Override
    public void createDictData(DictDataDTO dto) {
        DictData dictData = new DictData();
        BeanUtils.copyProperties(dto, dictData);
        dictData.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        dictDataMapper.insert(dictData);
    }

    @Override
    public void updateDictData(DictDataDTO dto) {
        DictData existing = dictDataMapper.selectById(dto.getId());
        if (existing == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "字典数据不存在");
        }

        DictData dictData = new DictData();
        BeanUtils.copyProperties(dto, dictData);
        dictDataMapper.updateById(dictData);
    }

    @Override
    public void deleteDictData(Long id) {
        dictDataMapper.deleteById(id);
    }
}
