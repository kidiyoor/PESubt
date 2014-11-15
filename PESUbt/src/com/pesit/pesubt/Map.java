package com.pesit.pesubt;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pesit.pesubt.GetLoc.MyLocationListener;
import com.pesit.pesubt.RoutesAvailable.MyTask;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Map extends FragmentActivity {
	GoogleMap map ;
	 ProgressDialog progress;
	 String sno;
	 Double Lat=(double) 0;
	 Double Lng=(double) 0;
	 MarkerOptions markerD;
	 MarkerOptions markerS;
	 String route ;
	 int i=0;
	 Double la=(double) 12.777;
	 Double lo=(double) 13.777;
	 int locFlag=0;
	 Criteria criteria;
	    LocationManager locationManager;
	    String provider ;
	    String lastUpdate;
	   TextView lu;
	   Button myloc;
	   int locationFlag=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		myloc=(Button)findViewById(R.id.myloc);
		lu=(TextView)findViewById(R.id.lastupdate);
		map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1)).getMap();
		
		Bundle bundle = getIntent().getExtras();
		sno = bundle.getString("sno");
		
		progress = new ProgressDialog(this);
		progress.setMessage("Loading...");
		new MyTask(progress).execute();
Button refresh = (Button)findViewById(R.id.refresh);
Button exit = (Button)findViewById(R.id.exit);
				refresh.setOnClickListener(new OnClickListener() {                       
		                    public void onClick(View v) {
		                    	
		                		new MyTask(progress).execute();

		                    }
		                });
		
				exit.setOnClickListener(new OnClickListener() {                       
                    public void onClick(View v) {
                    	Intent n = new Intent(Map.this,MainActivity.class);		
						startActivity(n);
                    }
                });
				myloc.setOnClickListener(new OnClickListener() {                       
                    public void onClick(View v) {
                    if(locationFlag==1)
                    {
                    	markerS = new MarkerOptions().position(
        						new LatLng(la,lo))
        						.title("MyLocation").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        				map.addMarker(markerS);
        				 markerS.snippet("MyLocation");
                    }
                    else
                    {
                	    Toast.makeText( getApplicationContext(), "Location feching yet", Toast.LENGTH_SHORT ).show();

                    }
                    }
                });
				criteria = new Criteria(); 
		/*	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			provider= locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(provider);
	
			la =  location.getLatitude();
			lo = location.getLongitude();
	System.out.println("initiaizing .... \n my last know Loc = "+la+";"+lo);			
			/*	LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		        MyLocationListener mlocListener = new MyLocationListener();
		        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 5, mlocListener);

	 LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

     MyLocationListener mlocListener = new MyLocationListener();
     mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 10, mlocListener);
	*/
				 LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

			     MyLocationListener mlocListener = new MyLocationListener();
			     mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 10, mlocListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	public class MyTask extends AsyncTask<Void, Void, Void> {
		  private ProgressDialog progress1;

		public MyTask(ProgressDialog progress2) {
		    this.progress1 = progress2;
		    progress=progress1;
		  }

		  public void onPreExecute() {
		    progress.show();
		  }

		

		  public void onPostExecute(Void unused) {
			  progress.dismiss();
			  lu.setText(lastUpdate);
			  CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(Lat,Lng)).zoom(15).build();
				System.out.println("MapSet");
				map.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				// Adding a marker
				markerD = new MarkerOptions().position(
						new LatLng(Lat,Lng))
						.title("bus : "+lastUpdate).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
				map.addMarker(markerD);
				 markerD.snippet("BusLocation");
if(true){
			/*	markerS = new MarkerOptions().position(
						new LatLng(la,lo))
						.title("MyLocation").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				map.addMarker(markerS);
				 markerS.snippet("MyLocation");*/}
if(i==1)
{	
	Thread splash_screen = new Thread() {
	public void run() {
		try {
			sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			markerD.notify();
			if(locFlag==1)
				{/*markerS.notify();*/}
			
			i=i+1;
		}
	}
};
splash_screen.start();

}
  }

		
		@Override
		protected Void doInBackground(Void... arg0) {
			System.out.println("backGround(mapClass)");
			InputStream is = null;
			try {
				
				/* Location location = locationManager.getLastKnownLocation(provider);
			     la =  location.getLatitude();
			    lo = location.getLongitude();
	*/
				System.out.println("initiaizing .... \n my last know Loc = "+Lat+";"+Lng);	
		 			 HttpClient httpclient = new DefaultHttpClient();
		 		    HttpPost httppost = new HttpPost("http://kidiyoor.site88.net/PESUbt/getLoc.php");
		 		   System.out.println("connect to website to get Loc");
		 		     // Add your data
		 		
		 	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		 	         nameValuePairs.add(new BasicNameValuePair("sno", sno));
		 	       
		 	          httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 	        //Execute HTTP Post Request
		 		   
		 	        HttpResponse response = httpclient.execute(httppost);
		 	    
		 	       HttpEntity entity = response.getEntity();
		 	      
		     	     is = entity.getContent();
		     	   
		 	    } catch (ClientProtocolException e) {
		 	        // TODO Auto-generated catch block
		 	    	System.out.println("..............catch e");
		 	    } catch (IOException e) {
		 	        // TODO Auto-generated catch block
		 	    	System.out.println("..............catch IO");
		 	    }
			
			System.out.println("in download");
			String result = null;
			try{
			           BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			           StringBuilder sb = new StringBuilder();
			           String line = null;
			           int y=0;
			           while ((line = reader.readLine()) != null&&y==0) {
			                   sb.append(line + "\n");y=1;
			                  
			           }
			        
			           is.close();
			           result  = sb.toString();
			          
			           System.out.println("end of first half");
			   }catch(Exception e){
			           Log.e("log_tag", "Error converting result "+e.toString());
			           System.out.println("caught e");
			           
			   }
			
		
			try{
				System.out.println("After download ");
				System.out.println("Try to Phrase");
			JSONArray jArray = new JSONArray(result);
		
			int k=0;
			  int y = jArray.length();
	
			  System.out.println("y="+y);


			JSONObject json_data = jArray.getJSONObject(0);
			System.out.println("one oject recieved");
		
			Lat=Double.parseDouble(json_data.getString("latitude"));
			Lng=Double.parseDouble(json_data.getString("longitude"));
			route=json_data.getString("routeNo");
			lastUpdate=json_data.getString("updateTime");
			System.out.println("recived :"+Lat+":"+Lng+":"+lastUpdate);
			
			
			} catch (JSONException e) {System.out.println("caught e");
		     
		 }
			return null;
			// TODO Auto-generated method stub
			
	
		  
		}
	}
	/* Class My Location Listener */
	public class MyLocationListener implements LocationListener
	{
		
	  @Override
	 
	  public void onLocationChanged(Location loc)
	  {
	 //  la = loc.getLatitude();
	  // lo =loc.getLongitude();
locationFlag=1;
	    String Text = "My current location is: " +
	    "Latitud = " + loc.getLatitude() +
	    "Longitud = " + loc.getLongitude();
	    locFlag=1;
 
la=loc.getLatitude();
lo=loc.getLongitude();
	 //   Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
	  
	    	
	  }

	  @Override
	  public void onProviderDisabled(String provider)
	  {
	    Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
	  }

	  @Override
	  public void onProviderEnabled(String provider)
	  {
	    Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras)
	  {

	  }
	  
	}
}
