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

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	

	public FancyCoverFlowSampleAdapter(String[] content,
			Context context, int width, int height) {
		super();
		this.content = content;
		this.context = context;
		this.height = height;
		this.width = width / 5;
	}

	// =============================================================================
	// Supertype overrides
	// =============================================================================

	@Override
	public int getCount() {
		if (content != null) {
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

	@Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
		ViewHolder holder;

		if (reuseableView != null) {
			holder = (ViewHolder) reuseableView.getTag();
		} else {
			reuseableView = LayoutInflater.from(context).inflate(
					R.layout.level_adapter, null);

			holder = new ViewHolder();
			holder.content = (TextView) reuseableView
					.findViewById(R.id.content);
			holder.logo = (ImageView) reuseableView.findViewById(R.id.logo);
			TextViewUtils.setCommonSize(holder.content);
			if (isSingle) {
				int padd = this.width / 3;
				holder.content.setPadding(padd, 0, padd, 0);
			}

			reuseableView.setLayoutParams(new FancyCoverFlow.LayoutParams(
					this.width, FancyCoverFlow.LayoutParams.MATCH_PARENT));
			reuseableView.setTag(holder);
		}
		Bitmap bitmap=BitmapUtil.readBitMap(context, DataInit.getDataByKey(content[i]));
		if (bitmap != null&&isShowImage) {
			holder.logo.setVisibility(View.VISIBLE);
			holder.logo.setImageBitmap(bitmap);
			holder.logo.setLayoutParams(new LinearLayout.LayoutParams(
					this.width, height / 5));
			holder.content.setLayoutParams(new LinearLayout.LayoutParams(
					this.width, height - 100));
		} else {
			holder.logo.setVisibility(View.GONE);
			holder.content.setLayoutParams(new LinearLayout.LayoutParams(
					this.width, height));
		}
		holder.content.setText(content[i]);
		return reuseableView;
	}

	private static class ViewHolder {
		ImageView logo;
		TextView content;
	}
}
