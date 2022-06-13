package com.pandatom.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandatom.example.entity.Rps;
import com.pandatom.example.mapper.RpsMapper;
import com.pandatom.example.service.IRpsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
@Service
public class RpsServiceImpl extends ServiceImpl<RpsMapper, Rps> implements IRpsService {

}
