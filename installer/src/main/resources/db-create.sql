CREATE TABLE IF NOT EXISTS config
(
   "key" character varying NOT NULL,
   "value" text NOT NULL,
   "default" boolean NOT NULL DEFAULT FALSE,
   CONSTRAINT config_pkey PRIMARY KEY ("key", "value")
);

CREATE TABLE IF NOT EXISTS users
(
   "id" bigserial NOT NULL,
   "username" character varying NOT NULL,
   "password" character varying NOT NULL,
   "email" character varying,
   "phone" character varying,
   CONSTRAINT users_pkey PRIMARY KEY ("id"),
   CONSTRAINT users_username_key UNIQUE ("username")
);

CREATE TABLE IF NOT EXISTS groups
(
   "id" serial NOT NULL,
   "name" character varying NOT NULL,
   CONSTRAINT groups_pkey PRIMARY KEY ("id"),
   CONSTRAINT groups_name_key UNIQUE ("name")
);

CREATE TABLE IF NOT EXISTS users_to_groups
(
   "userId" bigint NOT NULL,
   "groupId" integer NOT NULL,
   CONSTRAINT users_to_groups_pkey PRIMARY KEY ("userId", "groupId"),
   CONSTRAINT users_to_groups_userId_fkey FOREIGN KEY ("userId") REFERENCES users ("id") ON UPDATE CASCADE ON DELETE CASCADE,
   CONSTRAINT users_to_groups_groupId_fkey FOREIGN KEY ("groupId") REFERENCES groups ("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS log
(
   "id" bigserial NOT NULL,
   "userId" bigint,
   "clientTime" timestamp with time zone NOT NULL,
   "serverTime" timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
   "host" character varying NOT NULL,
   "level" character varying NOT NULL,
   "message" text NOT NULL,
   "trace" text,
   CONSTRAINT log_pkey PRIMARY KEY (id)
);