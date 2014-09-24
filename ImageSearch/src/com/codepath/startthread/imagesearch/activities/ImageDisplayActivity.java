package com.codepath.startthread.imagesearch.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.startthread.imagesearch.R;
import com.codepath.startthread.imagesearch.helpers.UiUtils;
import com.codepath.startthread.imagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends SherlockFragmentActivity {

	private static final String TAG = "ImageDisplayActivity";
	public static final String EXTRA_IMAGE_RESULT = "image_result";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		final Point size = UiUtils.getDisplaySize(this);
		Log.v(TAG, "display size is: " + size);
		
		final ImageResult imageResult = getIntent().getParcelableExtra(EXTRA_IMAGE_RESULT);
		final ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
		Picasso.with(this).load(imageResult.url).resize(600, 600).into(ivImageResult);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.image_display, menu);
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
