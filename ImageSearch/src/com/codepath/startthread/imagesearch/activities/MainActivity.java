package com.codepath.startthread.imagesearch.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.startthread.imagesearch.R;
import com.codepath.startthread.imagesearch.adapters.ImageResultsAdapter;
import com.codepath.startthread.imagesearch.custom.EndlessScrollListener;
import com.codepath.startthread.imagesearch.helpers.NetworkUtils;
import com.codepath.startthread.imagesearch.helpers.UiUtils;
import com.codepath.startthread.imagesearch.models.ImageFilter;
import com.codepath.startthread.imagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends SherlockFragmentActivity {
	
	private static final String TAG = "MainActivity";
	private static final String QUERY_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
	private static final int PAGE_ITEMS = 8;
	private static final String PAGES_PARAM =  "&rsz="+PAGE_ITEMS;
		
	private static final int REQUEST_FILTER = 1;

	private GridView gvResults;
	private List<ImageResult> imageResults;
	private ImageResultsAdapter mAdapter;
	private ImageFilter mFilter = new ImageFilter();
	private String mQuery;

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
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				requestImageSearch(page);
			}
		});
	}
	
	private String prepareUrl(int page) {
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=4
		final StringBuilder sb = new StringBuilder();
		sb.append(QUERY_URL).append(mQuery).append(PAGES_PARAM);
		
		if (!ImageFilter.DEFAULT_VALUE.equals(mFilter.size)) {
			sb.append("&imgsz=" + mFilter.size);
		}
		
		if (!ImageFilter.DEFAULT_VALUE.equals(mFilter.color)) {
			sb.append("&imgcolor=" + mFilter.color);
		}
		
		if (!ImageFilter.DEFAULT_VALUE.equals(mFilter.type)) {
			sb.append("&imgtype=" + mFilter.type);
		}
		
		if (!ImageFilter.DEFAULT_VALUE.equals(mFilter.site)) {
			sb.append("&as_sitesearch=" + mFilter.site);
		}
		
		final int pg = (page > 0 ? page -1 : 0);
		sb.append("&start=" + pg * PAGE_ITEMS);
		Log.v(TAG, "Loading page: " + pg);
		Log.v(TAG, sb.toString());
		return sb.toString();
	}
	
	private void requestImageSearch(int page) {
		AsyncHttpClient client = new AsyncHttpClient();		
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=4
		final String url = prepareUrl(page);
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, 
					Throwable throwable) {
				Log.e(TAG, "Image search request failed", throwable);
				if (!NetworkUtils.isNetworkAvailable(MainActivity.this)) {
					Toast.makeText(MainActivity.this, R.string.no_network_msg, Toast.LENGTH_SHORT).show();
				}
				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// responseData -> results[x]
				try {
					//Log.i(TAG, "result" + response.toString());					
					if (response.optJSONObject("responseData") != null) {
						JSONArray imageResultsJSON = response.getJSONObject(
								"responseData").getJSONArray("results");
						mAdapter.addAll(ImageResult
								.fromJSONArray(imageResultsJSON));
					}
					
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
	    final SearchView searchView = (SearchView) searchItem.getActionView();
	    searchView.setQueryHint(getResources().getString(R.string.image_search_hint));
	    //searchView.setIconifiedByDefault(false);
	    searchView.setOnQueryTextListener(new OnQueryTextListener() {
	       @Override
	       public boolean onQueryTextSubmit(String query) {
				if (!NetworkUtils.isNetworkAvailable(MainActivity.this)) {
					Toast.makeText(MainActivity.this, R.string.no_network_msg, Toast.LENGTH_SHORT).show();
					return true;
				}
	    	   mAdapter.clear();
	    	   UiUtils.hideSoftKeyboard(MainActivity.this, searchView);
	    	   mQuery = query;
	    	   requestImageSearch(0);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_FILTER) {
			if (resultCode == Activity.RESULT_OK) {
				mFilter = data.getParcelableExtra(SearchFiltersActivity.EXTRA_FILTER);
				requestImageSearch(0);
			}
		}
		
	}
}
