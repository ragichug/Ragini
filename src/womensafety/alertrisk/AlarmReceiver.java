package womensafety.alertrisk;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceView;



public class AlarmReceiver extends Service{

	
	
	Bitmap userImage;
	LocationManager lm,lm1;
	public static OurListener listener,listener1;
	@Override
	public int onStartCommand(final Intent intent,int flags,int startId)
	{

	super.onStartCommand(intent, flags, startId);

	lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
	listener=new OurListener();
	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
	
	lm1=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
	listener1=new OurListener();
	lm1.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener1);
	
	if(ActivateService.s!=null)
	{
	ActivateService.s.unregisterReceiver(ActivateService.s.mReceiver);
	ActivateService.s.register=false;
	}


	camera=Camera.open();
    SurfaceView view=new SurfaceView(this);
    try{
    	camera.setPreviewDisplay(view.getHolder());
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
    camera.startPreview();
	setData();
    startTimer();
    return startId;
		
	}
	
	public class OurListener implements LocationListener{

		@SuppressLint("UseValueOf")
		public Double latitude=new Double(0.0),longitude=new Double(0.0);
		public StringBuilder sb=new StringBuilder("");
		boolean b=false;
		@Override
		public void onLocationChanged(Location loc) {
			//Log.d("hello",b+"");
			 longitude=loc.getLongitude();
			 latitude=loc.getLatitude();
			
			
			Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());
			try {
				
				List<android.location.Address> addresses = gc.getFromLocation(latitude, longitude, 1);
				sb = new StringBuilder();
				if (addresses.size() > 0) {
				android.location.Address address = addresses.get(0);
				for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
				sb.append(address.getAddressLine(i)).append("\n");
				sb.append(address.getCountryName());
				sb.append("\nLongitude = "+longitude.toString()+"\n"+"Latitude = "+latitude.toString());
				b=true;
				}
				//Toast.makeText(MainActivity.this,sb.toString(), Toast.LENGTH_SHORT).show();

			}
				catch(Exception e){
					sb=new StringBuilder();
					sb.append("\nLongitude = "+longitude.toString()+"\n"+"Latitude = "+latitude.toString());
					sb.append("\nhttp://maps.google.com?q="+latitude.toString()+","+longitude.toString());
					b=true;
					//Log.d("h",b+e.toString()+MyLocation.listener.b+MyLocation.listener1.b);

				}
			
			
			
		}
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
	
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	ArrayList<String>al;
	ArrayList<String>m;
	ArrayList<String>s=new ArrayList<String>();
	ArrayList<String>up;

	ArrayList<String> phone=new ArrayList<String>();
	
	String username="",pwd="";
	String message;
	Camera camera;
	public void setData()
	{
		int i,pos;
		String s1;
		DBMainClass1 db=new DBMainClass1("11","Contacts","Name","Phone","Email",AlarmReceiver.this);
		db.open();
		al=db.getData();
		for(i=0;i<al.size();i++)
		{
			s1=al.get(i).toString();
			pos=s1.indexOf(":");
			phone.add(s1.substring(pos+1));
			
		}
		m=db.getEmail();
		db.close();
		DBMainClass1 db1=new DBMainClass1("13","SenderEmail","Id","Pwd",AlarmReceiver.this);
		db1.open();
		up=db1.getData();
		if(up.size()==2)
		{
		username=up.get(0).toString();
		pwd=up.get(1).toString();
		}
		Log.e(username,pwd);
		db1.close();
		DBMainClass1 db2=new DBMainClass1("12","Message","Msg",AlarmReceiver.this,1);
		db2.open();

		s=db2.getData();
		if(s.size()==0)
			s.add("Please help me.I am in danger");
		db2.close();
		/*if(m.size()!=0)
		{
		for(i=0;i<m.size()-1;i++)
		{
			recepientmail+=m.get(i).toString()+",";
		}
		recepientmail+=m.get(i).toString();
		}*/
		
	}
	
SendMail sendmail;
	
