package com.siddarthaboddu.cache.policy;

import java.util.LinkedHashSet;
import java.util.Set;

public class LRUEvictionPolicy<Key> implements EvictionPolicy<Key>{
	private final Set<Key> keySet;
	
	public LRUEvictionPolicy(){
		keySet = new LinkedHashSet<Key>();
	}
	@Override
	public void keyAccessed(Key key) {
		if(keySet.contains(key)) {
			keySet.remove(key);
		}
		keySet.add(key);
	}

	@Override
	public Key evict() {
		Key key = null;
		if(keySet.size() > 0) {
			key = keySet.iterator().next();
			keySet.remove(key);
		}
		return key;
	}
	
}
