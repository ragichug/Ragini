package womensafety.alertrisk;

import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class MyLocation extends Service{

	public static MyLocation s;
	Intent i;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		
		return null;
	}
	LocationManager lm,lm1;
	public static OurListener listener,listener1;
	@Override
	public int onStartCommand(final Intent intent,int flags,int startId)
	{
		i=intent;
		s=this;
	
				
		LocationManager lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		listener=new OurListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
		
		
		LocationManager lm1=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		listener1=new OurListener();
		lm1.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener1);
		
		
		return startId;
		
	}
	
	
	
	
	@SuppressLint("UseValueOf")
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
				//sb.append(address.getLocality()).append("\n");
				sb.append(address.getPostalCode()).append("\n");
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
}
