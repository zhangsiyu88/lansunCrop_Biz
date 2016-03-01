package com.lansun.qmyo.maijie_biz.dirmanager;

import java.util.List;

public abstract class DirectoryContext {

	protected Directory mBaseDirectory;
	
	public Directory getBaseDirectory() {
		return mBaseDirectory;
	}
	
	public DirectoryContext(String basePath) {
		Directory dir  = new Directory();
		dir.setName(basePath);
		dir.setType(DirType.APP_BASE);
		this.mBaseDirectory = dir;
		List<Directory> children = initDirectories();
		if (children != null && children.size()  >0)
			dir.addChildren(children);
	}
	
	protected abstract List<Directory> initDirectories();
}
