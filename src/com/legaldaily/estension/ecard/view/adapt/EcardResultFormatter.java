package com.legaldaily.estension.ecard.view.adapt;

import com.fzw.model.ResultFormatter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EcardResultFormatter implements ResultFormatter {

	private static final Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
		// 根据业务使用此策略防止quesion与answer互相嵌套而死循环
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			if (f.getDeclaredClass().getName().equals("com.legaldaily.estension.ecard.model.question.Question")
					&& f.getDeclaringClass().getName().equals("com.legaldaily.estension.ecard.model.question.Answer"))
				return true;
			return false;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
	}).addSerializationExclusionStrategy(new ExclusionStrategy() {
		// 根据业务使用此策略防止PostCategoryVO与PostCategory互相嵌套而死循环
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			if (f.getDeclaredClass().getName().equals("com.legaldaily.estension.ecard.model.post.PostCategory")
					&& f.getDeclaringClass().getName().equals("com.legaldaily.estension.ecard.model.post.PostCategoryVO"))
				return true;
			return false;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
	}).create();

	@Override
	public String format(Object object) {
		return gson.toJson(object);
	}

}
