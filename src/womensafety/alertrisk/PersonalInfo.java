package womensafety.alertrisk;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import crr.india.womensafety12.R;

public class PersonalInfo extends Activity implements OnClickListener{

	static int s=0;
	Button b1,b2;
	EditText et1,et2,et3;
	String text;
	 public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.personlayout);
		  b1=(Button)findViewById(R.id.savegmailid);
		  b2=(Button)findViewById(R.id.deletegmailid);
		  et1=(EditText)findViewById(R.id.user_id);
		  et2=(EditText)findViewById(R.id.pwd);
		  et3=(EditText)findViewById(R.id.comfirm_pwd);
		  text=et1.getText().toString();
		  b1.setOnClickListener(this);
		  b2.setOnClickListener(this);

		  DBMainClass1 db=new DBMainClass1("13","SenderEmail","Id","Pwd",this);
			db.open();
			ArrayList<String>al=db.getData();
			
			if(al.size()!=0)
			{
			et1.setText(al.get(0));
			et2.setText(al.get(1));
			}
			db.close();
		 
	 }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.savegmailid:
			String user=et1.getText().toString();
			String pwd=et2.getText().toString();
			String confirm=et3.getText().toString();
			if(user.matches(""))
			{
				Toast.makeText(getApplicationContext(), "Enter E-mail ID", Toast.LENGTH_SHORT).show();
			}else if(pwd.matches(""))
			{
				Toast.makeText(getApplicationContext(), "Enter password of your E-mail", Toast.LENGTH_SHORT).show();
			}
			else if(!pwd.matches(confirm))
			{
				Toast.makeText(getApplicationContext(), "Password donot match", Toast.LENGTH_SHORT).show();
			}
			else
			{
			DBMainClass1 db=new DBMainClass1("13","SenderEmail","Id","Pwd",this);
			db.open();
			db.write(user,pwd);
			db.close();
			s=1;
			Toast.makeText(this,"Email-id and password saved",Toast.LENGTH_SHORT).show();
			
			}
			break;
		case R.id.deletegmailid:
			DBMainClass1 db1=new DBMainClass1("13","SenderEmail","Id","Pwd",this);
			db1.open();
		    db1.delete2();
			db1.close();
			s=0;
			et1.setText(text);
			et2.setText(text);
			Toast.makeText(this,"Your Email details Deleted",Toast.LENGTH_SHORT).show();
			break;
		}
		
	}
}
