package com.helpdesk;

import com.helpdesk.model.Customer;
import com.helpdesk.model.Priority;
import com.helpdesk.model.SupportAgent;
import com.helpdesk.model.Ticket;
import com.helpdesk.service.HelpDeskSystem;

public class Main {
    public static void main(String[] args) {
        System.out.println("Help Desk Ticketing System");

        System.out.println("-----------------------------");

        // Creating demo users
        Customer customer1 = new Customer("James", "Murray", "j.murray@gmail.com", "07775 443 344");
        SupportAgent agent1 = new SupportAgent("Callum", "Stewart", "c.stewart@yahoo.com", "07199 556 789");
        SupportAgent agent2 = new SupportAgent("Lewis", "Brown", "l.brown@yahoo.com", "07766 516 382");

        // Handling ticket creation and demo workflow
        try {
            // Creating demo tickets
            Ticket ticket1 = new Ticket(Priority.MEDIUM, customer1, "Printer problem", "The printer can't detect the Wi-Fi");
            Ticket ticket2 = new Ticket(Priority.HIGH, customer1, "Power critical issue", "The system is experiencing power losses");
            Ticket ticket3 = new Ticket(Priority.LOW, customer1, "Mail does not work", "The mail app is not responding");

            // Creating the help desk system
            HelpDeskSystem system = new HelpDeskSystem();

            // Registering users in the system
            addAllCustomers(system, customer1);
            addAllAgents(system, agent1, agent2);

            // Adding tickets to the system
            addAllTickets(system, ticket1, ticket2, ticket3);

            // Assigning agents to tickets
            system.assignAgentToTicket(ticket1.getId(), agent1.getId());
            system.assignAgentToTicket(ticket2.getId(), agent2.getId());

            // Starting ticket progress
            system.startTicketProgress(ticket1.getId());
            system.startTicketProgress(ticket2.getId());

            // Changing ticket priority
            system.changeTicketPriority(ticket1.getId(), Priority.LOW);

            // Reassigning the agent
            system.reassignAgentToTicket(ticket1.getId(), agent2.getId());

            // Closing the ticket
            system.closeTicket(ticket2.getId());

            // Reopening the ticket
            system.reopenTicket(ticket2.getId());

            // Printing the current tickets
            System.out.println(ticket1);
            System.out.println(ticket2);
            System.out.println(ticket3);

            System.out.println("-----------------------------");

            // Printing ticket history
            System.out.println("Ticket 1 history: \n" + ticket1.getFullHistory());
            System.out.println("Ticket 2 history: \n" + ticket2.getFullHistory());

        }

        // Handling invalid ticket arguments
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    // Adds all agents to the system
    private static void addAllAgents(HelpDeskSystem system, SupportAgent... agents) {
        for (SupportAgent agent : agents) {
            system.addAgent(agent);
        }
    }

    // Adds all tickets to the system
    private static void addAllTickets(HelpDeskSystem system, Ticket... tickets) {
        for (Ticket ticket : tickets) {
            system.addTicket(ticket);
        }
    }

    // Adds all customers to the system
    private static void addAllCustomers(HelpDeskSystem system, Customer... customers) {
        for (Customer customer : customers) {
            system.addCustomer(customer);
        }
    }
}
