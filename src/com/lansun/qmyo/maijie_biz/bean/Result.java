package com.lansun.qmyo.maijie_biz.bean;

import java.io.Serializable;

import org.json.JSONObject;

public class Result implements Serializable, IParseObj {

	private static final long serialVersionUID = 1L;

	public String title;
	public int type;
	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		return false;
	}
}
