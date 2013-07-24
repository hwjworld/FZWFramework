package com.legaldaily.estension.ecard.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.fzw.Globals;
import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;
import com.legaldaily.estension.ecard.repository.AreaRepository;
import com.legaldaily.estension.ecard.repository.EcardRepository;
import com.legaldaily.estension.ecard.repository.LawRepository;
import com.legaldaily.estension.ecard.repository.PostRepository;
import com.legaldaily.estension.ecard.repository.SideRepository;
import com.legaldaily.estension.ecard.repository.UserRepository;

/**
 * Question Cache (LRU, 1000 questions) Answer Cache (LRU, 1000 questions) User
 * Cache (all users) RecommendQuestion Cache (all recommend questions)
 * WaitRecommendQuestion Cache (the lastest 150 questions) Question Group Cache
 * (all) UserGroup Cache (all)
 * 
 * @author hwj
 * 
 */
public class EcardCacheManager extends com.fzw.cache.CacheManager {

	public static final String CACHE_QUESTION = "Question";
	public static final String CACHE_ANSWER = "Answer";
	public static final String CACHE_USER = "User";
	/**
	 * 已推荐问题Question对象列表<br/>
	 * key=Long qid value=Recommendation
	 */
	public static final String CACHE_RECOMMENDQUESTION = "RecommendQuestion";
	/**
	 * 待推荐问题Question对象列表
	 */
	public static final String CACHE_WAITRECOMMENDQUESTION = "WaitRecommendQuestion";
	public static final String CACHE_QUESTIONGROUP = "QuestionGroup";
	public static final String CACHE_USERGROUP = "UserGroup";
	/**
	 * key = catid value = cache(key= postid, value=Post)
	 */
	public static final String CACHE_POSTS = "Posts";
	public static final String CACHE_POSTCATEGORY = "PostCategory";

	public static final String CACHE_CITIES = "cities";
	public static final String CACHE_PROVINCES = "provinces";

	public static final String CACHE_LAWCASES = "lawcases";
	public static final String CACHE_LAWOFFICE = "lawoffice";
	public static final String CACHE_LAWYER = "lawyer";
	public static final String CACHE_LAWCATEGORIES = "lawcategory";

	private static CacheManager singletonManager = CacheManager.create();

	static {
		// add caches to manager
		singletonManager.addCache(CACHE_QUESTION);
		singletonManager.getCache(CACHE_QUESTION).getCacheConfiguration().setMaxElementsInMemory(2000);
		singletonManager.addCache(CACHE_ANSWER);
		singletonManager.getCache(CACHE_ANSWER).getCacheConfiguration().setMaxElementsInMemory(4000);
		singletonManager.addCache(CACHE_USER);
		singletonManager.addCache(CACHE_USERGROUP);
		singletonManager.addCache(CACHE_RECOMMENDQUESTION);
		// singletonManager.addCache(CACHE_WAITRECOMMENDQUESTION);
		singletonManager.addCache(CACHE_QUESTIONGROUP);
		singletonManager.addCache(CACHE_POSTCATEGORY);
		singletonManager.addCache(CACHE_POSTS);
		singletonManager.addCache(CACHE_CITIES);
		singletonManager.addCache(CACHE_PROVINCES);
		singletonManager.addCache(CACHE_LAWCASES);
		singletonManager.addCache(CACHE_LAWOFFICE);
		singletonManager.addCache(CACHE_LAWYER);
		singletonManager.addCache(CACHE_LAWCATEGORIES);
	}

	public static Ehcache getCache(String cachetype) {
		return singletonManager.getEhcache(cachetype);
	}

	public static void main(String[] args) {
		Ehcache cache = getCache(CACHE_QUESTION);
		// Cache cache1 = getCache(CACHE_ANSWER);
		// ArrayList list = new ArrayList();
		// list.add("a");
		// cache.put(new Element("123", list));
		// cache1.put(new Element("123", cache.get("123").getValue()));
		// ((ArrayList)cache.get("123").getValue()).add("b");
		//
		//
		// System.err.println(((ArrayList)cache.get("123").getValue()).size());
		// System.err.println(((ArrayList)cache.get("123").getValue()).size());
		// System.out.println(cache.get("123").getValue()==cache1.get("123").getValue());

		System.out.println("----------");
		cache.put(new Element("aa", "bb"));
		cache.put(new Element("aa", "cc"), true);
		System.out.println(cache.get("aa").getValue());
		// Cache testCache = new Cache(new CacheConfiguration("test",
		// maxElements)
		// .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
		// .overflowToDisk(true).eternal(false).timeToLiveSeconds(60)
		// .timeToIdleSeconds(30).diskPersistent(false)
		// .diskExpiryThreadIntervalSeconds(0));
		// manager.addCache(cache);
	}

	public static void initCaches() {

		//第一步先加载user,这是基础
		UserRepository userRepository = (UserRepository) Globals
				.getBean("userRepository");
		userRepository.getAllUserGroups();
		userRepository.getAllUsers();
		//第二步地区
		AreaRepository areaRepository = (AreaRepository) Globals
				.getBean("areaRepository");
		areaRepository.getAllProvinces();
		areaRepository.getAllCities();
		
		
		LawRepository lawRepository = (LawRepository) Globals
				.getBean("lawRepository");
		lawRepository.getAllLawCategories();
		lawRepository.getAllLawCases();
		lawRepository.getAllLawOffices();
		lawRepository.getAllLawyers();


		PostRepository postRepository = (PostRepository) Globals
				.getBean("postRepository");
		postRepository.getAllPostCategorys();
		postRepository.getAllPosts();

		SideRepository sideRepository = (SideRepository) Globals.getBean("sideRepository");
		sideRepository.getAllDefaultSetting();
//		QuestionRepository questionRepository = (QuestionRepository)Globals.getBean("questionRepository");
//		questionRepository.getAllQuestions();
		
//		AnswerRepository answerRepository = (AnswerRepository)Globals.getBean("answerRepository");
//		answerRepository.getAllAnswers();
		

	}

	public static void saveListToCache(List<? extends EcardModel> list) {
		for (EcardModel model : list) {
			if (model instanceof Post) {
				Post post = (Post) model;
				putToCache(post.getPostid(), post, getCache(CACHE_POSTS));
			} else if (model instanceof PostCategory) {
				PostCategory category = (PostCategory) model;
				putToCache(category.getCatId(), category,
						getCache(CACHE_POSTCATEGORY));
			} else if (model instanceof User) {
				User user = (User) model;
				putToCache(user.getUid(), user, getCache(CACHE_USER));
			} else if (model instanceof UserGroup) {
				UserGroup group = (UserGroup) model;
				putToCache(group.getGroupid(), group, getCache(CACHE_USERGROUP));
			}
		}
	}

	public static List<EcardModel> getAllValues(String cacheName) {
		return getAllValues(getCache(cacheName));
	}

	public static List<EcardModel> getAllValues(Ehcache cache) {
		List<EcardModel> list = null;
		if (cache == null)
			return list;
		list = new ArrayList<EcardModel>();
		for (Object key : cache.getKeys()) {
			list.add((EcardModel) cache.get(key).getValue());
		}
		return list;
	}


}
