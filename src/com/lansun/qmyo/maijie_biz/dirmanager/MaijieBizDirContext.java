package com.lansun.qmyo.maijie_biz.dirmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class MaijieBizDirContext extends DirectoryContext {

	public MaijieBizDirContext(String appName) {
	      //外部
		super(Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator + appName);
	}

	@Override
	protected List<Directory> initDirectories() {
		ArrayList<Directory> children = new ArrayList<Directory>();

		// AddChild(children, DirType.LYRICS, "lyrics");
		AddChild(children, DirType.CACHE, "cache");
		AddChild(children, DirType.DOWNLOAD, "download");
		// AddChild(children, DirType.LOG, "log");
		// AddChild(children, DirType.MUSIC, "music");
		AddChild(children, DirType.PICTURE, "picture");
		AddChild(children, DirType.TEMP, "temp");
		// AddChild(children, DirType.SEARCH, "search");
		// AddChild(children, DirType.UPDATE, "update");
		// AddChild(children, DirType.SKIN, "skins");
		// AddChild(children, DirType.CODEC, "codecs");
		// AddChild(children, DirType.PREFETCH, "prefetch");
		AddChild(children, DirType.WELCOME, "welcome");
		AddChild(children, DirType.CRASH, "crash");
		// AddChild(children, DirType.GAME, "game");

		// Directory recordDirectory = AddChild(children, DirType.RECORD,
		// "recorder");
		// recordDirectory.addChild(DirType.TEXT, "text");
		// recordDirectory.addChild(DirType.PHOTO, "photo");
		// recordDirectory.addChild(DirType.AUDIO, "audio");
		// recordDirectory.addChild(DirType.VEDIO, "vedio");

		return children;
	}

	private Directory AddChild(ArrayList<Directory> children, DirType type,
			String name) {
		Directory child = new Directory();
		child.setType(type);
		child.setName(name);
		children.add(child);
		return child;
	}
}
