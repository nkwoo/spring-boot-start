Spring boot
===

### MariaDB Command (AWS RDS 기준)

```
use boot_start;

show variables like 'c%';

alter database boot_start character set = 'utf8mb4' COLLATE = 'utf8mb4_general_ci';

select @@time_zone, now();

create table test
(
    id bigint auto_increment comment '자동증가값',
    content varchar(255) not null comment '내용',
        primary key (id)
) comment 'test table';

insert into test(content) values ('테스트');

select * from test;
```