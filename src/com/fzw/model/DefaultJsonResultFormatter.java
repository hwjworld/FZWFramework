package com.fzw.model;

import com.fzw.connection.FZWService;

public class DefaultJsonResultFormatter implements ResultFormatter {

	private DefaultJsonResultFormatter() {

	}

	private static ResultFormatter formatter = null;

	public static ResultFormatter getInstance() {
		if (formatter == null)
			formatter = new DefaultJsonResultFormatter();
		return formatter;
	}

	@Override
	public String format(Object object) {
		return FZWService.gson.toJson(object);
	}

}
