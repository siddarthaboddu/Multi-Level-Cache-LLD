package com.siddarthaboddu.cache.provider;

import java.util.List;

import com.siddarthaboddu.cache.model.ReadResponse;
import com.siddarthaboddu.cache.model.Usage;
import com.siddarthaboddu.cache.model.WriteResponse;

public interface ILevelCache<Key, Value> {
	public ReadResponse<Key, Value> get(Key key);
	public WriteResponse<Key, Value> put(Key key, Value value);
	public List<Usage> getUsage();
}
