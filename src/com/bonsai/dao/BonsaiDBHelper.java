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
import com.bonsai.common.StringUtils;


public class BonsaiDBHelper extends SQLiteOpenHelper{

	//The Android's default system path of your application database.
	private static String DB_PATH = null;

	private static String DB_NAME = "bonsai.db";

	private SQLiteDatabase myDataBase; 

	private static String TABLE_BONSAI = "Bonsai";

	private final Context myContext;

	public static final String COLUMN_NAME_RAASI = "rasi";
	public static final String COLUMN_NAME_STAR = "star";
	public static final String COLUMN_NAME_RAAGA = "english_raaga";
	public static final String COLUMN_NAME_DATE = "Date";

	private static List<Item> listOfRaasis = new ArrayList<Item>();
	private static List<Item> listOfStyles = new ArrayList<Item>();
	private static List<Item> listOfStars = new ArrayList<Item>();

	private String[] treeColumns = { "id", "scientific_name", "date"};
	private String[] detailsColumns = { "date", "raasi", "planet","start"};

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
	private String[] ALL_COLUMNS = { "id", "scientific_name", "date", "english_name", "sanskrit_name", "telugu_name", "kannada_name", "origin", "english_raaga", "sanskrit_raaga", "rasi", "rishi", "planet", "star", "style"};

	/**
	 * Constructor
	 * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	 * @param context
	 */
	public BonsaiDBHelper(Context context) {

		super(context, DB_NAME, null, 1);

		DB_PATH = Environment.getExternalStorageDirectory().toString() +"/";
		this.myContext = context;
	}	

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 * */
	public void createDataBase() throws IOException{

		boolean dbExist = checkDataBase();

		if(dbExist){
			//do nothing - database already exist
		}else{

			//By calling this method and empty database will be created into the default system path
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

			//database does't exist yet.

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
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if(myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public List<Item> getAllTrees() {
		List<Item> listOfTrees = new ArrayList<Item>();

		Cursor cursor = myDataBase.query(BonsaiDBHelper.TABLE_BONSAI,
				treeColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Item comment = cursorToItem(cursor);
			if(comment.getEnglishName()!=null && comment.getEnglishName().trim().length()>0)
				listOfTrees.add(comment);
			cursor.moveToNext();
		}

		// make sure to close the cursor
		cursor.close();

		Log.d("Tree size: " , "Tree Size: " + listOfTrees.size());
		return listOfTrees;
	}


	public List<Item> getColumnData(String columnName) {



		if(COLUMN_NAME_RAASI.equalsIgnoreCase(columnName)){
			if(listOfRaasis!=null && listOfRaasis.size()>0)
				return listOfRaasis;
		}else if(COLUMN_NAME_STYLE.equalsIgnoreCase(columnName)){
			if(listOfStyles!=null && listOfStyles.size()>0)
				return listOfStyles;
		}else if(COLUMN_NAME_STAR.equalsIgnoreCase(columnName)){
			if(listOfStars!=null && listOfStars.size()>0)
				return listOfStars;
		}

		List<Item> listOfData = new ArrayList<Item>(); 

		String selectQuery = "SELECT distinct "+columnName + " cData FROM Bonsai order by lower(cData)";

		Cursor cursor = myDataBase.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		String content = null;
		while (!cursor.isAfterLast()) {
			content = cursor.getString(0);
			if(content!=null && content.trim().length()>0){
				Item item = new Item();
				item.setEnglishName(content);
				listOfData.add(item);
			}
			cursor.moveToNext();
		}

		// make sure to close the cursor
		cursor.close();

		if(COLUMN_NAME_RAASI.equalsIgnoreCase(columnName)){
			listOfRaasis = listOfData;
		}else if(COLUMN_NAME_STYLE.equalsIgnoreCase(columnName)){
			listOfStyles = listOfData;
		}else if(COLUMN_NAME_STAR.equalsIgnoreCase(columnName)){
			listOfStars = listOfData;
		}

		return listOfData;
	}



	public Item getTreeDetailsByDate(String date) {

		String selectQuery = "SELECT "+ StringUtils.formatCommaSeperated(ALL_COLUMNS)  +" FROM Bonsai WHERE Date = '" + date +"'";
		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		Item treeDetails = fillDetails(cursor);
		cursor.close();

		return treeDetails;
	}

	private Item fillDetails(Cursor cursor){
		Item treeDetails = new Item();
		treeDetails.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID)));
		treeDetails.setScientificName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SCIENTIFIC_NAME)));
		treeDetails.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE)));
		treeDetails.setEnglishName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ENGLISH_NAME)));
		treeDetails.setSanskritRaaga(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SANSKRIT_NAME)));
		treeDetails.setTeluguName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TELUGU_NAME)));
		treeDetails.setKannadaName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_KANNADA_NAME)));
		treeDetails.setOrigin(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORIGIN)));
		treeDetails.setEnglishRaaga(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ENGLISH_RAAGA)));
		treeDetails.setSanskritRaaga(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SANSKRIT_RAAGA)));
		treeDetails.setRaasi(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_RAASI)));
		treeDetails.setRishi(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_RISHI)));
		treeDetails.setPlanet(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PLANET)));
		treeDetails.setStar(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STAR)));
		treeDetails.setStyle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STYLE)));

		return treeDetails;
	}

	public Item getTreeDetails(Long treeId) {

		String selectQuery = "SELECT "+ StringUtils.formatCommaSeperated(ALL_COLUMNS)  +" FROM Bonsai WHERE id = '" + treeId +"'";
		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		cursor.moveToFirst();

		Item treeDetails = fillDetails(cursor);

		cursor.close();

		return treeDetails;
	}


	private Item cursorToItem(Cursor cursor) {
		Item comment = new Item(cursor.getString(2), cursor.getString(1));
		comment.setId(cursor.getInt(0));
		return comment;
	}

	public List<Item> getData(String columnName, String value) {

		//		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		Cursor cursor = null;
		if(columnName!=null && value!=null && value.trim().length()>0){
			columnName = columnName.toLowerCase();
			String condition = columnName + " = ?";
			cursor = myDataBase.query(TABLE_BONSAI, new String[]{"id", "english_name", "scientific_name", "date"}, condition, new String[]{value},  null, null, null);
		}else{
			cursor = myDataBase.query(TABLE_BONSAI, new String[]{"id", "english_name", "scientific_name", "date"}, null, null, null, null, null);
		}


		List<Item> listOfTrees = new ArrayList<Item>(); 


		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Item comment = new Item();
			comment.setEnglishName(cursor.getString(1));
			comment.setScientificName(cursor.getString(2));
			comment.setId(cursor.getInt(0));
			comment.setDate(cursor.getString(3));
			if(comment.getEnglishName()!=null && comment.getEnglishName().trim().length()>0)
				listOfTrees.add(comment);
			cursor.moveToNext();
		}

		// make sure to close the cursor
		cursor.close();

		Log.d("Tree size: " , "Tree Size: " + listOfTrees.size());
		return listOfTrees;	
	}

}