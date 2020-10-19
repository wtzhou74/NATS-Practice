-- create a twitter database
CREATE DATABASE twitter
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
    

-- create a table timeline_status
CREATE TABLE public.timeline_status
(
    id bigint NOT NULL,
    text character varying,
    source character varying,
    place character varying,
    "is_favorited" boolean,
    "is_retweet" boolean,
    "is_retweeted" boolean,
    "favorite_count" integer,
    "retweet_count" integer,
    "user_id" bigint,
    "is_possibly_sensitive" boolean,
    "created_at" timestamp without time zone,
    PRIMARY KEY (id)
);

ALTER TABLE public.timeline_status
    OWNER to postgres;