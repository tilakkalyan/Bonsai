package com.bonsai.dao;

import android.database.Cursor;

import com.bonsai.common.Item;

public class SingleColumnMapper implements RowMapper<Item>{

	@Override
	public Item mapRow(Cursor cursor) {
		Item treeDetails = new Item();
		treeDetails.setEnglishName(cursor.getString(0));
		return treeDetails;
	}

}
