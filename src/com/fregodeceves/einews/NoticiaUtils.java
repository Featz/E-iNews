package com.fregodeceves.einews;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import com.fregodeceves.einews.Noticia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NoticiaUtils {
	
	public void saveNoticias(final List<Noticia> noticias,final File destino) throws IOException{
		final Gson gson = new GsonBuilder().
				setPrettyPrinting().create();
		final String sNoticias = gson.toJson(noticias);
		FileUtils.writeStringToFile(destino, sNoticias);
	}

	@SuppressLint("NewApi") 
	public static File[] getExternalFileDir(final Context context){
		final int version = Build.VERSION.SDK_INT;
		
		if(version >= 19)
			return context.getExternalFilesDirs(null);
		if(version>=11)
			return new File[] {context.getExternalFilesDir(null)};
			
		return new File[] {};
	}
	
}
