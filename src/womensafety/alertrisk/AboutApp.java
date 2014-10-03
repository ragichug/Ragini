package womensafety.alertrisk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import crr.india.womensafety12.R;

public class AboutApp extends Activity{

	TextView tv1,tv2;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutapp);
		tv1=(TextView)findViewById(R.id.abtapp1);
		String text="        ABOUT APP \n \n";
		tv1.setText(text);
		tv2=(TextView)findViewById(R.id.abtapp2);
		text=" \nThis app is designed to provide women safety.You can activate this service whenever you want to by clicking on activate button. Clicking on power button twice will then forward alert msg to all the recepients added by you in contacts list. Message delevery can take maximum 10 minutes. PLEASE ENSURE that all the details filled by you are correct else the services will not work properly.";
		tv2.setText(text);
	}
}
