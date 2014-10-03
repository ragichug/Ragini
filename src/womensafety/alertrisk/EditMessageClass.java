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

public class EditMessageClass extends Activity implements OnClickListener{

	EditText et1;
	Button b1;
	  public void onCreate(Bundle savedInstanceState) {
		  
			  super.onCreate(savedInstanceState);
			  setContentView(R.layout.editmsg);
			  et1=(EditText)findViewById(R.id.msg);
			  DBMainClass1 db=new DBMainClass1("12","Message","Msg",this,1);
			  db.open();
			  ArrayList<String>al=db.getData();
			  if(al.size()!=0)
				  et1.setText(al.get(0));
			  db.close();
			  b1=(Button)findViewById(R.id.saveMsg);
			  b1.setOnClickListener(this);
		  }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		String msg=et1.getText().toString();
		if(msg.matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter The Message ", Toast.LENGTH_SHORT).show();
		}
		else
		{
		DBMainClass1 db=new DBMainClass1("12","Message","Msg",this,1);
		
		db.open();
		db.write(msg);
		Toast.makeText(this,"Message Saved",Toast.LENGTH_SHORT).show();
				db.close();
		}
				
	}

}