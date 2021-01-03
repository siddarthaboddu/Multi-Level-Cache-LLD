package com.siddarthaboddu.cache;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.siddarthaboddu.cache.model.ReadResponse;
import com.siddarthaboddu.cache.model.StatsResponse;
import com.siddarthaboddu.cache.model.Usage;
import com.siddarthaboddu.cache.model.WriteResponse;
import com.siddarthaboddu.cache.provider.ILevelCache;

public class MultiLevelCacheService<Key, Value> implements CacheService<Key, Value>{

	private ILevelCache<Key, Value> cache;
	private Deque<Long> readTimeDeque;
	private Deque<Long> writeTimeDeque;
	private Integer lastCount;
	
	private Long totalReadTime;
	private Long totalWriteTime;
	
	public MultiLevelCacheService(ILevelCache<Key, Value> cache, int lastCount){
		this.cache = cache;
		this.readTimeDeque = new ArrayDeque<>();
		this.writeTimeDeque = new ArrayDeque<>();
		this.lastCount = lastCount;
		
		this.totalReadTime = 0l;
		this.totalWriteTime = 0l;
	}
	
	@Override
	public ReadResponse<Key, Value> get(Key key) {
		ReadResponse<Key, Value> readResponse = cache.get(key);
		updateReadTime(readResponse);
		return readResponse;
	}

	private void updateReadTime(ReadResponse<Key, Value> readResponse) {
		if(readTimeDeque.size() == lastCount) {
			Long readTime = readTimeDeque.poll();
			totalReadTime -= readTime;
		}
		
		readTimeDeque.add(readResponse.getTime());
		totalReadTime += readResponse.getTime();
		}

	@Override
	public WriteResponse<Key, Value> put(Key key, Value value) {
		WriteResponse<Key, Value> writeResponse = cache.put(key, value);
		updateWriteTime(writeResponse);
		return writeResponse;
	}

	private void updateWriteTime(WriteResponse<Key, Value> writeResponse) {
		if(writeTimeDeque.size() == lastCount) {
			Long writeTime = writeTimeDeque.poll();
			totalWriteTime -= writeTime;
		}
		
		writeTimeDeque.add(writeResponse.getTime());
		totalWriteTime += writeResponse.getTime();
	}

	@Override
	public StatsResponse stats() {
		return new StatsResponse(getAvgReadTime(), getAvgWriteTime(), getUsage());
	}

	private Long getAvgReadTime() {
		return totalReadTime/readTimeDeque.size();
	}
	
	private Long getAvgWriteTime() {
		return totalWriteTime/writeTimeDeque.size();
	}

	private List<Usage> getUsage() {
		return cache.getUsage();
	}
	
	

}
