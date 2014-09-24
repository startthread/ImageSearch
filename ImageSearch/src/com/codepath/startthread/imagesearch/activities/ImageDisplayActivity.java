package com.codepath.startthread.imagesearch.activities;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.startthread.imagesearch.R;
import com.codepath.startthread.imagesearch.helpers.ImageUtils;
import com.codepath.startthread.imagesearch.helpers.UiUtils;
import com.codepath.startthread.imagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends SherlockFragmentActivity {

	private static final String TAG = "ImageDisplayActivity";
	public static final String EXTRA_IMAGE_RESULT = "image_result";

	private ImageView ivImageResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		final Point size = UiUtils.getDisplaySize(this);
		Log.v(TAG, "display size is: " + size);

		final ImageResult imageResult = getIntent().getParcelableExtra(
				EXTRA_IMAGE_RESULT);
		ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
		Picasso.with(this).load(imageResult.url).resize(600, 600)
				.into(ivImageResult);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_share) {
			final Uri imageUri = ImageUtils.addImageToMedia(ivImageResult,
					"Download");
			if (imageUri != null) {
				final Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("image/*");
				shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
				startActivity(Intent.createChooser(shareIntent, getString(R.string.share_image_msg)));
			} else {
				Toast.makeText(this, R.string.cannot_share_msg, Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
