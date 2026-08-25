package com.helpdesk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ticket {

    // Ticket data and shared ID counter
    private final int id;
    private Priority priority;
    private TicketStatus status;
    private final Customer customer;
    private SupportAgent agent;
    private static int counter;
    private final String ticketTitle;
    private final String ticketDescription;

    private final List<TicketHistory> history;


    // Constructor
    public Ticket(Priority priority, Customer customer, String ticketTitle, String ticketDescription) {

        if (priority == null || customer == null || ticketTitle == null || ticketDescription == null) {
            throw new IllegalArgumentException("Ticket's arguments cannot be null.");
        }
        counter++;
        this.id = counter;
        this.priority = priority;
        this.customer = customer;
        this.ticketTitle = ticketTitle;
        this.ticketDescription = ticketDescription;
        this.status = TicketStatus.OPEN;
        this.history = new ArrayList<>();
        this.history.add(new TicketHistory(TicketAction.CREATED, "Ticket Created"));


    }

    // Getters
    public int getId() {
        return id;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isOpen() {
        return this.status == TicketStatus.OPEN;
    }

    public boolean isActive() {
        return this.status != TicketStatus.CLOSED;
    }

    public TicketStatus getStatus() {
        return this.status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public SupportAgent getAgent() {
        return agent;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public List<TicketHistory> getHistory() {
        return Collections.unmodifiableList(history);
    }

    // Assigns an agent to the ticket
    public boolean assignAgent(SupportAgent agent) {

        if (agent != null && this.agent == null && this.status == TicketStatus.OPEN) {
            this.agent = agent;
            this.status = TicketStatus.ASSIGNED;
            history.add(new TicketHistory(TicketAction.ASSIGNED, "Agent Assigned"));
            return true;
        }
        return false;
    }

    // Starts progress of a ticket
    public boolean startProgress() {
        if (this.status == TicketStatus.ASSIGNED) {
            this.status = TicketStatus.IN_PROGRESS;
            history.add(new TicketHistory(TicketAction.STARTED_PROGRESS, "Ticket Progress Started"));
            return true;
        }
        return false;
    }

    // Closes an active ticket with an assigned agent
    public boolean closeTicket() {
        if (isActive() && this.agent != null) {
            this.status = TicketStatus.CLOSED;
            history.add(new TicketHistory(TicketAction.CLOSED, "Ticket Closed by agent " + this.agent.getName()));
            this.agent = null;
            return true;
        }
        return false;
    }

    // Reopen a ticket
    public boolean reopenTicket() {
        if (this.status == TicketStatus.CLOSED) {
            this.status = TicketStatus.OPEN;
            history.add(new TicketHistory(TicketAction.REOPENED, "Ticket Reopened"));
            return true;
        }
        return false;
    }

    // Reassigns an agent
    public boolean reassignAgent(SupportAgent agent) {
        if (this.agent != null && agent != null && this.isActive() && this.agent != agent) {
            this.agent = agent;
            this.status = TicketStatus.ASSIGNED;
            history.add(new TicketHistory(TicketAction.REASSIGNED, "Agent Reassigned"));
            return true;
        }
        return false;
    }

    // Unassigns an agent
    public boolean unassignAgent() {
        if (this.isActive() && this.agent != null) {
            this.agent = null;
            this.status = TicketStatus.OPEN;
            history.add(new TicketHistory(TicketAction.UNASSIGNED, "Agent Unassigned"));
            return true;
        }
        return false;
    }

    // Changes priority
    public boolean changePriority(Priority newPriority) {
        if (this.isActive() && newPriority != null && this.priority != newPriority) {
            this.priority = newPriority;
            history.add(new TicketHistory(TicketAction.PRIORITY_CHANGED, "Priority Changed"));
            return true;
        }
        return false;
    }

    // Get full ticket's history
    public String getFullHistory() {
        StringBuilder fullHistory = new StringBuilder();
        for (TicketHistory ticketHistory : history) {
            fullHistory.append(ticketHistory).append("\n");
        }

        return fullHistory.toString();
    }

    // Returns a string representation of the ticket
    @Override
    public String toString() {

        String agentName = this.agent != null ? this.agent.getName() : "Not assigned yet";
        String agentId = this.agent != null ? String.valueOf(this.agent.getId()) : "N/A";
        String customerName = this.customer != null ? this.customer.getName() : "Unknown";

        return "Ticket ID: " + this.id + ", Ticket Title: " + this.ticketTitle + ", Ticket Description: " + this.ticketDescription + ", Status: " + this.status + ", Priority: " + this.priority + ", Customer: " + customerName + ", Agent: " + agentName + ", Agent ID: " + agentId;
    }
}
