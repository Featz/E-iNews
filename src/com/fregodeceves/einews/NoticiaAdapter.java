package com.fregodeceves.einews;

import java.util.Collection;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.squareup.picasso.Picasso;



 class NoticiaAdapter extends BaseAdapter {
	private boolean mBusy = false;
    private LayoutInflater mInflater;
	private final SortedMap<Long,Noticia> noticias = Maps.newTreeMap();
    public NoticiaAdapter(Context context) {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void addNoticias(List<Noticia> noticias){
    	for(Noticia n : noticias){
    		this.noticias.put(n.getId(),n);
    	}
    }
  
    /**
     * @see android.widget.Adapter#getCount()
     */
     @Override
     public int getCount() {
    	 return this.noticias.size();
     }
    
     /**
     * @see android.widget.Adapter#getItemId(int)
     */
     @Override
     public long getItemId(final int position) {
    	 return (Long) this.noticias.keySet().toArray()[position];
     }
    
     /**
     * Dada una posicion, obtiene la Noticia en esa posicion.
     *
     * @see android.widget.Adapter#getItem(int)
     */
     @Override
     public Noticia getItem(final int position) {
    
	     // Obtengo el identificador de la noticia que se encuentra en la position.
	     final Long id = (Long) this.noticias.keySet().toArray()[position];	    
	     // Retorno la noticia en esa posicion.
	     return this.noticias.get(id);
     }
    
     /**
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    /**
     * Make a view to hold each row.
     * 
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
   /* public View getView(int position, View convertView, ViewGroup parent) {
    	LinearLayout text;
        
        if (convertView == null) {
            text = (LinearLayout)mInflater.inflate(R.layout.row_noticia, parent, false);

            TextView titulo=(TextView)text.findViewById(R.id.rn_tv_titulo);
            titulo.setText(this.getItem(position).getTitulo());
            
            TextView resumen=(TextView)text.findViewById(R.id.rn_tv_resumen);
            resumen.setText(this.getItem(position).getResumen());
            
            TextView autor=(TextView)text.findViewById(R.id.textView5);
            autor.setText(this.getItem(position).getAutor());
            
            TextView fecha=(TextView)text.findViewById(R.id.rn_tv_time);
            fecha.setText(this.getItem(position).getTime());
            
            TextView likes=(TextView)text.findViewById(R.id.textView1);
            likes.setText(this.getItem(position).getLikes());
            
            ImageView image=(ImageView)text.findViewById(R.id.rn_iv_image);
            String url=this.getItem(position).getImagen();
            
            Picasso.with(text.getContext()).load(url).resize(64,64).into(image);
            
        } else {
            text = (LinearLayout)convertView;
        }

        if (!mBusy) {
            //text.set
            // Null tag means the view has the correct data
            text.setTag(null);
        } else {
            //text.setText("Loading...");
            // Non-null tag means the view still needs to load it's data
            text.setTag(this);
        }

        return text;
    }*/
     @Override
      public View getView(final int position, View convertView, final ViewGroup parent) {
     
      // Si es una fila no existente ..
    	 if (convertView == null) {     
    		 //LOG.debug("Inflating {}", position);
    		 convertView = this.mInflater.inflate(R.layout.row_noticia, parent, false);
    	 }      
	       // Obtengo la noticia en esa posicion
	       final Noticia noticia = this.getItem(position);      
	       // TODO: Agregar la URL de la imagen
	       // final ImageView image = ViewHolder.get(convertView, R.id.rn_iv_image);
	       final ImageView icon = ViewHolder.get(convertView, R.id.rn_tv_image);       
	       // Si la noticia tiene una imagen ..
	       if (!StringUtils.isEmpty(noticia.getImagen())) {      
		       // .. uso Picasso to load image
		       Picasso.with(icon.getContext()) // Contexto para obtener las imagenes
		       .load(noticia.getImagen()) // URL de la imagen
		       //.placeholder(R.drawable.com_facebook_profile_default_icon) // Imagen por defecto
		       .noFade() // Desabilitar la animacion de carga
		       .resize(64, 64) // Cambio el tamanio de la imagen
		       .centerCrop() // Si la imagen no es cuadrada la redimensiono
		       .into(icon); // Donde se deja la imagen      
	       } else {
	       // .. no hay imagen, uso una por defecto.
	      // icon.setImageResource(R.drawable.com_facebook_profile_default_icon);
	       }
      
	       final TextView titulo = ViewHolder.get(convertView, R.id.rn_tv_titulo);
	       titulo.setText(noticia.getTitulo());	      
	       final TextView time = ViewHolder.get(convertView, R.id.rn_tv_time);
		   time.setText(noticia.getTime());
	       final TextView resumen = ViewHolder.get(convertView, R.id.rn_tv_resumen);
	       resumen.setText(noticia.getResumen());
	       final TextView autor=ViewHolder.get(convertView,R.id.textView5);
	       autor.setText("Fuente: "+noticia.getAutor());	 
	       final TextView likes=ViewHolder.get(convertView,R.id.rn_tv_likes);
	       likes.setText(noticia.getLikes());
	       return convertView;
       }
     
      // La inflo!
    /* Metodo para agregar una coleccion de noticias al adaptador.
     *
     * @param noticias
     */
	public void addAll(final Collection<Noticia> noticias) {	    
	     // Verificacion de nulidad
	     Preconditions.checkNotNull(noticias, "Noticias a agregar es null");	    
	     int counter = 0;	    
	     for (final Noticia n : noticias) {	    
		     // Agrego la noticia como la tupla (id, noticia)
		     // Si existiera ya una noticia con ese id no se ingresa.
		     if (this.noticias.put(n.getId(), n) == null) {
		    	 counter++;
		     }
	     }	    
	     //  LOG.info("Se han agregado {} noticias.", counter);	    
	     // Notifico que se realizo algun cambio
	     if (counter != 0) {
	    	 this.notifyDataSetChanged();
	     }        
	}
     
}

