package womensafety.alertrisk;

import java.util.ArrayList;


import android.R;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.CountDownTimer;

import android.os.IBinder;
import android.util.Log;

	
	@SuppressLint("NewApi")
	public class ActivateService extends Service{
		  private NotificationManager mNM;
		  Notification localNotification;

		public BroadcastReceiver mReceiver;
		int pwcount=0;
		public IntentFilter local;
		public boolean register;
		public static ActivateService s;
		ButtonClick bc=null;
		@Override
		public void onCreate()
		{
		showNotification();
			Log.e("create called","create called");
			s=this;
			Thread t=new Thread(new Runnable()
			{
				public void run()
				{
			try{
			
			 local=new IntentFilter("android.intent.action.SCREEN_OFF");
			local.addAction("android.intent.action.SCREEN_ON");
			
			}
			catch(Exception e){
				System.out.println("Exception");
			}
			mReceiver=new MyBroadcastReceiver();
			registerReceiver(mReceiver,local);
			register=true;
				}});
			t.start();
			DBMainClass1 db=new DBMainClass1("15","Pwcount_table","Pwcount",this,2);
			db.open();
			ArrayList<String>al=db.getData();
			if(al.size()>0)
			{
			int count=Integer.parseInt(al.get(0));
			if(count==4)
			{
				if(bc==null)
				{
					Log.e("restarted","restarted");

					bc=new ButtonClick(s);
					bc.performAction();
					//showNotfication();

				}
			}
			}
			db.close();
			
			
		}

		@Override
		public IBinder onBind(Intent a) {
			// TODO Auto-generated method stub
			return null;
		}
		/*private void startMyService() {
			
		// TODO Auto-generated method stub
			//Intent i= new Intent(this, AlarmReceiver.class);
		//startService(i);
			
			new ButtonClick().performAction();
		}*/
		class MyBroadcastReceiver extends BroadcastReceiver
		{

			@Override
			public void onReceive(Context context, Intent arg1) {
				// TODO Auto-generated method stub
				Log.e("pwcount",String.valueOf(pwcount));
				pwcount+=1;
				if(pwcount==1)
				{
					new CountDownTimer(6000L,1000L)
					{
						public void onFinish()
						{
							pwcount=0;
						}

						@Override
						public void onTick(long arg0) {
							// TODO Auto-generated method stub
							
						}
					}.start();
				}
				if(pwcount==4)
				{
					DBMainClass1 db=new DBMainClass1("15","Pwcount_table","Pwcount",s,2);
					db.open();
					db.write(pwcount);
					db.close();
					pwcount=0;
					//unregisterReceiver(mReceiver);
					
					//startMyService();
					bc=new ButtonClick(s);
					bc.performAction();
					//showNotfication();
				}
			}

		}
			
		public void showNotification()
		{
			mNM=(NotificationManager)getSystemService("notification");
		    Intent localIntent = new Intent(this, MainActivity.class);
		    PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, localIntent, 0);

			//Notification localNotification = new Notification(2130837652, "Alerts will continue to be sent till you deactivate", System.currentTimeMillis());
		    localNotification=new Notification.Builder(this).setContentTitle("Avert Risk Activated").setSmallIcon(R.drawable.checkbox_on_background).setContentIntent(localPendingIntent).build();

			localNotification.defaults = (0x2 | localNotification.defaults);
		    localNotification.flags = (0x10 | localNotification.flags);
		    localIntent.putExtra("active", "activate");
		   // localNotification.setLatestEventInfo(this, getText(2131361808), "Alerts will continue to be sent till you deactivate", localPendingIntent);
		    
		    this.mNM.notify(1, localNotification);
		  
		}
			@Override
			public void onDestroy()
			{
				super.onDestroy();
				mNM.cancel(1);
				if(bc!=null)
				bc.destroy();
				if(register==true)
				{
			unregisterReceiver(mReceiver);	
			register=false;
				}
				Log.e("destroy","destroy");
				
			}
			
	
		
	}