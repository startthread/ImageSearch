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

	public ImageResultsAdapter(Context context, List<ImageResult> objects) {
		super(context, R.layout.item_image_result, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
//		ImageResultItemView itemView = (ImageResultItemView) convertView;
//		if (itemView == null) {
//			itemView = ImageResultItemView.inflate(parent);
//		}
//		
//		itemView.setItem(getItem(position));		
//		
		final ImageResult item = getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.ivImage);
			holder.textView = (TextView) convertView.findViewById(R.id.tvTitle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.textView.setText(Html.fromHtml(item.title));
		
		// clear image from last time and download new one
		holder.imageView.setImageResource(0);
		Picasso.with(getContext()).load(item.thumbUrl).into(holder.imageView);
		
		return convertView;
	}

	public static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
	}
}
