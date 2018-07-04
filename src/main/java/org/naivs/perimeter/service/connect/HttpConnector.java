package org.naivs.perimeter.service.connect;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class HttpConnector {

    private HttpURLConnection con;

    public String get(String deviceUrl, int port, Map<String, String> parameters) {

        try {
            URL url = new URL("http", deviceUrl, port, "");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);

            StringBuilder params = new StringBuilder();
            params.append("?");
            if (parameters != null) parameters.forEach((name, value) -> params.append(name + "=" + value + "&"));

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(params.toString());
            out.flush();
            out.close();

            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();

            String line;
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            return response.toString();
        } catch (MalformedURLException | ProtocolException e) {
            return "error";
        } catch (IOException e) {
            return "error";
        } finally {
            if (con != null)
                con.disconnect();
        }
    }
}
