CREATE TABLE buyLog (
                         id BIGINT NOT NULL,
                         id_eventos BIGINT NOT NULL,
                         id_tickets BIGINT NOT NULL,
                         created_date DATE,
                         updated_date DATE,


                         PRIMARY KEY (id),
                         FOREIGN KEY (id_eventos) REFERENCES eventos(id),
                         FOREIGN KEY (id_tickets) REFERENCES tickets(id)

);