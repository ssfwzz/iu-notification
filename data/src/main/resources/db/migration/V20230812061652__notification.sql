create table notification
(
    id            bigint auto_increment,
    external_id   varchar(255) not null,
    type          varchar(255) not null,
    payload       json         not null,
    published     tinyint(1) not null,
    created_date  datetime(6) null,
    modified_date datetime(6) null,
    primary key (id),
    index         IDX_NOTIFICATION_EXTERNAL_ID (external_id)
);
