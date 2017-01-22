package org.maxwe.tao.server.common.cache.token;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

import java.io.File;

/**
 * Created by Pengwei Ding on 2017-01-18 17:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TokenCache {

    public static void main(String[] args) {
//        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
//                .withCache("preConfigured",
//                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
//                                ResourcePoolsBuilder.heap(100)).build())
//                .build(true);


        PersistentCacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence("/Users/dingpengwei/workspace/dingpw/MaxweTaoServer" + File.separator + "cacheData"))
                .withCache("threeTieredCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(10, EntryUnit.ENTRIES)
                                        .offheap(1, MemoryUnit.MB)
                                        .disk(20, MemoryUnit.MB)
                        )
                ).build(true);

        Cache<Long, String> preConfigured
                = cacheManager.getCache("threeTieredCache", Long.class, String.class);
        preConfigured.put(1L, "dingpengwei");

        System.out.println(preConfigured.get(1L));

//        Cache<Long, String> myCache = cacheManager.createCache("myCache",
//                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
//                        ResourcePoolsBuilder.heap(100)).build());
//
//        myCache.put(1L, "da one!");

//        String value = myCache.get(1L);


        cacheManager.close();
    }
}
