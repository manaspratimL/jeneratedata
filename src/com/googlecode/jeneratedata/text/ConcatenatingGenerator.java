package com.googlecode.jeneratedata.text;

import java.util.Arrays;
import java.util.Collection;

import com.googlecode.jeneratedata.core.Generator;

/**
 * Generates a String concatenating the generated values by the contained
 * {@link Generator} instances. 
 * 
 * @author Agustin Barto <abarto@gmail.com>
 */
public class ConcatenatingGenerator implements Generator<String> {
	/**
	 * The generators used to construct the data item.
	 */
	protected Collection<Generator<?>> generators;
	
	/**
	 * Constructor.
	 * 
	 * @param generators Generators to be used to construct this generator data
	 * item.
	 */
	public ConcatenatingGenerator(Generator<?>... generators) {
		super();
		this.generators = Arrays.asList(generators);
	}	

	/**
	 * Constructor.
	 * 
	 * @param generators Generators to be used to construct this generator data
	 * item.
	 */
	public ConcatenatingGenerator(Collection<Generator<?>> generators) {
		super();
		this.generators = generators;
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
