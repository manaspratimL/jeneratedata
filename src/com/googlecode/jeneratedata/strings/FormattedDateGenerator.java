package com.googlecode.jeneratedata.strings;

import java.text.DateFormat;
import java.util.Date;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.GeneratorWrapperBase;

public class FormattedDateGenerator extends GeneratorWrapperBase<Date>
		implements Generator<String> {
	private DateFormat dateFormat;

	public FormattedDateGenerator(DateFormat dateFormat,
			Generator<Date> generator) {
		super(generator);
		this.dateFormat = dateFormat;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public String generate() {
		return dateFormat.format(generator.generate());
	}

}
