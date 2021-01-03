package com.siddarthaboddu.cache.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class StatsResponse {
	@Getter private Long avgReadTime;
	@Getter private Long avgWriteTime;
	@Getter private List<Usage> usage;
}
