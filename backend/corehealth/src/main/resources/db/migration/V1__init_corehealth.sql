create table if not exists tenants (
    id bigserial primary key,
    name varchar(120) not null unique,
    slug varchar(80) not null unique,
    created_at timestamptz not null default now()
);

create table if not exists users (
    id bigserial primary key,
    tenant_id bigint not null references tenants(id),
    name varchar(140) not null,
    email varchar(180) not null,
    password varchar(255) not null,
    role varchar(30) not null,
    active boolean not null default true,
    created_at timestamptz not null default now(),
    constraint uk_users_tenant_email unique (tenant_id, email)
);

create table if not exists plans (
    id bigserial primary key,
    code varchar(120) not null unique,
    name varchar(140) not null,
    monthly_price numeric(10,2) not null,
    max_users integer not null
);

create table if not exists subscriptions (
    id bigserial primary key,
    tenant_id bigint not null references tenants(id),
    plan_id bigint not null references plans(id),
    status varchar(20) not null,
    starts_at date not null,
    ends_at date not null,
    constraint uk_subscription_tenant unique (tenant_id)
);
