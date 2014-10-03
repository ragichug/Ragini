package womensafety.alertrisk;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;



import android.annotation.SuppressLint;

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

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.SurfaceView;

public class ButtonClick{

	ActivateService c;
	
	Bitmap userImage;
	LocationManager lm,lm1;
	Boolean t1runned=false,t2runned=false;
	ArrayList<String>al;
	ArrayList<String>m;
	ArrayList<String>s=new ArrayList<String>();
	ArrayList<String>up;

	ArrayList<String> phone=new ArrayList<String>();
	
	String username="",pwd="";
	String message="";
	Camera camera;
	
	public static OurListener listener,listener1;
	ButtonClick(ActivateService c)
	{
		this.c=c;
	}

	public void performAction() {
		// TODO Auto-generated method stub
		
		lm=(LocationManager)c.getSystemService(Context.LOCATION_SERVICE);
		listener=new OurListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
		
		lm1=(LocationManager)c.getSystemService(Context.LOCATION_SERVICE);
		listener1=new OurListener();
		lm1.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener1);
		
		if(c!=null && c.register==true)
		{
		c.unregisterReceiver(ActivateService.s.mReceiver);
		c.register=false;
		}


		camera=Camera.open();
	    SurfaceView view=new SurfaceView(c);
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

		
	}
	CountDownTimer t1=null,t2=null,t3=null;
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
			//sendmail=new SendMail();
			//sendmail.execute();
			startCounterWithId(0);
						
			/*while(message.equals(""))
			{
				Log.e("meassage",message+"  string");

			}*/
			
		}
		catch(Exception e){
			Log.e("picture taken",e.toString());
		}
		
		}
	};

	public void startCounterWithId(int i)
	{
		switch(i)	
		{
		case 0:
			t1=new CountDownTimer(180000L,1000L)
			{
				int flag=0;
				int count1=0,count=0;
				GMailSender sender=new GMailSender(username,pwd);
				@Override
				public void onFinish()
				{
					if(flag==0)
					
						message=s.get(0).toString()+"\nPlz find me at "+listener1.sb.toString();
					
					else
					    message=s.get(0).toString()+"\nPlz find me at "+listener.sb.toString();

					try
					{
						new Thread(new Runnable()

						{
							public void run()
							{
							for(int j=0;j<m.size();j++)
								try {
									Log.e("sent","mail");
									
									sender.sendMail("Message from AvertRisk",message,username,m.get(j).toString());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();
						startCounterWithId(1);
					}
					
						
					
					catch(Exception e)
					{
						Log.e("exception",e.toString());
					}
					
				
				}
				@Override
				public void onTick(long arg0) {
					// TODO Auto-generated method stub
					if(listener.b==true)
					{
			
						//cancel();
						//count++;
						//if(count==1)
						//{
						//onFinish();
						flag=1;
						//}
						//c.stopService(new Intent(c,ActivateService.class));
						//c.startService(new Intent(c,ActivateService.class));
			
				
						Log.e("flaginside",String.valueOf(flag));
						Log.e("count1inside",String.valueOf(count1));
						Log.e("countinside",String.valueOf(count));
						
					}
					Log.e("flag",String.valueOf(flag));
					Log.e("count1",String.valueOf(count1++));
					//t1runned=true;
					
				}
			};
			
			t1.start();

		break;
		case 1:
			t2=new CountDownTimer(245000L, 120000L)
			{

				SmsManager sms=SmsManager.getDefault();


				@Override
				public void onFinish() {
					// TODO Auto-generated method stub

					try
					{
					
    				Intent i=new Intent(Intent.ACTION_CALL);
    				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    				for(int j=0;j<phone.size();j++)
					{
						i.setData(Uri.parse("tel:"+phone.get(j)));
						c.startActivity(i);
						Log.e("msg",message);
					}
    				
    
    				listener.b=false;
    				listener1.b=false;

    				if(c!=null && c.register==false)
    				{
    				c.registerReceiver(c.mReceiver, c.local);
    				c.register=true;
    				}
    				DBMainClass1 db=new DBMainClass1("15","Pwcount_table","Pwcount",c,2);
    				db.open();
    				db.write(c.pwcount);
    				db.close();
    	
    				destroy(); 

					}
					catch(Exception e)
					{
						Log.e("send call",e.toString());
					}
				}

				@Override
				public void onTick(long arg0) {
					// TODO Auto-generated method stub
					try
					{
					for(int j=0;j<phone.size();j++)
					{
						sms.sendTextMessage(phone.get(j),null,message,null, null);
						Log.e("message sent","message sent");
					}
					}
					catch(Exception e)
					{
						Log.e("send message",e.toString()+System.currentTimeMillis());
					}
				}
    		

			};
			t2.start();

		break;
		}
	}
	
	public void setData()
	{
		int i,pos;
		String s1;
		DBMainClass1 db=new DBMainClass1("11","Contacts","Name","Phone","Email",c);
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
		DBMainClass1 db1=new DBMainClass1("13","SenderEmail","Id","Pwd",c);
		db1.open();
		up=db1.getData();
		if(up.size()==2)
		{
		username=up.get(0).toString();
		pwd=up.get(1).toString();
		}
		Log.e(username,pwd);
		db1.close();
		DBMainClass1 db2=new DBMainClass1("12","Message","Msg",c,1);
		db2.open();

		s=db2.getData();
		if(s.size()==0)
			s.add("Please help me.I am in danger");
		db2.close();
		
	}

	public void startTimer()
    {
    	
    	t3=new CountDownTimer(10000,1000)
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
    
    };
    t3.start();
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
			
			
			Geocoder gc = new Geocoder(c.getApplicationContext(), Locale.getDefault());
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
				

			}
				catch(Exception e){
					sb=new StringBuilder();
					sb.append("\nLongitude = "+longitude.toString()+"\n"+"Latitude = "+latitude.toString());
					sb.append("\nhttp://maps.google.com?q="+latitude.toString()+","+longitude.toString());
					b=true;
					

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


	public void destroy() {
		// TODO Auto-generated method stub
		//if(sendmail!=null)
		//sendmail.cancel(true);
		if(t2!=null)
		t2.cancel();
		if(t1!=null)
		t1.cancel();
		if(t3!=null)
		t3.cancel();
		if(lm!=null)
		lm.removeUpdates(listener);
		if(lm1!=null)
		lm1.removeUpdates(listener1);
		c.bc=null;
		

	}




}
