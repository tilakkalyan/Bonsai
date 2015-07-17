package com.bonsai.dao;

import com.bonsai.common.Item;
import com.bonsai.common.StringUtils;

import android.database.Cursor;

public class BriefRowMapper implements RowMapper<Item>{

	@Override
	public Item mapRow(Cursor cursor) {
		Item treeDetails = new Item();
		treeDetails.setEnglishName(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_ENGLISH_NAME)));
		if(StringUtils.isEmpty(treeDetails.getEnglishName()))
			return null;
		treeDetails.setScientificName(cursor.getString(2));
		treeDetails.setId(cursor.getLong(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_ID)));
		treeDetails.setDate(cursor.getString(cursor.getColumnIndex(BonsaiDAO.COLUMN_NAME_DATE)));
			return treeDetails;
	}

}
