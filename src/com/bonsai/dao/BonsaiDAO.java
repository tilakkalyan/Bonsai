package com.bonsai.dao;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.bonsai.common.Item;
import com.bonsai.common.SearchParams;


public class BonsaiDAO extends SQLiteOpenHelper{

	//The Android's default system path of your application database.
	private static String DB_PATH = null;

	private static String DB_NAME = "bonsai.db";

	private static SQLiteDatabase bonsaiDB; 

	public static String TABLE_BONSAI = "Bonsai";

	private final Context myContext;

	public static final String COLUMN_NAME_RAASI = "rasi";
	public static final String COLUMN_NAME_STAR = "star";
	public static final String COLUMN_NAME_RAAGA = "english_raaga";
	public static final String COLUMN_NAME_DATE = "Date";


	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_SCIENTIFIC_NAME = "scientific_name";
	public static final String COLUMN_NAME_ENGLISH_NAME = "english_name";
	public static final String COLUMN_NAME_SANSKRIT_NAME = "sanskrit_name";
	public static final String COLUMN_NAME_TELUGU_NAME = "telugu_name";
	public static final String COLUMN_NAME_KANNADA_NAME = "kannada_name";
	public static final String COLUMN_NAME_ORIGIN = "origin";
	public static final String COLUMN_NAME_ENGLISH_RAAGA = "english_raaga";
	public static final String COLUMN_NAME_SANSKRIT_RAAGA = "sanskrit_raaga";
	public static final String COLUMN_NAME_RASI = "rasi";
	public static final String COLUMN_NAME_RISHI = "rishi";
	public static final String COLUMN_NAME_PLANET = "planet";
	public static final String COLUMN_NAME_START = "star";
	public static final String COLUMN_NAME_STYLE = "style";
	public static String[] ALL_COLUMNS = { "id", "scientific_name", "date", "english_name", "sanskrit_name", "telugu_name", "kannada_name", "origin", "english_raaga", "sanskrit_raaga", "rasi", "rishi", "planet", "star", "style"};
	public static String[] BRIEF_COLUMNS = {COLUMN_NAME_ID, COLUMN_NAME_ENGLISH_NAME, COLUMN_NAME_SCIENTIFIC_NAME, COLUMN_NAME_DATE.toLowerCase()};

	public final static String RAASI_SELECT_SQL = "select rasi cData  from raasi";
	public final static String STARS_SELECT_SQL = "select star cData  from stars";

	private static List<Item> listOfRaasis = new ArrayList<Item>();
	private static List<Item> listOfStars = new ArrayList<Item>();

	/**
	 * Constructor
	 * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	 * @param context
	 */
	public BonsaiDAO(Context context) {

		super(context, DB_NAME, null, 1);

		DB_PATH = Environment.getExternalStorageDirectory().toString() +"/";
		this.myContext = context;
	}	

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 * */
	public void createDataBase() throws IOException{

		boolean dbExist = checkDataBase();

		if(!dbExist){
			//By calling this method an empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase(){

		SQLiteDatabase checkDB = null;

		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		}catch(SQLiteException e){
			e.printStackTrace();
		}

		if(checkDB != null){
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException{

		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;
		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException{

		//Open the database
		String myPath = DB_PATH + DB_NAME;
		bonsaiDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

	}
	
	public static SQLiteDatabase getConnection(){
		return bonsaiDB;
	}

	@Override
	public synchronized void close() {

		if(bonsaiDB != null)
			bonsaiDB.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	private List<Item> getRaasis(){

		if(listOfRaasis!=null && listOfRaasis.size()>0)
			return listOfRaasis;

		Cursor cursor = bonsaiDB.rawQuery(BonsaiDAO.RAASI_SELECT_SQL, null);
		listOfRaasis = getItems(new SingleColumnMapper(), cursor);
		return listOfRaasis;
	}

	private List<Item> getStars(){

		if(listOfStars!=null && listOfStars.size()>0)
			return listOfStars;

		Cursor cursor = bonsaiDB.rawQuery(BonsaiDAO.STARS_SELECT_SQL, null);
		listOfStars = getItems(new SingleColumnMapper(), cursor);
		return listOfStars;
	}

	/**
	 * Get stars/raasi/styles/planet/raags
	 * @param columnName
	 * @return
	 */

	public List<Item> getColumnData(String columnName) {

		if(BonsaiDAO.COLUMN_NAME_RAASI.equalsIgnoreCase(columnName)){
			return getRaasis();
		}else if(BonsaiDAO.COLUMN_NAME_STAR.equalsIgnoreCase(columnName)){
			return getStars();
		}

		String selectQuery = "select distinct " + columnName + " cData  from Bonsai  order by  lower(cData)";
		Cursor cursor = bonsaiDB.rawQuery(selectQuery, null);
		return getItems(new SingleColumnMapper(), cursor);
	}


	public Item getTreeDetails(SearchParams params) {

		Cursor cursor = bonsaiDB.query(BonsaiDAO.TABLE_BONSAI, BonsaiDAO.ALL_COLUMNS, condition(params), new String[]{params.getSearchValue1()},  null, null, null);
		cursor.moveToFirst();

		Item treeDetails = mapRow(new DetailedRowMapper(), cursor);
		cursor.close();
		return treeDetails;
	}
	public List<Item> search(SearchParams searchParameters) {

		Cursor cursor = null;
		String searchParam[] = null;
		if(searchParameters.getSearchValue1()!=null)
			searchParam = new String[]{searchParameters.getSearchValue1()};

		cursor = bonsaiDB.query(BonsaiDAO.TABLE_BONSAI, BonsaiDAO.BRIEF_COLUMNS, condition(searchParameters), searchParam,  null, null, null);
		List<Item> listOfTrees = getItems(new BriefRowMapper(), cursor); 
		Log.d("Tree size: " , "Tree Size: " + listOfTrees.size());
		return listOfTrees;	
	}

	private String condition(SearchParams params){
		if(params.getSearchColumn()==null || params.getSearchValue1()==null)
			return null;

		return params.getSearchColumn().toLowerCase() + " = ?";
	}

	private Item mapRow(RowMapper<Item> t, Cursor cursor){
		return t.mapRow(cursor);
	}
	
	private List<Item> getItems(RowMapper<Item> t, Cursor cursor){
		List<Item> listOfData = new ArrayList<Item>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Item item = mapRow(t, cursor);
			if(item!=null ){
				listOfData.add(item);
			}
			cursor.moveToNext();
		}
		cursor.close();
		return listOfData;
	}

}