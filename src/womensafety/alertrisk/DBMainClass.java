package womensafety.alertrisk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMainClass {

	public static final String DATABASE_NAME="women_db";
	public static final int DATABASE_VERSION=2;
	public static String TABLE_NAME="intro_table";
	public static String COLUMN_NAME="intro_column";
	public static final String create_s="create table " + TABLE_NAME + " ( " +COLUMN_NAME + " INTEGER ); ";
	/////////////// table 2
	
	public static String TABLE_NAME_LT="login_table";
	public static String NAME = "lt_name";
	public static String PASSWORD="lt_password";

	public static String create_lt= " create table "+ TABLE_NAME_LT+" ("+NAME+" TEXT ,"+PASSWORD+" TEXT);";           
	
	
	
	SQLiteDatabase database;		
	DBHelper1 dbh;
	Context oc;
	int result;
	public DBMainClass(Context c){
		oc=c;
	}
	public DBMainClass open() {
	 dbh=new DBHelper1(oc);
	 database=dbh.getWritableDatabase();
	return this;
		
	}

	public void write(int intro) {
		ContentValues cv=new ContentValues();
		cv.put(COLUMN_NAME, intro);
		database.insert(TABLE_NAME, null,cv);
		
	}
	
	public void write_ld(String name2,String pass) {
		ContentValues cv=new ContentValues();
		cv.put(NAME, name2);
		cv.put(PASSWORD, pass);
		database.insert(TABLE_NAME_LT, null,cv);
		
	}
	

	public void close() {
		database.close();
		
	}
	public int getData()
	{
		String[] column=new String[]{COLUMN_NAME};
		Cursor c=database.query(TABLE_NAME, column, null, null, null, null, null);
		int icolumn;
		icolumn=c.getColumnIndex(COLUMN_NAME);
		while(c.moveToLast())
		{
			result=c.getInt(icolumn);
			break;
		}
		return result;
	}
	public int getLoginData() {
		int count=0;
	
		String[] column=new String[]{NAME,PASSWORD};
		Cursor c = database.query(TABLE_NAME_LT, column, null,null,null,null,null);
			while(c.moveToNext())
		{
			count=1;
			break;
		}
		return count;
	}
	///////////////
	
	public String getpass() {
		int ipass;
		String passlt = "0";
		String[] column=new String[]{NAME,PASSWORD};
		Cursor c = database.query(TABLE_NAME_LT, column, null,null,null,null,null);
		ipass=c.getColumnIndex(PASSWORD);
		while(c.moveToPosition(0))
		{
			passlt=c.getString(ipass);
			break;
		}
		
//		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
//		{
//			passlt= c.getString(ipass);
//		}
		 
		return passlt;
	}
	
	private class DBHelper1 extends SQLiteOpenHelper{

		public DBHelper1(Context context) {
			super(context,DATABASE_NAME, null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase arg0) {
			// TODO Auto-generated method stub
			arg0.execSQL(create_s);
			arg0.execSQL(create_lt);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		
	}

	

	

}
