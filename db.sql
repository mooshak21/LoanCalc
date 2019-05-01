CREATE TABLE loanapp (
  LOAN_ID bigint not null,
   LOAN_AMT double precision not null,
   APR double precision not null,
   INT_RATE double precision not null,
    MTHLY_PAYMT double precision not null,
    LENDER varchar(10) not null,
    ST varchar(10) not null,
    NUM_OF_YRS integer not null,
    CONSTRAINT loanapp_pkey primary key (LOAN_ID));

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
);

ALTER TABLE loan ADD COLUMN loan_type character varying(100) NOT NULL DEFAULT 'Home Loan';
ALTER TABLE loan ADD COLUMN email character varying(50) NOT NULL DEFAULT 'contact@loaninsight.online';
ALTER TABLE loan ADD COLUMN currency character varying(15) NOT NULL DEFAULT 'USD';
ALTER TABLE loan ADD COLUMN loan_denom character varying(100) NOT NULL DEFAULT 'USD';
ALTER TABLE loan DROP COLUMN currency;
ALTER TABLE loan ALTER COLUMN lender  SET DATA TYPE character varying(300);
ALTER TABLE pref ALTER COLUMN pref_value  SET DATA TYPE character varying(300);
ALTER TABLE pref ALTER COLUMN pref_emailaddress  SET DATA TYPE character varying(300);

ALTER TABLE loan  ADD COLUMN region character varying(20) NOT NULL DEFAULT 'North America';

-- DROP TABLE public.news_object;

CREATE TABLE public.news_object
(
  link_url character varying(300) COLLATE pg_catalog."default" NOT NULL,
  offer_amount double precision,
  offer_end_date date NOT NULL,
  offer_id bigint NOT NULL,
  offer_rate double precision,
  offer_start_date date NOT NULL,
  refferer character varying(100) COLLATE pg_catalog."default" NOT NULL,
  bank_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
  loan_type character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Home Loan'::character varying,
  region character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'USA'::character varying,
  news_type character varying(20) COLLATE pg_catalog."default" NOT NULL,
  discriminator character varying(255) COLLATE pg_catalog."default" NOT NULL,
  news_title character varying(256) COLLATE pg_catalog."default",
  CONSTRAINT news_object_pkey PRIMARY KEY (offer_id)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.news_object
  OWNER to postgres;

ALTER TABLE  news_object
  ALTER COLUMN link_url TYPE character varying(20000);


-- Payment table
  CREATE TABLE public.payment
(
    "PAYMENT_ID" bigint NOT NULL,
    "PAYMENT_TYPE" character varying,
    "PAYMENT_START_DATE" date,
    "PAYMENT_END_DATE" date,
    "PAYMENT_AMOUNT" double precision,
    "PAYMENT_FREQUENCY" character varying,
    "BALANCE_AMOUNT" double precision,
    "PAYPAL_ACCOUNT_NUMBER" character varying,
    "PAYPAL_EMAIL_ADDRESS" character varying,
    "PAYPAL_AUTH_PERSON_NAME" character varying,
    "PAYPAL_PASSWORD" character varying,
    PRIMARY KEY ("PAYMENT_ID")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;


ALTER TABLE loan ADD COLUMN username character varying(100) NOT NULL DEFAULT 'ayushi';
ALTER TABLE loan ADD COLUMN vehicle_model character varying(50);
ALTER TABLE loan ADD COLUMN vehicle_make character varying(50);
ALTER TABLE loan ADD COLUMN vehicle_year character varying(50);
ALTER TABLE loan ADD COLUMN vin character varying(50);
ALTER TABLE loan ADD COLUMN address character varying(100);
ALTER TABLE loan ADD COLUMN city character varying(100);
ALTER TABLE loan ADD COLUMN country character varying(100);
ALTER TABLE loan ADD COLUMN zipcode character varying(50)

CREATE TABLE public.equity_external_calculator
(
  link_url character varying(300) COLLATE pg_catalog."default" NOT NULL,
	external_calculator_id bigint NOT NULL,
  loan_type character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Home Loan'::character varying,
  region character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'USA'::character varying,
  CONSTRAINT equity_external_calculator_pkey PRIMARY KEY (external_calculator_id)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default

CREATE TABLE public.equity
(
equity_id bigint NOT NULL,
loan_id bigint NOT NULL,
email character varying(100) COLLATE pg_catalog."default" NOT NULL,
loan_type character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Home Loan'::character varying,
loan_balance_amount double precision not null,
equity_value double precision not null,
remaining_year integer not null,
asset_value double precision not null,
valuation_date date NOT NULL,
  CONSTRAINT equity_pkey PRIMARY KEY (equity_id)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default