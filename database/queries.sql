-- Shows tickets with customer details
SELECT t.ticket_id,
       t.title,
       t.status,
       u.first_name,
       u.last_name
FROM tickets t
         JOIN customers c
              ON t.customer_id = c.user_id
         JOIN users u
              ON c.user_id = u.user_id;

-- Shows all tickets with assigned agent details when available
SELECT
    t.ticket_id,
    t.title,
    t.status,
    au.first_name,
    au.last_name
FROM tickets t
         LEFT JOIN support_agents a
                   ON t.agent_id = a.user_id
         LEFT JOIN users au
                   ON a.user_id = au.user_id;

-- Shows tickets with customer and assigned agent details
SELECT
    t.ticket_id,
    t.title,
    t.status,
    cu.first_name AS customer_first_name,
    cu.last_name AS customer_last_name,
    au.first_name AS agent_first_name,
    au.last_name AS agent_last_name
FROM tickets t
         JOIN customers c
              ON t.customer_id = c.user_id
         JOIN users cu
              ON c.user_id = cu.user_id
         LEFT JOIN support_agents a
                   ON t.agent_id = a.user_id
         LEFT JOIN users au
                   ON a.user_id = au.user_id;

-- Shows open tickets waiting for agent assignment
SELECT
    t.ticket_id,
    t.status,
    t.title,
    t.agent_id
FROM tickets t
WHERE t.status = 'OPEN'
  AND t.agent_id IS NULL;

-- Shows tickets assigned to a specific agent
SELECT
    t.ticket_id,
    t.status,
    t.title,
    t.description,
    u.first_name AS agent_first_name,
    u.last_name AS agent_last_name
FROM tickets t
         JOIN support_agents a
              ON t.agent_id = a.user_id
         JOIN users u
              ON a.user_id = u.user_id
WHERE t.agent_id = 2;

-- Counts active tickets assigned to each agent
SELECT
    t.agent_id,
    COUNT(t.ticket_id) AS active_ticket_count
FROM tickets t
WHERE t.status != 'CLOSED'
    AND t.agent_id IS NOT NULL
GROUP BY t.agent_id;

-- Shows active ticket workload for each agent, highest workload first
SELECT
    u.first_name,
    u.last_name,
    COUNT(t.ticket_id) AS active_ticket_count
FROM tickets t
         JOIN support_agents a
              ON t.agent_id = a.user_id
         JOIN users u
              ON a.user_id = u.user_id
WHERE t.status != 'CLOSED'
    AND t.agent_id IS NOT NULL
GROUP BY u.first_name, u.last_name
ORDER BY active_ticket_count DESC;

-- Shows ticket count by status, highest count first
SELECT
    t.status,
    COUNT(t.ticket_id) AS ticket_count
FROM tickets t
GROUP BY t.status
ORDER BY ticket_count DESC;

-- Shows ticket count by priority, highest count first
SELECT
    t.priority,
    COUNT(t.ticket_id) AS ticket_count
FROM tickets t
GROUP BY t.priority
ORDER BY ticket_count DESC;