	Camera.PictureCallback jpegCallBack=new Camera.PictureCallback()
    {
		
		@Override
		public void onPictureTaken(byte[] data, Camera arg1) {
			
			// TODO Auto-generated method stub
			File destination =new File(Environment.getExternalStorageDirectory(),"myPicture.jpg");
			
		try{
			userImage=BitmapFactory.decodeByteArray(data,0, data.length);
			FileOutputStream out=new FileOutputStream(destination);
			userImage.compress(Bitmap.CompressFormat.JPEG,90,out);
			camera.stopPreview();
			camera.release();
			sendmail=new SendMail();
			sendmail.execute();
		}
		catch(Exception e){
		}
		
		}
	};
	 public void startTimer()
	    {
	    	
	    	new CountDownTimer(10000,1000)
	    	{
	    		@Override
	    	public void onFinish()
	    	{
	    		
				camera.takePicture(null, null, null,jpegCallBack);
				
	    	}
	    	
			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				
			}
	    
	    }.start();
	}
	
	 class SendMail extends AsyncTask<Void,Void,Void>
		{
int flag=0,flag1=0,flag2=0;
	@Override
			protected Void doInBackground(Void... arg0) {
				GMailSender sender=null;
				// TODO Auto-generated method stub
				try
				{
					sender=new GMailSender(username,pwd);
				}
				catch(Exception e)
				{
					flag2=1;
					Log.e("Exception","handled");
				}
				try
				{
					
						int j;
					for(j=0;j<300;j++)
					{
						Log.d(String.valueOf(j),listener.b+"value");
						if(listener.b==true)
						{
							flag=1;
							break;
						}
						
						Thread.sleep(1000);
					}
					
					if(listener1.b!=true)
					{
					for(j=0;j<200;j++)
					{
						Log.d(String.valueOf(j),listener1.b+"value");
						if(listener1.b==true)
						{
							flag1=1;
							break;
						}
						Thread.sleep(1000);
					}
					}
					else
					{
						flag1=1;
					}
					
					
		
					if(flag==0 && flag1==1)
					message=s.get(0).toString()+"\nPlz find me at "+listener1.sb.toString();
					if(flag==1 && flag1==1)
			    	message=s.get(0).toString()+"\nPlz find me at "+listener.sb.toString();
					if(flag1==0)
			    	message=s.get(0).toString()+"\nSorry location cannot be currently reterieved";

					if(flag2==0)
					{
					try
					{
						Log.e("sent","mail");
						for(j=0;j<m.size();j++)
	            	sender.sendMail("Message from AvertRisk",message,username,m.get(j).toString());
					}
					catch(Exception e)
					{
						Log.e("execption","mail");
					}
					}
				
	           
	        				int count=0;
	        				while(++count<=2)
	        				{
	        					
	        					Intent i=new Intent(Intent.ACTION_CALL);
	        					
	        					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        					
	        					
	        					//SmsManager sms=SmsManager.getDefault();

	        					for(j=0;j<phone.size();j++)
	        					{
	        						//sms.sendTextMessage(phone.get(j),null,message,null, null);
	        						Log.e("msg",message);
	        					}
	        					
	        					for(j=0;j<phone.size();j++)
	        					{
	        						i.setData(Uri.parse("tel:"+phone.get(j)));
	        						startActivity(i);
	        						Log.e("msg",message);
	        					}
	        					try
	        					{
	        						Thread.sleep(1000*60*2);
	        					}
	        					catch(Exception e){
	        						Log.e("errorinmsgsending",e.toString());
	        					}
	        					
	        				}
	        				
	        				listener.b=false;
	        				listener1.b=false;
	        				if(ActivateService.s!=null)
	        				{
	        				ActivateService.s.registerReceiver(ActivateService.s.mReceiver, ActivateService.s.local);
	        				ActivateService.s.register=true;
	        				}
	        				AlarmReceiver.this.stopSelf();
	        			 
	        				
	     
	            	
	            } catch (Exception e) {
	                Log.e("SendMail", e.getMessage(), e);
	            	//Log.e("SendMail","ff");
	            	}
				return null;
			}
			
		} 
	 
	 
@Override
public void onCreate()
{
	super.onCreate();

}
@Override
public void onDestroy()
{
	super.onDestroy();
	sendmail.cancel(true);
	lm.removeUpdates(listener);
	lm1.removeUpdates(listener1);

}
	 
	
}
	

   




