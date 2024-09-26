package com.example.weatherapplication.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd

@Composable
fun GooogleAds()
{
    BannerAd()
}


@Composable
fun BannerAd()
{
    AndroidView(modifier = Modifier.fillMaxWidth(), factory = { context ->
        AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = "ca-app-pub-3940256099942544/9214589741"
            loadAd(AdRequest.Builder().build())
        }
    })
}
@Composable
fun InterstitialAd()
{

}

private fun loadAd(adStatus : (Boolean) -> Unit){

}
private fun showAd(){

}