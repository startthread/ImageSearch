package com.codepath.startthread.imagesearch.custom;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.startthread.imagesearch.R;
import com.codepath.startthread.imagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageResultItemView extends LinearLayout {

	public ImageView thumbnailImageView;
	public TextView titleTextView;

	
	public ImageResultItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.item_image_result, this,
				true);
		setupChildren();
	}

	public static ImageResultItemView inflate(ViewGroup parent) {
		ImageResultItemView itemView = (ImageResultItemView) LayoutInflater
				.from(parent.getContext()).inflate(R.layout.item_view, parent,
						false);

		return itemView;
	}

	private void setupChildren() {
		thumbnailImageView = (ImageView) findViewById(R.id.ivImage);
		titleTextView = (TextView) findViewById(R.id.tvTitle);
	}

	public void setItem(ImageResult item) {
		titleTextView.setText(Html.fromHtml(item.title));

		// clear image from last time and download new one
		thumbnailImageView.setImageResource(0);
		Picasso.with(getContext()).load(item.thumbUrl).into(thumbnailImageView);
	}
}
