--
-- sites   Added By Ishag 
--

create table sites (
    id               bigint primary key,        
    section_id       bigint not null references sections(id),
    office_hours     varchar(255),    
    lecture_hours    varchar(255),
    lecture_room     varchar(255)
);

create table blocks (
    id                      bigint primary key,
    name                    varchar(255) not null,     
    site_id                 bigint not null references sites(id)
 );

create table items (
    id                bigint primary key, 
    name              varchar(255) not null,
    item_type         integer not null,      
    string_content    varchar(255),
    url_content       varchar(255),
    block_id          bigint not null references blocks(id),
    file_content_id   bigint references files(id),
    item_order        integer not null
);
  
alter table items add column tsv tsvector;

create function items_ts_trigger_function() returns trigger as $$
begin
    new.tsv :=
        setweight( to_tsvector('pg_catalog.english', coalesce(new.name,'')), 'A') ||
        setweight( to_tsvector('pg_catalog.english', coalesce(new.string_content,'')), 'D' ) ||
        setweight( to_tsvector('pg_catalog.english', coalesce(new.url_content,'')), 'D' );
    return new;
end
$$ language plpgsql;

create trigger items_ts_trigger
    before insert or update
    on items
    for each row
    execute procedure items_ts_trigger_function();

create index items_ts_index
    on items
    using gin(tsv);

 
create table announcements (
    id              bigint primary key,  
    content         varchar(255) not null,
    site_id         bigint not null references sites(id),
    time_stamp      timestamp not null      
);
