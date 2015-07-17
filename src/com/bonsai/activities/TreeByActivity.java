package com.bonsai.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bonsai.common.DashboardAdapter;
import com.bonsai.common.Item;
import com.bonsai.common.SearchParams;
import com.bonsai.dao.BonsaiDAO;
import com.bonsai.dao.BonsaiDBHelper;
import com.example.bonsai.R;

public class TreeByActivity extends BaseActivity{

	ListView listView;
	DashboardAdapter customGridAdapter;
	ImageView searchIcon = null;
	EditText searchTree = null;
	static List<Item> listOfRaasis = new ArrayList<Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.byraasi);
		setBackNavigation();
//		openConnection();
		final SearchParams parentParams = (SearchParams) getIntent().getSerializableExtra("params");
		listOfRaasis = getDao().getColumnData(parentParams.getSearchColumn());

		int listSize = 0;
		if(listOfRaasis!=null)
			listSize = listOfRaasis.size();

		setTitle(titles.get(parentParams.getSearchColumn()) + "("+ listSize + ")");

		customGridAdapter = new DashboardAdapter(this, R.layout.name_row, listOfRaasis,null);
		
		
		searchTree = (EditText) findViewById(R.id.searchTree);
		searchTree.setVisibility(View.GONE);

		if(listSize>0){

			searchIcon = (ImageView) findViewById(R.id.search);

			searchIcon.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					searchTree.setVisibility(View.VISIBLE);
					findViewById(R.id.activityTitle).setVisibility(View.GONE);
				}
			});

		}else{
			searchIcon.setVisibility(View.GONE);
		}
		searchTree.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.setFocusable(true);
				v.setFocusableInTouchMode(true);
				return false;
			}
		});

		searchTree.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				TreeByActivity.this.customGridAdapter.getFilter().filter(cs);   
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});


		
		listView = (ListView) findViewById(R.id.namelist);
		listView.setAdapter(customGridAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent parentIntent = getIntent();
				Intent intent = new Intent(TreeByActivity.this, TreeNameActivity.class);
				Item item = customGridAdapter.getItem(position);
				/*Bundle extras = parentIntent.getExtras();
				extras.putString("value", item.getEnglishName());
				intent.putExtras(extras);*/
				if(BonsaiDBHelper.COLUMN_NAME_RAASI.equalsIgnoreCase(parentParams.getSearchColumn()) || 
						BonsaiDBHelper.COLUMN_NAME_STAR.equalsIgnoreCase(parentParams.getSearchColumn()))
					parentParams.setSearchValue1(String.valueOf(item.getId()));
				else
					parentParams.setSearchValue1(String.valueOf(item.getEnglishName()));
				parentParams.setPageValue(item.getEnglishName());
				intent.putExtra("params", parentParams);
				startActivity(intent);
			}
		});

		searchIcon = (ImageView) findViewById(R.id.search);
		searchTree = (EditText) findViewById(R.id.searchTree);
		searchTree.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static void main(String[] args) throws JSONException, FileNotFoundException {

		File f = new File("D:\\Work\\Bonsai App\\telugu_json.json");
		FileInputStream fis = new FileInputStream(f);


		String jsonData = "%s - %s - %s - %s - %s - %s - %s - %s - %s - %s - %s - %s - %s - %s - %s - %s"; 

		String text = new Scanner(f).useDelimiter("\\A").next();
		JSONObject jsonObj = new JSONObject(text);

		// Getting JSON Array node
		JSONArray contacts = jsonObj.getJSONArray("");

		// looping through All Contacts
		for (int i = 0; i < contacts.length(); i++) {
			JSONObject c = contacts.getJSONObject(i);

			String id = c.getString("id");
			String name = c.getString("scientific_name");
			String email = c.getString("date");
			String address = c.getString("englist_name");
			String gender = c.getString("sanskrit_name");

			// Phone node is JSON Object
			String mobile = c.getString("telugu_name");
			String home = c.getString("kannada_name");
			String office = c.getString("origin");
			String english_raaga = c.getString("english_raaga");
			String sanskrit_raaga = c.getString("sanskrit_raaga");
			String rasi = c.getString("rasi");
			String rishi = c.getString("rishi");
			String planet = c.getString("planet");
			String star = c.getString("star");
			String year = c.getString("year");
			String style = c.getString("style");

			System.out.println(String.format(jsonData, new String[]{id, name, email, address, gender, mobile, home, office, english_raaga, sanskrit_raaga,
					rasi, rishi, planet, star, year, style}));
		}
	}
}
