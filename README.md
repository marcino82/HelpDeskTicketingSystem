# Help Desk Ticketing System

A Java help desk ticketing system built as a portfolio project.

I started the project to practise Java OOP and gradually expanded it with ticket workflow logic, agent assignment, ticket history, automated tests and a relational SQL Server database schema.

The project currently uses an in-memory Java implementation alongside a SQL Server database design. The next stage is connecting the application to the database using JDBC.

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
- Relational SQL Server database schema
- SQL sample data
- SQL reporting queries

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
A ticket can only be closed when an agent is assigned. When a ticket is closed, the closing agent and timestamp are recorded. Reopening the ticket returns it to OPEN and removes the current agent assignment so that it can be assigned again.

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

database
├── schema.sql
├── sample_data.sql
└── queries.sql

```

## Database

The SQL Server database models users, customers, support agents, tickets and ticket history using primary keys, foreign keys and constraints.
The database directory contains:
- `schema.sql` – creates the relational database structure
- `sample_data.sql` – inserts sample users, agents, customers and tickets
- `queries.sql` – contains reporting and support queries using joins, filtering, aggregation, grouping and sorting

Example reports include:
- Open tickets waiting for assignment
- Tickets assigned to a specific agent
- Ticket details with customer and agent information
- Active ticket workload per agent
- Ticket counts by status
- Ticket counts by priority

## Testing

The project uses JUnit 5 for automated testing covering:
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
- Microsoft SQL Server
- SQL
- Maven
- JUnit 5
- Git
- GitHub
- IntelliJ IDEA

## Current Design

The Java application currently stores runtime data in memory using Java collections. HelpDeskSystem handles system-level operations such as registering users, finding tickets, assigning agents and calculating workloads.
The Ticket class is responsible for ticket state changes such as assignment, progress, priority changes, closing and reopening.
A relational SQL Server schema has also been created to represent the application data persistently. Java and SQL are not yet connected.

## Next Steps
- Connect Java to SQL Server using JDBC
- Add a repository layer
- Replace in-memory application storage with database persistence
- Persist ticket history and workflow changes
