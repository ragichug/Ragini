package womensafety.alertrisk;

import crr.india.womensafety12.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Main_Login_j extends Activity implements OnCheckedChangeListener, OnClickListener {
	int intro=0;
	int intro_data;
	public static int login_data;
	CheckBox chk_intro;
	Button btn_skip;
	TextView tv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.introduction_x);
		chk_intro=(CheckBox)findViewById(R.id.check_intro);
		tv1=(TextView)findViewById(R.id.textView1);
		chk_intro.setOnCheckedChangeListener(this);
//		chk_intro.setOnClickListener(this);
		
		btn_skip=(Button)findViewById(R.id.button_skip);
		btn_skip.setOnClickListener(this);
		
        DBMainClass dbm=new DBMainClass(this);
        dbm.open();
        intro_data= dbm.getData();
        login_data=dbm.getLoginData();
        dbm.close();
        
      	if(intro_data==1)
      	{
      		if(login_data==0)
      		{
      			startActivity(new Intent(getApplicationContext(),Create_account_j.class));
      		}
      		else
      		{
      			startActivity(new Intent(getApplicationContext(),Login_j.class));
      		}
      		finish();
      	}
      
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		switch(arg0.getId())
		{
		case R.id.check_intro:
			if(chk_intro.isChecked()==true)
		      {
		      	intro=1;
		      	DBMainClass dbm=new DBMainClass(this);
		      	dbm.open();
		      	dbm.write(intro);
		      	dbm.close();
		      	
		      }
		      if(chk_intro.isChecked()==false)
		      {
		    	intro=0;
		    	DBMainClass dbm=new DBMainClass(this);
		      	dbm.open();
		      	dbm.write(intro);
		      	dbm.close();
		      }
			break;
		}
		
	}

	@Override
	public void onClick(View arg0) {
		
		switch(arg0.getId())
		{
		case R.id.button_skip:
			 DBMainClass dbm=new DBMainClass(this);
		        dbm.open();
		        login_data=dbm.getLoginData();
		        dbm.close();
			
		     if (login_data==0)
		     {
		    	try {
					Intent i1 =new Intent(getApplicationContext(),Create_account_j.class);
					startActivity(i1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	finally
		    	{
		    		finish();
		    	}
		     }
		     else
		     {
		    	 try {
					Intent i2 =new Intent(getApplicationContext(),Login_j.class);
					 startActivity(i2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		    	 finally{
		    		 finish();
		    	 }
		     }
			
			break;
		
		}	
		
	}

}
