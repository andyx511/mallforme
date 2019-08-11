package com.example.mall.service;

import com.example.mall.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Auther: Alex
 * @Date: 2019/8/11 11:41
 * @Description:
 */
public interface EsProductService {

    int importAll();

    void delete(Long id);

    EsProduct create(Long id );

    void delete(List<Long> ids);

    Page<EsProduct> search(String keyword,Integer pageNum,Integer pageSize);

}
