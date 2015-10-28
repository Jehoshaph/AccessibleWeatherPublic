package com.sonification.accessibleweather.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseDailyWeather extends SQLiteOpenHelper
{
	/*
	 * Written by Jehoshaph Akshay Chandran (2014) at the Sonification Lab, Georgia Institute of Technology
	 * Utility class that defines methods to store and access the next 10 days weather,
	 * while this application will update the database every time the application is opened,
	 * values will only be updated when they differ from the existing values.
	 * Contains the following columns:
	 * 0 - _id
	 * 1 - day (DD)
	 * 2 - month (MM)
	 * 3 - year (YYYY)
	 * 4 - text
	 * 5 - pop
	 * 6 - snow
	 * 7 - highTemp
	 * 8 - lowTemp
	 * 9 - conditions
	 */
	
	private static final String DATABASE_NAME = "dailyweather.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_NAME = "dailyweather";
	
	// Column name and identifier                                   Column number
	public static final String _ID = "_id";							//0
	public static final String DAY = "day";							//1
	public static final String MONTH = "month";						//2
	public static final String YEAR = "year";						//3
	public static final String TEXT = "text_desc";					//4
	public static final String POP = "pop";							//5
	public static final String SNOW = "snow";						//6
	public static final String HIGHTEMP = "hightemp";				//7
	public static final String LOWTEMP = "lowtemp";					//8
	public static final String CONDITIONS = "conditions";			//9
	
	public  DatabaseDailyWeather(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String sql = "CREATE TABLE " + TABLE_NAME + " ("
					+ _ID + " INTEGER PRIMARY KEY, "
					+ DAY + " TEXT, "
					+ MONTH + " TEXT, "
					+ YEAR + " TEXT, "
					+ TEXT + " TEXT, "
					+ POP + " TEXT, "
					+ SNOW + " TEXT, "
					+ HIGHTEMP + " TEXT, "
					+ LOWTEMP + " TEXT, "
					+ CONDITIONS + " TEXT);";
		
		db.execSQL(sql);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public void addRecord(String day, String month, String year, String text, String pop, String snow, String highTemp, String lowTemp, String conditions)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(DAY, day);
		values.put(MONTH, month);
		values.put(YEAR, year);
		values.put(TEXT, text);
		values.put(POP, pop);
		values.put(SNOW, snow);
		values.put(HIGHTEMP, highTemp);
		values.put(LOWTEMP, lowTemp);
		values.put(CONDITIONS, conditions);
		
		db.insert(TABLE_NAME, null, values);
	}
	
	public Cursor allRows()
	{
		/* 
		 * Returns a cursor to all rows and all columns in the database in the order in which they were initialized
		 */
		
		String[] from = { _ID, DAY, MONTH, YEAR, TEXT, POP, SNOW, HIGHTEMP, LOWTEMP, CONDITIONS};
		
		String order = _ID;
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, from, null, null, null, null, order);
		//activity.startManagingCursor(cursor);
		return cursor;
	}
	
	public Cursor searchRows(String key, String value)
	{
		/*
		 * Returns a Cursor to all columns in the db where rows are matched by the incoming key-value pair
		 */
		String[] from = { _ID, DAY, MONTH, YEAR, TEXT, POP, SNOW, HIGHTEMP, LOWTEMP, CONDITIONS};
		String order = _ID;
		String where = key + " = ?";
		String[] args = new String[] {value};
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, from, where, args, null, null, order);
		return cursor;
	}
	
	public long count()
	{
		long count;
		SQLiteDatabase db = getReadableDatabase();
		count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
		return count;
	}
	
	public void deleteEntry(int id)
	{
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_NAME, _ID+"="+id, null);
	}
	
	public void deleteAllEntries()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
	}
	
	public void close()
	{
		SQLiteDatabase db = getWritableDatabase();
		db.close();
	}
}