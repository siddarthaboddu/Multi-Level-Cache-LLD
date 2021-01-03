package com.siddarthaboddu.cache.provider;

import java.util.ArrayList;
import java.util.List;

import com.siddarthaboddu.cache.model.ReadResponse;
import com.siddarthaboddu.cache.model.Usage;
import com.siddarthaboddu.cache.model.WriteResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultLevelCache<Key, Value> implements ILevelCache<Key, Value> {

	private ILevelCache<Key, Value> next;
	private Cache<Key, Value> cache;
	
	@Override
	public ReadResponse<Key, Value> get(Key key) {
		Long time = 0 - System.currentTimeMillis();
		Value value = cache.get(key);
		time = time + System.currentTimeMillis();
		
		if(value == null) {
			ReadResponse<Key, Value> nextReadResponse = next.get(key);
			value = nextReadResponse.getValue();
			
			time = time - System.currentTimeMillis();
			if(value != null) {
				cache.put(key, value);
			}
			time = time + System.currentTimeMillis();
			
			time += nextReadResponse.getTime();
		}
	
		return new ReadResponse<Key, Value>(key, value, time);		
	}

	@Override
	public WriteResponse<Key, Value> put(Key key, Value value) {
		Long time = 0 - System.currentTimeMillis();
		Value valueInCache = cache.get(key);
		time += System.currentTimeMillis();
		
		if(value.equals(valueInCache)) {
			return new WriteResponse<Key, Value>(key, value, time);
		}
		
		time -= System.currentTimeMillis();
		cache.put(key, value);
		time += System.currentTimeMillis();
		WriteResponse<Key, Value> nextWriteResponse = next.put(key, value);
		time+= nextWriteResponse.getTime();
		
		return new WriteResponse<Key, Value>(key, value, time);
	}

	@Override
	public List<Usage> getUsage() {
		Usage usage = new Usage(cache.getCurrentSize(), cache.getCapacity());
		List<Usage> usageList = new ArrayList<>();
		usageList.add(usage);
		
		List<Usage> nextUsageList = next.getUsage();
		if(nextUsageList != null)
			usageList.addAll(next.getUsage());
		return usageList;
	}

}
