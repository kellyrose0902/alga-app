package edu.uwp.alga.NetworkManager;

/**
 * Created by Kelly on 11/21/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uwp.alga.utils.DataUtils;

/**
 * This class handle sending data to the server
 */
public class Networking {
    static final String baseURL = "http://alga.mateo.io/api";
    static final String sendData = baseURL+"/alga/store";

    public static class UploadDataTask extends AsyncTask<Context,Void,Void> {




        @Override
        protected Void doInBackground(Context... params) {
            Context context = params[0];

            String lat="";
            String longtitute="";
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager)params[0].getSystemService
                        (Context.LOCATION_SERVICE);

                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                lat = String.valueOf(lastLocation.getLatitude());
                longtitute = String.valueOf(lastLocation.getLongitude());
                Log.e("Networking",lat);
                Log.e("Networking",longtitute);
            }


            SharedPreferences InputDataLog = params[0].getSharedPreferences(DataUtils.mPreference,Context.MODE_PRIVATE);
            String urlParameters="";
            if(DataUtils.hasData(InputDataLog)){
                String uuid = Installation.id(params[0]);
                float p04 = InputDataLog.getFloat(DataUtils.PO, 0.0001f);
                float surtemp = InputDataLog.getFloat(DataUtils.TempSurface, 0f);
                float bottemp = InputDataLog.getFloat(DataUtils.TempBottom, 4f);
                float depth = InputDataLog.getFloat(DataUtils.LakeDepth, 0);
                float lux = InputDataLog.getFloat(DataUtils.lux,12000f);
                float total = InputDataLog.getFloat(DataUtils.DirectTotal, -1);
                float cyano = InputDataLog.getFloat(DataUtils.DirectCyano, -1);
                float oxygen = InputDataLog.getFloat(DataUtils.EstimateOxygen, -1);
                float secchi = InputDataLog.getFloat(DataUtils.EstimateSecchi, 0.1f);
                Log.e("Networking", uuid);
                urlParameters = "uuid="+ uuid+"&total_chla="+String.valueOf(total)+"&cyano_chla="+String.valueOf(cyano)+
                        "&sd_value="+String.valueOf(secchi)+"&do_value="+String.valueOf(oxygen)+"&lux="+String.valueOf(lux)
                        +"&pbot="+String.valueOf(p04)+"&depth="+String.valueOf(depth)+ "&surtemp="+String.valueOf(surtemp)+
                        "&bottemp="+String.valueOf(bottemp)+"&lat="+lat+"&long="+longtitute;
            }


            //String urlParameters = "uuid=9999999999999999&total_chla=9.0&cyano_chla=9.0&sd_value=9.0&do_value=9.0&lux=9.0&pbot=9.0&depth=9.0&surtemp=9.0&bottemp=9.0&lat=9.0&long=9.0";

            URL url;
            HttpURLConnection connection = null;
            try {
                //Create connection
                url = new URL(sendData);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream ());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                Log.e("Networking", "flag");
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }

                rd.close();
                Log.e("Networking",response.toString());


            } catch (Exception e) {

                e.printStackTrace();
                return null;

            } finally {

                if(connection != null) {
                    connection.disconnect();
                }
            }
            return null;

        }
    }

}
