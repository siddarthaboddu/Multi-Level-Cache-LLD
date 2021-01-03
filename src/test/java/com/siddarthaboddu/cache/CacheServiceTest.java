package com.siddarthaboddu.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.siddarthaboddu.cache.policy.EvictionPolicy;
import com.siddarthaboddu.cache.policy.LRUEvictionPolicy;
import com.siddarthaboddu.cache.provider.Cache;
import com.siddarthaboddu.cache.provider.ConcreteCache;
import com.siddarthaboddu.cache.provider.DefaultLevelCache;
import com.siddarthaboddu.cache.provider.ILevelCache;
import com.siddarthaboddu.cache.provider.NullLevelCache;
import com.siddarthaboddu.cache.storage.InMemoryStorage;
import com.siddarthaboddu.cache.storage.Storage;

public class CacheServiceTest {

	CacheService<Integer, String> cacheService;
	@BeforeEach
	public void init() {
		Storage<Integer, String> storage1 = new InMemoryStorage<>(2);
		Storage<Integer, String> storage2 = new InMemoryStorage<>(10);
		
		EvictionPolicy<Integer> evictionPolicy1 = new LRUEvictionPolicy<>();
		EvictionPolicy<Integer> evictionPolicy2 = new LRUEvictionPolicy<>();
		
		Cache<Integer, String> simpleCache1 = new ConcreteCache<>(evictionPolicy1, storage1);
		Cache<Integer, String> simpleCache2 = new ConcreteCache<>(evictionPolicy2, storage2);
		
		ILevelCache<Integer, String> nullLevelCache = new NullLevelCache<>();
		ILevelCache<Integer, String> secondLevelCache = new DefaultLevelCache<>(nullLevelCache, simpleCache2);
		ILevelCache<Integer, String> firstLevelCache = new DefaultLevelCache<>(secondLevelCache, simpleCache1);
		cacheService = new MultiLevelCacheService<>(firstLevelCache, 10);
	}
	
	
	@Test
	public void test1() {
		System.out.println(cacheService.put(1, "a"));
		System.out.println(cacheService.put(2, "b"));
		
		System.out.println(cacheService.get(1));
		System.out.println(cacheService.get(2));
		
		System.out.println(cacheService.put(3,  "c"));
		
		System.out.println(cacheService.get(3));
		System.out.println(cacheService.get(2));
		System.out.println(cacheService.get(1));

		System.out.println(cacheService.stats());
	}
}
