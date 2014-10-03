package womensafety.alertrisk;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import crr.india.womensafety12.R;

public class MainActivity extends Activity implements OnClickListener {

	Button b1,b2,b3,b4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1=(Button)findViewById(R.id.visit_app);
		b2=(Button)findViewById(R.id.abt_App);
		b3=(Button)findViewById(R.id.tut);
		b4=(Button)findViewById(R.id.exit);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.visit_app:
			Intent i=new Intent(MainActivity.this,SecondActivity.class);
			startActivity(i);
			break;
		case R.id.abt_App:
			Intent i1=new Intent(MainActivity.this,AboutApp.class);
			startActivity(i1);
			break;
		case R.id.tut:
			Intent i2=new Intent(getApplicationContext(),Tutorial2.class );
			startActivity(i2);
			break;
			
//		case R.id.tut:
//			startActivity(new Intent(getApplicationContext(),Tutorial2.class));
//			break;
		case R.id.exit:
			finish();
			break;
		}
	}

}
