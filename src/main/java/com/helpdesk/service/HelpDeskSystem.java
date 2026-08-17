package com.helpdesk.service;

import com.helpdesk.model.Customer;
import com.helpdesk.model.Priority;
import com.helpdesk.model.SupportAgent;
import com.helpdesk.model.Ticket;

import java.util.ArrayList;

public class HelpDeskSystem {

    // Stores customers, support agents, and tickets
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<SupportAgent> agents = new ArrayList<>();
    private ArrayList<Ticket> tickets = new ArrayList<>();


    // Adds a customer to the system
    public boolean addCustomer(Customer customer) {
        if (customer != null) {
            for (Customer c : customers) {
                if (customer.getEmail().equals(c.getEmail()) || customer.getPhoneNumber().equals(c.getPhoneNumber())) {
                    return false;
                }
            }
            return customers.add(customer);
        }
        return false;
    }

    // Adds an agent to the system
    public boolean addAgent(SupportAgent agent) {
        if (agent != null) {
            for (SupportAgent supportAgent : agents) {
                if (agent.getEmail().equals(supportAgent.getEmail()) || agent.getPhoneNumber().equals(supportAgent.getPhoneNumber())) {
                    return false;
                }
            }
            return agents.add(agent);
        }
        return false;
    }

    // Adds a ticket to the system
    public boolean addTicket(Ticket ticket) {
        if (ticket != null) {
            for (Ticket t : tickets) {
                if (ticket.getId() == t.getId()) {
                    return false;
                }
            }
            return tickets.add(ticket);
        }
        return false;
    }

    // Finds ticket by ID
    public Ticket findTicketById(int id) {
        for (Ticket t : tickets) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    // Prints all tickets from the list
    public void listTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets found");
            return;
        }
        for (Ticket t : tickets) {
            System.out.println(t);
        }
    }

    // Prints all agents in the system
    public void listAgents() {
        if (agents.isEmpty()) {
            System.out.println("No agents found");
            return;
        }
        for (SupportAgent agent : agents) {
            System.out.println(agent);
        }
    }

    // Lists all customers in the system
    public void listCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found");
            return;
        }
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    // Finds a customer by ID
    public Customer findCustomerById(int id) {
        for (Customer c : customers) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // Finds an agent by ID
    public SupportAgent findAgentById(int id) {
        for (SupportAgent s : agents) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    // Assigns an agent to a ticket
    public boolean assignAgentToTicket(int ticketId, int agentId) {
        SupportAgent agent = findAgentById(agentId);
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null && agent != null) {
            return ticket.assignAgent(agent);
        }
        return false;
    }

    // Unassigns an agent from a ticket
    public boolean unassignAgentFromTicket(int ticketId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            return ticket.unassignAgent();
        }
        return false;
    }

    // Reassigns an agent
    public boolean reassignAgentToTicket(int ticketId, int agentId) {

        Ticket ticket = findTicketById(ticketId);
        SupportAgent agent = findAgentById(agentId);
        if (ticket != null && agent != null) {
            return ticket.reassignAgent(agent);
        }
        return false;
    }

    // Reopens a ticket
    public boolean reopenTicket(int ticketId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            return ticket.reopenTicket();
        }
        return false;
    }

    // Closes a ticket
    public boolean closeTicket(int ticketId) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            return ticket.closeTicket();
        }
        return false;
    }

    // Changes a ticket's priority
    public boolean changeTicketPriority(int ticketId, Priority priority) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null && priority != null) {
            return ticket.changePriority(priority);
        }
        return false;
    }

    // Finds tickets by agent's ID
    public ArrayList<Ticket> findTicketsAssignedToAgent(int agentId) {

        ArrayList<Ticket> ticketsByAgentId = new ArrayList<>();

        SupportAgent agent = findAgentById(agentId);

        if(agent != null) {
            for (Ticket t : tickets) {
                if(t.getAgent() != null && t.isOpen() && t.getAgent().getId() == agentId) {
                        ticketsByAgentId.add(t);
                    }
                }
            }
        return ticketsByAgentId;

    }

    // Finds open tickets without an agent
    public ArrayList<Ticket> findTicketsWithoutAgent() {
        ArrayList<Ticket> ticketsWithoutAgent = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getAgent() == null && t.isOpen()) {
                ticketsWithoutAgent.add(t);
            }
        }
        return ticketsWithoutAgent;
    }
     // Finds tickets created by a specific customer
    public ArrayList<Ticket> findTicketsCreatedByCustomer(int customerId) {
        ArrayList<Ticket> ticketsByCustomerId = new ArrayList<>();

        Customer searchCustomer  =  findCustomerById(customerId);

        if(searchCustomer == null) {
            return ticketsByCustomerId;
        }

        for(Ticket t : tickets) {
            if(t.getCustomer().getId() == customerId) {
                ticketsByCustomerId.add(t);
            }
        }
        return ticketsByCustomerId ;
    }

    // Counts open tickets assigned to a specific agent
    public int countTicketsAssignedToAgent(int agentId) {
        return findTicketsAssignedToAgent(agentId).size();
    }

    // Finds an agent with the fewest open tickets
    public SupportAgent findAgentWithFewestOpenTickets() {
        int numberOfTickets = Integer.MAX_VALUE;
        SupportAgent agent = null;
        for (SupportAgent a : agents) {
            int ticketsCount = countTicketsAssignedToAgent(a.getId());
            if (ticketsCount < numberOfTickets) {
                numberOfTickets = ticketsCount;
                agent = a;
            }
        }
        return agent;
    }

    // Assigns a ticket to an agent with the fewest open tickets
    public boolean assignTicketToAgentWithFewestOpenTickets(int ticketId) {

        SupportAgent agent = findAgentWithFewestOpenTickets();
        if (agent == null) {
            return false;
        }

        return assignAgentToTicket(ticketId, agent.getId());
    }
    // Assigns all open tickets without an agent and counts successful assignments
    public int assignAllUnassignedOpenTickets() {

        ArrayList<Ticket> openTicketsWithoutAgent  = findTicketsWithoutAgent();
        int counter = 0;
        for(Ticket t : openTicketsWithoutAgent) {
            boolean assignSuccessful = assignTicketToAgentWithFewestOpenTickets(t.getId());
            if (assignSuccessful) {
                counter++;
            }
        }
        return counter;
    }

}
