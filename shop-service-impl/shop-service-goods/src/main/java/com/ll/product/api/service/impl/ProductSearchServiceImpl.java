package com.ll.product.api.service.impl;

import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.goods.output.dto.ProductDto;
import com.ll.product.es.entity.ProductEntity;
import com.ll.product.es.reposiory.ProductReposiory;
import com.ll.service.goods.ProductSearchService;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 商品接口实现类
 * @author: LL
 * @create: 2019-08-27 16:24
 */
@RestController
public class ProductSearchServiceImpl extends BaseApiService<List<ProductDto>> implements ProductSearchService {
    @Autowired
    private ProductReposiory productReposiory;

    @Override
    public BaseResponse<List<ProductDto>> search(String name, @PageableDefault(page = 0,
            value = 10) Pageable pageable) {

        // int i = 1 / 0;
        //String user = null;
        //System.out.println(user.getBytes());
        // 1.拼接查询条件
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 2.模糊查询 name、 subtitle、detail含有 搜索关键字
        builder.must(QueryBuilders.multiMatchQuery(name, "name", "subtitle", "detail"));
        // 3.调用ES接口查询
        Page<ProductEntity> page = productReposiory.search(builder, pageable);
        // 4.获取集合数据
        List<ProductEntity> content = page.getContent();
        // 5.将entity转换dto
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        List<ProductDto> mapAsList = mapperFactory.getMapperFacade().mapAsList(content, ProductDto.class);
        return setResultSuccess(mapAsList);
    }
}
