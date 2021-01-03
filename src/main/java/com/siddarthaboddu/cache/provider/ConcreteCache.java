package com.siddarthaboddu.cache.provider;

import com.siddarthaboddu.cache.exception.KeyNotFoundException;
import com.siddarthaboddu.cache.exception.StorageFullException;
import com.siddarthaboddu.cache.policy.EvictionPolicy;
import com.siddarthaboddu.cache.storage.Storage;

public class ConcreteCache<Key, Value> implements Cache<Key, Value>{

	private final EvictionPolicy<Key> evictionPolicy;
	private final Storage<Key, Value> storage;
	
	public ConcreteCache(EvictionPolicy<Key> evictionPolicy, Storage<Key, Value> storage) {
		this.evictionPolicy = evictionPolicy;
		this.storage = storage;
	}
	
	@Override
	public Value get(Key key) {
		Value value = null;
		try {
			value = storage.get(key);
			evictionPolicy.keyAccessed(key);
		} catch(KeyNotFoundException e) {
			System.out.println(e);
		}
		return value;
	}

	@Override
	public void put(Key key, Value value) {
		try {
			storage.put(key, value);
			evictionPolicy.keyAccessed(key);
		} catch(StorageFullException e) {
			Key evictKey = evictionPolicy.evict();
			if(evictKey == null) {
				throw new RuntimeException("Unexpected state => evictkey null but storage is full");
			}
			storage.remove(evictKey);
			storage.put(key, value);
			evictionPolicy.keyAccessed(key);
		}
	}

	@Override
	public Integer getCurrentSize() {
		return storage.getCurrentSize();
	}

	@Override
	public Integer getCapacity() {
		return storage.getCapacity();
	}

}
