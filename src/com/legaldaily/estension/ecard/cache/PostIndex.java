package com.legaldaily.estension.ecard.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.fzw.Globals;
import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.model.condition.PostCondition;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.repository.PostRepository;

/*
 * 现在是自己实现，以后再实现以下方式
 * 
 * 实现　post全文索引，基于lucense
 * 实现　按条件　取回id数据 只索引 starttime,endtime, status,catid
 * 有方法，借助Arrays.sort，返回排序过数组
 * 
 */
public class PostIndex {

	private static Ehcache postCache = EcardCacheManager.getCache(EcardCacheManager.CACHE_POSTS);
//	private static Ehcache postCategoryCache = EcardCacheManager
//			.getCache(EcardCacheManager.CACHE_POSTCATEGORY);

	private static final String INDEX_POSTSTATUS = "status.postStatus";
	private static final String INDEX_COMMENTSTATUS = "status.commentStatus";
	private static final String INDEX_POSTCATID = "category.catId";
	/*
	 * indexname, list(desc) ex, postStatus.,
	 */
	private static final Map<String, List<Post>> index = new HashMap<String, List<Post>>();

	private static boolean initid = false;
	public static void init() {
		if(!initid){
			LogUtils.info("准备文章索引");
			index();
			LogUtils.info("索引文章结束");
		}
	}
		
	private static void index() {
		List<Serializable> list = postCache.getKeys();
		for (Serializable s : list) {
			Post post = (Post) postCache.get(s).getValue();
			index(post);
		}
	}
	public static void index(Post post){
		if(post != null){
			index(INDEX_POSTSTATUS, post);
			index(INDEX_POSTCATID, post);
			index(INDEX_COMMENTSTATUS, post);
		}
	}
	public static void reindex(Post post){
		if(post != null){
			reindex(INDEX_POSTSTATUS, post);
			reindex(INDEX_POSTCATID, post);
			reindex(INDEX_COMMENTSTATUS, post);
		}
	}
	private static String getIndexName(String field, Post post) {

		String indexname = null;
		if (INDEX_POSTSTATUS.equals(field)) {
			indexname = buildIndexName(INDEX_POSTSTATUS, post.getStatus()
					.getPostStatus());// INDEX_POSTSTATUS+ "=" +
										// post.getStatus().getPostStatus();
		} else if (INDEX_POSTCATID.equals(field)) {
			indexname = getIndexName(INDEX_POSTCATID, post.getCategory()
					.getCatId());// INDEX_POSTCATID+ "=" +
									// post.getCategory().getCatId();
		} else if (INDEX_COMMENTSTATUS.equals(field)) {
			indexname = buildIndexName(INDEX_COMMENTSTATUS, post.getStatus()
					.getCommentStatus());// INDEX_COMMENTSTATUS+ "=" +
											// post.getStatus().getCommentStatus();
		}
		return indexname;
	}
	private static void reindex(String field, Post post) {
		unwrite2index(getIndexName(field, post), post);
		write2index(getIndexName(field, post), post);
	}
	private static void index(String field, Post post) {
		write2index(getIndexName(field, post), post);
	}
	

	public static String buildIndexName(String field, String value) {
		return field + "=" + value;
	}

	public static String getIndexName(String field, int value) {
		return buildIndexName(field, String.valueOf(value));
	}

