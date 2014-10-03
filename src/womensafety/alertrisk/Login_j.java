package womensafety.alertrisk;

import crr.india.womensafety12.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Login_j extends Activity implements OnClickListener{

	public EditText et_pass;
	Button btn_login;
	TextView tv_forgotpass;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_x);
		et_pass=(EditText) findViewById(R.id.editText_enterpass);
		btn_login=(Button) findViewById(R.id.button_login);
		tv_forgotpass=(TextView) findViewById(R.id.textView_fp);
		btn_login.setOnClickListener(this);
		
    }
	
	@Override
	public void onBackPressed() {
	   finish();
	}
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.button_login:
			//startActivity( new Intent(getApplicationContext(),MainActivity.class));
			String lt_pass;
			  DBMainClass dbm=new DBMainClass(this);
		      dbm.open();
		      lt_pass=dbm.getpass();
		      dbm.close();
		      if(et_pass.getText()!=null)
		      {
		    	  if(et_pass.getText().toString().equals(lt_pass))
		    	  {
		    		  try {
						startActivity( new Intent(Login_j.this,MainActivity.class));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finally{
							finish();
						}
		    	  }
		    	  else{
		    		  startActivity(new Intent(Login_j.this,Forget.class));
		    		  //finish();
		    		  //Toast.makeText(getApplicationContext(),"Sorry Wrong Password..", Toast.LENGTH_SHORT).show();
		    	  }
		      }
		      else
		    	  startActivity(new Intent(Login_j.this,Forget.class));
			break;
	
		}
		
	}
}
