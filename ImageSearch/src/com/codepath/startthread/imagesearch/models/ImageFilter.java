package com.codepath.startthread.imagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageFilter implements Parcelable {
	public String size;
	public String color;
	public String type;
	public String site;

	public static final String DEFAULT_VALUE = "all";
	
	public ImageFilter() {
		size = DEFAULT_VALUE;
		color = DEFAULT_VALUE;
		type = DEFAULT_VALUE;
		site = DEFAULT_VALUE;
	}
	
	public ImageFilter(Parcel in) {
		size = in.readString();
		color = in.readString();
		type = in.readString();
		site = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(size);
		out.writeString(color);
		out.writeString(type);
		out.writeString(site);
	}

	public static final Parcelable.Creator<ImageFilter> CREATOR = new Parcelable.Creator<ImageFilter>() {
		public ImageFilter createFromParcel(Parcel in) {
			return new ImageFilter(in);
		}

		public ImageFilter[] newArray(int size) {
			return new ImageFilter[size];
		}
	};

}
