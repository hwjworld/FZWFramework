package com.legaldaily.estension.ecard.model.law;

import com.legaldaily.estension.ecard.model.EcardModel;

/**
 * 对应q_group
 * @author hwj
 *
 */
public class LawCategory extends EcardModel {
	public static final LawCategory NULL_CATEGORY = new LawCategory();
	private int catId;
	private LawCategory parentCategory;
	private String catName;
	//栏目的权限
	private String qg_ruler;
	//栏目的点击量
	private int pv_values;
	
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public LawCategory getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(LawCategory parentCategory) {
		this.parentCategory = parentCategory;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getQg_ruler() {
		return qg_ruler;
	}
	public void setQg_ruler(String qg_ruler) {
		this.qg_ruler = qg_ruler;
	}
	public int getPv_values() {
		return pv_values;
	}
	public void setPv_values(int pv_values) {
		this.pv_values = pv_values;
	}
}
