package NetworkManager;

/**
 * Created by Kelly on 11/21/2015.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class handle sending data to the server
 */
public class Networking {
    static final String baseURL = "http://dev.mateo.io/se/";
    static final String sendData = baseURL+"api/alga/store";

    public static class AsynTaskProductAdd extends AsyncTask<String,Void,Void> {




        @Override
        protected Void doInBackground(String... params) {
            String title = params[0];
            String quantity = params[1];
            String price = params[2];
            String urlParameters = "uuid=9999999999999999&total_chla=9.0&cyano_chla=9.0&sd_value=9.0&do_value=9.0&lux=9.0&pbot=9.0&depth=9.0&surtemp=9.0&bottemp=9.0&lat=9.0&long=9.0";

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
