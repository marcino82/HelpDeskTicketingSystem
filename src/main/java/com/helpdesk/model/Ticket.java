package com.helpdesk.model;

public class Ticket {

    // com.helpdesk.model.Ticket data and shared ID counter
    private int id;
    private Priority priority;
    private boolean isOpen;
    private Customer customer;
    private SupportAgent agent;
    private static int counter;
    private String ticketTitle;
    private String ticketDescription;


    // Constructor
    public Ticket(Priority priority, Customer customer, String ticketTitle, String ticketDescription) {

        if (priority == null || customer == null || ticketTitle == null || ticketDescription == null) {
            throw new IllegalArgumentException("com.helpdesk.model.Ticket's arguments cannot be null.");
        }
        counter++;
        this.id = counter;
        this.priority = priority;
        this.customer = customer;
        this.ticketTitle = ticketTitle;
        this.ticketDescription = ticketDescription;
        this.isOpen = true;
    }

    // Assigns an agent to the ticket
    public boolean assignAgent(SupportAgent agent) {

        if (agent != null && this.agent == null && this.isOpen) {
            this.agent = agent;
            return true;
        }
        return false;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isOpen() {
        return isOpen;
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

    // Closes an open ticket
    public boolean closeTicket() {
        if (this.isOpen) {
            this.isOpen = false;
            return true;
        }
        return false;
    }

    // Reopens a closed ticket
    public boolean reopenTicket() {
        if (!this.isOpen) {
            this.isOpen = true;
            return true;
        }
        return false;
    }

    // Reassigns an agent
    public boolean reassignAgent(SupportAgent agent) {
        if (this.agent != null && agent != null && this.isOpen && this.agent != agent) {
            this.agent = agent;
            return true;
        }
        return false;
    }

    // Unassigns an agent
    public boolean unassignAgent(){
        if(this.isOpen && this.agent != null) {
        this.agent = null;
        return true;
        }
        return false;
    }

    // Changes priority
    public boolean changePriority(Priority newPriority) {
        if (isOpen && newPriority != null && this.priority != newPriority) {
            this.priority = newPriority;
            return true;
        }
        return false;
    }

    // Returns a string representation of the ticket
    @Override
    public String toString() {

        String agentName = this.agent != null ? this.agent.getName() : "Not assigned yet";
        String agentId = this.agent != null ? String.valueOf(this.agent.getId()) : "N/A";
        String customerName = this.customer != null ? this.customer.getName() : "Unknown";

        return "com.helpdesk.model.Ticket ID: " + this.id + ", com.helpdesk.model.Ticket Title: " + this.ticketTitle + ", com.helpdesk.model.Ticket Description: " + this.ticketDescription + ", IsOpen: " + this.isOpen + ", com.helpdesk.model.Priority: " + this.priority + ", com.helpdesk.model.Customer: " + customerName + ", Agent: " + agentName + ", Agent ID: " + agentId;
    }
}
