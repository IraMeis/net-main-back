CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table dict_scopes
(
    unique_id          bigserial
        constraint dict_scopes_pkey
            primary key,
    uuid               uuid                     default uuid_generate_v4()    not null,
    created_timestamp  timestamp with time zone default statement_timestamp() not null,
    modified_timestamp timestamp with time zone default statement_timestamp() not null,
    is_deleted         boolean                  default false,
    name               varchar                                                not null,
    code               bigint                                                 not null
        constraint dict_scopes_code_key
            unique,
    description        varchar
);

comment on table dict_scopes is 'Таблица, содержащая зоны видимости постов';

comment on column dict_scopes.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column dict_scopes.uuid is 'uuid объекта';

comment on column dict_scopes.created_timestamp is 'Дата и время создания записи';

comment on column dict_scopes.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column dict_scopes.is_deleted is 'Признак удалённой записи';

comment on column dict_scopes.name is 'Наименование';

comment on column dict_scopes.code is 'Код записи';

comment on column dict_scopes.description is 'Описание';

create table dict_roles
(
    unique_id              bigserial    constraint dict_roles_pkey primary key,
    uuid                   uuid                     default uuid_generate_v4()    not null,
    created_timestamp      timestamp with time zone default statement_timestamp() not null,
    modified_timestamp     timestamp with time zone default statement_timestamp() not null,
    is_deleted             boolean                  default false,
    name                   varchar                                                not null,
    code                   bigint       constraint dict_roles_code_key unique     not null,
    description            varchar
);

comment on table dict_roles is 'Таблица, содержащая атрибуты объекта - Роли пользователя';

comment on column dict_roles.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column dict_roles.uuid is 'uuid объекта';

comment on column dict_roles.name is 'Наименование';

comment on column dict_roles.code is 'Код записи';

comment on column dict_roles.description is 'Описание';

comment on column dict_roles.created_timestamp is 'Дата и время создания записи';

comment on column dict_roles.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column dict_roles.is_deleted is 'Признак удалённой записи';

create table sys_users
(
    unique_id              bigserial    constraint sys_users_pkey primary key,
    uuid                   uuid                     default uuid_generate_v4()    not null,
    created_timestamp      timestamp with time zone default statement_timestamp() not null,
    modified_timestamp     timestamp with time zone default statement_timestamp() not null,
    is_deleted             boolean                  default false,
    login                  varchar      constraint sys_users_login_key unique     not null,
    password               varchar                                                not null,
    scope_type bigint
        constraint sys_users_scope_type_fkey
            references dict_scopes (code) on update restrict on delete restrict,
    about varchar default '',
    is_user boolean default true not null,
    is_active boolean default false not null

);

comment on table sys_users is 'Таблица, содержащая данные пользователя';

comment on column sys_users.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column sys_users.uuid is 'UUID записи';

comment on column sys_users.login is 'Логин пользователя';

comment on column sys_users.password is 'Пароль пользователя';

comment on column sys_users.created_timestamp is 'Дата и время создания записи';

comment on column sys_users.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column sys_users.is_deleted is 'Признак удалённой записи';

comment on column sys_users.scope_type is 'Область доступных постов';

comment on column sys_users.is_active is 'Признак активности учетной записи';

comment on column sys_users.is_user is 'Признак принадлежности к пользователям';

comment on column sys_users.about is 'Любая информация от пользователя';

create table link_users_roles
(
    unique_id              bigserial    constraint link_users_roles_pkey primary key,
    user_ref    bigint not null constraint link_users_roles_user_ref_fkey
        references sys_users
        on update restrict on delete restrict,
    role_ref    bigint not null constraint link_users_roles_role_ref_fkey
        references dict_roles
        on update restrict on delete restrict
);

comment on table link_users_roles is 'Таблица связи пользователь-роль';

comment on column link_users_roles.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column link_users_roles.user_ref is 'Ссылка на пользователя';

comment on column link_users_roles.role_ref is 'Ссылка на роль';

create table sys_tokens
(
    unique_id          bigserial
        constraint sys_tokens_pkey
            primary key,
    uuid               uuid                     default uuid_generate_v4()    not null,
    created_timestamp  timestamp with time zone default statement_timestamp() not null,
    modified_timestamp timestamp with time zone default statement_timestamp() not null,
    is_deleted         boolean                  default false,
    content            bytea,
    user_ref   bigint not null
        constraint sys_tokens_user_ref_fkey
            references sys_users
            on update restrict on delete restrict
);

comment on table sys_tokens is 'Таблица, содержащая refresh токены';

comment on column sys_tokens.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column sys_tokens.uuid is 'UUID записи';

comment on column sys_tokens.created_timestamp is 'Дата и время создания записи';

comment on column sys_tokens.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column sys_tokens.is_deleted is 'Признак удалённой записи';