	private static void write2index(String indexname, Post post) {
		List<Post> list = getIndexList(indexname);
		list.add(post);
	}
	private static void unwrite2index(String indexname, Post post) {
		List<Post> list = getIndexList(indexname);
		for (int i = list.size()-1; i >= 0 ; i--) {
			if(list.get(i).getPostid() == post.getPostid()){
				list.remove(i);
				break;
			}
		}
	}
	private static List<Post> getIndexList(String indexname) {
		List<Post> list = null;
		if (index.containsKey(indexname)) {
			list = index.get(indexname);
		} else {
			list = new ArrayList<Post>();
			index.put(indexname, list);
		}
		return list;
	}
	/**
	 * 根据索引listPost，算法需改变
	 * 
	 * @param condition
	 * @return
	 */
	public static List<Post> listPost(PostCondition condition) {
		List<Post> rvList = new ArrayList<Post>();
		if (ArrayUtils.isNotEmpty(condition.getPostid())) {
			for (int i = 0; i < condition.getPostid().length; i++) {
				Post post = ((PostRepository) Globals.getBean("postRepository"))
						.getPost(condition.getPostid()[i]);
				if(post!=null){
					rvList.add(post);
				}
			}
			
		} else {
			if (condition.getCatid() > 0) {

				List<Post> tmp = index.get(getIndexName(INDEX_POSTCATID,
						condition.getCatid()));
				if (CollectionUtils.isNotEmpty(tmp)) {
					rvList.addAll(tmp);
				}
			}
			if (!StringUtils.isBlank(condition.getStatus())) {
				List<Post> tmp = index.get(buildIndexName(INDEX_POSTSTATUS,
						condition.getStatus()));

				if (CollectionUtils.isNotEmpty(tmp)) {
					if (condition.getCatid() > 0) {
						rvList = ListUtils.intersection(rvList, tmp);
					} else {
						rvList = ListUtils.union(rvList, tmp);
					}
				}
			}
		}
		for (int i = rvList.size() - 1; i >= 0; i--) {
			Post post = rvList.get(i);
			if (post.getPostDate().before(condition.getStarttime())
					|| post.getPostDate().after(condition.getEndtime())) {
				rvList.remove(i);
			}
		}
		if (CollectionUtils.isNotEmpty(rvList)) {
//			Post[] postsRv = new Post[rvList.size()];
//			rvList.toArray(postsRv);
			if (ArrayUtils.isNotEmpty(condition.getOrderby()) && condition.getOrderby().length > 1) {
//				Arrays.sort(postsRv, getComparator(condition.getOrderby()));
				Collections.sort(rvList,getComparator(condition.getOrderby()));
			}
			if (condition.getCount() >= 0) {
//				postsRv = (Post[]) ArrayUtils.subarray(postsRv,
//						condition.getBenpos(), condition.getCount()+condition.getBenpos());
				rvList = (List<Post>) com.fzw.utils.ListUtils.subList(rvList, condition.getBenpos(), condition.getCount()+condition.getBenpos());
			}
//			rvList.clear();
//			CollectionUtils.addAll(rvList, postsRv);
		}
		return rvList;
	}
	
	/**
	 * 从索引中删除post
	 * @param post
	 */
	public static void removePostFromIndex(Post post){
		String indexname = getIndexName(INDEX_POSTCATID, post.getCategory().getCatId());
		removeFromIndex(indexname, post);
		
		indexname = buildIndexName(INDEX_COMMENTSTATUS, post.getStatus().getCommentStatus());
		removeFromIndex(indexname, post);
		indexname = buildIndexName(INDEX_POSTSTATUS, post.getStatus().getPostStatus());
		removeFromIndex(indexname, post);
	}
	private static void removeFromIndex(String indexname, Post post) {
		List<Post> list = index.get(indexname);
		list.remove(post);
	}

	
	private static ComparatorChain getComparator(String[] orderby) {
		ComparatorChain cc = new ComparatorChain();
		for (int i = 0; i < orderby.length; i += 2) {
			final String order = orderby[i];
			final String by = orderby[i + 1];
			cc.addComparator(new Comparator<Post>() {

				@Override
				public int compare(Post o1, Post o2) {
					if ("menu_order".equalsIgnoreCase(order)) {
						if (o1.getStatus().getOrder() > o2.getStatus()
								.getOrder()) {
							if ("asc".equals(by)) {
								return 1;
							} else {
								return -1;
							}
						} else if (o1.getStatus().getOrder() == o2.getStatus()
								.getOrder()) {
							return 0;
						} else {
							if ("asc".equals(by)) {
								return -1;
							} else {
								return 1;
							}
						}
					}

					return 0;
				}
			});
			cc.addComparator(new Comparator<Post>() {

				@Override
				public int compare(Post o1, Post o2) {
					if ("post_date".equalsIgnoreCase(order)) {
						if (o1.getPostDate().after(o2.getPostDate())) {
							if ("asc".equals(by)) {
								return 1;
							} else {
								return -1;
							}
						}else if (o1.getPostDate().equals(o2.getPostDate())) {
							return 0;
						}else {
							if ("asc".equals(by)) {
								return -1;
							} else {
								return 1;
							}
						} 
					}
					return 0;
				}
			});
		}
		return cc;
	}

	public static void main(String[] args) {
		// Maput
		// list.add(new Date());
		// list.add(new Date(2010-1900,11,3));
		// list.add(new Date(2010-1900,11,1));
		// list.add(new Date(2010-1900,11,5));
		// Object [] o = list.toArray();
		// Arrays.sort(o);
		// Arrays.
		// Object [] o1 = ArrayUtils.subarray(o, ArrayUtils.(o, new
		// Date(2010-1900,11,2)), ArrayUtils.indexOf(o, new
		// Date(2010-1900,11,6)));
		// // for(Object oo:o){
		// // System.out.println(oo);
		// // }
		// for(Object oo:o1){
		// System.out.println(oo);
		// }
	}
}
