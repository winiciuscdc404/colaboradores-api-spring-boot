CREATE TABLE sistema_db.COLABORADORES (
                                          ID INTEGER auto_increment NOT NULL,
                                          NOME varchar(200) NOT NULL,
                                          SENHA varchar(100) NOT NULL,
                                          SCORE INTEGER NULL,
                                          ID_CHEFE INTEGER NULL,
                                          CONSTRAINT COLABORADORES_pk PRIMARY KEY (ID)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

