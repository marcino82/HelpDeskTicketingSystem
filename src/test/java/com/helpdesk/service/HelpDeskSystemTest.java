package com.helpdesk.service;

import com.helpdesk.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HelpDeskSystemTest {

    private HelpDeskSystem system;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private SupportAgent agent1;
    private SupportAgent agent2;
    private SupportAgent agent3;
    private SupportAgent agent4;
    private Ticket ticket1;
    private Ticket ticket2;
    private Ticket ticket3;
    private Ticket ticket4;

    @BeforeEach
    public void setUp() {
        system = new HelpDeskSystem();
        customer1 = new Customer("James", "Murray", "j.murray@gmail.com", "07775 443 344");
        customer2 = new Customer("Daniel", "Wilson", "j.murray@gmail.com", "07755 443 111");                                            // duplicate email
        customer3 = new Customer("Emma", "Fraser", "e.fraser@gmail.com", "07775 443 344");                                              // duplicate phone
        customer4 = new Customer("Sophie", "Campbell", "s.campbell@gmail.com", "07075 443 304");

        agent1 = new SupportAgent("Callum", "Stewart", "c.stewart@yahoo.com", "07199 556 789");
        agent2 = new SupportAgent("Lewis", "Brown", "c.stewart@yahoo.com", "07766 516 382");                                            // duplicate email
        agent3 = new SupportAgent("Isla", "MacDonald", "i.macdonald@outlook.com", "07199 556 789");                                     // duplicate phone
        agent4 = new SupportAgent("Emily", "Robertson", "e.robertson@outlook.com", "07349 156 382");
        ticket1 = new Ticket(Priority.LOW, customer1, "Printer problem", "The printer can't detect the Wi-Fi");
        ticket2 = new Ticket(Priority.HIGH, customer1, "Power critical issue", "The system is experiencing power losses");
        ticket3 = new Ticket(Priority.LOW, customer2, "Keyboard issue", "Some keys seem not working");
        ticket4 = new Ticket(Priority.MEDIUM, customer4, "laptop problem", "Laptop sometimes doesn't start after pressing power button");
    }

    @Test
    void addCustomerShouldAddCustomerToSystem() {
        assertTrue(system.addCustomer(customer1));
    }

    @Test
    void addCustomerShouldRejectDuplicateEmail() {
        assertTrue(system.addCustomer(customer1));
        assertFalse(system.addCustomer(customer2));
    }

    @Test
    void addCustomerShouldRejectDuplicatePhoneNumber() {
        assertTrue(system.addCustomer(customer1));
        assertFalse(system.addCustomer(customer3));
    }

    @Test
    void addAgentShouldAddAgentToSystem() {
        assertTrue(system.addAgent(agent1));
    }

    @Test
    void addAgentShouldRejectDuplicateEmail() {
        assertTrue(system.addAgent(agent1));
        assertFalse(system.addAgent(agent2));
    }

    @Test
    void addAgentShouldRejectDuplicatePhoneNumber() {
        assertTrue(system.addAgent(agent1));
        assertFalse(system.addAgent(agent3));
    }

    @Test
    void addTicketShouldAddTicketToSystem() {
        assertTrue(system.addTicket(ticket1));
    }

    @Test
    void addTicketShouldRejectDuplicateTicket() {
        assertTrue(system.addTicket(ticket1));
        assertFalse(system.addTicket(ticket1));
    }

    @Test
    void findCustomerByIdShouldReturnCorrectCustomer() {
        assertTrue(system.addCustomer(customer1));
        int customerID = customer1.getId();
        assertSame(customer1, system.findCustomerById(customerID));
    }

    @Test
    void findAgentByIdShouldReturnCorrectAgent() {
        assertTrue(system.addAgent(agent1));
        int agentID = agent1.getId();
        assertSame(agent1, system.findAgentById(agentID));
    }

    @Test
    void findTicketByIdShouldReturnCorrectTicket() {
        assertTrue(system.addTicket(ticket1));
        int ticketID = ticket1.getId();
        assertSame(ticket1, system.findTicketById(ticketID));
    }

    @Test
    void findMethodsShouldReturnNullWhenObjectDoesNotExist() {
        assertNull(system.findCustomerById(1));
        assertNull(system.findAgentById(1));
        assertNull(system.findTicketById(1));
    }
    @Test
    void assignAgentToTheTicketShouldAssignRegisteredAgent() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addAgent(agent1));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertSame(agent1, ticket1.getAgent());
        assertEquals(TicketStatus.ASSIGNED, ticket1.getStatus());
    }

    @Test
    void assignAgentToTheTicketShouldFailForUnregisteredAgent() {
        assertTrue(system.addTicket(ticket1));
        assertFalse(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
    }
    @Test
    void unassignAgentFromTicketShouldUnassignAgent() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addAgent(agent1));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertTrue(system.unassignAgentFromTicket(ticket1.getId()));
        assertNull(ticket1.getAgent());
        assertEquals(TicketStatus.OPEN, ticket1.getStatus());
    }

    @Test
    void reassignAgentToTicketShouldChangeAgent() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addAgent(agent1));
        assertTrue(system.addAgent(agent4));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertSame(agent1, ticket1.getAgent());
        assertTrue(system.reassignAgentToTicket(ticket1.getId(), agent4.getId()));
        assertSame(agent4, ticket1.getAgent());
    }

    @Test
    void closeTicketShouldCloseTicketWithAgent() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addAgent(agent1));
        assertEquals(TicketStatus.OPEN, ticket1.getStatus());
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertTrue(system.closeTicket(ticket1.getId()));
        assertEquals(TicketStatus.CLOSED, ticket1.getStatus());
        assertNull(ticket1.getAgent());
    }

    @Test
    void reopenTicketShouldReopenTicket() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addAgent(agent1));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertEquals(TicketStatus.ASSIGNED, ticket1.getStatus());
        assertTrue(system.closeTicket(ticket1.getId()));
        assertEquals(TicketStatus.CLOSED, ticket1.getStatus());
        assertTrue(system.reopenTicket(ticket1.getId()));
        assertEquals(TicketStatus.OPEN, ticket1.getStatus());
        assertNull(ticket1.getAgent());
    }

    @Test
    void changeTicketPriorityShouldChangePriority() {
        assertTrue(system.addTicket(ticket1));
        assertEquals(Priority.LOW, ticket1.getPriority());
        assertTrue(system.changeTicketPriority(ticket1.getId(), Priority.HIGH));
        assertEquals(Priority.HIGH, ticket1.getPriority());
    }

    @Test
    void startTicketProgressShouldChangeStatusToInProgress() {
        assertTrue(system.addTicket(ticket1));
        assertEquals(TicketStatus.OPEN, ticket1.getStatus());
        assertTrue(system.addAgent(agent1));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertEquals(TicketStatus.ASSIGNED, ticket1.getStatus());
        assertTrue(system.startTicketProgress(ticket1.getId()));
        assertEquals(TicketStatus.IN_PROGRESS, ticket1.getStatus());
    }

    @Test
    void findTicketsAssignedToAgentShouldOnlyReturnActiveTicketsForAgent() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addTicket(ticket2));
        assertTrue(system.addTicket(ticket3));
        assertTrue(system.addAgent(agent1));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertTrue(system.assignAgentToTicket(ticket2.getId(), agent1.getId()));
        assertTrue(system.assignAgentToTicket(ticket3.getId(), agent1.getId()));
        assertTrue(ticket2.closeTicket());
        assertTrue(ticket3.startProgress());
        assertTrue(ticket1.isActive());
        assertFalse(ticket2.isActive());
        assertTrue(ticket3.isActive());
        List<Ticket> ticketsWithoutAgent = system.findTicketsAssignedToAgent(agent1.getId());
        assertEquals(2, ticketsWithoutAgent.size());
        assertTrue(ticketsWithoutAgent.contains(ticket1));
        assertTrue(ticketsWithoutAgent.contains(ticket3));
        assertFalse(ticketsWithoutAgent.contains(ticket2));
    }

    @Test
    void findTicketWithoutAgentShouldOnlyReturnOpenUnassignedTickets() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addTicket(ticket2));
        assertTrue(system.addTicket(ticket3));
        assertTrue(system.addAgent(agent1));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        List<Ticket> unassignTickets = system.findTicketsWithoutAgent();
        assertEquals(2, unassignTickets.size());
        assertTrue(unassignTickets.contains(ticket2));
        assertTrue(unassignTickets.contains(ticket3));
        assertFalse(unassignTickets.contains(ticket1));
    }

    @Test
    void findTicketsCreatedByCustomerShouldOnlyReturnCorrectTickets() {
        assertTrue(system.addCustomer(customer1));
        assertTrue(system.addCustomer(customer4));

        assertTrue(system.addTicket(ticket1));      // Created by customer 1
        assertTrue(system.addTicket(ticket2));      // Created by customer 1
        assertTrue(system.addTicket(ticket4));      // Created by customer 4

        List<Ticket> ticketsCreatedByCustomer1 = system.findTicketsCreatedByCustomer(customer1.getId());             // Creating the list of tickets created by the customer 1
        assertEquals(2, ticketsCreatedByCustomer1.size());                                                  // Checking if number of tickets in the list is equal to numbers of ticket created by the customer 1
        assertTrue(ticketsCreatedByCustomer1.contains(ticket1));                                                     // Checking if the ticket 1 was indeed created by the customer 1
        assertTrue(ticketsCreatedByCustomer1.contains(ticket2));                                                     // Checking if the ticket 2 was indeed created by the customer 1
        assertFalse(ticketsCreatedByCustomer1.contains(ticket3));                                                    // Checking if ticket 3 is not in the list of tickets created by the customer 1

    }

    @Test
    void countActiveTicketsAssignedToAgentShouldReturnCorrectCount() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addTicket(ticket2));
        assertTrue(system.addTicket(ticket3));
        assertTrue(system.addAgent(agent1));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertTrue(system.assignAgentToTicket(ticket2.getId(), agent1.getId()));
        assertTrue(system.assignAgentToTicket(ticket3.getId(), agent1.getId()));
        assertTrue(ticket2.closeTicket());
        assertTrue(ticket3.startProgress());
        assertTrue(ticket1.isActive());
        assertFalse(ticket2.isActive());
        assertTrue(ticket3.isActive());
        assertEquals(2, system.countActiveTicketsAssignedToAgent(agent1.getId()));
    }

    @Test
    void findAgentWithFewestActiveTicketsShouldReturnLeastBusyAgent() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addTicket(ticket2));
        assertTrue(system.addTicket(ticket3));
        assertTrue(system.addTicket(ticket4));
        assertTrue(system.addAgent(agent1));
        assertTrue(system.addAgent(agent4));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));                        // Agent 1 has ony one assigned ticket
        assertTrue(system.assignAgentToTicket(ticket2.getId(), agent4.getId()));
        assertTrue(system.assignAgentToTicket(ticket3.getId(), agent4.getId()));
        assertTrue(system.assignAgentToTicket(ticket4.getId(), agent4.getId()));                        // Agent 4 has 3 assigned tickets
        assertEquals(1, system.countActiveTicketsAssignedToAgent(agent1.getId()));             // Checking if agent 1 has indeed 1 assigned ticket
        assertEquals(3, system.countActiveTicketsAssignedToAgent(agent4.getId()));             // Checking if agent 4 has indeed 3 assigned tickets
        assertSame(agent1, system.findAgentWithFewestActiveTickets());                                  // Checking if method finds the least busy agent, which in this case is agent 1
    }

    @Test
    void findAgentWithFewestActiveTicketsShouldReturnNullWhenNoAgentsExist() {
        assertTrue(system.addTicket(ticket1));
        assertTrue(system.addTicket(ticket2));
        assertTrue(system.addTicket(ticket3));
        assertNull(system.findAgentWithFewestActiveTickets());      // System does not contain any agents, therefore the method should return null
    }

    @Test
    void assignTicketToAgentWithFewestActiveTicketsShouldAssignLeastBusyAgent() {
        assertTrue(system.addTicket(ticket1));                                                          // Will be assigned to agent 1
        assertTrue(system.addTicket(ticket2));                                                          // Will be assigned to agent 4
        assertTrue(system.addTicket(ticket3));                                                          // Will be assigned to agent 4
        assertTrue(system.addTicket(ticket4));                                                          // Will not be assigned for a testing purposes
        assertTrue(system.addAgent(agent1));
        assertTrue(system.addAgent(agent4));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));                        // Agent 1 has ony one assigned ticket
        assertTrue(system.assignAgentToTicket(ticket2.getId(), agent4.getId()));
        assertTrue(system.assignAgentToTicket(ticket3.getId(), agent4.getId()));                        // Agent 4 has 2 assigned tickets
        assertNull(ticket4.getAgent());                                                                 // Checking if ticket 4 has no assigned agents

        assertEquals(1, system.countActiveTicketsAssignedToAgent(agent1.getId()));             // Checking if agent 1 has indeed 1 assigned ticket
        assertEquals(2, system.countActiveTicketsAssignedToAgent(agent4.getId()));             // Checking if agent 4 has indeed 2 assigned tickets
        assertTrue(system.assignTicketToAgentWithFewestActiveTickets(ticket4.getId()));
        assertSame(agent1, ticket4.getAgent());                                                         // Checking if agent 4 has been assigned to the ticket 4

    }

    @Test
    void assignAllUnassignedOpenTicketsShouldAssignAllEligibleTickets() {
        assertTrue(system.addTicket(ticket1));                                                            // Will be closed
        assertTrue(system.addTicket(ticket2));                                                            // Ticket 2 status is open - should be assigned
        assertTrue(system.addTicket(ticket3));                                                            // Ticket 3 status is open - should be assigned
        assertTrue(system.addTicket(ticket4));                                                            // Will be assigned to another agent - should not be assigned again

        assertTrue(system.addAgent(agent1));
        assertTrue(system.addAgent(agent4));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));
        assertTrue(system.closeTicket(ticket1.getId()));                                                  // Ticket 1 status closed - should not be assigned

        assertTrue(system.assignAgentToTicket(ticket4.getId(), agent4.getId()));                          // Ticket status assigned to another agent - should not be assigned again

        assertEquals(2, system.assignAllUnassignedOpenTickets());
        assertEquals(2, system.countActiveTicketsAssignedToAgent(agent1.getId()));               // Checking if agent 1 has 2 assigned tickets
        assertEquals(1, system.countActiveTicketsAssignedToAgent(agent4.getId()));               // Checking if agent 4 has 1 assigned ticket
        assertFalse(ticket1.isActive());                                                                  // Checking if ticket 1 is closed
        assertEquals(agent1.getId(), ticket2.getAgent().getId());                                         // Checking if ticket 2 was assigned to agent 1
        assertEquals(agent1.getId(), ticket3.getAgent().getId());                                         // Checking if ticket 3 was assigned to agent 1


    }

    @Test
    void getTicketHistoryShouldReturnTicketHistory() {
        assertTrue(system.addTicket(ticket1));                                             // Adds Created  - history size 1
        assertTrue(system.addAgent(agent1));
        assertTrue(system.assignAgentToTicket(ticket1.getId(), agent1.getId()));          // Adds assigned = history size 2
        system.startTicketProgress(ticket1.getId());                                      // Adds in progress - history size 3
        assertNotNull(system.getTicketHistory(ticket1.getId()));
        String history = system.getTicketHistory(ticket1.getId());
        assertEquals(3, ticket1.getHistory().size());
        assertTrue(history.contains("CREATED"));
        assertTrue(history.contains("ASSIGNED"));
        assertTrue(history.contains("STARTED_PROGRESS"));
    }

    @Test
    void getTicketHistoryShouldReturnMessageWhenTicketDoesNotExist() {
        assertEquals("No ticket found!", (system.getTicketHistory(ticket1.getId())));
    }
}


