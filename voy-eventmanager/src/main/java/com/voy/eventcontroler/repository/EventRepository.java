package com.voy.eventcontroler.repository;

import com.alo.base.model.Event;
import com.alo.base.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Bozo on 05/11/2015.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    //@Query(nativeQuery = true, value = "select event.* from event_inviteduser join event on event_inviteduser.id_event = event.id_event where id_inviteduser = :idUser")
    Collection<Event> findByInvitedUsersAndInactive(Person person, Boolean inactive);

    /**
     * Indica se o usuario convidado possui acesso ao evento
     * @param idEvent
     * @param idUser
     * @return
     */
    @Query(nativeQuery = true, value = "select (COUNT(*) > 0) from event_inviteduser where id_event = :idEvent and id_inviteduser = :idUser")
    Boolean haveEventAccess(@Param("idEvent") Long idEvent,@Param("idUser") Long idUser);
}
