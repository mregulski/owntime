package additionalInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Positioner {
    private final String urlPatch = "http://www.mpk.wroc.pl/position.php";
    private final String userAgent = "Chrome/20.0.1090.0";
    String postParameters;
    private String response="";

    Positioner(String line) {
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

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        this.setResponse(response.toString());
    }
    
    public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

    /**
     * shows position of all vehicles on 243 line
     */
    public static void main(String[] args) {
        Positioner pos = new Positioner("2");
        try {
            pos.sendPositionRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(pos.getResponse());
    }
}
