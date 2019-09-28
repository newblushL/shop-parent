package com.ll.spike.mapper;

import com.ll.spike.mapper.entity.SeckillEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @description: SeckillMapper
 * @author: LL
 * @create: 2019-09-28 21:20
 */
@Repository
public interface SeckillMapper {

    @Select("SELECT seckill_id AS seckillId,name as name,inventory as inventory,start_time as startTime,end_time as endTime,create_time as createTime,version as version from seckill where seckill_id=#{seckillId}")
    SeckillEntity findBySeckillId(Long seckillId);

    /**
     * 使用乐观锁
     *
     * @param seckillId
     * @return
     */

    @Update("update seckill set inventory=inventory-1, version=version+1 where  seckill_id=#{seckillId} and inventory>0  and version=#{version} ;")
    int inventoryDeduction(@Param("seckillId") Long seckillId, @Param("version") Long version);

}