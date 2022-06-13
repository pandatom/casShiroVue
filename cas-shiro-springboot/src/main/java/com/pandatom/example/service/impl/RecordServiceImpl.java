package com.pandatom.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandatom.example.entity.Record;
import com.pandatom.example.mapper.RecordMapper;
import com.pandatom.example.service.IRecordService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hcly
 * @since 2022-05-04
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Override
    public boolean saveBatch(Collection<Record> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Record> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<Record> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Record entity) {
        return false;
    }

    @Override
    public Record getOne(Wrapper<Record> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Record> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<Record> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public Class<Record> getEntityClass() {
        return null;
    }
}
