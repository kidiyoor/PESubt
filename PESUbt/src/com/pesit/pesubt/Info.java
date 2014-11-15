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

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Info extends Activity {
	 InputStream is;
	Button map;
	String result = null;
	TextView routeNo;
	TextView driver;
	TextView info;
	boolean f;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		map=(Button)findViewById(R.id.map);
		routeNo=(TextView)findViewById(R.id.r1);
		driver=(TextView)findViewById(R.id.d1);
		info=(TextView)findViewById(R.id.i1);
		
		Bundle bundle = getIntent().getExtras();
		final String sno = bundle.getString("sno");
		 f = isNetworkAvailable();
        if(!isNetworkAvailable())
        {
    	    Toast.makeText( getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();

        }
		map.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				if(!isNetworkAvailable())
		        {
		    	    Toast.makeText( getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();

		        }
				else
				{
				Intent l = new Intent(Info.this,Map.class);
				l.putExtra("sno",sno);
		          startActivity(l);
				}
			}
	});
		try {
			 HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://kidiyoor.site88.net/PESUbt/info.php");
		   System.out.println("connect to website first time");
		     // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	            nameValuePairs.add(new BasicNameValuePair("sno", sno));
	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
         
	       HttpEntity entity = response.getEntity();

    	      is = entity.getContent();
    	    System.out.println("..............");
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
     
download();
try{
	System.out.println("inside try catch1");
JSONArray jArray = new JSONArray(result);
 System.out.println("inside try catch2");

 	int k=0;
  int y = jArray.length();
  System.out.println("y="+y);
 
	System.out.println("inside try catch3");
	
JSONObject json_data = jArray.getJSONObject(0);
System.out.println("inside try catch 3.5");
routeNo.setText(json_data.getString("routeNo"));
driver.setText(json_data.getString("driver"));
info.setText(json_data.getString("info"));

System.out.println("inside try catch4");
	


} catch (JSONException e) {System.out.println("caught");
 
}
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}
	
	void download()
	{ System.out.println("in download");
		result = null;
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
		           System.out.println("caught");
		           
		   }
	}

}
