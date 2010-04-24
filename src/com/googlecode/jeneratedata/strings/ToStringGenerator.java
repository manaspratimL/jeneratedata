package com.googlecode.jeneratedata.strings;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.GeneratorWrapperBase;

/**
 * Uses the contained {@link Generator} to generate data items calling
 * {@link String#valueOf(Object)} to give its own results.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 */
public class ToStringGenerator extends GeneratorWrapperBase<Object> implements
		Generator<String> {
	/**
	 * Constructor.
	 * 
	 * @param generator The wrapped {@link Generator} instance.
	 */
	public ToStringGenerator(Generator<Object> generator) {
		super(generator);
	}

	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public String generate() {
		return String.valueOf(generator.generate());
	}
}
