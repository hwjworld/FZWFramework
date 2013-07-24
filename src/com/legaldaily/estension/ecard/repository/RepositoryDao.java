package com.legaldaily.estension.ecard.repository;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Ehcache;

import org.apache.commons.lang.StringUtils;

import com.fzw.domain.DomainEvents;
import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.cache.EcardCacheManager;
import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;
import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;
import com.legaldaily.estension.ecard.repository.dao.EcardDao;

public abstract class RepositoryDao implements EcardRepository{
	
	protected static Ehcache questionCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_QUESTION);
	protected static Ehcache recommendCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_RECOMMENDQUESTION);
	protected static Ehcache lawcaseCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_LAWCASES);
	protected static Ehcache lawofficeCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_LAWOFFICE);
	protected static Ehcache lawyerCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_LAWYER);
	protected static Ehcache lawCategories = EcardCacheManager.getCache(EcardCacheManager.CACHE_LAWCATEGORIES);
	protected static Ehcache userCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_USER);
	protected static Ehcache userGroupCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_USERGROUP);
	protected static Ehcache cityCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_CITIES);
	protected static Ehcache provinceCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_PROVINCES);
	protected static Ehcache answerCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_ANSWER);
	protected static Ehcache postCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_POSTS);
	protected static Ehcache postCategoryCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_POSTCATEGORY);
	protected static final DomainEvents DOMAIN_EVENTS = new DomainEvents();
	
	protected void setQuestion(Question question) {
		setQuestionUser(question);
		setQuestionLawCategory(question);
	}
	private void setQuestionLawCategory(Question question) {
		question.setLawCategory(lawRepository.getLawCategory(question.getLawCategory().getCatId()));
	}
	protected void setQuestionUser(Question question) {
		if(question == null){
			return;
		}
		if( question.getUser().getUid() <= 0){
			question.setUser(null);
		}
		if(question.getUser().getUid() > 0){
			question.setUser(userRepository.getUser(question.getUser().getUid()));
		}
		setAnswerUser(question.getBestAnswer());
		for (Answer answer : question.getAnswers()) {
			setAnswerUser(answer);
		}
	}

	protected void setQuestionArea(Question question) {
		if(question != null && question.getProvince()!= null && question.getProvince().getAreaId()>0){
			question.setProvince(areaRepository.getProvince(question.getProvince().getAreaId()));
		}
	}
	protected void setAnswer(Answer answer){
		if(answer == null || answer.getUser().getUid() <= 0){
			return;
		}
		answer.setQuestion(questionRepository.getQuestion(answer.getQuestion().getQid()));
		setAnswerUser(answer);
	}
	protected void setAnswerUser(Answer answer) {
		if(answer == null || answer.getUser().getUid() <= 0){
			return;
		}
		answer.setUser(userRepository.getUser(answer.getUser().getUid()));
	}

	protected void setUsersGroup(List<User> users) {
		for(User user:users){
			setUsersGroup(user);
		}
	}
	protected void setUsersGroup(User user) {
		if(user.getUserGroup().getGroupid() > 0){
			user.setUserGroup(userRepository.getUserGroup(user.getUserGroup().getGroupid()));
		}
	}

	protected void setLawCase(LawCase lawCase) {
		if(lawCase.getCaseId()<=0)
			return;
		lawCase.setUser(userRepository.getUser(lawCase.getUser().getUid()));
		lawCase.setCategory(lawRepository.getLawCategory(lawCase.getCategory().getCatId()));
		lawCase.setProvince(areaRepository.getProvince(lawCase.getProvince().getAreaId()));
		lawCase.setCity(areaRepository.getCity(lawCase.getCity().getAreaId()));
	}
	protected void setLawCatetory(LawCategory lawCategory) {
		if(lawCategory.getParentCategory().getCatId() <= 0){
			lawCategory.setParentCategory(LawCategory.NULL_CATEGORY);
		} else {
			lawCategory.setParentCategory(lawRepository.getLawCategory(lawCategory.getParentCategory().getCatId()));
		}
	}

	protected void storeLawoffice(LawOffice lawOffice) {
		if(lawOffice.getCooperateId()<=0)
			return;
		EcardCacheManager.putToCache(lawOffice.getCooperateId(), lawOffice, lawofficeCache);
	}
	protected void setLawoffice(LawOffice lawOffice) {
		lawOffice.setUser(userRepository.getUser(lawOffice.getUser().getUid()));
		lawOffice.setProvince(areaRepository.getProvince(lawOffice.getProvince().getAreaId()));
		lawOffice.setCity(areaRepository.getCity(lawOffice.getCity().getAreaId()));
	}
	protected void storeLawyer(Lawyer lawyer) {
		if(lawyer.getCooperateId()>0)
			EcardCacheManager.putToCache(lawyer.getCooperateId(), lawyer, lawyerCache);
	}
	protected void setLawyer(Lawyer lawyer) {
		try{
			lawyer.setUser(userRepository.getUser(lawyer.getUser().getUid()));
			lawyer.setProvince(areaRepository.getProvince(lawyer.getProvince().getAreaId()));
			lawyer.setCity(areaRepository.getCity(lawyer.getCity().getAreaId()));
			lawyer.setLawOffice(lawRepository.getLawOffice(lawyer.getLawOffice().getCooperateId()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void storeLawCatetory(LawCategory lawCategory) {
		if(lawCategory.getCatId()>0)
			EcardCacheManager.putToCache(lawCategory.getCatId(), lawCategory, lawCategories);
	}

	protected void setPost(Post post) {
		post.setCategory(postRepository.getCategory(post.getCategory().getCatId()));
		post.setAuthor(userRepository.getUser(post.getAuthor().getUid()));
	}


	protected void setPostCategory(PostCategory category) {
		String[] editorids = StringUtils.split(category.getEditors(), ",");
		User[] users = new User[editorids.length];
		for (int i = 0; i < users.length; i++) {
			users[i] = userRepository.getUser(StringValueUtils
					.getInt(editorids[i]));
		}
		category.setEditor(users);
	}
	protected void storeLawCase(LawCase lawCase) {
		if(lawCase == null)
			return;
		EcardCacheManager.putToCache(lawCase.getCaseId(), lawCase, lawcaseCache);
	}

	public static void storeCityToCache(City city){
		if(city != null){
			EcardCacheManager.putToCache(city.getAreaId(), city, cityCache);
		}
	}
	
	public static void storeProvinceToCache(Province province){
		if(province != null){
			EcardCacheManager.putToCache(province.getAreaId(), province, provinceCache);
		}
	}
	
	public static void storeUser(User user) {
		if(user == null)
			return;
		EcardCacheManager.putToCache(user.getUid(), user, userCache);
	}

	public static void storeUserGroup(UserGroup group) {
		if(group == null)
			return;
		EcardCacheManager.putToCache(group.getGroupid(), group, userGroupCache);
	}

	/**
	 * 从Post对象中提取author放到User仓储中
	 * @param post
	 */
	public static void storeUserFromPost(Post post) {
		if(post == null)
			return;
		storeUser(post.getAuthor());
	}
	
	/**
	 * 从QUESTION对象中提取USER放到User仓储中
	 * @param post
	 */
	public static void storeUserFromPostCategory(PostCategory postCategory) {
		if(postCategory == null)
			return;
		for(User user: postCategory.getEditor()){
			storeUser(user);
		}
	}
	
	/**
	 * 从QUESTION对象中提取USER放到User仓储中
	 * @param question
	 */
	public static void storeUserFromAnswer(Answer answer) {
		if(answer == null)
			return;
		storeUser(answer.getUser());
	}

	/**
	 * 从QUESTION对象中提取USER放到User仓储中
	 * @param question
	 */
	public static void storeUserFromQuestion(Question question) {
		if(question == null)
			return;
		storeUser(question.getUser());
	}
	
	public static List<? extends EcardModel> getAllCacheValues(String cacheName) {
		return EcardCacheManager.getAllValues(cacheName);
	}
	public static List<? extends EcardModel> getAllCacheValues(Ehcache cache) {
		return EcardCacheManager.getAllValues(cache);
	}

	/**
	 * store to cache
	 * 
	 * @param question
	 */
	public static void storeQuestion(Question question) {
		if (question == null)
			return;
		EcardCacheManager
				.putToCache(question.getQid(), question, questionCache);
		AnswerRepositoryDao.storeAnswerFromQuestion(question);
		UserRepositoryDao.storeUserFromQuestion(question);
	}

	public static void storeAnswerFromQuestion(Question question) {
		if(question == null)
			return;
		storeAnswer(question.getBestAnswer());
		if(question.getAnswers() != null && question.getAnswers().size()>0){
			for (Answer answer : question.getAnswers()) {
				storeAnswer(answer);
			}
		}
	}
	
	public static void storeAnswer(Answer answer) {
		if(answer == null)
			return;
		EcardCacheManager.putToCache(answer.getId(), answer, answerCache);
		UserRepositoryDao.storeUserFromAnswer(answer);
	}

	public static void storePost(Post post) {
		if (post == null)
			return;
		EcardCacheManager.putToCache(post.getPostid(), post, postCache);
		UserRepositoryDao.storeUserFromPost(post);
	}

	public static void storePostCategoryFromPost(Post post) {
		if (post == null)
			return;
		EcardCacheManager.putToCache(post.getCategory().getCatId(),
				post.getCategory(), postCategoryCache);
		UserRepositoryDao.storeUserFromPostCategory(post.getCategory());
	}

	public static void storePostCategory(PostCategory category) {
		if (category == null)
			return;
		EcardCacheManager.putToCache(category.getCatId(), category,
				postCategoryCache);
		UserRepositoryDao.storeUserFromPostCategory(category);
	}
	
	public static Object getValueFromCache(Ehcache cache, Serializable key) {
		return EcardCacheManager.getValue(cache, key);
	}
	public static boolean containKeyInCache(Ehcache cache, Serializable key) {
		return EcardCacheManager.containKeyInCache(cache, key);
	}
	public static void removeFromCache(Ehcache cache, Serializable key) {
		EcardCacheManager.removeFromCache(key, cache);
	}
	
	protected long getCount(EcardServiceCondition condition){
		return EcardDao.getCount(condition);
	}
}
