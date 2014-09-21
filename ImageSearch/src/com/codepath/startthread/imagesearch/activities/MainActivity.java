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
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.startthread.imagesearch.R;
import com.codepath.startthread.imagesearch.adapters.ImageResultsAdapter;
import com.codepath.startthread.imagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	private static final String QUERY_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
	private static final String PAGES_PARAM =  "&rsz=8";

	private EditText etQuery;
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
		etQuery = (EditText) findViewById(R.id.etQuery);
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

		// Search button click handler
		findViewById(R.id.btnSearch).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						final String query = etQuery.getText().toString();
						requestImages(query);
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
}
