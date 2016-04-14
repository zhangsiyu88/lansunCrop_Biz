package com.lansun.qmyo.maijie_biz.bean;

import java.util.ArrayList;

public class ActivityInfoBean {

	/**活动名称*/
	public String _name;

	/**活动等级*/
	public String _degree;
	
	/**活动分类*/
	public String _classfication;
	
	/**活动标签*/
	public String _tag;
	
	/**活动标题*/
	public String _title;
	
	/**活动详情*/
	public ArrayList<String>  _detail;
	
	/**活动使用规则*/
	public ArrayList<String>  _rules;
	
	/**活动选中的图片路径*/
	public ArrayList<String>  _picture_path;
	
	/**活动持续时间*/
	public String _duration;
	
	/**活动开始日期*/
	public String _start;
	
	/**活动结束日期*/
	public String _end;
	
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_degree() {
		return _degree;
	}

	public void set_degree(String _degree) {
		this._degree = _degree;
	}

	public String get_classfication() {
		return _classfication;
	}

	public void set_classfication(String _classfication) {
		this._classfication = _classfication;
	}

	public String get_tag() {
		return _tag;
	}

	public void set_tag(String _tag) {
		this._tag = _tag;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public ArrayList<String> get_detail() {
		return _detail;
	}

	public void set_detail(ArrayList<String> _detail) {
		this._detail = _detail;
	}

	public ArrayList<String> get_rules() {
		return _rules;
	}

	public void set_rules(ArrayList<String> _rules) {
		this._rules = _rules;
	}

	public ArrayList<String> get_picture_path() {
		return _picture_path;
	}

	public void set_picture_path(ArrayList<String> _picture_path) {
		this._picture_path = _picture_path;
	}

	public String get_duration() {
		return _duration;
	}

	public void set_duration(String _duration) {
		this._duration = _duration;
	}

	public String get_start() {
		return _start;
	}

	public void set_start(String _start) {
		this._start = _start;
	}

	public String get_end() {
		return _end;
	}

	public void set_end(String _end) {
		this._end = _end;
	}

}
