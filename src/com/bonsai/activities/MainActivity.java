package com.bonsai.activities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;

import com.bonsai.common.DashboardAdapter;
import com.bonsai.common.Item;
import com.bonsai.common.SearchParams;
import com.example.bonsai.R;

public class MainActivity extends Activity {

	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	DashboardAdapter customGridAdapter;
	MyAdapter adapter = null;

	private static final String months[] = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"}; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// Inflate the menu; this adds items to the action bar if it is present.
		//		getMenuInflater().inflate(R.menu.main, menu);


		//set grid view item

		CopyAssets();
		GridView gridView = (GridView)findViewById(R.id.gridview);
		adapter = new MyAdapter(this);
		
		gridView.setAdapter(adapter);
		gridView.setNumColumns(2);


		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				com.bonsai.activities.MainActivity.MyAdapter.ItemLocal itemLocal = (com.bonsai.activities.MainActivity.MyAdapter.ItemLocal) adapter.getItem(position);
				handleDashboard(itemLocal.imageId);
			}
		});		
	}


	private void CopyAssets() {

		/*File file = new File(Environment.getExternalStorageDirectory() , "bonsai.db" );
		if (file.exists()) {
			return;
		}*/

		AssetManager assetManager = getAssets();

		InputStream in = null;
		OutputStream out = null;
		try {
			in = assetManager.open("Files/bonsai.db");   // if files resides inside the "Files" directory itself
			out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() +"/bonsai.db");
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch(Exception e) {
			Log.e("tag", e.getMessage());
		}
	}
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while((read = in.read(buffer)) != -1){
			out.write(buffer, 0, read);
		}
	}

	private class MyAdapter extends BaseAdapter
	{
		private List<ItemLocal> items = new ArrayList<ItemLocal>();
		private LayoutInflater inflater;

		public MyAdapter(Context context)
		{
			inflater = LayoutInflater.from(context);

			items.add(new ItemLocal("By Name", R.drawable.byname, "nameImage"));
			items.add(new ItemLocal("By Styles", R.drawable.bystyles, "styleImage"));
			items.add(new ItemLocal("By Ragas", R.drawable.byraaga, "raagaImage"));
			items.add(new ItemLocal("By Planets", R.drawable.byplanets, "planetImage"));
			items.add(new ItemLocal("By Stars", R.drawable.bystarts, "starImage"));
			items.add(new ItemLocal("By Raasi", R.drawable.byraasi, "raasiImage"));
			items.add(new ItemLocal("By Date", R.drawable.bydate, "dateImage"));
			items.add(new ItemLocal("By Info", R.drawable.info, "infoImage"));
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int i)
		{
			return items.get(i);
		}

		@Override
		public long getItemId(int i)
		{
			return items.get(i).drawableId;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup)
		{
			View v = view;
			ImageView picture;

			if(v == null) {
				v = inflater.inflate(R.layout.dashboard_item, viewGroup, false);
				v.setTag(R.id.picture, v.findViewById(R.id.picture));
			}

			picture = (ImageView)v.getTag(R.id.picture);
			ItemLocal item = (ItemLocal)getItem(i);

			picture.setImageResource(item.drawableId);
			picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
			return v;
		}

		private class ItemLocal
		{
			final int drawableId;
			final String imageId;
			final String imageName;
			ItemLocal(String name, int drawableId, String imageId)
			{
				this.imageName = name;
				this.drawableId = drawableId;
				this.imageId = imageId;
			}
		}
	}

	private void handleDashboard(String imageName){
		if("nameImage".equalsIgnoreCase(imageName)){
			Intent i = new Intent(getBaseContext(), TreeNameActivity.class);
			startActivity(i);
		}else

			if("raasiImage".equalsIgnoreCase(imageName)){				
				Intent i = new Intent(getBaseContext(), TreeByActivity.class);
				Bundle intentData = new Bundle();
//				intentData.put("params", SearchParams.byRaasi());
				i.putExtra("params", SearchParams.byRaasi());
				startActivity(i);
			}else

				if("planetImage".equalsIgnoreCase(imageName)){
					Intent i = new Intent(getBaseContext(), TreeByActivity.class);
					/*Bundle intentData = new Bundle();
					intentData.putString("params", DBHelper.COLUMN_NAME_PLANET);
					i.putExtras(intentData);*/
					i.putExtra("params", SearchParams.byPlanet());
					startActivity(i);
				}else

					if("starImage".equalsIgnoreCase(imageName)){
						Intent i=new Intent(getBaseContext(), TreeByActivity.class);
						/*Bundle intentData = new Bundle();
						intentData.putString("params", DBHelper.COLUMN_NAME_STAR);
						i.putExtras(intentData);*/
						i.putExtra("params", SearchParams.byStar());
						startActivity(i);
					}else

						if("raagaImage".equalsIgnoreCase(imageName)){
							Intent i = new Intent(getBaseContext(), TreeByActivity.class);
							/*Bundle intentData = new Bundle();
							intentData.putString("params", DBHelper.COLUMN_NAME_RAAGA);
							i.putExtras(intentData);*/
							i.putExtra("params", SearchParams.byRaaga());
							startActivity(i);
						}else

							if("styleImage".equalsIgnoreCase(imageName)){
								Intent i=new Intent(getBaseContext(), TreeByActivity.class);
								/*Bundle intentData = new Bundle();
								intentData.putString("params", DBHelper.COLUMN_NAME_STYLE);
								i.putExtras(intentData);*/
								i.putExtra("params", SearchParams.byStyle());
								startActivity(i);
							}else 
								if("infoImage".equalsIgnoreCase(imageName)){
									Uri uri = Uri.parse("http://www.sgsbonsai.org/");
									startActivity(new Intent(Intent.ACTION_VIEW, uri));
								}else{
									// Process to get Current Date
									final Calendar c = Calendar.getInstance();
									int mYear = c.get(Calendar.YEAR);
									int mMonth = c.get(Calendar.MONTH);
									int mDay = c.get(Calendar.DAY_OF_MONTH);

									// Launch Date Picker Dialog
									DatePickerDialog dpd = new DatePickerDialog(this,
											new DatePickerDialog.OnDateSetListener() {

										@Override
										public void onDateSet(DatePicker view, int year,
												int monthOfYear, int dayOfMonth) {
											Intent i=new Intent(getBaseContext(), TreeDetailsActivity.class);
											/*Bundle intentData = new Bundle();
											intentData.putString("params", DBHelper.COLUMN_NAME_DATE);
											intentData.putString("value", months[monthOfYear] + " " + dayOfMonth);
											i.putExtras(intentData);*/
											SearchParams params = SearchParams.byDate();
											params.setSearchValue1(months[monthOfYear] + " " + dayOfMonth);
											i.putExtra("params", params);
											startActivity(i);
										}
									}, mYear, mMonth, mDay);
									dpd.show();
								}

	}
}
