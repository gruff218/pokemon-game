import org.apache.commons.text.WordUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import pokeapi.bittle.*;
import pokeapi.bittle.models.moves.PMove;


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

    public Move(String moveName) {
        this.setMove(moveName);
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
        PMove temp = PMove.getByName(moveName);
        StringBuilder sb = new StringBuilder();
        String[] arr = moveName.split("-");
        for (int i = 0; i < arr.length; i++) {
            sb.append(WordUtils.capitalize(arr[i]));
            if (i != arr.length - 1) {
                sb.append(" ");
            }
        }
        this.display = sb.toString();
        this.id = moveName;
        this.power = temp.getPower();
        this.pp = temp.getPp();
        this.accuracy = temp.getAccuracy();
        this.isMove = true;
        this.dmgClass = temp.getDamageClass().getName();
        this.type = temp.getType().getName();
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
