package com.siddarthaboddu.cache.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Usage {
	@Getter private Integer currentSize;
	@Getter private Integer capacity;
}
