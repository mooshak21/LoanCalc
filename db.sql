CREATE TABLE loanapp (
  LOAN_ID bigint not null,
   LOAN_AMT double precision not null,
   APR double precision not null,
   INT_RATE double precision not null,
    MTHLY_PAYMT double precision not null,
    LENDER varchar(10) not null,
    ST varchar(10) not null,
    NUM_OF_YRS integer not null,
    CONSTRAINT loanapp_pkey primary key (LOAN_ID), unique (LOAN_AMT));

-----------------------------------------------------------------------------------------

CREATE TABLE loan (
  LOAN_ID bigint not null,
  LOAN_AMT double precision not null,
  APR double precision not null,
  INT_RATE double precision not null,
  MTHLY_PAYMT double precision not null,
  LENDER varchar(10) not null,
  ST varchar(10) not null,
  NUM_OF_YRS integer not null,
  CONSTRAINT loan_pkey primary key (LOAN_ID));

-----------------------------------------------------------------------------------------

CREATE TABLE loanagg

(

    loan_agg_id bigint NOT NULL,

    name character varying(100) COLLATE pg_catalog."default" NOT NULL,

    start_date date NOT NULL,

    term character varying(50) COLLATE pg_catalog."default",

    type character varying(50) COLLATE pg_catalog."default",

    email character varying(50) COLLATE pg_catalog."default",

    created_date date,

    created_user_id bigint,

    updated_date date,

    updated_user_id bigint,

    active_yn boolean,

    CONSTRAINT loanagg_pkey PRIMARY KEY (loan_agg_id)

);


-----------------------------------------------------------------------------------------

CREATE TABLE loan_relationship

(

    loan_agg_id bigint NOT NULL,

    loan_rel_id bigint NOT NULL,

    loan_id bigint NOT NULL,

    name character varying(100) COLLATE pg_catalog."default" NOT NULL,

    type character varying(50) COLLATE pg_catalog."default",

    email character varying(50) COLLATE pg_catalog."default",

    created_date date,

    created_user_id bigint,

    updated_date date,

    updated_user_id bigint,

    active_yn boolean,

    CONSTRAINT loan_relationship_pkey PRIMARY KEY (loan_rel_id),

    CONSTRAINT loan_relationship_loan_agg_id_fkey FOREIGN KEY (loan_agg_id)

        REFERENCES loanagg (loan_agg_id) MATCH SIMPLE

        ON UPDATE NO ACTION

        ON DELETE NO ACTION

);


CREATE INDEX "loan_Agg"

    ON loan_relationship USING btree

    (loan_agg_id)

    TABLESPACE pg_default;


create table pref
(
	pref_id INT4 not null,
	pref_emailaddress VARCHAR(30) not null,
	pref_type VARCHAR(20) not null,
	pref_name VARCHAR(30) not null,
	pref_value VARCHAR(30) not null,
	pref_description VARCHAR(50) not null,
        discriminator VARCHAR(255) not null,
	primary key (pref_id, pref_emailaddress)
)