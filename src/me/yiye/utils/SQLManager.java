package me.yiye.utils;

import me.yiye.contents.BookMark;
import me.yiye.contents.Channel;
import me.yiye.contents.User;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class SQLManager {
	
	private final static String TAG = "SQLManager";
	public static void init(Context context) {
		
		SharedPreferences dbSharedPreferences= context.getSharedPreferences("db", Activity.MODE_PRIVATE);
		String isInit = dbSharedPreferences.getString("init", "no");
		
		if(isInit.equals("yes")) {  // 已经初始化过数据库
			return;
		}
		
		// 清理工作
		MLog.d(TAG, "init### clear the old db");
		context.deleteDatabase("yiye.db");
		SQLiteDatabase db = context.openOrCreateDatabase("yiye.db", Context.MODE_PRIVATE, null);
		db.execSQL("DROP TABLE IF EXISTS user");
		db.execSQL("DROP TABLE IF EXISTS bookmark");
		db.execSQL("DROP TABLE IF EXISTS channel");
		
		// 建表
		MLog.d(TAG, "init### create new table");
		db.execSQL("CREATE TABLE user " +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"username VARCHAR UNIQUE," + 
				"password VARCHAR," +
				"avatar VARCHAR" + 
				")");
		db.execSQL("CREATE TABLE channel " +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"channelId VARCHAR UNIQUE," + 
				"name VARCHAR," +
				"logo VARCHAR," +
				"type VARCHAR," +
				"news INTEGER," +
				"lastTime VARCHAR" +
				")");		
		db.execSQL("CREATE TABLE bookmark " +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT," + 
				"title VARCHAR UNIQUE," + 
				"description VARCHAR," +
				"url VARCHAR," +
				"image VARCHAR," +
				"postUser VARCHAR," + 
				"likeNum INTEGER," +
				"postTime VARCHAR," +
				"channelId VARCHAR," +
				"comments VARCHAR" + 
				")");		
		SharedPreferences.Editor editor = dbSharedPreferences.edit(); 
		editor.putString("init", "yes"); // 标记已经初始化
		editor.commit();
		db.close();
		
		MLog.d(TAG, "init### init ok");
	}
	
	public static void saveuser(Context context,User user) {
		SQLiteDatabase db = context.openOrCreateDatabase("yiye.db", Context.MODE_PRIVATE, null);

		ContentValues cv = new ContentValues();
		cv.put("username", user.username);
		cv.put("password", user.password);
		long id = db.insert("user", null, cv);
		if(id == -1) {
			MLog.e(TAG, "saveuser### insert error");
		} else {
			user.id = id;
		}
		db.close();
	}
	
	public static void saveChannel(Context context,Channel c) {
		MLog.d(TAG, "saveChannel###channel:" + c.toString());
		SQLiteDatabase db = context.openOrCreateDatabase("yiye.db", Context.MODE_PRIVATE, null);
		ContentValues cv = new ContentValues();
		cv.put("channelId",c.channelId);
		cv.put("lastTime", c.lastTime);
		cv.put("logo", c.logo);
		cv.put("name", c.name);
		cv.put("news", c.news);
		cv.put("type", c.type);
		long id = db.insert("channel", null, cv);
		if(id == -1) {
			MLog.e(TAG, "saveChannel### insert error");
		}
		db.close();
	}

	public static void saveBookMark(Context context, BookMark b) {
		MLog.d(TAG, "saveBookMark###bookmark:" + b.toString());
		SQLiteDatabase db = context.openOrCreateDatabase("yiye.db", Context.MODE_PRIVATE, null);
		ContentValues cv = new ContentValues();
		cv.put("channelId",b.channelId);
		cv.put("comments", b.comments);
		cv.put("description", b.description);
		cv.put("image", b.image);
		cv.put("postTime", b.postTime);
		cv.put("postUser", b.postUser);
		cv.put("title",b.title);
		cv.put("url", b.url);
		cv.put("likeNum", b.likeNum);
		long id = db.insert("bookmark", null, cv);
		if(id == -1) {
			MLog.e(TAG, "saveBookMark### insert error");
		}
		db.close();
	}
}
