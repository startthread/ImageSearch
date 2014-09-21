package com.codepath.startthread.imagesearch.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.startthread.imagesearch.R;
import com.codepath.startthread.imagesearch.adapters.ImageResultsAdapter;
import com.codepath.startthread.imagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends SherlockFragmentActivity {
	
	private static final String TAG = "MainActivity";
	private static final String QUERY_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
	private static final String PAGES_PARAM =  "&rsz=8";
	
	private static final int REQUEST_FILTER = 1;

	private GridView gvResults;
	private List<ImageResult> imageResults;
	private ImageResultsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupViews();
		imageResults = new ArrayList<ImageResult>();
		mAdapter = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(mAdapter);
	}

	private void setupViews() {
		gvResults = (GridView) findViewById(R.id.gvResults);
		
		gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final ImageResult item = imageResults.get(position);
				final Intent intent = new Intent(MainActivity.this, ImageDisplayActivity.class);
				intent.putExtra(ImageDisplayActivity.EXTRA_IMAGE_RESULT, item);
				startActivity(intent);
			}
		});
	}
	
	private void requestImages(String search) {
		AsyncHttpClient client = new AsyncHttpClient();		
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=4
		final String url = QUERY_URL + search + PAGES_PARAM;
		client.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, 
					Throwable throwable) {

				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// responseData -> results[]
				try {
					JSONArray imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					//imageResults.addAll(ImageResult.fromJSONArray(imageResultsJSON));
					mAdapter.addAll(ImageResult.fromJSONArray(imageResultsJSON));
					//mAdapter.notifyDataSetChanged();
					
				} catch (JSONException e) {
					Log.e(TAG, "Error while parsing JSON result", e);
				}
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    
	    MenuItem searchItem = menu.findItem(R.id.action_search);	    
	    SearchView searchView = (SearchView) searchItem.getActionView();
	    searchView.setQueryHint(getResources().getString(R.string.image_search_hint));
	    //searchView.setIconifiedByDefault(false);
	    searchView.setOnQueryTextListener(new OnQueryTextListener() {
	       @Override
	       public boolean onQueryTextSubmit(String query) {
	    	   	requestImages(query);
	            return true;
	       }

	       @Override
	       public boolean onQueryTextChange(String newText) {
	           return false;
	       }
	   });
	    
	   return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_filter:
				final Intent intent = new Intent(this, SearchFiltersActivity.class);
				startActivityForResult(intent, REQUEST_FILTER);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
