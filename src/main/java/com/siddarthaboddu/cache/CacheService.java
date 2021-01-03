package com.siddarthaboddu.cache;

import com.siddarthaboddu.cache.model.ReadResponse;
import com.siddarthaboddu.cache.model.StatsResponse;
import com.siddarthaboddu.cache.model.WriteResponse;

public interface CacheService<Key, Value> {
	
	public ReadResponse<Key, Value> get(Key key);
	public WriteResponse<Key, Value> put(Key key, Value value);
	public StatsResponse stats();
	
}
