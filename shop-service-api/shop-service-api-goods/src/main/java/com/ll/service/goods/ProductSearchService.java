package com.ll.service.goods;

import com.ll.base.BaseResponse;
import com.ll.goods.output.dto.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description: 商品服务接口
 * @author: LL
 * @create: 2019-08-27 16:01
 */
public interface ProductSearchService {
    @GetMapping("/search")
    BaseResponse<List<ProductDto>> search(String name, @PageableDefault(page = 0, value = 10)
            Pageable pageable);
}