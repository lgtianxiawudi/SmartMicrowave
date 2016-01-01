package com.ctl.smart.microwave.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctl.smart.microwave.R;

import jxl.Sheet;
import jxl.Workbook;
import android.content.Context;
import android.text.TextUtils;

public class ExcelUtil {

	private static Map<String, List<String>> kinds = new HashMap<String, List<String>>();

	private static Map<String, Map<String, String>> detail = new HashMap<String, Map<String, String>>();

	public static void readExcelFile(Context context) {
		try {

			Workbook book = Workbook.getWorkbook(context.getAssets().open(
					context.getString(R.string.data_name)));
			System.out.println(">>>>>>number of sheet "
					+ book.getNumberOfSheets());
			// 获得第一个工作表对象

			Sheet sheet = book.getSheet(0);
			int Rows = sheet.getRows();
			int Cols = sheet.getColumns();
			System.out.println("当前工作表的名字:" + sheet.getName());
			System.out.println("总行数:" + Rows);
			System.out.println("总列数:" + Cols);
			for (int i = 0; i < Rows; ++i) {
				String parent = (sheet.getCell(0, i)).getContents();
				String child = (sheet.getCell(1, i)).getContents();
				if (kinds.containsKey(parent)) {
					List<String> list = kinds.get(parent);
					list.add(child);
					kinds.put(parent, list);
				} else {
					List<String> list = new ArrayList<String>();
					list.add(child);
					kinds.put(parent, list);
				}
			}

			Sheet sheet1 = book.getSheet(1);
			int Rows1 = sheet1.getRows();
			int Cols1 = sheet1.getColumns();
			System.out.println("当前工作表的名字:" + sheet1.getName());
			System.out.println("总行数:" + Rows1);
			System.out.println("总列数:" + Cols1);
			for (int i = 0; i < Rows1; ++i) {
				Map<String, String> map = new HashMap<String, String>();
				String name = sheet1.getCell(0, i).getContents();
				for (int j = 0; j < Cols1; ++j) {
					// getCell(Col,Row)获得单元格的值
					String content = sheet1.getCell(j, i).getContents();
					map.put(j + "", content);
				}
				detail.put(name, map);
				System.out.print("\n");
			}
			book.close();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}

	public static String[] getKindsByKey(String key) {

		List<String> list = kinds.get(key);

		if (list != null && list.size() > 0) {

			String[] result = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				result[i] = list.get(i);
			}

			return result;
		}

		return null;
	}

	public static Map<String, String> getDetailByKey(String key) {

		return detail.get(key);
	}

	public static String[] getDetailKinds(String key) {

		Map<String, String> map = detail.get(key);

		List<String> list = new ArrayList<String>();

		if (map != null) {
			String str_5 = map.get("4");

			if (!TextUtils.isEmpty(str_5)) {
				list.add(str_5);
			}

			String str_7 = map.get("6");

			if (!TextUtils.isEmpty(str_7)) {
				list.add(str_7);
			}
			String str_9 = map.get("8");

			if (!TextUtils.isEmpty(str_9)) {
				list.add(str_9);
			}
			String str_11 = map.get("10");

			if (!TextUtils.isEmpty(str_11)) {
				list.add(str_11);
			}
		}
		if (list != null && list.size() > 0) {

			String[] result = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				result[i] = list.get(i);
			}

			return result;
		}
		return null;
	}

}
