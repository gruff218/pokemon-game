import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;

public class Move {


    private String display;
    private String id;
    private int power;
    private int accuracy;
    private String type;
    private int pp;
    private boolean isMove;
    private String dmgClass;

    public Move() {
        this.display = "";
        this.id = "";
        this.power = 0;
        this.accuracy = 0;
        this.type = null;
        this.pp = 0;
        this.isMove = false;
        this.dmgClass = "";
    }

    public void setMove(String moveName) {
        /*this.display = "Water Gun";
        this.id = "water-gun";
        this.power = 40;
        this.accuracy = 100;
        this.pp = 25;
        this.isMove = true;
        this.dmgClass = "special";
        this.type = Pokemon.Type.WATER;*/
        System.out.println(moveName);
        System.out.println("https://pokeapi.co/api/v2/pokemon/Squirtle");
        try {
            URL obj = new URL("https://pokeapi.co/api/v2/move/" + moveName);
            System.out.println(obj);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                System.out.println("success");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Object o = JSONValue.parse(response.toString());
                JSONObject urlObject = (JSONObject)o;
                this.setAccuracy((Integer)urlObject.get("accuracy"));
                JSONObject dmgClassObject = (JSONObject)urlObject.get("damage_class");
                this.dmgClass = (String)dmgClassObject.get("name");
                this.pp = (Integer)urlObject.get("pp");
                this.power = (Integer)urlObject.get("power");
                JSONObject typeObject = (JSONObject)urlObject.get("type");
                String type = (String)typeObject.get("name");
            }
        } catch (IOException e) {
            System.out.println("sumtin went wrong brah");
        }
    }



    public boolean isMove() {
        return isMove;
    }

    public void setIsMove(boolean move) {
        isMove = move;
    }

    public String getDisplay() {
        if (this.isMove) {
            return display;
        } else {
            return "Empty";
        }
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Move{" +
                "display='" + display + '\'' +
                ", id='" + id + '\'' +
                ", power=" + power +
                ", accuracy=" + accuracy +
                ", type=" + type +
                ", pp=" + pp +
                ", isMove=" + isMove +
                ", dmgClass='" + dmgClass + '\'' +
                '}';
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }
}
