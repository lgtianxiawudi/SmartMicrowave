package com.ctl.smart.microwave.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.favorite.FavoriteActivity;
import com.ctl.smart.microwave.model.SaveModel;
import com.ctl.smart.microwave.utils.TextViewUtils;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SaveModel> list = null;
	private Context mContext;
	private boolean isNeedPY=false;
	private int type=0;
	
	
	public SortAdapter(List<SaveModel> list, Context mContext, boolean isNeedPY) {
		super();
		this.list = list;
		this.mContext = mContext;
		this.isNeedPY = isNeedPY;
	}
	
	public void setType(int type){
		this.type=type;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SaveModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SaveModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			TextViewUtils.setCommonSize(viewHolder.tvLetter);
			TextViewUtils.setCommonSize(viewHolder.tvTitle);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		
		switch (type) {
		case FavoriteActivity.TYPE1:
		{
			viewHolder.tvTitle.setText(this.list.get(position).getuName().replace("\n", ""));
			//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if(position == getPositionForSection(section)&&isNeedPY){
				viewHolder.tvLetter.setVisibility(View.VISIBLE);
				viewHolder.tvLetter.setText(mContent.getuPingYin());
			}else{
				viewHolder.tvLetter.setVisibility(View.GONE);
			}
		}
			break;
		case FavoriteActivity.TYPE2:
		{
			viewHolder.tvTitle.setText(this.list.get(position).getContionName().replace("\n", ""));
			//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if(position == getPositionForSection(section)&&isNeedPY){
				viewHolder.tvLetter.setVisibility(View.VISIBLE);
				viewHolder.tvLetter.setText(mContent.getContentNamePY());
			}else{
				viewHolder.tvLetter.setVisibility(View.GONE);
			}
		}
			break;
		case FavoriteActivity.TYPE3:
		{
			viewHolder.tvTitle.setText(this.list.get(position).getuName().replace("\n", ""));
			//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if(position == getPositionForSection(section)&&isNeedPY){
				viewHolder.tvLetter.setVisibility(View.VISIBLE);
				viewHolder.tvLetter.setText(mContent.getuPingYin());
			}else{
				viewHolder.tvLetter.setVisibility(View.GONE);
			}
		}
			break;
		default:
			break;
		}
		
		
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		
		int rusult=0;
	
			rusult= getPY(position).charAt(0);
		
		
		return rusult;
	}
	
	public String getPY(int position){
		
		String rusult="";
		
		switch (type) {
		case FavoriteActivity.TYPE1:
		{
			rusult= list.get(position).getuPingYin();
		}
			
			break;
			
		case FavoriteActivity.TYPE2:
		{
			rusult= list.get(position).getContentNamePY();
		}
			
			break;
			
		case FavoriteActivity.TYPE3:
		{
			rusult= list.get(position).getuPingYin();
		}
			
			break;

		default:
		{
			rusult= list.get(position).getuPingYin();
		}
			break;
		}
		
		return rusult;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = getPY(i).toLowerCase();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}

}