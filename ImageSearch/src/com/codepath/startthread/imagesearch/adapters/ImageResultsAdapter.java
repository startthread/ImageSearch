package com.codepath.startthread.imagesearch.adapters;

import java.util.List;

import com.codepath.startthread.imagesearch.helpers.UiUtils;
import com.codepath.startthread.imagesearch.models.ImageResult;

import com.codepath.startthread.imagesearch.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	private Point mDisplaySize;
	
	public ImageResultsAdapter(Context context, List<ImageResult> objects) {
		super(context, R.layout.item_image_result, objects);
		mDisplaySize = UiUtils.getDisplaySize(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ImageResult item = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
		}
		
		ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
		
		tvTitle.setText(Html.fromHtml(item.title));
		
		// clear image from last time and download new one
		ivImage.setImageResource(0);
		Picasso.with(getContext()).load(item.thumbUrl).into(ivImage);
		
		return convertView;
	}

	
}
