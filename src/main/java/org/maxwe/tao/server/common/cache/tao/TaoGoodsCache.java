package org.maxwe.tao.server.common.cache.tao;

import com.alibaba.fastjson.JSON;
import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponsePageEntity;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-01-18 15:05.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 站内发布商品的缓存
 */
public class TaoGoodsCache {

    private TaoGoodsCache() {
    }

    private static volatile TaoGoodsCache instance;

    public static TaoGoodsCache getInstance() {
        if (instance == null) {
            synchronized (TaoGoodsCache.class) {
                if (instance == null) {
                    instance = new TaoGoodsCache();
                }
            }
        }
        return instance;
    }


    private static PersistentCacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerBuilder.persistence(ApplicationConfigure.SYSTEM_CACHE_FILE_DIR + File.separator + "goodsCache"))
            .withCache("goodsCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                            ResourcePoolsBuilder.newResourcePoolsBuilder()
                                    .heap(10, EntryUnit.ENTRIES)
                                    .offheap(1, MemoryUnit.MB)
                                    .disk(20, MemoryUnit.MB)
                    )
            ).build(true);


    public void add(AliResponsePageEntity aliResponsePageEntity) {
        Cache<Long, String> goodsCache = cacheManager.getCache("goodsCache", Long.class, String.class);
        goodsCache.put(aliResponsePageEntity.getAuctionId(), aliResponsePageEntity.getJsonString());
    }

    public void remove(Long[] ids) {
        Cache<Long, String> goodsCache = cacheManager.getCache("goodsCache", Long.class, String.class);
        for (Long id : ids) {
            goodsCache.remove(id);
        }
    }

    public int size() {
        Cache<Long, String> goodsCache = cacheManager.getCache("goodsCache", Long.class, String.class);
        int size = goodsCache.getAll(new HashSet<Long>()).size();
        return size;
    }

    public List<AliResponsePageEntity> list(long pageIndex, long pageSize) {
        Cache<Long, String> goodsCache = cacheManager.getCache("goodsCache", Long.class, String.class);
        Iterator<Cache.Entry<Long, String>> iterator = goodsCache.iterator();
        List<AliResponsePageEntity> aliResponsePageEntities = new LinkedList<>();
        long offset = pageIndex * pageSize;
        for (int i = 0; i < offset; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            } else {
                break;
            }
        }

        for (int i = 0; i < pageSize; i++) {
            if (iterator.hasNext()) {
                Cache.Entry<Long, String> next = iterator.next();
                String value = next.getValue();
                AliResponsePageEntity aliResponsePageEntity = JSON.parseObject(value, AliResponsePageEntity.class);
                aliResponsePageEntities.add(aliResponsePageEntity);
            } else {
                break;
            }
        }
        return aliResponsePageEntities;
    }
}
