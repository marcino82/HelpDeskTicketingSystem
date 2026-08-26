package com.helpdesk.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketHistory {

    // Data fields
    private final TicketAction action;
    private final LocalDateTime timestamp;
    private final String description;
    private final SupportAgent agent;
    // Constructor
    public TicketHistory(TicketAction action, String description, SupportAgent agent) {
        this.action = action;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.agent  = agent;
    }

    // Getters
    public TicketAction getAction() {
        return action;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public SupportAgent getAgent() {
        return agent;
    }

    public String getDescription() {
        return description;
    }

    // Time formatter
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Returns a string representation of the ticket history
    @Override
    public String toString() {
        String agentData = this.agent != null ? "Agent name: " + this.agent.getName() + ", ID: " + this.agent.getId() : "No agent (system or customer action)";
        String formattedTimestamp = timestamp.format(FORMATTER);
        return "[" + formattedTimestamp + "] "
                + action + " - " + description + " - " + agentData;
    }
}


