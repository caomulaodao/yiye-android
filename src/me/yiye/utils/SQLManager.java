package me.yiye.utils;

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
		
		MLog.d(TAG, "init### clear the old db");
		context.deleteDatabase("yiye.db");
		SQLiteDatabase db = context.openOrCreateDatabase("yiye.db", Context.MODE_PRIVATE, null);
		db.execSQL("DROP TABLE IF EXISTS user");
		db.execSQL("DROP TABLE IF EXISTS bookmark");
		db.execSQL("DROP TABLE IF EXISTS channel");
		
		MLog.d(TAG, "init### create new table");
		db.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR UNIQUE, password VARCHAR)");
		
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
			return;
		}
		user.id = id;
	}
}
