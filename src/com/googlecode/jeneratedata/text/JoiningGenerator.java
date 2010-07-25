package com.googlecode.jeneratedata.text;

import java.util.Collection;
import java.util.Iterator;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.GeneratorCollectionWrapperBase;

/**
 * Generates a String data item joining the generated values from the wrapped
 * {@link Generator} instances using the supplied separator in between.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 */
public class JoiningGenerator extends GeneratorCollectionWrapperBase implements Generator<String> {
	/**
	 * Separator to be put in between the data items provided by the wrapped
	 * generators.
	 */
	private String separator;
	
	/**
	 * Constructor.
	 * 
	 * @param separator Separator to be put in between the data items provided
	 * by the wrapped generators.
	 * @param generators {@link Generator} instances to be used to provide the
	 * data items to be joined.
	 */
	public JoiningGenerator(String separator, Generator<?>... generators) {
		super(generators);
		this.separator = separator;
	}	

	/**
	 * Constructor.
	 * 
	 * @param separator Separator to be put in between the data items provided
	 * by the wrapped generators.
	 * @param generators {@link Generator} instances to be used to provide the
	 * data items to be joined.
	 */
	public JoiningGenerator(String separator, Collection<Generator<?>> generators) {
		super(generators);
		this.generators = generators;
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public String generate() {
		StringBuffer stringBuffer = new StringBuffer();
		
		for (Iterator<Generator<?>> iterator = generators.iterator() ; iterator.hasNext() ;) {
			stringBuffer.append(iterator.next().generate());
			if (iterator.hasNext()) {
				stringBuffer.append(separator);
			}
		}
		
		return stringBuffer.toString();
	}
}