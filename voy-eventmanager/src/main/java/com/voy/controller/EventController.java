package com.voy.controller;

import com.alo.base.model.Event;
import com.alo.base.model.User;
import com.voy.repository.EventRepository;
import com.voy.viewbean.BasicEventViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Bozo on 05/11/2015.
 */
@RestController()
public class EventController {

    @Autowired
    EventRepository repository;

    @RequestMapping(value = "/listEventInvitedUser",method = RequestMethod.GET)
    public Collection<BasicEventViewBean>  listEventInvitedUser( HttpServletRequest request){
        User user = (User)request.getAttribute("loggedUser");
        Collection<Event>  events = repository.findByInvitedUsersAndInactive(user, false);
        Collection<BasicEventViewBean> basicEventViewBean = new ArrayList<BasicEventViewBean>();
        for (Event e : events){
            BasicEventViewBean b = new BasicEventViewBean();
            b.setEventName((e.getDescription1()+" "+e.getDescription2()).replace("null",""));
            b.setIdEvent(e.getIdEvent());
            b.setDateBegin(e.getDateBegin());
            b.setDateEnd(e.getDateEnd());
            b.setTimeLimitValidation(e.getTimeLimitValidation());

            basicEventViewBean.add(b);

        }
        return basicEventViewBean;


    }


    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public String info() {
        return "OK";
    }


}
