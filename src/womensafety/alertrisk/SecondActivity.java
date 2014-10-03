package womensafety.alertrisk;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import crr.india.womensafety12.R;

public class SecondActivity extends Activity implements OnClickListener{

	Button btn_editcont,btn_editmsg,btn_activate,btn_back,btn_pinfo;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondlayout);
		btn_editcont=(Button)findViewById(R.id.editContact);
		btn_editmsg=(Button)findViewById(R.id.editMessage);
		btn_activate=(Button)findViewById(R.id.activate);
		btn_back=(Button)findViewById(R.id.back);
		btn_pinfo=(Button)findViewById(R.id.personinfo);
		
		btn_editcont.setOnClickListener(this);
		btn_editmsg.setOnClickListener(this);
		btn_activate.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_pinfo.setOnClickListener(this);
		
		DBMainClass1 db=new DBMainClass1("14","Button","Activate",true,this);
		db.open();
		String s=db.get();
		if(s==null)
		{
			
			btn_activate.setBackgroundResource(R.drawable.activatb);	
			
		}
		else if(s.equals("true"))
			btn_activate.setBackgroundResource(R.drawable.deactivate);
		else
			btn_activate.setBackgroundResource(R.drawable.activatb);

		db.close();
	}
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.editContact:
			Intent i=new Intent(SecondActivity.this,FourthActivity.class);
			startActivity(i);	
			break;
		case R.id.editMessage:
			Intent i1=new Intent(SecondActivity.this,EditMessageClass.class);
			startActivity(i1);
			break;
		case R.id.activate:
			DBMainClass1 db=new DBMainClass1("14","Button","Activate",true,this);
			db.open();
			String s=db.get();
				if(s==null)
				{
					db.write1("true");
					btn_activate.setBackgroundResource(R.drawable.deactivate);
					Toast.makeText(getApplicationContext(), "Service Activated", Toast.LENGTH_SHORT).show();
					Intent i2=new Intent(SecondActivity.this,ActivateService.class);
					startService(i2);
				}
			
				else if(s.equals("true"))
					{
					db.write1("false");
					btn_activate.setBackgroundResource(R.drawable.activatb);
					Toast.makeText(getApplicationContext(), "Service Deactivated", Toast.LENGTH_SHORT).show();
	
					
						//stopService(new Intent(this,AlarmReceiver.class));
						stopService(new Intent(this,ActivateService.class));

						DBMainClass1 db1=new DBMainClass1("15","Pwcount_table","Pwcount",ActivateService.s,2);
						db1.open();
						db1.write(0);
						db1.close();
						
					}
				else 
				{
					db.write1("true");
					btn_activate.setBackgroundResource(R.drawable.deactivate);
					Toast.makeText(getApplicationContext(), "Service Activated", Toast.LENGTH_SHORT).show();
					Intent i2=new Intent(SecondActivity.this,ActivateService.class);
					startService(i2);
				}
				db.close();
			
				break;
		case R.id.back:
			finish();
			break;
		case R.id.personinfo:
			Intent i3=new Intent(SecondActivity.this,PersonalInfo.class);
			startActivity(i3);
			break;
		}
	}
}
