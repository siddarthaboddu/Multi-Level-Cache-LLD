package com.siddarthaboddu.cache.provider;

public interface Cache<Key, Value> {
	public Value get(Key key);
	public void put(Key key, Value value);
	public Integer getCurrentSize();
	public Integer getCapacity();
}
