package com.bonsai.activities;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bonsai.dao.BonsaiDAO;
import com.example.bonsai.R;

public abstract class BaseActivity extends Activity{

	protected BonsaiDAO datasource = null;
	protected static Map<String, String> titles = new HashMap<String, String>();
	static{
		titles.put(BonsaiDAO.COLUMN_NAME_ENGLISH_RAAGA, "By Raagas");
		titles.put(BonsaiDAO.COLUMN_NAME_STYLE, "By Style");
		titles.put(BonsaiDAO.COLUMN_NAME_PLANET, "By Planet");
		titles.put(BonsaiDAO.COLUMN_NAME_STAR, "By Star");
		titles.put(BonsaiDAO.COLUMN_NAME_RAASI, "By Raasi");
	}
	
	
	protected void openConnection(){
		datasource = new BonsaiDAO(this);
		try {
			datasource.openDataBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected BonsaiDAO getDao(){
		if(datasource==null)
			openConnection();
		return datasource;
	}
	
	protected void setTitle(String fromActivity){
		TextView activityTitle = (TextView) findViewById(R.id.activityTitle);
		activityTitle.setText(fromActivity);
	}
	
	protected void setBackNavigation(){
		findViewById(R.id.backImage).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ImageButton) v).setImageResource(R.drawable.back_press);
				BaseActivity.this.onBackPressed();
				
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_SETTINGS) {
			return false;
			}
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item= menu.findItem(R.id.action_settings);
	    item.setVisible(false);
	    return super.onPrepareOptionsMenu(menu);
	}
}
