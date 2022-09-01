drop table if exists "user_roles" cascade;
drop table if exists "users" cascade;
drop table if exists "reimbursement_statuses" cascade;
drop table if exists "reimbursement_types" cascade;
drop table if exists "reimbursements" cascade;



create table "user_roles"(
	"role_id" varchar not null,
	"role" varchar not null,
	primary key ("role_id")
	
);

CREATE TABLE "users" (
  "user_id" varchar not null,
  "username" varchar not null,
  "email" varchar not null,
  "password" varchar not null,
  "given_name" varchar not null,
  "surname" varchar not null,
  "is_active" boolean,
  "role_id" varchar,
  PRIMARY KEY ("user_id"),
  CONSTRAINT "FK_role_id.role_id"
    FOREIGN KEY ("role_id")
      REFERENCES "user_roles"("role_id")
);

create table "reimbursement_statuses"(
	"status_id" varchar not null,
	"status" varchar not null,
	primary key("status_id")
);

create table "reimbursement_types"(
"type_id" varchar not null,
"type" varchar not null,
primary key ("type_id")

);


create table "reimbursements"(
"reimb_id" varchar not null,
"amount" numeric(6,2) not null,
"submitted" timestamp not null,
"resolved" timestamp,
"description" varchar not null,
"receipt" bytea,
"payment_id" varchar,
"author_id" varchar not null,
"resolver_id" varchar,
"status_id" varchar not null,
"type_id" varchar not null,
primary key("reimb_id"),
CONSTRAINT "FK_author_id.author_id"
    FOREIGN KEY ("author_id")
      REFERENCES "user_roles"("role_id"),
CONSTRAINT "FK_resolver_id.resolver_id"
    FOREIGN KEY ("resolver_id")
      REFERENCES "user_roles"("role_id"),
CONSTRAINT "FK_status_id.status_id"
    FOREIGN KEY ("status_id")
      REFERENCES "reimbursement_statuses"("status_id"),
CONSTRAINT "FK_type_id.type_id"
    FOREIGN KEY ("type_id")
      REFERENCES "reimbursement_types"("type_id")
);