package com.example.pocket.class_.chat.model;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomData {

    private String userName;
    private String roomNumber;

    public RoomData(String userName, String roomNumber) {
        this.userName = userName;
        this.roomNumber = roomNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", getUserName());
            jsonObject.put("roomNumber", getRoomNumber());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
