package com.bonsai.dao;

import android.database.Cursor;


public interface RowMapper<T> {

	public T mapRow(Cursor cursor);
}
