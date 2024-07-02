CREATE TABLE public.authors_books
(
    author_uuid character varying(50) NOT NULL,
    book_isbn bigint NOT NULL,
    PRIMARY KEY (author_uuid, book_isbn),
    CONSTRAINT fk_book_isbn FOREIGN KEY (book_isbn)
        REFERENCES public.book (isbn) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_author_uuid FOREIGN KEY (author_uuid)
        REFERENCES public.author (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.authors_books
    OWNER to postgres;