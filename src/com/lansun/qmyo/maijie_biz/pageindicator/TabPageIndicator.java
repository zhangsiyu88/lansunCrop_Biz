package com.lansun.qmyo.maijie_biz.pageindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;

/**
 * 实现了带underline动画效果的tab导航栏，如果tab数超出了屏幕（导航栏自动滑动），�?好使用默认的DefaultTabPageIndicator样式
 * 或�?�使用setTabCursor(int resId,int duration,boolean isStrongSupported),并将isStrongSupported
 * 置为true
 * @author  Yeun.Zhang
 *
 */
public class TabPageIndicator extends HorizontalScrollView implements PageIndicator {
    /** Title text used when no title is provided by the adapter. */
    private static final CharSequence EMPTY_TITLE = "";

    /**
     * Interface for a callback when the selected tab has been reselected.
     */
    public interface OnTabReselectedListener {
        /**
         * Callback when the selected tab has been reselected.
         *
         * @param position Position of the current center item.
         */
        void onTabReselected(int position);
    }

    private Runnable mTabSelector;
     

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            TabView tabView = (TabView)view;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected,false);
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }
        }
    };

    private final IcsLinearLayout mTabLayout;

    
    private MainViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    
    //underline
    private ImageView mTabCursor = null;
    private int mCursorResId = 0;
    private boolean mTabCursorInit = false;
    private int mPreItemId = 0;

    private int mMaxTabWidth;
    private int mSelectedTabIndex;
    private boolean mIsStrongSupported = false; //是否强支持动画
    private int mDuration = 300; //默认300毫秒动画
    private Runnable mCursorRunnable;

    private OnTabReselectedListener mTabReselectedListener;

    public TabPageIndicator(Context context) {
        this(context, null);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);

        
        //IcsLinearLayout是 具体涉及到每个标签的样式内容
        mTabLayout = new IcsLinearLayout(context, R.attr.TabPageIndicatorStyle);
        addView(mTabLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        
        
        //mTabCursor为下划线
        if (mTabCursor == null) {
        	mTabCursor = new ImageView(context);
		}
    }
    
    /**
     * 注意：使用TabPageIndicator时最外层需要有RelativeLayout作为parent
     * 当出现位置计算不准时可以将isStrongSupported设为true，或者采用DefaultTabPageIndicator
     * @param resId
     * @param duration
     */
	public void setTabCursor(int resId, int duration) {

		mDuration = duration;
		mCursorResId = resId;
		// 外层采用相对布局
		RelativeLayout parent = (RelativeLayout) (this.getParent());
		if (parent != null) {
			RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT, //注意MATCH_PARENT
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			rLayoutParams.alignWithParent = true;
			rLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			parent.addView(mTabCursor, rLayoutParams);
		}	
	}
	
	/**
	 * @deprecated use {@link #setTabCursor(int resId,int duration)} instead
	 * @param resId
	 * @param duration
	 * @param isStrongSupported
	 */
	@Deprecated
	public void setTabCursor(int resId,int duration,boolean isStrongSupported){
		mIsStrongSupported = isStrongSupported;
		setTabCursor(resId, duration);
	}
	
	

    public void setOnTabReselectedListener(OnTabReselectedListener listener) {
        mTabReselectedListener = listener;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);

        final int childCount = mTabLayout.getChildCount();
        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            if (childCount > 2) {
                mMaxTabWidth = (int)(MeasureSpec.getSize(widthMeasureSpec) * 0.4f);//当子childCount数目大于2时，其宽度为原本的0.4
            } else {
                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            }
        } else {
            mMaxTabWidth = -1;
        }

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentItem(mSelectedTabIndex);
        }
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
        
        if (mCursorRunnable != null) {
			post(mCursorRunnable);
		}
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
            mTabSelector = null;
        }
        if (mCursorRunnable != null) {
        	removeCallbacks(mCursorRunnable);
        	mCursorRunnable = null;
		}
    }

    /**
     * TabView中  添加      Drawable的icon  和    文字的text
     * 
     * @param index
     * @param text
     * @param icon
     */
    private void addTab(int index, CharSequence text, Drawable icon) {
    	 final TabView tabView = new TabView(getContext());
         tabView.mIndex = index;
         tabView.setFocusable(true);
         tabView.setOnClickListener(mTabClickListener);
         
         //已经放到配置�?
         //float d = getResources().getDimension(R.dimen.tab_title_textSize);
         //tabView.setTextSize(TypedValue.COMPLEX_UNIT_PX, d);
         //tabView.setTextColor(getResources().getColorStateList(R.drawable.my_tabtext_selector));
         
         tabView.setText(text);
         
         if (icon != null) {
             tabView.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);        
//             tabView.setBackground(icon);  //Yadd 
//             tabView.setBackgroundResource(R.drawable.tab_bg_selector);
         }
        mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
	public void onPageSelected(final int arg0) {
		setCurrentItem(arg0);
		if (mListener != null) {
			mListener.onPageSelected(arg0);
		}

		// 处理动画
		if (mCursorResId != 0) {

			if (mIsStrongSupported) {

				if (mCursorRunnable != null) {
					removeCallbacks(mCursorRunnable);
				}
				mCursorRunnable = new Runnable() {
					@Override
					public void run() {
						startCursorAnimation(arg0, mPreItemId,mDuration);
						mPreItemId = arg0;
						mCursorRunnable = null;
					}
				};
				postDelayed(mCursorRunnable, 200);////延迟消息等待坐标计算准确
			} else {
				startCursorAnimation(arg0, mPreItemId,mDuration);
				mPreItemId = arg0;
			}
		}
	}

    @Override
    public void setViewPager(MainViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    /**
     * 变换TabHost的每一个标签的样式
     */
    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        IconPagerAdapter iconAdapter = null;
        if (adapter instanceof IconPagerAdapter) {
            iconAdapter = (IconPagerAdapter)adapter;
        }
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            Drawable icon = null;
            if (iconAdapter != null) {
            	icon = iconAdapter.getIconDrawable(i);
            }
            addTab(i, title, icon);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    @Override
    public void setViewPager(MainViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }
    
	@Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            return;
        }
       
        mViewPager.setCurrentItem(item);
        

        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
            }
        }
        mSelectedTabIndex = item;
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    /**
     * TabView对象作为底部文字塞入到mTabLayout中
     * 
     * 就文字内容进行的大小分割，重写了其中的onDraw()方法
     * 
     * @author Yeun.Zhang
     *
     */
    private class TabView extends TextView {
        private int mIndex;

        public TabView(Context context) {
            super(context, null, R.attr.TabPageIndicatorStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Re-measure if we went beyond our maximum size.
            if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
                
            }
        }
        
        @Override
        protected void onDraw(Canvas canvas) {
            Drawable[] drawables = getCompoundDrawables();
            if (drawables != null) {
                Drawable drawableTop = drawables[1];
                if (drawableTop != null) {
                	//画文字内容
                    float textHeight = getPaint().measureText(getText().toString())/getText().length();
//                    float textWidth = getPaint().measureText(getText().toString())/getText().length();//Yadd
                    int drawablePadding = getCompoundDrawablePadding();
                    int drawableHeight = 0;
                    drawableHeight = drawableTop.getIntrinsicHeight();
                    float bodyHeight = textHeight + drawableHeight + drawablePadding;
//                    canvas.translate((getWidth() - textWidth) / 2,(getHeight() - bodyHeight) / 2);//Yadd
                    canvas.translate(0,(getHeight() - bodyHeight) / 2);//Yadd
                    invalidate();
                }
            }
            super.onDraw(canvas);
        }

        public int getIndex() {
            return mIndex;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    public int getTabItemLeft(int item) {
    	final int tabCount = mTabLayout.getChildCount();
    	if (item >= 0 && item < tabCount) {
    		return mTabLayout.getChildAt(item).getLeft();
    	}
    	return -1;
    }
    public int getTabItemPaddingLeft(int item) {
    	final int tabCount = mTabLayout.getChildCount();
    	if (item >= 0 && item < tabCount) {
    		return mTabLayout.getChildAt(item).getPaddingLeft();
    	}
    	return -1;
    }
    public int getTabItemPaddingRight(int item) {
    	final int tabCount = mTabLayout.getChildCount();
    	if (item >= 0 && item < tabCount) {
    		return mTabLayout.getChildAt(item).getPaddingRight();
    	}
    	return -1;
    }
    public int getTabItemWidth(int item) {
    	final int tabCount = mTabLayout.getChildCount();
    	if (item >= 0 && item < tabCount) {   		
    		return mTabLayout.getChildAt(item).getWidth();
    	}
    	return -1;
    }
    public int getTabItemHeight(int item) {
    	final int tabCount = mTabLayout.getChildCount();
    	if (item >= 0 && item < tabCount) {
    		return mTabLayout.getChildAt(item).getHeight();
    	}
    	return -1;
    }
    
    /**
     * 初始化 TabCursor的就是滑动的那条线
     * @return
     */
    private boolean initTabCursor() {  	
    	final int item  = mViewPager.getCurrentItem();
    	
    	int width = getTabItemWidth(item); 
    	
    	if (width <= 0) {
    		return false;
    	}
    	Bitmap bmp = BitmapFactory.decodeResource(getResources(), mCursorResId);
    	if (bmp == null) {
    		return false;
    	}
    	
    	Matrix m = new Matrix();
    	mTabCursor.setScaleType(ScaleType.MATRIX);
		m.postScale(width / bmp.getWidth(),(float) 0.2); //参数为宽高的比例，如缩小0.8,放大1.2
		final Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
		
//		mTabCursor.setScaleType(ScaleType.FIT_CENTER);
//		final Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth()/10, bmp.getHeight()/10);
		
		
		if (!bmp.isRecycled()) {
			bmp.recycle();
			bmp = null;
		}
		
		if (bitmap == null) {
			return false;
		}
		mTabCursor.setImageBitmap(bitmap);
		
		if (mCursorRunnable != null) {
			removeCallbacks(mCursorRunnable);
		}
		mCursorRunnable = new Runnable() {
			
			@Override
			public void run() {				
				//当默认不�?0时计算有问题，采�?0延迟动画方式初始�?
//				int x = getLeft()+getTabItemLeft(item) - getScrollX();
//				Matrix matrix = new Matrix();
//				matrix.postTranslate(x, 0);
//				mTabCursor.setImageMatrix(matrix);// 设置动画初始位置
				
				//采用0延迟动画初始�?
				startCursorAnimation(item,0,0);
				
				mPreItemId = item;
				mCursorRunnable = null;
			}
		};
		postDelayed(mCursorRunnable,200); //延迟消息等待坐标计算准确

		mTabCursorInit = true;
		return true;
    }
    
    private void startCursorAnimation(int newItem, int oldItem,int duration) {   

    	if (!mTabCursorInit) {
    		mTabCursorInit = initTabCursor();
    		return;
		}
    	if (newItem == oldItem) {
			return;
		}
		int x1 = getLeft()+getTabItemLeft(oldItem)-getScrollX(); 
		int x2 = getLeft()+getTabItemLeft(newItem)-getScrollX(); 
		//int y1 = getTabItemHeight(oldItem) - mTabCursor.getHeight();
		//int y2 = getTabItemHeight(newItem) - mTabCursor.getHeight();

		Animation animation = new TranslateAnimation(x1, x2, 0, 0);	
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);
        animation.setDuration(duration);
        mTabCursor.startAnimation(animation);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	 if (!mTabCursorInit && mIsStrongSupported) {
         	mTabCursorInit = initTabCursor();
 		}
    }
    ///////////////////////////////////////////////////////////////////////////
}
