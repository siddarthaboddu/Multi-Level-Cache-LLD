package com.siddarthaboddu.cache.policy;

public interface EvictionPolicy<Key> {
	public void keyAccessed(Key key);
	public Key evict();
}
