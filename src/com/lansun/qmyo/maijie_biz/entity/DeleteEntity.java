package com.lansun.qmyo.maijie_biz.entity;


/**
 * Fragment 跳转对象
 * 
 * @author gdpancheng@gmail.com 2013-10-28 上午10:06:08
 */
public class DeleteEntity {
	private int position;
	
	//下面两个变量是本人后来添加的
	private boolean single;
	private int flag;



	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
