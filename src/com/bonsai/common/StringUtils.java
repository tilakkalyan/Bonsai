package com.bonsai.common;

public class StringUtils {

	public static boolean isEmpty(String strValue){
		return (strValue==null || strValue.trim().length()==0);
	}

	public static String formatCommaSeperated(String[] array){
		if(array==null || array.length==0)
			return "";

		StringBuilder builder = new StringBuilder();
		int length = array.length;
		for(int i=0; i< length ; i++){
			builder.append(array[i]);
			if(i<length-1)
				builder.append(",");
		}

		return builder.toString();
	}
}
