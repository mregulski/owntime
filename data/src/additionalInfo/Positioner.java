package additionalInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Positioner {
    private final String urlPatch = "http://pasazer.mpk.wroc.pl/jak-jezdzimy/mapa-pozycji-pojazdow";
    private final String userAgent = "Chrome/20.0.1090.0";
    String postParameters;
    private String response;

    Positioner(String line) {
        //TODO check if correct
        this.postParameters = "busList[bus][]=" + line;
    }

    private void sendPositionRequest() throws Exception {
        URL url = new URL(urlPatch);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", userAgent);

        connection.setDoOutput(true);
        DataOutputStream sender = new DataOutputStream(connection.getOutputStream());
        sender.writeBytes(postParameters);
        sender.flush();
        sender.close();

        int responseCode = connection.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //TODO filter response
        System.out.println(response.toString());
        //TODO convert data
    }

    public static void main(String[] args) {
        Positioner pos = new Positioner("4");
        try {
            pos.sendPositionRequest();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
