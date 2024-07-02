CREATE TABLE public.book
(
    author_id bigint,
    isbn bigint NOT NULL PRIMARY KEY,
    date date,
    name character varying(300) NOT NULL
);