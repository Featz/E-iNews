package com.fregodeceves.einews;

 import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
 //import org.slf4j.Logger;
 //import org.slf4j.LoggerFactory;

import retrofit.RestAdapter;
import android.app.Application;
import android.content.Context;

import com.fregodeceves.einews.gnews.GoogleNewsService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

 /**
 * Clase central de la aplicacion.
 *
 * @author F.Regodeceves
 * @version 1.0
 */
 public class App extends Application {
	
	 /**
	 * Logger
	 */
	 //static final Logger LOG = LoggerFactory.getLogger(App.class);
	
	 /**
	 * Servicio de noticias de Google.
	 */
	 private GoogleNewsService newsService;
	
	/**
	 * @see android.app.Application#onCreate()
	 */
	 @Override
	 public void onCreate() {	 
		 super.onCreate();	
		 // Construccion del adaptador
		final RestAdapter rest = new RestAdapter.Builder().
		setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint("http://ajax.googleapis.com").build();	
		this.newsService = rest.create(GoogleNewsService.class);
	 }
	
	 /**
	 * @return the NewsService.
	 */
	 public GoogleNewsService getNewsService() {
		 return this.newsService; 
	}
	
	 /**
	 * Metodo que escribe las noticias en el storage.
	 *
	 * @param noticias
	 * @param destino
	 * @throws IOException
	 */
	 public void saveNoticias(final List<Noticia> noticias, final File destino) throws IOException {
	 // Serializador GSON
		final Gson gson = new GsonBuilder().setPrettyPrinting().
		create();	
		 // List<Noticia> --> String
		final String sNoticias = gson.toJson(noticias);	
		 // String --> Storage
		FileUtils.writeStringToFile(destino, sNoticias);
	 }
	
	 /**
	 * @param origen
	 * @return the {@link List} of {@link Noticia}.
	 * @throws IOException
	 */
	 @SuppressWarnings("serial")
	 public List<Noticia> readNoticias(final File origen) throws IOException {
		 // File --> String
		 final String json = FileUtils.readFileToString(origen);
		 if (json == null) {
			 return null;
		 }
		 // String --> List<Noticias>
		 final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		 return gson.fromJson(json, new TypeToken<List<Noticia>>() { }.getType());
	 }
	
	 /**
	 * @param context
	 * @return the App.
	 */
	 public static App getInstance(final Context context) {
	
		 if (context == null) {
			 return null;
		 }	
		 final Context app = context.getApplicationContext();	
		 return app instanceof App ? (App) app : null;
	}
}
