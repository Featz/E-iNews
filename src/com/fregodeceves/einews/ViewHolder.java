package com.fregodeceves.einews;

 import android.util.SparseArray;
 import android.view.View;

 /**
 * @author 
 */
 public class ViewHolder {
	
	 // I added a generic return type to reduce the casting noise in client code
	 @SuppressWarnings("unchecked")
	 public static <T extends View> T get(final View view, final int id) {
	
		 SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		 if (viewHolder == null) {
		 viewHolder = new SparseArray<View>();
		 view.setTag(viewHolder);
		 }
	
		 View childView = viewHolder.get(id);
		
		 if (childView == null) {
			 childView = view.findViewById(id);
			 viewHolder.put(id, childView);
		 }		
		 return (T) childView;
	 }
 }
