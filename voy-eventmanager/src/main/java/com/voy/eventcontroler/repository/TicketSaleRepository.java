package com.voy.eventcontroler.repository;

import com.alo.base.model.Event;
import com.alo.base.model.Ticket;
import com.alo.base.model.TicketSale;
import org.jfree.chart.axis.Tick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by Bozo on 09/11/2015.
 */
public interface TicketSaleRepository extends JpaRepository<TicketSale, String> {

    String sqlListTicketNotExported = " select ticketsale.barcode, ticketsale.datetimetransaction, ticketsale.status, (select ticketName(ticket.id_ticket)) as tktname from ticketsale " +
            " left join ticket on id_ticket = fk_ticket " +
            " left join ticketType on id_ticketType = fk_type " +
            " left join ticketsector on id_ticketsector = fk_sector " +
            "  where ticketsale.fk_event =  :idEvent and ticketsale.status in ('CONCLUIDO','CANCELADO') and ticketsale.exported = false order by datetimetransaction";


    String sqlListTikect = " select ticketsale.barcode, ticketsale.datetimetransaction, ticketsale.status, (select ticketName(ticket.id_ticket)) as tktname from ticketsale " +
            " left join ticket on id_ticket = fk_ticket " +
            " left join ticketType on id_ticketType = fk_type " +
            " left join ticketsector on id_ticketsector = fk_sector " +
            "  where ticketsale.fk_event =  :idEvent and ticketsale.status in ('CONCLUIDO','CANCELADO')  order by datetimetransaction desc limit :lastAmount ";

    @Query(value = sqlListTicketNotExported, nativeQuery = true)
    Collection<Object[]> listTicketNotExported(@Param("idEvent") Event idEvent);

    /**
     * Retorna a quantidade de ingressos com status de Concluido e Cancelado
     * @param idEvent
     * @return
     */
    @Query(value = "select COUNT(*) from ticketsale where fk_event = :idEvent and status in ('CONCLUIDO','CANCELADO') ", nativeQuery = true)
    Integer countAmountTicket(@Param("idEvent") Event idEvent);

    /**
     * Retorna todos os ultimos X ingressos com status de Cancelado ou Concluido
     * independente se ja foi marcado ou nao a flag de exportado
     * @param idEvent
     * @param lastAmount
     * @return
     */
    @Query(value = sqlListTikect, nativeQuery = true)
    Collection<Object[]> listTicket(@Param("idEvent") Event idEvent, @Param("lastAmount") Integer lastAmount);

    @Modifying
    @Query(value = "UPDATE ticketsale SET exported = true where barcode IN (:barcodes)", nativeQuery = true)
    void updateExportedTickets(@Param("barcodes") List<String> barcodes);
}
