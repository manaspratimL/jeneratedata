package com.googlecode.jeneratedata.text;

import java.util.Arrays;
import java.util.Collection;

import com.googlecode.jeneratedata.core.Generator;

public class ConcatenatingGenerator implements Generator<String> {
	private Collection<Generator<?>> generators;
	
	public ConcatenatingGenerator(Generator<?>... generators) {
		super();
		this.generators = Arrays.asList(generators);
	}	

	public ConcatenatingGenerator(Collection<Generator<?>> generators) {
		super();
		this.generators = generators;
	}

	public Collection<Generator<?>> getGenerators() {
		return generators;
	}

	public void setGenerators(Collection<Generator<?>> generators) {
		this.generators = generators;
	}

	@Override
	public String generate() {
		StringBuffer stringBuffer = new StringBuffer();
		
		for (Generator<?> generator : generators) {
			stringBuffer.append(generator.generate());
		}
		
		return stringBuffer.toString();
	}

}
