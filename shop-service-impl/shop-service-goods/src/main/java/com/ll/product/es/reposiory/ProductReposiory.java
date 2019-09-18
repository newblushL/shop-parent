package com.ll.product.es.reposiory;

import com.ll.product.es.entity.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ProductReposiory extends ElasticsearchRepository<ProductEntity, Long> {

}
 