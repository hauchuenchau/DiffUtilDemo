package com.cnlive.diffutildemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * @Author Mr.hou
 * @time 2017/3/31
 * @Desc 用来判断 新旧item是否相同
 */

public class DiffCallBack extends DiffUtil.Callback {
    private List<TestBean>mOlDatas,mNewDatas;

    public DiffCallBack(List<TestBean> mOlDatas, List<TestBean> mNewDatas) {
        this.mOlDatas = mOlDatas;
        this.mNewDatas = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return mOlDatas!=null?mOlDatas.size():0;
    }

    @Override
    public int getNewListSize() {
        return mNewDatas!=null?mNewDatas.size():0;
    }
    /**
     * Called by the DiffUtil to decide whether two object represent the same Item.
     * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
     * For example, if your items have unique ids, this method should check their id equality.
     * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
     * 本例判断name字段是否一致
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOlDatas.get(oldItemPosition).getName().equals(mNewDatas.get(newItemPosition).getName());
    }
    /**
     * Called by the DiffUtil when it wants to check whether two items have the same data.
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * DiffUtil uses this information to detect if the contents of an item has changed.
     * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
     * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
     * DiffUtil 用这个方法替代equals方法去检查是否相等。
     * so that you can change its behavior depending on your UI.
     * 所以你可以根据你的UI去改变它的返回值
     * For example, if you are using DiffUtil with a
     * {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter}, you should
     * return whether the items' visual representations are the same.
     * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the
     *                        oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TestBean beanOld=mOlDatas.get(oldItemPosition);
        TestBean beanNew=mNewDatas.get(newItemPosition);
        if (!beanOld.getDesc().equals(beanNew.getDesc())){
            return false;//如果内容不同就返回false
        }
        if (beanOld.getPic()!=beanNew.getPic()){
            return false;//同上
        }
        return true;//默认两个data内容是相同的
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        TestBean beanOld=mOlDatas.get(oldItemPosition);
        TestBean beanNew=mNewDatas.get(newItemPosition);
        Bundle payload=new Bundle();
        if (!beanOld.getDesc().equals(beanNew.getDesc())){
            payload.putString("KEY_DESC",beanNew.getDesc());
        }
        if (beanOld.getPic()!=beanNew.getPic()){
            payload.putInt("KEY_PIC",beanNew.getPic());
        }
        if (payload.size()==0)return null;//如果没有变化就传空
        return payload;
    }
}
