/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.ctl.smart.microwave.adapter;

import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.utils.BitmapUtil;
import com.ctl.smart.microwave.utils.DataInit;
import com.ctl.smart.microwave.utils.TextViewUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {

	// =============================================================================
	// Private members
	// =============================================================================


	private String[] content = null;

	private Context context;

	private int height;

	private int width;

	private boolean isSingle = true;
	
	private boolean isShowImage = true;
	
	private int position=-1;
	private String contentP="";

	public FancyCoverFlowSampleAdapter(String[] content,
			Context context, int width, int height) {
		super();
		this.content = content;
		this.context = context;
		this.height = height - 100;
		this.width = width / 4;
	}
	private int  currentSelectItem=0;

	// =============================================================================
	// Supertype overrides
	// =============================================================================

	@Override
	public int getCount() {
		if (content != null&&content.length>4) {
			return Integer.MAX_VALUE;
		}else if(content != null&&content.length<5){
			return content.length;
		}
		return 0;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}
	
	

	public boolean isShowImage() {
		return isShowImage;
	}

	public void setShowImage(boolean isShowImage) {
		this.isShowImage = isShowImage;
	}

	@Override
	public Integer getItem(int i) {
		return 0;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@SuppressLint("NewApi") @Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
		ViewHolder holder;

		i=i%content.length;
		if (reuseableView != null) {
			holder = (ViewHolder) reuseableView.getTag();
		} else {
			reuseableView = LayoutInflater.from(context).inflate(
					R.layout.level_adapter, null);

			holder = new ViewHolder();
			holder.content = (TextView) reuseableView
					.findViewById(R.id.content);
			holder.logo = (ImageView) reuseableView.findViewById(R.id.logo);
			holder.bottom_item=(ImageView) reuseableView.findViewById(R.id.bottom_item);
			TextViewUtils.setCommonSize(holder.content);
//			if (isSingle) {
//				int padd = this.width / 3;
//				holder.content.setPadding(padd, 0, padd, 0);
//			}
			
			holder.bottom_item.setLayoutParams(new LinearLayout.LayoutParams(
					this.width, 100));

			reuseableView.setLayoutParams(new FancyCoverFlow.LayoutParams(
					this.width, FancyCoverFlow.LayoutParams.MATCH_PARENT));
			
			reuseableView.setTag(holder);
		}
		Bitmap bitmap=BitmapUtil.readBitMap(context, DataInit.getDataByKey(content[i]));
		if (bitmap != null&&isShowImage) {
			holder.logo.setVisibility(View.VISIBLE);
			holder.logo.setImageBitmap(bitmap);
			LayoutParams layoutParams=new LinearLayout.LayoutParams(
					this.width, height / 2);
			layoutParams.gravity=Gravity.TOP;
			holder.logo.setLayoutParams(layoutParams);
			holder.content.setLayoutParams(new LinearLayout.LayoutParams(
					this.width, height / 2));
		} else {
			holder.logo.setVisibility(View.GONE);
			holder.content.setLayoutParams(new LinearLayout.LayoutParams(
					this.width, height));
		}
		String contentStr="";
		if (position==i) {
			contentStr=contentP+content[i];
		}else{
			contentStr=content[i];
		}
		
		holder.content.setText(formatContent(isSingle, contentStr));
		return reuseableView;
	}
	private String formatContent(boolean isSingle,String content){
		StringBuilder builder=new StringBuilder();
		if (isSingle) {
			if (content.contains("ml")) {
				char[] chars=content.replace("ml", "").toCharArray();
				String reg="^\\d+$";
				for (char c:chars) {
					if((String.valueOf(c)).matches(reg)){
						builder.append(c);
						continue;
					}
					builder.append(c).append("\n");
				}
				return builder.toString()+"ml";
			}else{
			char[] chars=content.toCharArray();
			for (char c:chars) {
				builder.append(c).append("\n");
			}
			return builder.toString();
			}
		}else if(content.contains("g")){
			return content.substring(0, content.length()-1)+"\n"+"g";
		}else{
			return content;
		}
	}
	private static class ViewHolder {
		ImageView logo;
		TextView content;
		ImageView bottom_item;
	}
	
	public void setPostionContent(int position,String contetnP){
		this.position=position;
		this.contentP=contetnP;
	}



		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
}
