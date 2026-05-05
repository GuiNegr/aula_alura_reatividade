CREATE TABLE tickets (
                         id BIGINT NOT NULL,
                         id_eventos BIGINT NOT NULL,
                         created_date DATE,
                         updated_date DATE,
                         qtd_remain INT,

                         PRIMARY KEY (id),
                         FOREIGN KEY (id_eventos) REFERENCES eventos(id)
);