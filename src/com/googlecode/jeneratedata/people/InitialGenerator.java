package com.googlecode.jeneratedata.people;

import org.apache.commons.lang.RandomStringUtils;

import com.googlecode.jeneratedata.core.Generator;

public class InitialGenerator implements Generator<String> {
	@Override
	public String generate() {
		return RandomStringUtils.randomAlphabetic(1).toUpperCase() + ".";
	}
}
