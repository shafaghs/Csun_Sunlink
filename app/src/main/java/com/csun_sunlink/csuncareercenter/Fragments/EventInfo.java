package com.csun_sunlink.csuncareercenter.Fragments;

/**
 * Created by bigmatt76 on 11/24/16.
 */

public class EventInfo {
    private String eventTitle,eventId,eventDate,eventLocation,eventInfo;

    public EventInfo(String mEventId, String mEventTitle, String mEventDate, String mEventInfo, String mEventLocation) {
        this.eventTitle = mEventTitle;
        this.eventId = mEventId;
        this.eventDate = mEventDate;
        this.eventLocation = mEventLocation;
        this.eventInfo = mEventInfo;
    }

    String geteventId() {
        return eventId;
    }
    public void seteventId(String meventId) {
        this.eventId = meventId;
    }

    String geteventTitle() {
        return eventTitle;
    }
    public void seteventTitle(String meventTitle) {
        this.eventTitle = meventTitle;
    }

    String geteventDate() {
        return eventDate;
    }
    public void seteventDate(String meventDate) {
        this.eventDate = meventDate;
    }

    String geteventInfo() {
        return eventInfo;
    }
    public void seteventInfo(String meventInfo) {
        this.eventInfo = meventInfo;
    }


    String geteventLocation() {
        return eventLocation;
    }
    public void seteventLocation(String meventLocation) {
        this.eventLocation = meventLocation;
    }


}
