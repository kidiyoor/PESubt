package com.pesit.pesubt;


import com.google.android.gms.drive.internal.v;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater.Filter;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button button ;	
	
	Context Cmain;
	 private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 250;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	    private GestureDetector gestureDetector;
	    View.OnTouchListener gestureListener;
	    ImageView fl;
	    Context v;
	    Boolean f;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Cmain=this;
		fl = (ImageView)findViewById(R.id.imageView1);
	v= getApplicationContext();
		////////
		  // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        fl.setOnClickListener( MainActivity.this); 
        fl.setOnTouchListener(gestureListener);
		
        
        f =isNetworkAvailable();
        if(!isNetworkAvailable())
        {
    	    Toast.makeText( getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();

        }
		//Button driver = (Button)findViewById(R.id.Driver);
		//Button Student = (Button)findViewById(R.id.Student);
		
		////////
		/*
		////// go to driver login
				driver.setOnClickListener(new OnClickListener() {                       
		                    public void onClick(View v) {
		                        Intent intent = new Intent (MainActivity.this,DriverLogin.class);
		           startActivity(intent);
		                    }
		                });
		/////
				Student.setOnClickListener(new OnClickListener() {                       
                    public void onClick(View v) {
                    	
                        Intent intent = new Intent (MainActivity.this,RoutesAvailable.class);
           startActivity(intent);
                    }
                });
		
		*/		
				
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 class MyGestureDetector extends SimpleOnGestureListener {

			@Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	            try {
	                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                    return false;
	                // right to left swipe
	                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                	f =isNetworkAvailable();
	                	if(f){
                        Intent intent = new Intent (MainActivity.this,RoutesAvailable.class);
                        startActivity(intent);
	                	}
	                	else
	                	{
	                	    Toast.makeText( v, "No internet", Toast.LENGTH_LONG).show();

	                	}
	                	} 
	                	else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                	f =isNetworkAvailable();
	                	if(f){
	                	Intent intent = new Intent (MainActivity.this,DriverLogin.class);
	 		           	startActivity(intent);
	                	}
	                	else
	                	{
	                	    Toast.makeText( v, "No internet", Toast.LENGTH_LONG).show();

	                	}
	                	}
	            } catch (Exception e) {
	                // nothing
	            }
	            return false;
	        }

	            @Override
	        public boolean onDown(MotionEvent e) {
	              return true;
	        }
	    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
