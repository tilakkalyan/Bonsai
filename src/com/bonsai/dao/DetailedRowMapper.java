package com.bonsai.dao;

import com.bonsai.common.Item;

import android.database.Cursor;

public class DetailedRowMapper implements RowMapper<Item>{

	@Override
	public Item mapRow(Cursor cursor) {
		Item treeDetails = new Item();
		treeDetails.setId(cursor.getLong(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_ID)));
		treeDetails.setScientificName(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_SCIENTIFIC_NAME)));
		treeDetails.setDate(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_DATE)));
		treeDetails.setEnglishName(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_ENGLISH_NAME)));
		treeDetails.setSanskritRaaga(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_SANSKRIT_NAME)));
		treeDetails.setTeluguName(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_TELUGU_NAME)));
		treeDetails.setKannadaName(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_KANNADA_NAME)));
		treeDetails.setOrigin(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_ORIGIN)));
		treeDetails.setEnglishRaaga(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_ENGLISH_RAAGA)));
		treeDetails.setSanskritRaaga(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_SANSKRIT_RAAGA)));
		treeDetails.setRaasi(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_RAASI)));
		treeDetails.setRishi(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_RISHI)));
		treeDetails.setPlanet(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_PLANET)));
		treeDetails.setStar(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_STAR)));
		treeDetails.setStyle(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_STYLE)));
		
		return treeDetails;
	}

}
