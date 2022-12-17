package ga.jsjyz.service;

import ga.jsjyz.pojo.Ticket;
import ga.jsjyz.util.Response;

import java.util.List;

public interface TicketService {
    Response saveTicket(Ticket ticket);

    Response getTicket();

    Response getTicketList(String state, String order);

    Response alterTicket(String id, String state);

    Response deleteTicket(List<Long> Tickets);
}
