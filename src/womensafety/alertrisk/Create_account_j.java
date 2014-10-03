package womensafety.alertrisk;

import crr.india.womensafety12.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Create_account_j extends Activity implements OnClickListener{

	Button btn_save;
	EditText et_name,et_email,et_phone,et_pass,et_repass;
	public static String name,email,phone,pass,repass;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account_x);
		et_name=(EditText) findViewById(R.id.editText_enterpass);
	
		et_pass=(EditText) findViewById(R.id.editText_pass);
		et_repass=(EditText) findViewById(R.id.editText_repass);
		btn_save=(Button) findViewById(R.id.button_login);
		btn_save.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button_login:
			name=et_name.getText().toString();
			pass=et_pass.getText().toString();
			repass=et_repass.getText().toString();
			if(name.matches("")||pass.matches("")||repass.matches(""))
			{
				Toast.makeText(getApplicationContext(), "Please fill all the values ", Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(et_pass.getText().toString().equals(et_repass.getText().toString()))
				{
					
					DBMainClass dbm=new DBMainClass(this);
					dbm.open();
					dbm.write_ld(name,repass);
					dbm.close();
					Toast.makeText(getApplicationContext(), "Password Created Successfully", Toast.LENGTH_SHORT).show();
				
						try 
						{
							Intent i2 =new Intent(getApplicationContext(),Login_j.class);
							startActivity(i2);
						} catch (Exception e) 
						{
							e.printStackTrace();
						} 
						finally
						{
							finish();
						}
					}
				else
				{
					Toast.makeText(getApplicationContext(), "Passwords do not match..!!", Toast.LENGTH_SHORT).show();	
				}
			}
		break;

		}
		
	}
}
