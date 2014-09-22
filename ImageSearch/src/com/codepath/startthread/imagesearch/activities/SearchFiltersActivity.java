package com.codepath.startthread.imagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.startthread.imagesearch.R;
import com.codepath.startthread.imagesearch.models.ImageFilter;

public class SearchFiltersActivity extends SherlockFragmentActivity {

	public static final String EXTRA_FILTER = "filter";
	
	private Spinner mSpinnerImageSize;
	private Spinner mSpinnerColorFilter;
	private Spinner mSpinnerImageType;
	private EditText etSite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_filters);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		setupViews();
	}

	private void setupViews() {
		etSite = (EditText) findViewById(R.id.etSite);
		
		mSpinnerImageSize = (Spinner) findViewById(R.id.spinnerImaageSize);
		setupSpinners(mSpinnerImageSize, R.array.img_sizes);
		
		mSpinnerColorFilter = (Spinner) findViewById(R.id.spinnerColorFilter);
		setupSpinners(mSpinnerColorFilter, R.array.img_colors);
		
		mSpinnerImageType = (Spinner) findViewById(R.id.spinnerImageType);
		setupSpinners(mSpinnerImageType, R.array.img_types);
	}
	
	private void setupSpinners(Spinner spinner, int arrayResId) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayResId, 
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.search_filters, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_save) {
			
			final ImageFilter filter = new ImageFilter();
			filter.size = mSpinnerImageSize.getSelectedItem().toString();
			filter.color = mSpinnerColorFilter.getSelectedItem().toString();
			filter.type = mSpinnerImageType.getSelectedItem().toString();
			filter.site = etSite.getText().toString();
			
			final Intent data = new Intent();
			data.putExtra(EXTRA_FILTER, filter);
			setResult(Activity.RESULT_OK, data);
			finish();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
