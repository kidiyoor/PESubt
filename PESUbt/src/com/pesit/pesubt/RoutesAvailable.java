package com.pesit.pesubt;

import android.R.integer;
import android.widget.AdapterView;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ListView;

public class RoutesAvailable extends Activity {

	public TextView text;
	public  ListView list;
	InputStream is = null;
	public int[] sno;
	String result;
    ArrayList<String> itemArrey=null;
    ArrayAdapter<String> itemAdapter=null;
    ProgressDialog progress;
    List<RowItem> rowItems=null;
	CustomListViewAdapter adapter=null ;
    @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		 
		super.onCreate(savedInstanceState);	
		  
		  	itemArrey = new ArrayList<String>();
	        itemArrey.clear();
	        //itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemArrey);
	    
		progress = new ProgressDialog(this);
		progress.setMessage("Loading...");
		rowItems = new ArrayList<RowItem>();
		adapter = new CustomListViewAdapter(this,
	            android.R.layout.activity_list_item, rowItems);
		new MyTask(progress).execute();
		
		
		 //R.layout.list_items
	
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.routes_available, menu);
		return true;
	}
	
	
	
	public class MyTask extends AsyncTask<Void, Void, Void> {
		  private ProgressDialog progress1;

		public MyTask(ProgressDialog progress2) {
		    this.progress1 = progress2;
		    progress=progress1;
		  }

		  public void onPreExecute() {
			  setContentView(R.layout.activity_routes_available);
			  	 text=(TextView) findViewById(R.id.textView1);
				 list = (ListView)findViewById(R.id.routes);
				 
				 list.setAdapter(adapter);
		    progress.show();
		   
		  }

		

		  public void onPostExecute(Void unused) {
			  
			  list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
				    	
				    		String snoS=String.valueOf(sno[pos]);
				  
				    	Intent l = new Intent(RoutesAvailable.this, Info.class);
				    	l.putExtra("sno",snoS);
			          startActivity(l);
				    }

					
					
				});
			  
			//  itemAdapter.notifyDataSetChanged();	
			  	adapter.notifyDataSetChanged();
			  progress.dismiss();
		  }

		@SuppressLint("NewApi")
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
		System.out.println("backGround");
			try {
		 			 HttpClient httpclient = new DefaultHttpClient();
		 		    HttpPost httppost = new HttpPost("http://kidiyoor.site88.net/PESUbt/routesAvailable.php");
		 		   System.out.println("connect to website first time");
		 		     // Add your data
		 	       // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		 	         //   nameValuePairs.add(new BasicNameValuePair("username", username));
		 	          //  httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 	        // Execute HTTP Post Request
		 		   
		 	        HttpResponse response = httpclient.execute(httppost);
		 	       System.out.println("1");
		 	       HttpEntity entity = response.getEntity();
		 	      System.out.println("..............2");
		     	     is = entity.getContent();
		     	    System.out.println("..............3");
		 	    } catch (ClientProtocolException e) {
		 	        // TODO Auto-generated catch block
		 	    	System.out.println("..............cTCH");
		 	    } catch (IOException e) {
		 	        // TODO Auto-generated catch block
		 	    	System.out.println("..............catch");
		 	    }
			
			System.out.println("in download");
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
			
			
		//text.setText(result);
			
			System.out.println("inside try catch5");
	      
	      
			try{
				System.out.println("inside try catch1");
			JSONArray jArray = new JSONArray(result);
			 System.out.println("inside try catch2");

			 	int k=0;
			  int y = jArray.length();
			  System.out.println("inside try catch 2.5");
			  System.out.println("y="+y);
			  sno = new int[y];
			  sno[0]=-1;
			  final Integer[] images = { R.drawable.bus1,
		            R.drawable.bus2, R.drawable.bus3, R.drawable.bus4,
		            R.drawable.bus5,R.drawable.bus6 };
				System.out.println("inside try catch3");
				for(k=0;k<y-1;k++)
				{ 
			
					System.out.println("inside loop"+k);
					JSONObject json_data = jArray.getJSONObject(k+1);
					sno[k]=Integer.parseInt(json_data.getString("sno"));
					System.out.println(sno[k]);
			String bus="bus"+k;
					if(Integer.parseInt(json_data.getString("status"))==1)
					{
						RowItem item = new RowItem((int)images[k], json_data.getString("routeNo"), "online");
						rowItems.add(item);
						//itemArrey.add(k,json_data.getString("routeNo")+"(online)");
						
					}
			else
			{
				RowItem item = new RowItem((int)images[k], json_data.getString("routeNo"), "offline");
				rowItems.add(item);
					//itemArrey.add(k,json_data.getString("routeNo")+"(offline)");
			}
			
				
			
				
				}
				
				System.out.println("inside try catch4");
				
				
			
			} catch (JSONException e) {System.out.println("caught");
		     
		 }
			return null;
		  
		}
	}
	}

