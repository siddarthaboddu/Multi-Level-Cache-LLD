package com.siddarthaboddu.cache.provider;

import java.util.ArrayList;
import java.util.List;

import com.siddarthaboddu.cache.model.ReadResponse;
import com.siddarthaboddu.cache.model.Usage;
import com.siddarthaboddu.cache.model.WriteResponse;

public class NullLevelCache<Key, Value> implements ILevelCache<Key, Value> {

	@Override
	public ReadResponse<Key, Value> get(Key key) {
		return new ReadResponse<>(key, null, 0L);
	}

	@Override
	public WriteResponse<Key, Value> put(Key key, Value value) {
		return new WriteResponse<>(key, null, 0L);
	}

	@Override
	public List<Usage> getUsage() {
		return new ArrayList<>();
	}

}
