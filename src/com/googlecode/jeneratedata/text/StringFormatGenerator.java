package com.googlecode.jeneratedata.text;

import java.util.Collection;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.GeneratorCollectionWrapperBase;

/**
 * Generates a String data item using the generated values from the wrapped.
 *
 * {@link Generator} instances and the supplied format to call {@link String#format(String, Object...)}.
 * @author Agustin Barto <abarto@gmail.com>
 */

public class StringFormatGenerator extends GeneratorCollectionWrapperBase implements Generator<String> {
	
	/** The format. */
	private String format;
	
	/**
	 * Instantiates a new string format generator.
	 *
	 * @param format the format
	 * @param generators the generators
	 */
	public StringFormatGenerator(String format, Collection<Generator<?>> generators) {
		super(generators);
	}

	/**
	 * Instantiates a new string format generator.
	 *
	 * @param format the format
	 * @param generators the generators
	 */
	public StringFormatGenerator(String format, Generator<?>... generators) {
		super(generators);
	}

	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public String generate() {
		Object[] args = new Object[generators.size()];

		int i = 0;
		for (Generator<?> generator : generators) {
			args[i++] = generator.generate();
		}
		
		return String.format(format, args);
	}
}