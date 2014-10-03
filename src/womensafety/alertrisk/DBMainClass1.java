package womensafety.alertrisk;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMainClass1 {

	public String DATABASE_NAME;
	public int DATABASE_VERSION=2;
	public String DATABASE_TABLENAME;
	static String NAME;
	static String PHONE;
	static String EMAIL;
	static String MSG;
	static String USER_ID;
	static String PWD;
	public String CREATE;
	static String ACTIVATE;
	Context ourContext;
	SQLiteDatabase database;
	DBHelper helperObject;
	String result="";
	String PWCOUNT;
	public DBMainClass1(String databaseName, String database_tablename,String name,String phone,String email,Context c)
	{
		
		DATABASE_NAME=databaseName;
		DATABASE_TABLENAME=database_tablename;
		NAME=name;
		PHONE=phone;
		EMAIL=email;
		ourContext=c;
		CREATE="CREATE TABLE IF NOT EXISTS "+DATABASE_TABLENAME+"("+
				NAME+" TEXT, "+
				PHONE+" TEXT, "+
				EMAIL+" TEXT);";
	}
	
	
	
	
	public DBMainClass1(String databaseName,String database_tablename,String msg,Context c,int i)
	{
		DATABASE_NAME=databaseName;
		DATABASE_TABLENAME=database_tablename;
		ourContext=c;
		if(i==1)
		{
		MSG=msg;

		CREATE="CREATE TABLE IF NOT EXISTS "+DATABASE_TABLENAME+"("+
				MSG+" TEXT);";
		}
		if(i==2)
		{
			PWCOUNT=msg;
			CREATE="CREATE TABLE IF NOT EXISTS "+DATABASE_TABLENAME+"("+PWCOUNT+" INTEGER);";


		}
	}
	public DBMainClass1(String databaseName,String database_tablename,String value,boolean b,Context c)
	{
		DATABASE_NAME=databaseName;
		DATABASE_TABLENAME=database_tablename;
		ACTIVATE=value;
		ourContext=c;
		CREATE="CREATE TABLE IF NOT EXISTS "+DATABASE_TABLENAME+"("+
				ACTIVATE+" TEXT);";
	}
	
	
	public DBMainClass1(String databaseName,String database_tablename,String user_id,String pwd,Context c)
	{
		DATABASE_NAME=databaseName;
		DATABASE_TABLENAME=database_tablename;
		USER_ID=user_id;
		PWD=pwd;
		ourContext=c;
		CREATE="CREATE TABLE IF NOT EXISTS "+DATABASE_TABLENAME+"("+
				USER_ID+" TEXT, "+
				PWD+" TEXT);";
		
	}
	public DBMainClass1 open() {
		// TODO Auto-generated method stub
		helperObject=new DBHelper(ourContext);
		database=helperObject.getWritableDatabase();
		database.execSQL(CREATE);
		return this;
	}
	
	public void write(String user_id,String pwd)
	{
		ContentValues cv=new ContentValues();
		cv.put(USER_ID,user_id);
		cv.put(PWD,pwd);
		database.execSQL("Delete from "+DATABASE_TABLENAME);
		database.insert(DATABASE_TABLENAME, null,cv);
	}
	public void write(String name, String phone, String email) {
		// TODO Auto-generated method stub
		
		ContentValues cv=new ContentValues();
		cv.put(NAME,name);
		cv.put(PHONE,phone);
		cv.put(EMAIL,email);
	
		database.insert(DATABASE_TABLENAME, null,cv);
	}
	public void write(String msg) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		cv.put(MSG,msg);
		//database.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLENAME);
		
		database.execSQL("Delete from "+DATABASE_TABLENAME);
		database.insert(DATABASE_TABLENAME, null,cv);
		
	}
	public void write(int pwcount)
	{
		ContentValues cv=new ContentValues();
		cv.put(PWCOUNT,pwcount);
		//database.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLENAME);
		
		database.execSQL("Delete from "+DATABASE_TABLENAME);
		database.insert(DATABASE_TABLENAME, null,cv);
		
	}
	public void write1(String value) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		cv.put(ACTIVATE,value);
		
		
		database.execSQL("Delete from "+DATABASE_TABLENAME);
		database.insert(DATABASE_TABLENAME, null,cv);
		
	}
	public String get()
	{
		String s = null;
		String[] columns=new String[]{ACTIVATE};
		Cursor c=database.query(DATABASE_TABLENAME, columns, null, null,null,null,null);
		int value=c.getColumnIndex(ACTIVATE);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			s=c.getString(value);
		
		}
		//Log.e("value",s);
		return s;
	}
	public void close() {
		// TODO Auto-generated method stub
		database.close();
	}
	private class DBHelper extends SQLiteOpenHelper
	{

		public DBHelper(Context context) {
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLENAME);
		}
		
	}
	
	public ArrayList<String> getEmail()
	{
		String columns[]=new String[]{NAME,PHONE,EMAIL};
		Cursor c=database.query(DATABASE_TABLENAME, columns, null, null,null,null,null);
		int email=c.getColumnIndex(EMAIL);
		ArrayList<String>al=new ArrayList<String>();
		String s;
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			s=c.getString(email);
			if(s!=null)
			al.add(s);
		}
		return al;
		
	}
	public ArrayList<String> getData() {
		// TODO Auto-generated method stub
		String []columns;
		Cursor c;
		int name;
		ArrayList<String> al=new ArrayList<String>();

		if(DATABASE_TABLENAME=="Contacts")
		{
		String s;
		columns=new String[]{NAME,PHONE,EMAIL};
		c=database.query(DATABASE_TABLENAME, columns, null, null,null,null,null);
		name=c.getColumnIndex(NAME);
		int phone=c.getColumnIndex(PHONE);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			s=c.getString(name);
			s=s+" :"+c.getString(phone);
			al.add(s);
		}
		}
		else if(DATABASE_TABLENAME=="Message")
		{
			columns=new String[]{MSG};
			c=database.query(DATABASE_TABLENAME, columns, null, null,null,null,null);
			name=c.getColumnIndex(MSG);
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				al.add(c.getString(name));
			
			}
		}
		else if(DATABASE_TABLENAME=="Pwcount_table")
		{
			columns=new String[]{PWCOUNT};
			c=database.query(DATABASE_TABLENAME, columns, null, null,null,null,null);
			name=c.getColumnIndex(PWCOUNT);
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				al.add(String.valueOf(c.getInt(name)));
			
			}
		}
		else
		{
			columns=new String[]{USER_ID,PWD};
			c=database.query(DATABASE_TABLENAME, columns, null, null,null,null,null);
			name=c.getColumnIndex(USER_ID);
			int pwd=c.getColumnIndex(PWD);
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				al.add(c.getString(name));
				al.add(c.getString(pwd));
			}
		}
		
		
		return al;
	}
	public String[] getField(String phone) {
		// TODO Auto-generated method stub
		String []columns=new String[]{NAME,PHONE,EMAIL};
		Cursor c=database.query(DATABASE_TABLENAME, columns,PHONE+" = '"+phone+"'",null,null,null,null);
	
		int iName=c.getColumnIndex(NAME);
		int iPhone=c.getColumnIndex(PHONE);
		int iEmail=c.getColumnIndex(EMAIL);
		String s[]=new String[3];
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			s[0]= c.getString(iName);
			s[1]=c.getString(iPhone);
			s[2]=c.getString(iEmail);
			
		}
	
		return s;
	}
	public void delete(String name, String phone, String email) {
		// TODO Auto-generated method stub	`
		database.execSQL("Delete from "+DATABASE_TABLENAME+" where "+NAME+"='"+name+"' and "+PHONE+"='"+phone+"' and "+EMAIL+"='"+email+"'");
	}
	
	public void delete2() {
		// TODO Auto-generated method stub
		database.execSQL("Delete from "+DATABASE_TABLENAME);
	}
	
public void update(String name,String phone,String email)
{
	ContentValues cv=new ContentValues();
	cv.put(NAME,name);
	cv.put(PHONE,phone);
	cv.put(EMAIL,email);
	database.execSQL("Delete from "+DATABASE_TABLENAME+" where "+PHONE+"='"+phone+"'");
	database.insert(DATABASE_TABLENAME, null,cv);
	
}
}
