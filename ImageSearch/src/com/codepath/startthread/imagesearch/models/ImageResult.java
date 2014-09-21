package com.codepath.startthread.imagesearch.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageResult implements Parcelable {
	public String url;
	public String thumbUrl;
	public String title;
	
	
	public static final Parcelable.Creator<ImageResult> CREATOR = new Creator<ImageResult>() {
		
		@Override
		public ImageResult[] newArray(int size) {
			return new ImageResult[size];
		}
		
		@Override
		public ImageResult createFromParcel(Parcel source) {
			return new ImageResult(source);
		}
	};
	
	public ImageResult(Parcel in) {
		url = in.readString();
		thumbUrl = in.readString();
		title = in.readString();
	}

	public ImageResult(JSONObject json) throws JSONException  {
		url = json.getString("url");
		thumbUrl = json.getString("tbUrl");
		title = json.getString("title");
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(url);
		dest.writeString(thumbUrl);
		dest.writeString(title);
	}
	
	public static List<ImageResult> fromJSONArray(JSONArray jsonArray) throws JSONException {
		final List<ImageResult> results = new ArrayList<ImageResult>();
		for (int i=0; i<jsonArray.length(); i++) {
			results.add(new ImageResult(jsonArray.getJSONObject(i)));
		}
		return results;
	}

}
