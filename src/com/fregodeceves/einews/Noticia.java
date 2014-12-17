package com.fregodeceves.einews;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ocpsoft.prettytime.PrettyTime;

import android.annotation.SuppressLint;

import com.google.common.collect.Lists;

public class Noticia {
	
	private long id;
	private String titulo;
	private String resumen;
	private String contenido;
	private final List<String> tag = Lists.newArrayList();
	private String fecha;
	private String imagen;
	private String autor;
	private String link;
	private int likes;
	
	public Noticia(){
		
	}

	public final long getId() {
		return this.id;
	}

	public final Noticia setId(final long id) {
		this.id = id;
		return this;
	}

	public final String getTitulo() {
		return this.titulo;
	}

	public final Noticia setTitulo(final String titulo) {
		this.titulo = titulo;
		return this;
	}

	public final String getResumen() {
		return this.resumen;
	}

	public final Noticia setResumen(final String resumen) {
		this.resumen = resumen;
		return this;
	}

	public final String getContenido() {
		return this.contenido;
	}

	public final Noticia setContenido(final String contenido) {
		this.contenido = contenido;
		return this;
	}

	public final String getFecha() {
		return this.fecha;
	}
	
	@SuppressLint("SimpleDateFormat")
	public final Noticia setFecha(final String fecha) {
		/*try {
			this.fecha = new SimpleDateFormat("EE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(fecha).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		this.fecha=fecha;
		return this;
	}

	public final String getImagen() {
		return this.imagen;
	}

	public final Noticia setImagen(final String imagen) {
		this.imagen = imagen;
		return this;
	}

	public final String getAutor() {
		return this.autor;
	}

	public final Noticia setAutor(final String autor) {
		this.autor = autor;
		return this;
	}

	public final String getLikes() {
		return "Likes: "+this.likes; 
	}

	public final Noticia setLikes(final int likes) {
		this.likes = likes;
		return this;
	}

	public final List<String> getTag() {
		return this.tag;
	}
	
	public String getTime(){
		//long fecha_actual=System.currentTimeMillis();
		//long res=(fecha_actual-this.fecha)/(1000 * 60);
		//SimpleDateFormat fecha = new SimpleDateFormat(this.fecha);
		/*String prettyTimeString = new PrettyTime().format(new Date(this.fecha));
		return prettyTimeString;*/
		/*PrettyTime p = new PrettyTime();
		return p.format(new Date(this.fecha));*/
		return this.fecha ;
	}
	
	@Override
	public final String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
