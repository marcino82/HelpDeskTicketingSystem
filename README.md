# Help Desk Ticketing System

A Java help desk ticketing system built as a portfolio project.

I started the project to practise Java OOP and gradually expanded it with ticket workflow logic, agent assignment, ticket history and automated tests.

The next stage is adding a relational SQL database and JDBC persistence.

## Features

- Customer and support agent registration
- Duplicate email and phone number validation
- Ticket creation with priority levels
- Agent assignment, reassignment and unassignment
- Ticket status workflow
- Ticket progress tracking
- Ticket priority changes
- Ticket closing and reopening
- Ticket history with timestamps
- Ticket filtering by agent and customer
- Agent workload calculation
- Automatic assignment of open unassigned tickets
- Protection against invalid ticket state transitions

## Ticket Workflow

The current ticket lifecycle is:

```text
OPEN
  |
  | assign agent
  v
ASSIGNED
  |
  | start progress
  v
IN_PROGRESS
  |
  | close
  v
CLOSED
  |
  | reopen
  v
OPEN
```
A ticket can only be closed when an agent is assigned.
When a ticket is closed, the current agent assignment is removed. Reopening the ticket returns it to OPEN, ready to be assigned again.

## Project Structure

```text
src
├── main
│   └── java
│       └── com.helpdesk
│           ├── Main.java
│           ├── model
│           │   ├── User.java
│           │   ├── Customer.java
│           │   ├── SupportAgent.java
│           │   ├── Ticket.java
│           │   ├── TicketHistory.java
│           │   ├── TicketStatus.java
│           │   ├── TicketAction.java
│           │   └── Priority.java
│           └── service
│               └── HelpDeskSystem.java
│
└── test
    └── java
        └── com.helpdesk
            ├── model
            │   └── TicketTest.java
            └── service
                └── HelpDeskSystemTest.java

```

## Testing

The project uses JUnit 5 for automated testing.
There are currently 39 passing tests covering:
- Customer and agent registration
- Duplicate validation
- Ticket creation
- Agent assignment and reassignment
- Ticket closing and reopening
- Ticket status transitions
- Priority changes
- Ticket history
- Ticket filtering
- Agent workload calculations
- Automatic ticket assignment
- Invalid operations
Tests can be run with Maven:
```
mvn test
```
## Technologies

- Java
- Maven
- JUnit 5
- Git
- GitHub
- IntelliJ IDEA

## Current Design

The project currently stores data in memory using Java collections.
HelpDeskSystem handles system-level operations such as registering users, finding tickets, assigning agents and calculating workloads.
The Ticket class is responsible for ticket state changes such as assignment, progress, priority changes, closing and reopening.

## Next Steps
- Design relational database schema
- Add SQL sample data
- Add SQL reporting and validation queries
- Connect Java to the database using JDBC
- Add repository layer
- Move application data from in-memory collections to persistent storage
