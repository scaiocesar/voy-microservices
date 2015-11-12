package com.voy.eventcontroler.controller;

import com.alo.base.model.Event;
import com.alo.base.model.TicketSale;
import com.alo.base.model.User;
import com.voy.eventcontroler.repository.EventRepository;
import com.voy.eventcontroler.repository.TicketSaleRepository;
import com.voy.eventcontroler.viewbean.BasicTicketSaleViewBean;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Bozo on 09/11/2015.
 */
@RestController
public class TicketController {

    @Autowired
    TicketSaleRepository repository;

    @Autowired
    EventRepository eventRepository;

    /**
     * Retorna os ingressos vendidos com status de CONCLUIDO ou AGUARDANDO
     * que ainda não foram sincronizados
     * @param request
     * @return
     */
    @RequestMapping(value = "/syncTicketSold/{idEvent}",method = RequestMethod.GET)
    @Transactional
    public SyncSaledTicketReturn syncTicketSold(HttpServletRequest request, @PathVariable Long idEvent) throws ServletException{
        User user = (User)request.getAttribute("loggedUser");
        Event ev = new Event();
        ev.setIdEvent(idEvent);

        //Verifica se o usuario tem acesso no evento informado
        if(!checkAccess(idEvent,user.getIdPerson())){
            throw new ServletException("You have no access on this event!");
        }

        Collection<Object[]> listTicketSale = repository.listTicketNotExported(ev);

        Collection<BasicTicketSaleViewBean> listViewBean = new ArrayList<BasicTicketSaleViewBean>();
        List<String> barcodes = new ArrayList<String>();
        for (Object[] ts:listTicketSale){
            BasicTicketSaleViewBean tvb = new BasicTicketSaleViewBean();
            tvb.setBarcode((String)ts[0]);
            tvb.setName((String)ts[3]);
            tvb.setSaleDate((Date)ts[1]);
            tvb.setStatus((String)ts[2]);
            listViewBean.add(tvb);
            barcodes.add(tvb.getBarcode());
        }
        //Seta para exportado os ingressos
        if(barcodes.size() > 0) {
            repository.updateExportedTickets(barcodes);
        }

        SyncSaledTicketReturn rtr = new SyncSaledTicketReturn(listViewBean,listTicketSale.size());
        rtr.totalResults = repository.countAmountTicket(ev);
        return rtr;
    }

    /**
     * Retorna todos os ingressos CONCLUIDO ou CANCELADO limitado pelo
     * parametro "last". Os ingressos são ordenados pela data de venda do mais atual para o mais antigo.
     * @param request
     * @return
     */
    @RequestMapping(value = "/syncTicketSold/{idEvent}/all",method = RequestMethod.GET)
    @Transactional
    public SyncSaledTicketReturn syncTicketSold(HttpServletRequest request, @PathVariable Long idEvent, @RequestParam(value = "last", required = false) Integer last) throws ServletException{
        User user = (User)request.getAttribute("loggedUser");
        Event ev = new Event();
        ev.setIdEvent(idEvent);

        //Verifica se o usuario tem acesso no evento informado
        if(!checkAccess(idEvent,user.getIdPerson())){
            throw new ServletException("You have no access on this event!");
        }

        Collection<Object[]> listTicketSale = repository.listTicket(ev, last);

        Collection<BasicTicketSaleViewBean> listViewBean = new ArrayList<BasicTicketSaleViewBean>();
        List<String> barcodes = new ArrayList<String>();
        for (Object[] ts:listTicketSale){
            BasicTicketSaleViewBean tvb = new BasicTicketSaleViewBean();
            tvb.setBarcode((String)ts[0]);
            tvb.setName((String)ts[3]);
            tvb.setSaleDate((Date)ts[1]);
            tvb.setStatus((String)ts[2]);
            listViewBean.add(tvb);
            barcodes.add(tvb.getBarcode());
        }

        SyncSaledTicketReturn rtr = new SyncSaledTicketReturn(listViewBean,listTicketSale.size());
        rtr.totalResults = repository.countAmountTicket(ev);
        return rtr;
    }


    private Boolean checkAccess(Long idEvent, Long idUser){
        return eventRepository.haveEventAccess(idEvent,idUser);
    }


    private static class SyncSaledTicketReturn{
        public Collection<BasicTicketSaleViewBean> listViewBean;
        public int results;
        public int totalResults;
        public SyncSaledTicketReturn(Collection<BasicTicketSaleViewBean> listViewBean,int results){
            this.listViewBean = listViewBean;
            this.results = results;
        }

    }

}
