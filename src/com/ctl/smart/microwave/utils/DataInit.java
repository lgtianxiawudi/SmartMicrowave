package com.ctl.smart.microwave.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;

public class DataInit {
	private static Map<String, String> map=new HashMap<String, String>();
	public static void init(Context context)throws Exception{
		InputStream is=context.getAssets().open("imagedatas.xml");
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document document=builder.parse(is);
		NodeList list=document.getElementsByTagName("Image");
		for (int i = 0; i < list.getLength(); i++) {
			Element element=(Element) list.item(i);
			String name = null;
			try {
				name = element.getElementsByTagName("FoodName").item(0).getFirstChild().getNodeValue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			}
			String nameEn = null;
			try {
				nameEn = element.getElementsByTagName("FoodNameEn").item(0).getFirstChild().getNodeValue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			}
			String type=element.getElementsByTagName("ImageName").item(0).getFirstChild().getNodeValue();
			System.out.println(nameEn+":"+name+":"+type);
			map.put(name, type);
			map.put(nameEn, type);
		}
	}
	public static String getDataByKey(String key){
		return map.get(key);
	}
}
