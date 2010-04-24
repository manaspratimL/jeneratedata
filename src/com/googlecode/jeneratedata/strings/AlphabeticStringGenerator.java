package com.googlecode.jeneratedata.strings;

import org.apache.commons.lang.RandomStringUtils;

import com.googlecode.jeneratedata.core.Generator;

/**
 * Generates random alphabetic strings as returned by
 * {@link RandomStringUtils#randomAlphabetic(int)} from the
 * <a href="http://commons.apache.org/lang/">Commons Lang</a> library.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 * @see <a href="http://commons.apache.org/lang/">Lang - Home</a>
 */
public class AlphabeticStringGenerator implements Generator<String> {
	/**
	 * Length of the generated strings.
	 */
	private int count;

	/**
	 * Constructor.
	 * 
	 * @param count Length the generated strings.
	 */
	public AlphabeticStringGenerator(int count) {
		super();
		this.count = count;
	}

	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public String generate() {
		return RandomStringUtils.randomAlphabetic(count);
	}
}
