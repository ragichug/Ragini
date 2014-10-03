package womensafety.alertrisk;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import crr.india.womensafety12.R;

public class FifthActivity extends Activity implements OnClickListener,OnFocusChangeListener {

	EditText et1,et2,et3;
	Button b1,b2;
	String text;
	static final int PICK_CONTACT_REQUEST = 1;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.thirdlayout);
	    
	    et1=(EditText)findViewById(R.id.editText1);
	    et2=(EditText)findViewById(R.id.editText2);
	    et3=(EditText)findViewById(R.id.editText3);
	    text=et1.getText().toString();
	    et1.setOnFocusChangeListener(this);
	    et1.setOnClickListener(this);
	    b1=(Button)findViewById(R.id.savecontact);
	    b2=(Button)findViewById(R.id.deletecontact);
	    
	    b1.setOnClickListener(this);
	    b2.setOnClickListener(this);
	   // b1.setVisibility(1);
	    Intent i=getIntent();
	    String s=i.getStringExtra("selectedString");
	    //String ss=s.substring(s.indexOf(":")+1,s.length());
	    if(s!=null)
	    {
	    //Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
	    	String ss=s.substring(s.indexOf(":")+1,s.length());
	    DBMainClass1 db=new DBMainClass1("11","Contacts","Name","Phone","Email",this);
	    db.open();
	   
	   String s1[]= db.getField(ss);
	   et1.setText(s1[0].toString());
	   et2.setText(s1[1].toString());
	   et3.setText(s1[2].toString());
	    db.close();
	    }  
	    else
	    {
	    	b2.setVisibility(View.GONE);
	    }
	  }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.savecontact:
			
			String s1=et1.getText().toString();
			String s2=et2.getText().toString();
			String s3=et3.getText().toString();
			
			if(s2.equals(text))
				Toast.makeText(this,"Phone Should Be Entered", Toast.LENGTH_SHORT).show();
			else
			{
			DBMainClass1 db=new DBMainClass1("11","Contacts","Name","Phone","Email",this);
			db.open();
			String ss1[] =db.getField(s2);
			if(ss1[1]!=null)
			{
			
		db.update(s1,s2,s3);
			}
			else
			{
				db.write(s1,s2,s3);
			}
			db.close();
			
			Toast.makeText(this,"Contact Saved",Toast.LENGTH_SHORT).show();
			}
			break;
		
		
			
		case R.id.deletecontact:
			DBMainClass1 db1=new DBMainClass1("11","Contacts","Name","Phone","Email",this);
			db1.open();
			db1.delete(et1.getText().toString(),et2.getText().toString(),et3.getText().toString());
			db1.close();
			Toast.makeText(this,"Contact Deleted", Toast.LENGTH_SHORT).show();
			et1.setText(text);
			et2.setText(text);
			et3.setText(text);
			break;
		
		case R.id.editText1:
			Intent intent = new Intent(Intent.ACTION_PICK,Uri.parse("content://contacts"));
			intent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
		    startActivityForResult(intent, PICK_CONTACT_REQUEST);
			break;
		
		}
	}
	  
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == PICK_CONTACT_REQUEST) {
	        if (resultCode == RESULT_OK) {
	            Uri contactUri = data.getData();
	            String[] projection = {Phone.NUMBER,Phone.DISPLAY_NAME};
	           
	            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
	           
	            cursor.moveToFirst();
	            int column1 = cursor.getColumnIndex(Phone.NUMBER);
	            int column2=cursor.getColumnIndex(Phone.DISPLAY_NAME);
	            String number = cursor.getString(column1);
	            String name=cursor.getString(column2);
	            if(number.equals(name))
	            {
	            et2.setText(number);
	            }
	            else
	            {
	            	et2.setText(number);
		            et1.setText(name);

	            }
	        }
    }
	}
	
	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(arg1==true)
		{
		Intent intent = new Intent(Intent.ACTION_PICK,Uri.parse("content://contacts"));
		intent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
	    startActivityForResult(intent, PICK_CONTACT_REQUEST);
		}
	}

}
