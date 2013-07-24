/**
 * 
 */
package com.legaldaily.estension.ecard.model.question;

import org.apache.commons.lang.StringUtils;

/**
 * @author hwj
 *
 */
public enum QuestionsType {
	getSolvedQuestions, getUnSolvedQuestions, getNoneAnswerQuestions, getHighScoreQuestions;
	
	public static boolean exist(String name){
		if(StringUtils.isBlank(name)){
			return false;
		}
		QuestionsType[] types = QuestionsType.values();
		for (QuestionsType questionsType : types) {
			if(name.equals(questionsType.name())){
				return true;
			}
		}
		return false;
	}
}