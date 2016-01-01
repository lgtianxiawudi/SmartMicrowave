package com.ctl.smart.microwave.favorite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.ab.activity.AbActivity;
import com.ab.fragment.AbAlertDialogFragment.AbDialogOnClickListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.ctl.smart.microwave.R;
import com.ctl.smart.microwave.activity.CookingActivity;
import com.ctl.smart.microwave.activity.MainActivity;
import com.ctl.smart.microwave.activity.RemindAddWaterActivity;
import com.ctl.smart.microwave.adapter.SortAdapter;
import com.ctl.smart.microwave.db.SaveDao;
import com.ctl.smart.microwave.model.SaveModel;
import com.ctl.smart.microwave.reheart.FirstLevelReHeartActivity;
import com.ctl.smart.microwave.utils.BottomUtilTwo;
import com.ctl.smart.microwave.utils.ExcelUtil;
import com.ctl.smart.microwave.utils.HeadUtil;
import com.ctl.smart.microwave.utils.Pinyin_Comparator;
import com.ctl.smart.microwave.utils.StartActivityUtil;
import com.ctl.smart.microwave.views.SideBar;
import com.ctl.smart.microwave.views.SideBar.OnTouchingLetterChangedListener;

public class FavoriteActivity extends AbActivity {
	/** Called when the activity is first created. */
	private static List<SaveModel> parentData = new ArrayList<SaveModel>();

	private ListView lvContact;
	private SideBar sideBar;
	SortAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.favorite);
		BottomUtilTwo bottomUtilTwo = new BottomUtilTwo(this).setBackListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						StartActivityUtil.startActivityFinish(
								FavoriteActivity.this, MainActivity.class);
						StartActivityUtil.clearActivity();
					}
				}).setOkListener();
		Bundle bundle = getIntent().getExtras();

		HeadUtil headUtil = new HeadUtil(this)
				.setTitleName(getString(R.string.favorites));
		lvContact = (ListView) this.findViewById(R.id.lvContact);
		adapter = new SortAdapter(this, parentData);
		sideBar = (SideBar) this.findViewById(R.id.sideBar);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					lvContact.setSelection(position);
				}

			}
		});
		getdata();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lvContact.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				SaveModel model=parentData.get(arg2);
				Bundle bundle=new Bundle();
				bundle.putString("title", ""+model.getuName());
				bundle.putString("temperature", ""+model.getTemperature());
				bundle.putString("imageid", model.getImageid());
				bundle.putString("secondtime", model.getSecondtime());
				bundle.putString("time", model.getTime());
				Map<String, String> map=ExcelUtil.getDetailByKey(model.getImageid());
				
				if (map!=null&&"蒸汽".equals(map.get("3"))) {
					StartActivityUtil.startActivityForResult(FavoriteActivity.this, RemindAddWaterActivity.class,
							bundle);
					return;
				}
				if (model.getuName().indexOf("蒸汽")!=-1) {
					StartActivityUtil.startActivityForResult(FavoriteActivity.this, RemindAddWaterActivity.class,
							bundle);
					return;
				}
				
				StartActivityUtil.startActivityForResult(FavoriteActivity.this, CookingActivity.class,
						bundle);
			}
		});
		lvContact.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub

				final SaveModel model=parentData.get(arg2);
				
				AbDialogUtil.showAlertDialog(FavoriteActivity.this, getString(R.string.remind), getString(R.string.remind_message)+model.getuName(),new AbDialogOnClickListener() {
					
					@Override
					public void onPositiveClick() {
						// TODO Auto-generated method stub
						SaveDao dao = new SaveDao(FavoriteActivity.this);
						dao.startWritableDatabase(false);
						int num = dao.delete(" u_name = ?", new String[] { model.getuName() });
						if (num > 0) {
							parentData.remove(arg2);
							adapter.notifyDataSetChanged();
							AbToastUtil.showToast(FavoriteActivity.this,
									getString(R.string.delete_fav_toase)
											+ model.getuName());
						}

						dao.closeDatabase();
					}
					
					@Override
					public void onNegativeClick() {
						// TODO Auto-generated method stub
						
					}
				});
			
				return false;
			}
		});
	}
	public void getdata() {
		SaveDao dao = new SaveDao(this);
		dao.startReadableDatabase();
		parentData = dao.queryList();
		dao.closeDatabase();
		Collections.sort(parentData, new Pinyin_Comparator());
		adapter = new SortAdapter(this, parentData);
		lvContact.setAdapter(adapter);
		
	}
}
