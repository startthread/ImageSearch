package com.codepath.startthread.imagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.codepath.startthread.imagesearch.R;
import com.codepath.startthread.imagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

	public static final String EXTRA_IMAGE_RESULT = "image_result";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		
		getActionBar().hide();
		
		final ImageResult imageResult = getIntent().getParcelableExtra(EXTRA_IMAGE_RESULT);
		final ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
		Picasso.with(this).load(imageResult.url).into(ivImageResult);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
