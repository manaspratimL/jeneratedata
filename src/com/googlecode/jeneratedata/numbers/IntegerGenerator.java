package com.googlecode.jeneratedata.numbers;

import java.util.Random;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.RandomBasedGeneratorBase;

/**
 * Generate {@link Integer} data items as returned by
 * {@link Random#nextInt()}. 
 * 
 * @author Agustin Barto <abarto@gmail.com> 
 */
public class IntegerGenerator extends RandomBasedGeneratorBase implements Generator<Integer> {
	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public Integer generate() {
		return random.nextInt();
	}
}
