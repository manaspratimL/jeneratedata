package com.googlecode.jeneratedata.people;

import java.util.Random;

import com.googlecode.jeneratedata.core.Generator;

/**
 * Generates random male and female names (with equal probability) using a
 * {@link MaleNameGenerator} and a {@link FemaleNameGenerator}.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 */
public class NameGenerator implements Generator<String> {
	/**
	 * A random number generator used to decide wether to generate a male or
	 * female name.
	 */
	private Random random = new Random();

	/**
	 * Male name generator.
	 */
	private Generator<String> maleNameGenerator = new MaleNameGenerator();

	/**
	 * Female name generator.
	 */
	private Generator<String> femaleNameGenerator = new FemaleNameGenerator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public String generate() {
		return (random.nextDouble() < 0.5) ? maleNameGenerator.generate()
				: femaleNameGenerator.generate();
	}

}
