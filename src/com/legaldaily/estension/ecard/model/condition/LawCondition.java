package com.legaldaily.estension.ecard.model.condition;

import org.apache.commons.lang.ArrayUtils;

import com.fzw.model.ConnectionMessage;
import com.fzw.utils.StringValueUtils;

public class LawCondition extends EcardServiceCondition {

	public LawCondition(ConnectionMessage message) {
		super(message);
	}
	private int id[];
	private int startpos;
	private int count;
	private int parentId[];
	private boolean all;
	
	@Override
	public void buildCondition() {		
		id = StringValueUtils.convert2intArrays(getIds(params.get("catid")));
		if(ArrayUtils.isEmpty(id)){
			id = StringValueUtils.convert2intArrays(getIds(params.get("id")));
		}
		parentId = StringValueUtils.convert2intArrays(getIds(params.get("parentcatid")));
		count = StringValueUtils.getWWPInt(params, "count");
		startpos = StringValueUtils.getWWPInt(params, "startpos");
		all = StringValueUtils.getBoolean(params.get("all"),false);
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int[] getParentId() {
		return parentId;
	}

	public void setParentId(int[] parentId) {
		this.parentId = parentId;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public int getStartpos() {
		return startpos;
	}

	public void setStartpos(int startpos) {
		this.startpos = startpos;
	}

}
