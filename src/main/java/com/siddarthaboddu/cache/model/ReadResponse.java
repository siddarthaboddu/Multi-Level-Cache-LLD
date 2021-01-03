package com.siddarthaboddu.cache.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ReadResponse<Key, Value> {
	@Getter private Key key;
	@Getter private Value value;
	@Getter private Long time;
}
