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

import com.pesit.pesubt.RoutesAvailable.MyTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DriverLogin extends Activity {

	Button login;
	EditText password;
	 InputStream is;
	 String result;
	 String password1="";
	 int y =0;
	 JSONArray jArray;
	 JSONObject json_data=null;
	 ProgressDialog progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		setContentView(R.layout.activity_driver_login);
		login=(Button)findViewById(R.id.login);
		password=(EditText)findViewById(R.id.password);
		
		progress = new ProgressDialog(this);
		progress.setMessage("Loading...");
		new MyTask(progress).execute();
		
	


		login.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				System.out.println("button clicked");
				password1=password.getText().toString();
				System.out.println("password entered ="+password1);
				int g=0;
for(g=1;g<y;g++)
{
	System.out.println("value of g ="+g);
	
	try {
		json_data = jArray.getJSONObject(g);
	} catch (JSONException e) {System.out.println("caught b1");
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		System.out.println("password obtained="+json_data.getString("password").toString());
		
		if(json_data.getString("password").toString().equals(password1))
		{
		System.out.println("inside try catch 3.5");
		System.out.println("seletec sno ="+json_data.getString("sno"));
		Intent l = new Intent(DriverLogin.this,GetLoc.class);
		l.putExtra("sno",json_data.getString("sno"));
		//Integer.parseInt(json_data.getString("sno"))
		  startActivity(l);
		  
		}
	} catch (NumberFormatException e) {System.out.println("caught b2");
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {System.out.println("caught b3");
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
		}
			});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.driver_login, menu);
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
		  }

		@SuppressLint("NewApi")
		@Override
		protected Void doInBackground(Void... arg0) {
			
			try {
				 HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://kidiyoor.site88.net/PESUbt/driverAuthenticate.php");
			   System.out.println("connect to website first time");
			     // Add your data
		  //      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		    //        nameValuePairs.add(new BasicNameValuePair("sno", sno));
		      //     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
	jArray = new JSONArray(result);
	 System.out.println("inside try catch2");

	 	int k=0;
	  y= jArray.length();
	  System.out.println("y="+y);
	 
		System.out.println("inside try catch3");
	/*	
	JSONObject json_data = jArray.getJSONObject(0);
	System.out.println("inside try catch 3.5");
	routeNo.setText(json_data.getString("routeNo"));
	driver.setText(json_data.getString("driver"));
	info.setText(json_data.getString("info"));
	*/
	System.out.println("inside try catch4");
		


	} catch (JSONException e) {System.out.println("caught");
	 
	}
			return null;
			// TODO Auto-generated method stub
			
		
	}
}
}
