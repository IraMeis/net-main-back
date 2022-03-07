insert into dict_roles (code, name, description)
VALUES (1, 'system', 'Роль с правами на просмотр и  модификацию всех объектов'),
       (2, 'comment_viewer', 'Роль с правами на просмотр комментариев'),
       (3, 'comment_modifier', 'Роль с правами на модификацию комментариев'),
       (4, 'post_viewer', 'Роль с правами на просмотр постов'),
       (5, 'post_modifier', 'Роль с правами на модификацию постов'),
       (6, 'user_data_viewer', 'Роль с правами на просмотр данных пользователя'),
       (7, 'user_data_modifier', 'Роль с правами на модификацию данных пользователя'),
       (8, 'user_data_admin_viewer', 'Роль с правами на просмотр данных любого пользователя'),
       (9, 'user_data_admin_deleter', 'Роль с правами на удаление данных любого пользователя'),
       (10, 'comment_admin_viewer', 'Роль с правами на просмотр всех, у т.ч. удаленных комментариев'),
       (11, 'comment_admin_deleter', 'Роль с правами на удаление любых комментариев');

insert into dict_scopes (code, name)
VALUES (1, 'scope_private'),
       (2, 'scope_trusted'),
       (3, 'scope_viewers'),
       (4, 'scope_public');