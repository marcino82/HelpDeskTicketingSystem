package com.helpdesk;

import com.helpdesk.model.Customer;
import com.helpdesk.model.Priority;
import com.helpdesk.model.SupportAgent;
import com.helpdesk.model.Ticket;
import com.helpdesk.service.HelpDeskSystem;

public class Main {
    public static void main(String[] args) {
        System.out.println("Help Desk Ticketing System");

        // Creating objects for testing
        Customer customer1 = new Customer("Marcin", "Tracz", "m.tracz@gmail.com", "07775 443 344");
        Customer customer2 = new Customer("Mariusz", "Gawron", "m.gawron@gmail.com", "07755 443 111");
        Customer customer3 = new Customer("Marianna", "Trąba", "m.trąba@gmail.com", "07735 443 341");
        SupportAgent agent1 = new SupportAgent("Tomasz", "Kawa", "t.kawa@yahoo.com", "07199 556 789");
        SupportAgent agent2 = new SupportAgent("Piotr", "Groniec", "p.groniec@yahoo.com", "07766 516 382");
        SupportAgent agent3 = new SupportAgent("Daniel", "Pierd", "d.pierd@yahoo.com", "07734 995 023");
        SupportAgent agent4 = new SupportAgent("Teresa", "Bąk", "t.bak@onet.pl", "07399 556 781");
        SupportAgent agent5 = new SupportAgent("Maria", "Raca", "m.raca@onet.pl", "07349 156 382");

        // Testing ticket creation with exception handling
        try {
            // Adding com.helpdesk.model.Ticket Objects
            Ticket ticket1 = new Ticket(Priority.MEDIUM, customer1, "Printer problem", "The printer can't detect the Wi-Fi");
            Ticket ticket2 = new Ticket(Priority.HIGH, customer1, "Power critical issue", "The system is experiencing power losses");
            Ticket ticket3 = new Ticket(Priority.LOW, customer1, "Mail not working", "The mail app is not responding");
            Ticket ticket4 = new Ticket(Priority.MEDIUM, customer2, "laptop problem", "Laptop sometimes doesn't start after pressing power button");
            Ticket ticket5 = new Ticket(Priority.HIGH, customer3, "Cybersecurity issue", "Received many Phishing emails");
            Ticket ticket6 = new Ticket(Priority.LOW, customer2, "Keyboard issue", "Some keys seem not working");

            Ticket ticket7 = new Ticket(Priority.MEDIUM, customer2, "laptop problem", "Laptop sometimes doesn't start after pressing power button");
            Ticket ticket8 = new Ticket(Priority.HIGH, customer1, "Cybersecurity issue", "Brute force attack detected");
            Ticket ticket9 = new Ticket(Priority.LOW, customer2, "Mouse issue", "Right click doesnt work");

            Ticket ticket10 = new Ticket(Priority.MEDIUM, customer2, "laptop problem", "Laptop doesn't hibernate after closing the lid");
            Ticket ticket11 = new Ticket(Priority.HIGH, customer3, "I got hacked!", "i am sure that someone too control over my device!Help!");
            Ticket ticket12 = new Ticket(Priority.MEDIUM, customer2, "Black screen", "My monitor's screen is pitch black and reset doesn't help");

            // Creating system1 for testing
            HelpDeskSystem system1 = new HelpDeskSystem();

            //  Printing customers, agents and tickets in the system1
            System.out.println("**************    System1 before adding...    ***************");
            system1.listCustomers();
            system1.listAgents();
            system1.listTickets();

            System.out.println();

            System.out.println("**************    System1 after adding...    ***************");

            // Adding all customers to the system
            addAllCustomers(system1, customer1, customer2, customer3);

            System.out.println("**********   Customers   **********");
            system1.listCustomers();

            // Adding all agents to the system1
            addAllAgents(system1, agent1, agent2, agent3, agent4, agent5);

            System.out.println("**********   Agents   **********");
            system1.listAgents();

            // Adding all tickets to the system
            addAllTickets(system1, ticket1, ticket2, ticket3, ticket4, ticket5, ticket6, ticket7, ticket8, ticket9, ticket10, ticket11, ticket12);

            System.out.println("**********   Tickets   **********");
            system1.listTickets();

            // Assigning agents to all open tickets without agents
            System.out.println("**********   Assigning agents to all open tickets   **********");

            system1.assignAllUnassignedOpenTickets();
            system1.listTickets();
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
    // ============================================================
// MANUAL TESTS COMPLETED SUCCESSFULLY
// ============================================================

// Object creation:
// - com.helpdesk.model.Customer object creation
// - com.helpdesk.model.SupportAgent object creation
// - com.helpdesk.model.Ticket object creation
// - com.helpdesk.model.Ticket constructor validation for null arguments
// - IllegalArgumentException handling for invalid ticket arguments

// com.helpdesk.model.User / inheritance:
// - com.helpdesk.model.User ID auto-incrementing
// - Shared com.helpdesk.model.User ID counter across com.helpdesk.model.Customer and com.helpdesk.model.SupportAgent
// - Inherited com.helpdesk.model.User.toString() for com.helpdesk.model.Customer
// - Inherited com.helpdesk.model.User.toString() for com.helpdesk.model.SupportAgent
// - com.helpdesk.model.User getters

// com.helpdesk.model.Ticket:
// - com.helpdesk.model.Ticket ID auto-incrementing
// - com.helpdesk.model.Ticket getters
// - com.helpdesk.model.Ticket.toString()
// - com.helpdesk.model.Ticket.toString() with an assigned agent
// - com.helpdesk.model.Ticket.toString() without an assigned agent
// - Safe display of Agent ID when no agent is assigned

// Agent assignment:
// - Assigning an agent to an open ticket
// - Preventing a second assignment when an agent is already assigned
// - Reassigning a ticket to another agent
// - Preventing reassignment to the same agent
// - Preventing reassignment when the ticket is closed
// - Reassigning an agent after reopening the ticket
// - Unassigning an agent from an open ticket
// - Preventing unassignment when no agent is assigned
// - Preventing unassignment from a closed ticket
// - Preserving the assigned agent when a ticket is closed

// com.helpdesk.model.Ticket status:
// - Closing an open ticket
// - Preventing an already closed ticket from being closed again
// - Reopening a closed ticket
// - Preventing an already open ticket from being reopened

// com.helpdesk.model.Priority:
// - Reading ticket priority
// - Changing ticket priority
// - Preventing a priority change to the same priority
// - Preventing a priority change when the ticket is closed

// com.helpdesk.service.HelpDeskSystem registration:
// - Adding a customer to the system
// - Adding a support agent to the system
// - Preventing the same customer from being added twice
// - Preventing the same agent from being added twice
// - Preventing duplicate customer email
// - Preventing duplicate customer phone number
// - Preventing duplicate agent email
// - Preventing duplicate agent phone number

// com.helpdesk.service.HelpDeskSystem tickets:
// - Adding a ticket to the system
// - Preventing the same ticket from being added twice
// - Adding multiple different tickets to the system
// - Listing tickets in the system
// - Listing tickets in an empty system

// com.helpdesk.service.HelpDeskSystem agents and customers:
// - Listing agents in the system
// - Listing agents in an empty system
// - Listing customers in the system
// - Finding a customer by ID
// - Finding an agent by ID
// - Finding a ticket by ID
// - Returning null when an object with the requested ID is not found

// System-level ticket operations:
// - Assigning an agent to a ticket by ticket ID and agent ID
// - Preventing assignment when the agent does not exist in the system
// - Unassigning an agent from a ticket by ticket ID
// - Reassigning an agent to a ticket by ticket ID and agent ID
// - Closing a ticket through com.helpdesk.service.HelpDeskSystem
// - Reopening a ticket through com.helpdesk.service.HelpDeskSystem
// - Changing ticket priority through com.helpdesk.service.HelpDeskSystem
// - Null-safe system-level ticket operations

// com.helpdesk.model.Ticket filtering and searching:
// - Finding open tickets assigned to a specific agent
// - Returning an empty list when an agent has no matching tickets
// - Returning an empty list when the requested agent does not exist
// - Finding open tickets without an assigned agent
// - Finding all tickets created by a specific customer
// - Returning an empty list when the requested customer does not exist

// com.helpdesk.model.Ticket counting and workload:
// - Counting open tickets assigned to a specific agent
// - Finding the agent with the fewest open tickets
// - Handling agents with zero open tickets
// - Returning null when no agents exist in the system
// - Handling equal workloads by keeping the first matching agent

// Architecture / consistency tests:
// - Confirming that objects may exist without being registered in com.helpdesk.service.HelpDeskSystem
// - Confirming that tickets can reference an agent object not registered in the system
//   when com.helpdesk.model.Ticket.assignAgent() is called directly
// - Confirming that com.helpdesk.service.HelpDeskSystem.assignAgentToTicket() prevents assigning
//   an agent that is not registered in that system
}
