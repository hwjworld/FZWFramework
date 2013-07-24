package com.legaldaily.estension.ecard.utils;

import java.io.Serializable;
import java.util.Comparator;

import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.domain.context.AreaContext;
import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.model.question.Answer;

public class Comparators {
	public static final Comparator<Serializable> STRING_COMPARATOR_DESC_INTEGER = new Comparator<Serializable>() {

		@Override
		public int compare(Serializable o1, Serializable o2) {
			int i1 = StringValueUtils.getInt(o1.toString());
			int i2 = StringValueUtils.getInt(o2.toString());
			int rv = 0;
			if(i1 > i2){
				rv = 1;
			}else if (i1 < i2) {
				rv = -1;
			}
			return rv;
		}
		
	};
	public static final Comparator<AreaContext> AREACONTEXT_COMPARATOR_DESC_ORDER = new Comparator<AreaContext>() {
		@Override
		public int compare(AreaContext o1, AreaContext o2) {
			if(o1.getAreaOrder()<o2.getAreaOrder()){
				return -1;
			}else if (o1.getAreaOrder()>o2.getAreaOrder()) {
				return 1;
			}else
				return 0;
		}
	};
	public static final Comparator<AreaContext> AREACONTEXT_COMPARATOR_ASC_ORDER = new Comparator<AreaContext>() {
		@Override
		public int compare(AreaContext o1, AreaContext o2) {
			if(o1.getAreaOrder()>o2.getAreaOrder()){
				return -1;
			}else if (o1.getAreaOrder()<o2.getAreaOrder()) {
				return 1;
			}else
				return 0;
		}
	};
	public static final Comparator<PostCategory> POSTCATEGORYT_COMPARATOR_ASC_ID = new Comparator<PostCategory>() {
		@Override
		public int compare(PostCategory o1, PostCategory o2) {
			if(o1.getCatId()>o2.getCatId()){
				return 1;
			}else if (o1.getCatId()<o2.getCatId()) {
				return -1;
			}else
				return 0;
		}
	};
	
	public static final Comparator<LawCase> LAWCASE_COMPARATOR_ASC_SUBMITDATE = new Comparator<LawCase>() {

		@Override
		public int compare(LawCase o1, LawCase o2) {
			if (o1.getSubmitDate().after(o2.getSubmitDate())) {
				return -1;
			} else if (o1.getSubmitDate().before(o2.getSubmitDate())) {
				return 1;
			} else {
				return 0;
			}
		}
	};	
	public static final Comparator<LawCase> LAWCASE_COMPARATOR_DESC_SUBMITDATE = new Comparator<LawCase>() {

		@Override
		public int compare(LawCase o1, LawCase o2) {
			if (o1.getSubmitDate().after(o2.getSubmitDate())) {
				return 1;
			} else if (o1.getSubmitDate().before(o2.getSubmitDate())) {
				return -1;
			} else {
				return 0;
			}
		}
	};
	public static final Comparator<LawCase> LAWCASE_COMPARATOR_DESC_ID = new Comparator<LawCase>() {

		@Override
		public int compare(LawCase o1, LawCase o2) {
			if (o1.getCaseId()>o2.getCaseId()) {
				return 1;
			} else if (o1.getCaseId()<o2.getCaseId()) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	public static final Comparator<LawOffice> LAWOFFICE_COMPARATOR_DESC_PROFINCEID = new Comparator<LawOffice>() {

		@Override
		public int compare(LawOffice o1, LawOffice o2) {
			if(o1.getProvince().getAreaId()>o2.getProvince().getAreaId()){
				return 1;
			}else if (o1.getProvince().getAreaId()<o2.getProvince().getAreaId()) {
				return -1;
			}else {
				return 0;
			}
		}
	};
	public static final Comparator<Answer> ANSWER_COMPARATOR_DESC_ANSWERID = new Comparator<Answer>() {

		@Override
		public int compare(Answer o1, Answer o2) {
			if(o1.getId()>o2.getId()){
				return 1;
			}else if (o1.getId()<o2.getId()) {
				return -1;
			}else {
				return 0;
			}
		}
	};
}
