CREATE TABLE users
(
    user_id      INT IDENTITY(1,1) PRIMARY KEY,
    first_name   NVARCHAR(50) NOT NULL,
    last_name    NVARCHAR(50) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20)  NOT NULL UNIQUE
);

CREATE TABLE customers
(
    user_id INT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE support_agents
(
    user_id INT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE tickets
(
    ticket_id          INT IDENTITY (1,1) PRIMARY KEY,
    customer_id        INT         NOT NULL,
    agent_id           INT,

    title              NVARCHAR (100) NOT NULL,
    description        NVARCHAR(1000) NOT NULL,

    priority           VARCHAR(10) NOT NULL,
    status             VARCHAR(20) NOT NULL,

    created_at         DATETIME2   NOT NULL DEFAULT SYSDATETIME(),
    closed_by_agent_id INT NULL,
    closed_at          DATETIME2 NULL,

    FOREIGN KEY (customer_id) REFERENCES customers (user_id),
    FOREIGN KEY (agent_id) REFERENCES support_agents (user_id),
    FOREIGN KEY (closed_by_agent_id) REFERENCES support_agents (user_id),

    CHECK ( priority IN ('LOW', 'MEDIUM', 'HIGH') ),
    CHECK ( status IN ('OPEN', 'ASSIGNED', 'IN_PROGRESS', 'CLOSED') )
);

CREATE TABLE ticket_history
(
    history_id INT IDENTITY(1,1) PRIMARY KEY,
    ticket_id  INT         NOT NULL,
    agent_id INT NULL,

    action     VARCHAR(30) NOT NULL,
    event_timestamp DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    description NVARCHAR(255) NOT NULL,

    FOREIGN KEY (ticket_id) REFERENCES tickets (ticket_id),
    FOREIGN KEY (agent_id) REFERENCES support_agents(user_id),

    CHECK ( action IN ('CREATED', 'ASSIGNED', 'REASSIGNED', 'UNASSIGNED','STARTED_PROGRESS', 'PRIORITY_CHANGED', 'CLOSED', 'REOPENED') )
);