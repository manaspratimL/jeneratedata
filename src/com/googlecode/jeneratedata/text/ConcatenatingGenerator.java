package com.googlecode.jeneratedata.text;

import java.util.Collection;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.GeneratorCollectionWrapperBase;

/**
 * Generates a String concatenating the generated values by the contained.
 *
 * {@link Generator} instances.
 * @author Agustin Barto <abarto@gmail.com>
 */
public class ConcatenatingGenerator extends GeneratorCollectionWrapperBase implements Generator<String> {
	
	/**
	 * Instantiates a new concatenating generator.
	 *
	 * @param generators the generators
	 */
	public ConcatenatingGenerator(Collection<Generator<?>> generators) {
		super(generators);
	}

	/**
	 * Instantiates a new concatenating generator.
	 *
	 * @param generators the generators
	 */
	public ConcatenatingGenerator(Generator<?>... generators) {
		super(generators);
	}

	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public String generate() {
		StringBuffer stringBuffer = new StringBuffer();
		
		for (Generator<?> generator : generators) {
			stringBuffer.append(generator.generate());
		}
		
		return stringBuffer.toString();
	}

}
