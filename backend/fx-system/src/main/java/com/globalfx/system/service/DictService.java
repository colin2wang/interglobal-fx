package com.globalfx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.globalfx.system.dto.DictDataDTO;
import com.globalfx.system.dto.DictTypeDTO;
import com.globalfx.system.entity.DictData;
import com.globalfx.system.entity.DictType;

import java.util.List;

public interface DictService extends IService<DictType> {

    List<DictType> listDictTypes(Integer status);

    void createDictType(DictTypeDTO dto);

    void updateDictType(DictTypeDTO dto);

    void deleteDictType(Long id);

    List<DictData> listDictDataByType(String dictType);

    void createDictData(DictDataDTO dto);

    void updateDictData(DictDataDTO dto);

    void deleteDictData(Long id);
}
