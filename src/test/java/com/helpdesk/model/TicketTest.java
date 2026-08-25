package com.helpdesk.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {
    private Ticket ticket1;
    private SupportAgent agent1;
    private SupportAgent agent2;


    @BeforeEach
    void setUp() {
        agent1 = new SupportAgent("Callum", "Stewart", "callum.stewart@test.com", "07700 123456");
        agent2 = new SupportAgent("Lewis", "Brown", "lewis.brown@yahoo.com", "07766 516382");

        Customer customer1 = new Customer("James", "Murray", "james.murray@gmail.com", "07700 654321");

        ticket1 = new Ticket(
                Priority.MEDIUM, customer1, "Printer problem", "The printer can't detect the Wi-Fi");
    }

    @Test
    void newTicketShouldHaveOpenStatus() {

        assertEquals(TicketStatus.OPEN, ticket1.getStatus());
    }
    @Test
    void assignAgentChangesStatusToAssigned(){

        assertTrue(ticket1.assignAgent(agent1));
        assertEquals(TicketStatus.ASSIGNED, ticket1.getStatus());
    }

    @Test
    void startProgressChangesStatusToInProgress(){

        assertTrue(ticket1.assignAgent(agent1));
        assertTrue(ticket1.startProgress());
        assertEquals(TicketStatus.IN_PROGRESS, ticket1.getStatus());
    }

    @Test
    void closeTicketWithAgentShouldChangeStatusToClosed(){

        assertTrue(ticket1.assignAgent(agent1));
        assertTrue(ticket1.closeTicket());
        assertEquals(TicketStatus.CLOSED, ticket1.getStatus());
    }
    @Test
    void closeTicketWithoutAgentShouldReturnFalse(){

        assertFalse(ticket1.closeTicket());
        assertEquals(TicketStatus.OPEN, ticket1.getStatus());
    }

    @Test
    void reassignAgentChangesAgentAndStatusToAssigned(){

        assertTrue(ticket1.assignAgent(agent1));
        assertTrue(ticket1.reassignAgent(agent2));
        assertEquals(TicketStatus.ASSIGNED,ticket1.getStatus());
        assertSame(agent2, ticket1.getAgent());
    }

    @Test
    void unassignAgentChangesStatusToOpen(){

        assertTrue(ticket1.assignAgent(agent1));
        assertTrue(ticket1.unassignAgent());
        assertEquals(TicketStatus.OPEN, ticket1.getStatus());
        assertNull(ticket1.getAgent());
    }

    @Test
    void closedTicketPreventsPriorityChange(){

        assertTrue(ticket1.assignAgent(agent1));
        assertTrue(ticket1.closeTicket());
        assertFalse(ticket1.changePriority(Priority.HIGH));
        assertEquals(Priority.MEDIUM, ticket1.getPriority());
    }
    @Test
    void ticketHistoryGrowsOnlyAfterSuccessfulActions(){
                                                                            // Ticket created - historySize is 1
        assertTrue(ticket1.assignAgent(agent1));                            // Successful, historySize is 2
        assertTrue(ticket1.reassignAgent(agent2));                          // Successful, historySize is 3
        assertTrue(ticket1.closeTicket());                                  // Successful, historySize is 4

        int historySizeBefore = ticket1.getHistory().size();
        assertEquals(4, historySizeBefore);
        assertFalse(ticket1.changePriority(Priority.HIGH));                 // Unsuccessful, historySize still 4;
        int historySizeAfter = ticket1.getHistory().size();

        assertEquals(historySizeBefore, historySizeAfter);
    }

    @Test
    void startProgressShouldFailForOpenTicketWithoutAgent(){
        // Ticket created - historySize is 1
        assertFalse(ticket1.startProgress());
        assertEquals(TicketStatus.OPEN, ticket1.getStatus());
        assertEquals(1, ticket1.getHistory().size());
    }
}
