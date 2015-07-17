package com.bonsai.activities;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bonsai.common.Item;
import com.bonsai.common.SearchParams;
import com.bonsai.common.StringUtils;
import com.example.bonsai.R;

/**
 * Activity that deals with displaing tree details along with image
 * @author Tilak
 *
 */
public class TreeDetailsActivity extends BaseActivity{

	EditText searchTree = null;
	TextView scientificNameTextView = null;
	TextView englishNameTextView = null;
	TextView teluguNameTextView = null;
	TextView kannadaNameTextView = null;
	TextView sanskritRaagaTextView = null;
	TextView englishRaagaTextView = null;
	TextView dateTextView = null;
	TextView raasiTextView = null;
	TextView rishiTextView = null;

	TextView descriptionTxtView = null;
	ImageView detailsImage = null;
	String imageNameFormat = "image%d.JPG";
	Typeface kannadaFont, teluguFont;
	Configuration config = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tree_details);
		setBackNavigation();
		SearchParams params = (SearchParams) getIntent().getSerializableExtra("params");
		getDetails(params);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private Bitmap getBitmapFromAsset(String strName)
	{
		AssetManager assetManager = getAssets();
		InputStream istr = null;
		Bitmap bitmap = null;
		try {
			istr = assetManager.open(strName);
			if(istr!=null){
				bitmap = BitmapFactory.decodeStream(istr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public void getDetails(SearchParams params){
		openConnection();
		Item treeDetails = getDao().getTreeDetails(params);
		fillDetails(treeDetails);
	}

	@SuppressLint("NewApi")
	private void fillDetails(Item treeDetails) {
		setTitle(" Details");

		if(treeDetails==null){
			findViewById(R.id.treedetails).setVisibility(View.GONE);
			LinearLayout linearLayout =  (LinearLayout) findViewById(R.id.detailsLayout);
			TextView valueTV = new TextView(this);
			valueTV.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			valueTV.setText("No Data Found");
			valueTV.setGravity(Gravity.CENTER);
			valueTV.setTextAppearance(this, android.R.style.TextAppearance_Medium);
			linearLayout.addView(valueTV);
			return;

		}
		setTitle(treeDetails.getEnglishName());
		detailsImage = (ImageView) findViewById(R.id.picture);

		Bitmap image = getBitmapFromAsset(String.format(imageNameFormat, treeDetails.getId()));
		if(image!=null)
			detailsImage.setImageBitmap(image);
		else
			detailsImage.setVisibility(View.GONE);
		//			descriptionTxtView = (TextView) findViewById(R.id.description);
		if(!StringUtils.isEmpty(treeDetails.getScientificName())){
			((TextView) findViewById(R.id.scientificNameTextView)).setText(treeDetails.getScientificName());
			setTitle(treeDetails.getScientificName());
		}else{
			findViewById(R.id.scientificNameLayout).setVisibility(View.GONE);
			findViewById(R.id.scientificNameLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getEnglishName())){
			( (TextView) findViewById(R.id.englishNameTextView)).setText(treeDetails.getEnglishName());
			setTitle(treeDetails.getEnglishName());
		}else{
			findViewById(R.id.englishNameLayout).setVisibility(View.GONE);
			findViewById(R.id.englishNameLayoutHR).setVisibility(View.GONE);
		}


		if(!StringUtils.isEmpty(treeDetails.getTeluguName())){
			teluguNameTextView = ((TextView) findViewById(R.id.teluguNameTextView));
			teluguNameTextView.setText(treeDetails.getTeluguName());
		}else{
			findViewById(R.id.teluguNameLayout).setVisibility(View.GONE);
			findViewById(R.id.teluguNameLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getKannadaName())){
			kannadaNameTextView = ((TextView) findViewById(R.id.kannadaNameTextView));
			kannadaNameTextView.setText(treeDetails.getKannadaName());
		}else{
			findViewById(R.id.kannadaNameLayout).setVisibility(View.GONE);
			findViewById(R.id.kannadaNameLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getSanskritRaaga())){
			((TextView) findViewById(R.id.sanskritRaagaTextView)).setText(treeDetails.getSanskritRaaga());
		}else{
			findViewById(R.id.sanskritRaagaLayout).setVisibility(View.GONE);
			findViewById(R.id.sanskritRaagaLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getEnglishRaaga())){
			((TextView) findViewById(R.id.englishRaagaTextView)).setText(treeDetails.getEnglishRaaga());
		}else{
			findViewById(R.id.englishRaagaLayout).setVisibility(View.GONE);
			findViewById(R.id.englishRaagaLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getDate())){
			((TextView) findViewById(R.id.dateTextView)).setText(treeDetails.getDate());
		}else{
			findViewById(R.id.dateLayout).setVisibility(View.GONE);
			findViewById(R.id.dateLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getRaasi())){
			((TextView) findViewById(R.id.raasiTextView)).setText(treeDetails.getRaasi());
		}else{
			findViewById(R.id.raasiLayout).setVisibility(View.GONE);
			findViewById(R.id.raasiLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getRishi())){
			((TextView) findViewById(R.id.rishiTextView)).setText(treeDetails.getRishi());
		}else{
			findViewById(R.id.rishiLayout).setVisibility(View.GONE);
			findViewById(R.id.rishiLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getPlanet())){
			((TextView) findViewById(R.id.planetTextView)).setText(treeDetails.getPlanet());
		}else{
			findViewById(R.id.planetLayout).setVisibility(View.GONE);
			findViewById(R.id.planetLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getStar())){
			((TextView) findViewById(R.id.starTextView)).setText(treeDetails.getStar());
		}else{
			findViewById(R.id.starLayout).setVisibility(View.GONE);
			findViewById(R.id.starLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getStyle())){
			((TextView) findViewById(R.id.styleTextView)).setText(treeDetails.getStyle());
		}else{
			findViewById(R.id.styleLayout).setVisibility(View.GONE);
			findViewById(R.id.styleLayoutHR).setVisibility(View.GONE);
		}

		if(!StringUtils.isEmpty(treeDetails.getOrigin())){
			((TextView) findViewById(R.id.originTextView)).setText(treeDetails.getOrigin());
		}else{
			findViewById(R.id.originLayout).setVisibility(View.GONE);
		}
	}
}
