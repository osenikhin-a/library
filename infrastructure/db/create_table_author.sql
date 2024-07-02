CREATE TABLE public.author
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name character varying(50) NOT NULL,
    patronymic character varying(70),
    surname character varying(100) NOT NULL,
    "birth_date" date NOT NULL,
    "death_date" date,
    uuid character varying(50) NOT NULL
);