comment on column sys_tokens.user_ref is 'Ссылка на пользователя';

comment on column sys_tokens.content is 'Токен';

create table note_posts
(
    unique_id          bigserial
        constraint note_posts_pkey
            primary key,
    uuid               uuid                     default uuid_generate_v4()    not null,
    created_timestamp  timestamp with time zone default statement_timestamp() not null,
    modified_timestamp timestamp with time zone default statement_timestamp() not null,
    is_deleted         boolean                  default false,
    content            varchar                                                not null,
    scope_type         bigint
        constraint note_posts_scope_type_fkey
            references dict_scopes (code)
            on update restrict on delete restrict,
    header varchar not null default ''
);

comment on table note_posts is 'Таблица, содержащая посты';

comment on column note_posts.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column note_posts.uuid is 'UUID записи';

comment on column note_posts.created_timestamp is 'Дата и время создания записи';

comment on column note_posts.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column note_posts.is_deleted is 'Признак удалённой записи';

comment on column note_posts.content is 'Содержимое поста';

comment on column note_posts.scope_type is 'Область видимости поста';

comment on column note_posts.header is 'Заголовок';

create table note_comments
(
    unique_id          bigserial
        constraint note_comments_pkey
            primary key,
    uuid               uuid                     default uuid_generate_v4()    not null,
    created_timestamp  timestamp with time zone default statement_timestamp() not null,
    modified_timestamp timestamp with time zone default statement_timestamp() not null,
    is_deleted         boolean                  default false,
    content            varchar                                                not null,
    author_ref    bigint not null constraint note_comments_author_ref_fkey
        references sys_users
        on update restrict on delete restrict,
    post_ref    bigint not null constraint note_comments_post_ref_fkey
        references note_posts
        on update restrict on delete restrict,
    is_modified         boolean                  default false
);

comment on table note_comments is 'Таблица, содержащая комментарии';

comment on column note_comments.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column note_comments.uuid is 'UUID записи';

comment on column note_comments.created_timestamp is 'Дата и время создания записи';

comment on column note_comments.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column note_comments.is_deleted is 'Признак удалённой записи';

comment on column note_comments.content is 'Содержимое комментария';

comment on column note_comments.author_ref is 'Ссылка на автора комметария';

comment on column note_comments.post_ref is 'Ссылка на пост';

comment on column note_comments.is_modified is 'Признак изменения комментария';

create table link_comment_reply_to
(
    unique_id          bigserial
        constraint link_comment_reply_to_pkey
            primary key,
    comment_ref    bigint not null constraint link_comment_reply_to_comment_ref_fkey
        references note_comments
        on update restrict on delete restrict,
    reply_to_ref    bigint not null constraint link_comment_reply_to_reply_to_ref_fkey
        references sys_users
        on update restrict on delete restrict
);

comment on table link_comment_reply_to is 'Таблица связи комментария и юзеров, которым ответчают в данном коментарии';

comment on column link_comment_reply_to.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column link_comment_reply_to.comment_ref is 'Ссылка на комментарий';

comment on column link_comment_reply_to.reply_to_ref is 'Ссылка на юзера';

create table dict_user_contacts
(
    unique_id          bigserial
        constraint dict_user_contacts_pkey
            primary key,
    uuid               uuid                     default uuid_generate_v4()    not null,
    created_timestamp  timestamp with time zone default statement_timestamp() not null,
    modified_timestamp timestamp with time zone default statement_timestamp() not null,
    is_deleted         boolean                  default false,
    name               varchar                                                not null,
    code               bigint                                                 not null
        constraint dict_user_contacts_code_key
            unique,
    description        varchar
);

comment on table dict_user_contacts is 'Таблица, содержащая атрибуты объекта - Контактные данные пользователя';

comment on column dict_user_contacts.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column dict_user_contacts.uuid is 'uuid объекта';

comment on column dict_user_contacts.created_timestamp is 'Дата и время создания записи';

comment on column dict_user_contacts.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column dict_user_contacts.is_deleted is 'Признак удалённой записи';

comment on column dict_user_contacts.name is 'Наименование';

comment on column dict_user_contacts.code is 'Код записи';

comment on column dict_user_contacts.description is 'Описание';

create table sys_users_contacts
(
    unique_id  bigserial
        constraint sys_users_contacts_pkey
            primary key,
    uuid               uuid                     default uuid_generate_v4()    not null,
    created_timestamp  timestamp with time zone default statement_timestamp() not null,
    modified_timestamp timestamp with time zone default statement_timestamp() not null,
    is_deleted         boolean                  default false,
    user_ref   bigint not null
        constraint sys_users_contacts_user_ref_fkey
            references sys_users
            on update restrict on delete restrict,
    contact_type   bigint not null
        constraint sys_users_contacts_contact_type_fkey
            references dict_user_contacts (code)
            on update restrict on delete restrict,
    content varchar not null
);

