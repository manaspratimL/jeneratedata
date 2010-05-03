package com.googlecode.jeneratedata.strings;

import java.text.MessageFormat;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.MultiGenerator;

/**
 * Generates text using a {@link MessageFormat} pattern and a specific
 * {@link Generator} for each argument.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 * 
 * @see MessageFormat
 */
public class MessageFormatBasedGenerator implements Generator<String> {
	/**
	 * The {@link Generator} for {@link MessageFormat#format(String, Object...)}
	 * arguments.
	 */
	private Generator<Object[]> argumentsGenerator;

	/**
	 * The pattern for {@link MessageFormat#format(String, Object...)}.
	 */
	private String pattern;

	/**
	 * Constructor.
	 * 
	 * @param pattern Pattern from which to generate the message.
	 * @param argumentGenerators A list of {@link Generator} instances that
	 * will generate each argument to
	 * {@link MessageFormat#format(String, Object...)}.
	 * @see MultiGenerator
	 */
	public MessageFormatBasedGenerator(String pattern, Generator<?>... argumentGenerators) {
		this.pattern = pattern;
		this.argumentsGenerator = new MultiGenerator(argumentGenerators);
	}
	
	/**
	 * Constructor
	 * 
	 * @param pattern Pattern from which to generate the message.
	 * @param argumentsGenerator A {@link Generator} that will generate the
	 * arguments to {@link MessageFormat#format(String, Object...)}.
	 */
	public MessageFormatBasedGenerator(String pattern, Generator<Object[]> argumentsGenerator) {
		this.pattern = pattern;
		this.argumentsGenerator = argumentsGenerator;
	}
	
	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public String generate() {
		return MessageFormat.format(pattern, argumentsGenerator.generate());
	}
}
