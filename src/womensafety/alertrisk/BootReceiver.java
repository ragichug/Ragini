package womensafety.alertrisk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver
{

@Override
public void onReceive(Context context, Intent arg1) {
	// TODO Auto-generated method stub
	DBMainClass1 db=new DBMainClass1("14","Button","Activate",true,context);
	db.open();
	String s=db.get();
	if(s.equals("true"))
	{
	Intent i=new Intent(context,ActivateService.class);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	context.startService(i);
	}
}
}