comment on table sys_users_contacts is 'Таблица связи пользователь-контакт';

comment on column sys_users_contacts.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column sys_users_contacts.user_ref is 'Ссылка на пользователя';

comment on column sys_users_contacts.contact_type is 'Ссылка на тип контакта';

comment on column sys_users_contacts.is_deleted is 'Признак удалённой записи';

comment on column sys_users_contacts.content is 'Значение контакта';

comment on column sys_users_contacts.uuid is 'UUID записи';

comment on column sys_users_contacts.created_timestamp is 'Дата и время создания записи';

comment on column sys_users_contacts.modified_timestamp is 'Дата и время последнего изменения записи';

create table link_comment_reply_to
(
    unique_id          bigserial
        constraint link_comment_reply_to_pkey
            primary key,
    is_deleted         boolean                  default false,
    comment_ref    bigint not null constraint link_comment_reply_to_comment_ref_fkey
        references note_comments
        on update restrict on delete restrict,
    reply_to_ref    bigint not null constraint link_comment_reply_to_reply_to_ref_fkey
        references sys_users
        on update restrict on delete restrict
);

comment on table link_comment_reply_to is 'Таблица связи комментария и юзеров, которым ответчают в данном коментарии';

comment on column link_comment_reply_to.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column link_comment_reply_to.is_deleted is 'Признак удалённой записи';

comment on column link_comment_reply_to.comment_ref is 'Ссылка на комментарий';

comment on column link_comment_reply_to.reply_to_ref is 'Ссылка на юзера';

create table net_nets
(
    unique_id          bigserial
        constraint net_nets_pkey
            primary key,
    uuid               uuid                     default uuid_generate_v4()    not null,
    created_timestamp  timestamp with time zone default statement_timestamp() not null,
    modified_timestamp timestamp with time zone default statement_timestamp() not null,
    is_deleted         boolean                  default false,
    description        varchar,
    name               varchar   constraint net_nets_name_key unique        not null,
    config             varchar                                                not null,
    weigths_url        varchar
);

comment on table net_nets is 'Таблица, содержащая данные о модели нейронной сети';

comment on column net_nets.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column net_nets.uuid is 'UUID записи';

comment on column net_nets.created_timestamp is 'Дата и время создания записи';

comment on column net_nets.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column net_nets.is_deleted is 'Признак удалённой записи';

comment on column net_nets.description is 'Описание модели';

comment on column net_nets.weigths_url is 'Ссылка на веса';

comment on column net_nets.name is 'Название модели';

comment on column net_nets.config is 'Кофигурация сети';

create table net_runs
(
    unique_id          bigserial
        constraint net_runs_pkey
            primary key,
    uuid               uuid                     default uuid_generate_v4()    not null,
    created_timestamp  timestamp with time zone default statement_timestamp() not null,
    modified_timestamp timestamp with time zone default statement_timestamp() not null,
    is_deleted         boolean                  default false,
    net_ref varchar not null constraint net_runs_net_ref_fkey
        references net_nets (name)
        on update restrict on delete restrict,
    content            varchar                                                not null
);

comment on table net_runs is 'Таблица, содержащая данные о оценке нейронной сети';

comment on column net_runs.unique_id is 'Идентификатор записи. Первичный ключ';

comment on column net_runs.uuid is 'UUID записи';

comment on column net_runs.created_timestamp is 'Дата и время создания записи';

comment on column net_runs.modified_timestamp is 'Дата и время последнего изменения записи';

comment on column net_runs.is_deleted is 'Признак удалённой записи';

comment on column net_runs.content is 'Объект evaluation';

comment on column net_runs.net_ref is 'Ссылка на модель';

create view post_and_comment
            (post_unique_id, post_uuid, post_header, post_content, post_scope_type, post_created_timestamp,
             post_modified_timestamp, post_is_deleted, commenter_unique_id, commenter_login, comment_content,
             comment_unique_id, comment_uuid)
as
SELECT DISTINCT np.unique_id          AS post_unique_id,
                np.uuid               AS post_uuid,
                np.header             AS post_header,
                np.content            AS post_content,
                np.scope_type         AS post_scope_type,
                np.created_timestamp  AS post_created_timestamp,
                np.modified_timestamp AS post_modified_timestamp,
                np.is_deleted         AS post_is_deleted,
                nc.author_ref         AS commenter_unique_id,
                su.login              AS commenter_login,
                nc.content            AS comment_content,
                nc.unique_id          AS comment_unique_id,
                nc.uuid               AS comment_uuid
FROM note_posts np
         LEFT JOIN note_comments nc ON np.unique_id = nc.post_ref
         LEFT JOIN sys_users su ON nc.author_ref = su.unique_id
ORDER BY np.created_timestamp;