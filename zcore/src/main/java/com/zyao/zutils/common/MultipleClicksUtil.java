package com.zyao.zutils.common;

import android.content.Context;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: MultipleClicksUtil
 * Description: 多次点击工具类
 * Author: Zyao89
 * Time: 2016/10/23 18:00
 */
public class MultipleClicksUtil
{
	private static MultipleClicksUtil mInstance = null;
	private final List<long[]> mHitsList = new ArrayList<>();
	private final Context mContext;

	private MultipleClicksUtil (Context context)
	{
		this.mContext = context;
		//数组长度表示点击次数
		mHitsList.add(new long[2]);
		mHitsList.add(new long[3]);
		mHitsList.add(new long[4]);
		mHitsList.add(new long[5]);
		mHitsList.add(new long[6]);
		mHitsList.add(new long[7]);
		mHitsList.add(new long[8]);
		mHitsList.add(new long[9]);
	}

	static MultipleClicksUtil with(Context context)
	{
		if (mInstance == null)
		{
			synchronized (MultipleClicksUtil.class)
			{
				if (mInstance == null)
				{
					mInstance = new MultipleClicksUtil(context);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 双击事件
	 */
	public boolean onDoubleClick()
	{
		return onClick(mHitsList.get(0));
	}

	/**
	 * 3击事件
	 */
	public boolean on3Click()
	{
		return onClick(mHitsList.get(1));
	}

	/**
	 * 4击事件
	 */
	public boolean on4Click()
	{
		return onClick(mHitsList.get(2));
	}

	/**
	 * 5击事件
	 */
	public boolean on5Click()
	{
		return onClick(mHitsList.get(3));
	}

	/**
	 * 6击事件
	 */
	public boolean on6Click()
	{
		return onClick(mHitsList.get(4));
	}

	/**
	 * 7击事件
	 */
	public boolean on7Click()
	{
		return onClick(mHitsList.get(5));
	}

	/**
	 * 8击事件
	 */
	public boolean on8Click()
	{
		return onClick(mHitsList.get(6));
	}

	/**
	 * 9击事件
	 */
	public boolean on9Click()
	{
		return onClick(mHitsList.get(7));
	}

	/**
	 * 10击事件
	 */
	public boolean on10Click()
	{
		return onClick(mHitsList.get(8));
	}

	/**
	 * 11击事件
	 */
	public boolean on11Click()
	{
		return onClick(mHitsList.get(9));
	}

	/**
	 * 点击
	 * @param mHits
	 * @return
     */
	public boolean onClick(long[] mHits)
	{
		System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
		mHits[mHits.length - 1] = SystemClock.uptimeMillis();//开机后计算时间
		if (mHits[0] >= (SystemClock.uptimeMillis() - 500))
		{
			return true;
		}
		return false;
	}

}
