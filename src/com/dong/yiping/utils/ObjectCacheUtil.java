package com.dong.yiping.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.dong.yiping.bean.StarStudentBean.Student;

public class ObjectCacheUtil {

	private static String cache_path = "/sdcard/yiping";
	
	/**
	 * 将对象存到文件
	 * @param object
	 * @param name
	 */
	public static void writeBean2File(Object object,String name){
		
		File file = new File(cache_path);
		if(!file.exists()){
			file.mkdirs();
		}
		File cacheFile = new File(cache_path,name);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(cacheFile));
			oos.writeObject(object);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(oos != null){
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 根据名字获取对象
	 * @param name
	 * @return
	 */
	public static Object readFile2Bean(String name){
		File file = new File(cache_path,name);
		Object object=null;
		if(file.exists()){
			ObjectInputStream oos = null;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				oos = new ObjectInputStream(fis);
				object = (List<Student>) oos.readObject();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(oos!=null){
						oos.close();
					}
					if(fis != null){
						fis.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return object;
	}
}
