package com.fregodeceves.einews.gnews;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import com.fregodeceves.einews.gnews.GoogleNews;
 public interface GoogleNewsService {

 /**
 * Busqueda de noticias.
 *
 * @param query
 * @param callback
 */
 @GET("/ajax/services/search/news?v=1.0&hl=es&")
 void searchGoogleNews(@Query("q") String query, Callback<
GoogleNews> callback);

 }