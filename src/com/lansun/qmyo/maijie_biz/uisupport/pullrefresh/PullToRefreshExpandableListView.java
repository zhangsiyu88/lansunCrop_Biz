/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.lansun.qmyo.maijie_biz.uisupport.pullrefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.log.FrameLog;
import com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.internal.EmptyViewMethodAccessor;

public class PullToRefreshExpandableListView extends PullToRefreshAdapterViewBase<ExpandableListView> {

	public PullToRefreshExpandableListView(Context context) {
		super(context);
	}

	public PullToRefreshExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshExpandableListView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToRefreshExpandableListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected PinnedExpandableListView createRefreshableView(Context context, AttributeSet attrs) {
		final PinnedExpandableListView lv;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			lv = new PinnedExpandableListView(context, attrs);
		} else {
			lv = new PinnedExpandableListView(context, attrs);
		}

		// Set it to this so it can be used in ListActivity/ListFragment
		lv.setId(android.R.id.list);
		return lv;
	}

	class InternalExpandableListView extends ExpandableListView implements EmptyViewMethodAccessor {

		public InternalExpandableListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshExpandableListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}
	}

	@TargetApi(9)
	final class InternalExpandableListViewSDK9 extends InternalExpandableListView {

		public InternalExpandableListViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshExpandableListView.this, deltaX, scrollX, deltaY, scrollY,
					isTouchEvent);

			return returnValue;
		}
	}
	
	public interface OnHeaderUpdateListener {
        /**
         * 返回一个view对象即可
         * 注意：view必须要有LayoutParams
         */
        public View getPinnedHeader();

        public void updatePinnedHeader(View headerView, int firstVisibleGroupPos);
    }
	
	public class PinnedExpandableListView extends ExpandableListView implements OnScrollListener {
	    private static final String TAG = "PinnedHeaderExpandableListView";

	    private View mHeaderView;
	    private int mHeaderWidth;
	    private int mHeaderHeight;

	    private View mTouchTarget;

	    private OnScrollListener mScrollListener;
	    private OnHeaderUpdateListener mHeaderUpdateListener;

	    private boolean mActionDownHappened = false;
	    protected boolean mIsHeaderGroupClickable = true;


	    public PinnedExpandableListView(Context context) {
	        super(context);
	        initView();
	    }

	    public PinnedExpandableListView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        initView();
	    }

	    public PinnedExpandableListView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        initView();
	    }

	    private void initView() {
	        setFadingEdgeLength(0);
	        setOnScrollListener(this);
	    }

	    @Override
	    public void setOnScrollListener(OnScrollListener l) {
	        if (l != this) {
	            mScrollListener = l;
	        } else {
	            mScrollListener = null;
	        }
	        super.setOnScrollListener(this);
	    }

	    @Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshExpandableListView.this, deltaX, scrollX, deltaY, scrollY,
					isTouchEvent);

			return returnValue;
		}
	    
	    /**
	     * 给group添加点击事件监听
	     * @param onGroupClickListener 监听
	     * @param isHeaderGroupClickable 表示header是否可点击<br/>
	     * note : 当不想group可点击的时候，需要在OnGroupClickListener#onGroupClick中返回true，
	     * 并将isHeaderGroupClickable设为false即可
	     */
	    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener, boolean isHeaderGroupClickable) {
	        mIsHeaderGroupClickable = isHeaderGroupClickable;
	        super.setOnGroupClickListener(onGroupClickListener);
	    }

	    public void setOnHeaderUpdateListener(OnHeaderUpdateListener listener) {
	        mHeaderUpdateListener = listener;
	        if (listener == null) {
	            mHeaderView = null;
	            mHeaderWidth = mHeaderHeight = 0;
	            return;
	        }
	        mHeaderView = listener.getPinnedHeader();
	        int firstVisiblePos = getFirstVisiblePosition();
	        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
	        listener.updatePinnedHeader(mHeaderView, firstVisibleGroupPos);
	        requestLayout();
	        postInvalidate();
	    }

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        if (mHeaderView == null) {
	            return;
	        }
	        measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
	        mHeaderWidth = mHeaderView.getMeasuredWidth();
	        mHeaderHeight = mHeaderView.getMeasuredHeight();
	    }

	    @Override
	    protected void onLayout(boolean changed, int l, int t, int r, int b) {
	        super.onLayout(changed, l, t, r, b);
	        if (mHeaderView == null) {
	            return;
	        }
	        int delta = mHeaderView.getTop();
	        mHeaderView.layout(0, delta, mHeaderWidth, mHeaderHeight + delta);
	    }

	    @Override
	    protected void dispatchDraw(Canvas canvas) {
	        super.dispatchDraw(canvas);
	        if (mHeaderView != null) {
	            drawChild(canvas, mHeaderView, getDrawingTime());
	        }
	    }

	    @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
	        int x = (int) ev.getX();
	        int y = (int) ev.getY();
	        int pos = pointToPosition(x, y);
	        if (mHeaderView != null && y >= mHeaderView.getTop() && y <= mHeaderView.getBottom()) {
	            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
	                mTouchTarget = getTouchTarget(mHeaderView, x, y);
	                mActionDownHappened = true;
	            } else if (ev.getAction() == MotionEvent.ACTION_UP) {
	                View touchTarget = getTouchTarget(mHeaderView, x, y);
	                if (touchTarget == mTouchTarget && mTouchTarget.isClickable()) {
	                    mTouchTarget.performClick();
	                    invalidate(new Rect(0, 0, mHeaderWidth, mHeaderHeight));
	                } else if (mIsHeaderGroupClickable){
	                    int groupPosition = getPackedPositionGroup(getExpandableListPosition(pos));
	                    if (groupPosition != INVALID_POSITION && mActionDownHappened) {
	                        if (isGroupExpanded(groupPosition)) {
	                            collapseGroup(groupPosition);
	                            ((ImageView)mHeaderView.findViewById(R.id.image)).setImageResource(R.drawable.right_arrow);
	                        } else {
	                            expandGroup(groupPosition);
	                            ((ImageView)mHeaderView.findViewById(R.id.image)).setImageResource(R.drawable.down_arrow);
	                        }
	                    }
	                }
	                mActionDownHappened = false;
	            }
	            return true;
	        }

	        return super.dispatchTouchEvent(ev);
	    }

	    private View getTouchTarget(View view, int x, int y) {
	        if (!(view instanceof ViewGroup)) {
	            return view;
	        }

	        ViewGroup parent = (ViewGroup)view;
	        int childrenCount = parent.getChildCount();
	        final boolean customOrder = isChildrenDrawingOrderEnabled();
	        View target = null;
	        for (int i = childrenCount - 1; i >= 0; i--) {
	            final int childIndex = customOrder ? getChildDrawingOrder(childrenCount, i) : i;
	            final View child = parent.getChildAt(childIndex);
	            if (isTouchPointInView(child, x, y)) {
	                target = child;
	                break;
	            }
	        }
	        if (target == null) {
	            target = parent;
	        }

	        return target;
	    }

	    private boolean isTouchPointInView(View view, int x, int y) {
	        if (view.isClickable() && y >= view.getTop() && y <= view.getBottom()
	                && x >= view.getLeft() && x <= view.getRight()) {
	            return true;
	        }
	        return false;
	    }

	    public void requestRefreshHeader() {
	        refreshHeader();
	        invalidate(new Rect(0, 0, mHeaderWidth, mHeaderHeight));
	    }

	    protected void refreshHeader() {
	        if (mHeaderView == null) {
	            return;
	        }
	        int firstVisiblePos = getFirstVisiblePosition();
	        int pos = firstVisiblePos + 1;
	        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
	        int group = getPackedPositionGroup(getExpandableListPosition(pos));
//	        if (DEBUG) {
//	            Log.d(TAG, "refreshHeader firstVisibleGroupPos=" + firstVisibleGroupPos);
//	        }

	        if (group == firstVisibleGroupPos + 1) {
	            View view = getChildAt(1);
	            if (view == null) {
	            	FrameLog.w(TAG, "Warning : refreshHeader getChildAt(1)=null");
	                return;
	            }
	            if (view.getTop() <= mHeaderHeight) {
	                int delta = mHeaderHeight - view.getTop();
	                mHeaderView.layout(0, -delta, mHeaderWidth, mHeaderHeight - delta);
	            } else {
	                //TODO : note it, when cause bug, remove it
	                mHeaderView.layout(0, 0, mHeaderWidth, mHeaderHeight);
	            }
	        } else {
	            mHeaderView.layout(0, 0, mHeaderWidth, mHeaderHeight);
	        }

	        if (mHeaderUpdateListener != null) {
	            mHeaderUpdateListener.updatePinnedHeader(mHeaderView, firstVisibleGroupPos);
	        }
	    }

	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
	        if (mScrollListener != null) {
	            mScrollListener.onScrollStateChanged(view, scrollState);
	        }
	    }

	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
	            int visibleItemCount, int totalItemCount) {
	        if (totalItemCount > 0) {
	            refreshHeader();
	        }
	        if (mScrollListener != null) {
	            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
	        }
	    }

	}
}
