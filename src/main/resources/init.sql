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