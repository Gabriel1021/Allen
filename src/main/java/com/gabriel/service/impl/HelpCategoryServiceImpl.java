package com.gabriel.service.impl;

import com.gabriel.entity.HelpCategory;
import com.gabriel.mapper.HelpCategoryMapper;
import com.gabriel.service.IHelpCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * help categories 服务实现类
 * </p>
 *
 * @author Z.XX
 * @since 2020-12-25
 */
@Service
public class HelpCategoryServiceImpl extends ServiceImpl<HelpCategoryMapper, HelpCategory> implements IHelpCategoryService {

  public void test(){
    System.out.println(this.getBaseMapper().selectByMap(null));
  }
}
