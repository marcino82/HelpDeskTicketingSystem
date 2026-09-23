# Help Desk Ticketing System

A Java help desk ticketing system built as a portfolio project.

I started the project to practise Java OOP and gradually expanded it with ticket workflow logic, agent assignment, ticket history, automated tests and a relational Microsoft SQL Server database.

The Java application now has a working JDBC connection to SQL Server. Database credentials are supplied through environment variables, and the application can execute parameterised SQL queries and process results returned from the database.

The next stage is moving database access into a repository layer and gradually replacing the remaining in-memory persistence with SQL Server.

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
- JDBC connection to Microsoft SQL Server
- Parameterised SQL queries using `PreparedStatement`
- Database result processing using `ResultSet`
- Database credentials supplied through environment variables
- Retrieval of ticket data directly from SQL Server

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

The `database` directory contains:

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

## Database Connection

The Java application connects to Microsoft SQL Server using the Microsoft JDBC Driver.

Database credentials are supplied through environment variables:

```text
HELPDESK_DB_USER
HELPDESK_DB_PASSWORD
```

Credentials are not stored in the source code or committed to the repository.

The current JDBC implementation uses:

- `Connection` to connect to SQL Server
- `PreparedStatement` for parameterised SQL queries
- `ResultSet` to process rows returned by SQL Server
- try-with-resources to close database connections automatically

The application can currently retrieve tickets from SQL Server by status and read ticket properties such as ID, title, priority and status.

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

```text
mvn test
```

## Technologies

- Java
- Microsoft SQL Server
- SQL
- JDBC
- Maven
- JUnit 5
- Git
- GitHub
- IntelliJ IDEA

## Current Design

The project contains an object-oriented Java domain model for customers, support agents, tickets and ticket history.

`HelpDeskSystem` currently provides the original in-memory implementation of system-level operations such as registering users, finding tickets, assigning agents and calculating workloads.

The `Ticket` class is responsible for ticket state changes such as assignment, progress, priority changes, closing and reopening.

A relational Microsoft SQL Server database represents users, customers, support agents, tickets and ticket history.

The Java application now has a working JDBC connection to SQL Server and can execute parameterised queries and read ticket data using `PreparedStatement` and `ResultSet`.

Database access is currently being moved towards a repository layer. Full application persistence through SQL Server is not yet implemented.

## Next Steps

- Map SQL query results to Java `Ticket` objects
- Add a `TicketRepository` for database access
- Implement ticket `SELECT`, `INSERT` and `UPDATE` operations using JDBC
- Use SQL Server generated identity values as application IDs
- Persist ticket history and workflow changes
- Add a transaction for related ticket and history updates
- Expand analytical SQL reporting queries
- Improve sample data for reporting scenarios
- Add an entity relationship diagram
- Add Maven Wrapper
- Complete final documentation and project cleanup