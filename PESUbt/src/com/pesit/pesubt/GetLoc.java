
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

import com.pesit.pesubt.DriverLogin.MyTask;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("NewApi")
public class GetLoc extends Activity {
	private double la;
	private double lo;
	public String routeNo="1";
	String sno;
	String to=null;
	 ProgressDialog progress;
	 String status=null;
	 Time t;
	 int s;
	 Spinner sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_loc);
//		sp=(Spinner)findViewById(R.id.spinner);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		
	
		t=new Time();
		s=1;
status="1";
		Bundle bundle = getIntent().getExtras();
			sno= bundle.getString("sno");
			
			progress = new ProgressDialog(this);
			progress.setMessage("Login...");
			new MyTask(progress).execute();
		 System.out.println("sno = "+sno);

		 Button logout = (Button)findViewById(R.id.logout);
			logout.setOnClickListener(new OnClickListener() {                       
	                    public void onClick(View v) {
	                    	status="0";
	                    	s=0;
	                    	progress.setMessage("Logout...");
	                    	new MyTask(progress).execute();

	                    }
	                });
		 LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        MyLocationListener mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000, 10, mlocListener);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_loc, menu);
		return true;
	}
	/* Class My Location Listener */
	public class MyLocationListener implements LocationListener
	{
		
	  @Override
	 
	  public void onLocationChanged(Location loc)
	  {

	   la = loc.getLatitude();
	   lo =loc.getLongitude();

	    String Text = "My current location is: " +
	    "Latitud = " + loc.getLatitude() +
	    "Longitud = " + loc.getLongitude();
	    

	    Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
	  
	    InputStream is=null;
	    
	    
	    try {
				 HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://kidiyoor.site88.net/PESUbt/updatelocation.php");
			  
			     // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		     
		        String sla=Double.toString(la);
		        String slo=Double.toString(lo);
		        t.setToNow();
		        String time1 = t.toString();
		        String time2 = time1.substring(0, 15);
		        String time=time2.substring(0,4)+"-"+time2.substring(4, 6)+"-"+time2.substring(6, 8)+" "+time2.substring(8, 9)+" "+time2.substring(9, 11)+":"+time2.substring(11, 13)+":"+time2.substring(13, 15);
		        System.out.println("time="+time);
		       nameValuePairs.add(new BasicNameValuePair("sno", sno));
		        nameValuePairs.add(new BasicNameValuePair("latitude", sla));
		       nameValuePairs.add(new BasicNameValuePair("longitude", slo));
		       nameValuePairs.add(new BasicNameValuePair("time", time));

		       httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
	        
		       HttpEntity entity = response.getEntity();

	   	     is = entity.getContent();
		        
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    	to="catch ClientProtocol";
	    System.out.println("caught1");
		    		    Toast.makeText( getApplicationContext(), to, Toast.LENGTH_SHORT).show();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    	to="catch IO";
		    	 System.out.println("caught2");	    
    		    Toast.makeText( getApplicationContext(), to, Toast.LENGTH_SHORT).show();
		    }
	    
	    System.out.println("success");

	
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
			  if(s==0)
			  {
			  Intent n=new Intent(GetLoc.this,MainActivity.class);
			  System.out.println("go");
			  startActivity(n);
			  
			  }
		  }

		@SuppressLint("NewApi")
		@Override
		protected Void doInBackground(Void... arg0) {
			
			try {
				 HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://kidiyoor.site88.net/PESUbt/status.php");
			   System.out.println("connect to website first time");
			     // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		          nameValuePairs.add(new BasicNameValuePair("sno", sno));
		          nameValuePairs.add(new BasicNameValuePair("status", status));
		          httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	       // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
	        
		       HttpEntity entity = response.getEntity();

	   	 InputStream is = entity.getContent();
	   	    System.out.println("..............");
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    }
	    

			return null;
			// TODO Auto-generated method stub
			
		
	}
}
}
