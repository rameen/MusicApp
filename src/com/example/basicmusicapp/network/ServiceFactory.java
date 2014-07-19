
package com.example.basicmusicapp.network;


import android.content.Context;


public class ServiceFactory
{


    public static ApiService getInstance(Context context, int i)
    {
        switch (i)
        {
        default:
            return null;

        case 1: // '\001'
            return new FetchArtistServiceImpl();
        }
    }
}
