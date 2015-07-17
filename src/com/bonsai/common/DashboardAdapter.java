package com.bonsai.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.bonsai.R;

/**
 * Adapter to take care of listview
 * @author Tilak
 *
 */
public class DashboardAdapter extends ArrayAdapter<Item> implements Filterable{
	Context context;
	int layoutResourceId;
	List<Item> data = new ArrayList<Item>();
	private List<Item> filteredModelItemsArray;
	private ModelFilter filter;
	private String mode;

	public DashboardAdapter(Context context, int layoutResourceId,
			List<Item> data, String mode) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.filteredModelItemsArray = data;
		this.mode = mode;
	}

	@Override
	public int getCount() 
	{
		return data.size();
	}

	@Override
	public Item getItem(int position) 
	{
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public Filter getFilter() {
		if (filter == null){
			filter  = new ModelFilter();
		}
		return filter;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.scientificName);
			if("name".equals(mode))
				holder.txtDate = (TextView) row.findViewById(R.id.date);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		if(position % 2 ==0)
			row.setBackgroundColor(Color.WHITE);
		else
			row.setBackgroundColor(Color.parseColor("#d3d3d3"));
		Item item = data.get(position);
		holder.txtTitle.setText(item.getEnglishName());
		if("name".equals(mode))
			holder.txtDate.setText(item.getDate());
		holder.txtId = item.getId();
		return row;

	}



	static class RecordHolder {
		TextView txtTitle;
		TextView txtDate;
		long txtId;

	}

	private class ModelFilter extends Filter
	{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if(constraint != null && constraint.toString().length() > 0)
			{
				List<Item> filteredItems = new ArrayList<Item>();

				for(int i = 0, l = filteredModelItemsArray.size(); i < l; i++)
				{
					Item m = filteredModelItemsArray.get(i);
					if(m.getEnglishName().toLowerCase().contains(constraint.toString().toLowerCase()))
						filteredItems.add(m);
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			}
			else
			{
				synchronized(this)
				{
					result.values = filteredModelItemsArray;
					result.count = filteredModelItemsArray.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {

			data = (ArrayList<Item>) results.values;
			notifyDataSetChanged();

		}
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
}
