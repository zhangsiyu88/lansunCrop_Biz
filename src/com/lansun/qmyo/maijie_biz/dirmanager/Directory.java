package com.lansun.qmyo.maijie_biz.dirmanager;

import java.util.ArrayList;
import java.util.List;

public class Directory{
	private DirType mType;
	private String mName;
	private Directory mParent;
	private List<Directory> mChildren;
	
	public DirType getType() {
		return mType;
	}
	
	public void setType(DirType value) {
		mType = value;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String value) {
		mName = value;
	}
	
	public Directory getParent() {
		return mParent;
	}
	
	public void setParent(Directory value) {
		mParent = value;
	}
	
	public List<Directory> getChildren() {
		return mChildren;
	}	
	
	public void addChild(Directory directory) {
		if (mChildren == null) {
			mChildren = new ArrayList<Directory>();
		}
		directory.setParent(this);
		mChildren.add(directory);
	}
	
	public void addChild(DirType type, String name) {
		Directory child = new Directory();
		child.setType(type);
		child.setName(name);		
		addChild(child);
	}
	
	public void addChildren(List<Directory> children) {
		if (children == null || children.size() == 0)
			return;
		for (Directory child: children) {
			addChild(child);
		}
	}
}