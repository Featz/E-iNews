package com.fregodeceves.einews;


import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.fregodeceves.einews.gnews.GoogleNews;
import com.fregodeceves.einews.gnews.GoogleNewsService;
import com.fregodeceves.einews.gnews.Result;
import com.google.common.collect.Lists;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;
 /**
 * Clase principal encargada de mostrar el listado de {@link
oticia}.
 *
 * @author Fernando R.
 * @version 0.2
 */
public final class MainActivity extends Activity implements OnItemClickListener {

	 PullToRefreshListView listView;
	 private NoticiaAdapter adapter;
 
	 @Override
	 protected void onCreate(final Bundle savedInstanceState) {
	
		 // Siempre inicializo
		 super.onCreate(savedInstanceState);
		 // Agrego el Layout
		 this.setContentView(R.layout.activity_main);
		 // ListView de noticias
		 this.listView = (PullToRefreshListView) this.findViewById(R.id.pull_to_refresh_listview);
		 // Construyo el adaptador de noticias
		 this.adapter = new NoticiaAdapter(this);
		 // Asigna el adaptador al ListActivity
		 this.listView.setAdapter(this.adapter);
		 // Listener de click
		 this.listView.setOnItemClickListener(this);
		 // Listener de refresh
		 this.listView.setOnRefreshListener(new OnRefreshListener() {   
			  @Override
			  public void onRefresh() {		 
				  MainActivity.this.refresh();	 
				  MainActivity.this.listView.onRefreshComplete();	
				  MainActivity.this.listView.setLockScrollWhileRefreshing(true);
			  }
		 });
	 
		  // Cargar las noticias desde el backend
		  final App app = App.getInstance(this);
		 
		  if (app != null) {
			  try {
			 
			  final List<Noticia> noticias = app.readNoticias(new
			 File(this.getCacheDir(), "noticias.json"));
			  //LOG.debug("Backend: {}", noticias);
			 
			  if (noticias != null) {
			  this.adapter.addAll(noticias);
			  }
			 
			  } catch (final IOException e) {
		 //LOG.error("Error al leer", e);
	  			}
		  }
	  }
 
  
  @Override
  public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
 
	  //LOG.trace("Position: {}. Id: {}", position, id);
	  //Toast.makeText(this, "Clicked row " + position, Toast.LENGTH_SHORT).show(); 
	  final Noticia noticia = this.adapter.getItem(position);	 
	  
	  //LOG.debug("Noticia: {}", noticia);
	 
	  final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
	  builder.setMessage("Url Noticia:" + noticia.getLink());
	  builder.show();
	 /* Intent myIntent = new Intent(getApplicationContext(), DialogActivity.class);
		Bundle bundle=new Bundle();
		
		//builder.setView(view)
		bundle.putString("titulo", noticia.getTitulo());
		bundle.putString("contenido",noticia.getContenido());
		bundle.putString("imagen",noticia.getImagen());
		
		myIntent.putExtras(bundle);
		//text.setText("sadasda");
		startActivity(myIntent);*/
	  
  	}  

    private void refresh() {    	
    	final App app = App.getInstance(this);
    	if (app == null) {
    		return;
    	}
         //Log.debug("Loading News ..");
    	final GoogleNewsService ns = app.getNewsService();
       	 /*
    	 * CallBack!
    	 */
	    final Callback<GoogleNews> callback = new Callback<GoogleNews>() {
	    	
	    	 /**
	    	 *
	    	 * @see retrofit.Callback#failure(retrofit.RetrofitError)
	    	 */
	    	 @Override
	    	 public void failure(final RetrofitError error) {
	    	// Log.error("Error: {}", error);
	    	 }	    	
	    	 /**
	    	 *
	    	 * @see retrofit.Callback#success(java.lang.Object, retrofit
	    	.client.Response)
	    	 */
	    	 @Override
		    public void success(final GoogleNews gnews, final Response response) {
		    	// Log.debug("GNews: {}", gnews);
		    	
		    	 final List<Noticia> news = Lists.newArrayList();		    	
		    	 // http://www.jsonschema2pojo.org/
		    	 for (final Result r : gnews.getResponseData().getResults()) {		    	
			    	 final Noticia noticia = new Noticia();
			    	 noticia.setAutor(r.getPublisher());
			    	 if (r.getImage() != null) {
			    	 noticia.setImagen(r.getImage().getUrl());		    	 
			    	 }
			    	 noticia.setId(r.getTitleNoFormatting().hashCode());
			    	 noticia.setTitulo(Html.fromHtml(r.getTitleNoFormatting()).toString());			
			    	 noticia.setResumen(Html.fromHtml(r.getContent()).toString());
			    	 noticia.setContenido(Html.fromHtml(r.getContent()).toString());
			    	 noticia.setFecha(r.getPublishedDate());
			    	 noticia.setLink(Html.fromHtml(r.getUrl()).toString());
			    	 news.add(noticia);		    	
		    	 }
		    	 MainActivity.this.adapter.addAll(news);		    	
		    	 // Write to sd
		    	 try {
		    		 app.saveNoticias(news, new File(MainActivity.this.getCacheDir(), "noticias.json"));
		    	 } catch (final IOException e) {		    	
		    	// LOG.error("Error: {}", e);
		    	 }
		    }	    	
	    };
	    // Ejecucion del metodo de busqueda de Noticias de GoogleNews.
    	ns.searchGoogleNews("Chile", callback);
    }
}
