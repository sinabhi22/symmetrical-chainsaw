package com.nokia.configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiRoutingDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DBContextHolder.getCurrentDb();
	}
}
