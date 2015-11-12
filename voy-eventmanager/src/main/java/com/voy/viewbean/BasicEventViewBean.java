package com.voy.viewbean;

import java.util.Date;

/**
 * Created by Bozo on 06/11/2015.
 */
public class BasicEventViewBean {

    Long idEvent;
    String eventName;
    Date dateBegin;
    Date dateEnd;
    Date timeLimitValidation;



    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getTimeLimitValidation() {
        return timeLimitValidation;
    }

    public void setTimeLimitValidation(Date timeLimitValidation) {
        this.timeLimitValidation = timeLimitValidation;
    }
}
