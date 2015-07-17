package com.bonsai.dao;

import android.database.Cursor;

import com.bonsai.common.Item;
import com.bonsai.common.StringUtils;

public class SingleColumnMapper implements RowMapper<Item>{

	@Override
	public Item mapRow(Cursor cursor) {
		Item treeDetails = new Item();
		treeDetails.setId(cursor.getLong(0));
		treeDetails.setEnglishName(cursor.getString(1));
		if(StringUtils.isEmpty(treeDetails.getEnglishName()))
			return null;
		return treeDetails;
	}

}
