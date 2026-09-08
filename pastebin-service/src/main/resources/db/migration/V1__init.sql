-- Initial schema: mirrors the tables Hibernate created with ddl-auto=update so
-- pre-Flyway databases (baselined at V1) and fresh databases (this migration)
-- converge on the same structure.
create table pastes (
    paste_id          varchar(255) not null,
    title             varchar(255) not null,
    alias             varchar(50),
    content           text         not null,
    privacy           varchar(20)  not null,
    syntax            varchar(20)  not null,
    expired_at        timestamp(6) with time zone,
    burn_after_read   boolean      not null,
    status            varchar(20)  not null,
    created_by        varchar(64)  not null,
    created_at        timestamp(6) with time zone not null,
    last_modified_by  varchar(64),
    last_modified_at  timestamp(6) with time zone,
    primary key (paste_id),
    constraint uk_pastes_alias unique (alias)
);

create table paste_files (
    file_id       varchar(255) not null,
    paste_id      varchar(255) not null,
    name          varchar(255) not null,
    content_type  varchar(255) not null,
    size          bigint       not null,
    storage_key   varchar(255) not null,
    primary key (file_id),
    constraint uk_paste_files_storage_key unique (storage_key)
);
