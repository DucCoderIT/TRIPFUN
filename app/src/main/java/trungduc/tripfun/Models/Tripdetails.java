package trungduc.tripfun.Models;

import java.sql.Time;
import java.util.Date;

public class Tripdetails {
    public int tripID;
    public int userID;
    public String origin;
    public String destination;
    public Date date;
    public Time time;
    public String typevehicle;
    public String position;
    public int emptyseat;
    public int fullseat;
    public int seatprice;
    public String service;
    public String luggage;
    public String suggest;
    public String plan;
    public String wgender;



    public Tripdetails() {
    }

    public Tripdetails(int tripID, int userID, String origin, String destination, Date date, Time time, String typevehicle, String position, int emptyseat, int fullseat, int seatprice, String service, String luggage, String suggest, String plan, String wgender) {
        this.tripID = tripID;
        this.userID = userID;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.typevehicle = typevehicle;
        this.position = position;
        this.emptyseat = emptyseat;
        this.fullseat = fullseat;
        this.seatprice = seatprice;
        this.service = service;
        this.luggage = luggage;
        this.suggest = suggest;
        this.plan = plan;
        this.wgender = wgender;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getTypevehicle() {
        return typevehicle;
    }

    public void setTypevehicle(String typevehicle) {
        this.typevehicle = typevehicle;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getEmptyseat() {
        return emptyseat;
    }

    public void setEmptyseat(int emptyseat) {
        this.emptyseat = emptyseat;
    }

    public int getFullseat() {
        return fullseat;
    }

    public void setFullseat(int fullseat) {
        this.fullseat = fullseat;
    }

    public int getSeatprice() {
        return seatprice;
    }

    public void setSeatprice(int seatprice) {
        this.seatprice = seatprice;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getWgender() {
        return wgender;
    }

    public void setWgender(String wgender) {
        this.wgender = wgender;
    }

    @Override
    public String toString() {
        return tripID +
                ";\n" + origin +
                ";\n" + destination +
                ";\n" + date +
                ";\n" + time +
                ";\n" + typevehicle +
                ";\n" + position +
                ";\n" + emptyseat +
                ";\n" + fullseat +
                ";\n" + seatprice +
                ";\n" + service +
                ";\n" + luggage +
                ";\n" + suggest +
                ";\n" + plan +
                ";\n" + wgender;
    }
}
