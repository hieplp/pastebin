create table roots (
    root_id           varchar(255) not null,
    username          varchar(50)  not null,
    password_hash     varchar(255) not null,
    email             varchar(255),
    created_by        varchar(64)  not null,
    created_at        timestamp(6) with time zone not null,
    last_modified_by  varchar(64),
    last_modified_at  timestamp(6) with time zone,
    primary key (root_id),
    constraint uk_roots_username unique (username)
);

-- ponytail: one root; drop this index if multiple roots become a thing
create unique index uk_roots_singleton on roots ((true));
