insert into sys_users (login, password, is_user)
values ('system', 'system', false),
       ('admin', 'root', false),
       ('morena', 'root', false),
       ('erichkraft', 'root', true),
       ('noone', 'root', true);

insert into link_users_roles (user_ref, role_ref)
values (1, 1),
       (2, 1),

       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (3, 8),
       (3, 9),
       (3, 10),
       (3, 11),

       (4, 2),
       (4, 4),
       (4, 6),
       (4, 3),
       (4, 7),

       (5, 2),
       (5, 4),
       (5, 6),
       (5, 3),
       (5, 7);

update sys_users set scope_type=1 where login in ('system', 'admin', 'morena');
update sys_users set scope_type=2 where login ='erichkraft';
update sys_users set scope_type=4 where login ='noone';

insert into note_posts (head, content, scope_type, created_timestamp)
values ('java безопасность', 'Безопасность. Язык Java предназначен для создания программ, работающих  в  сети.  По  этой  причине  большое  внимание  при  его создании  было  уделено  безопасности:  в  язык  встроена настраиваемая  система  обеспечения  безопасности  при  выполнении  кода.  Например, эта система предотвращает намеренное переполнение стека выполняемой программы (один из распространенных способов атаки,  используемых  вирусами),  повреждение  участков  памяти,  находящихся за пределами пространства, выделенного процессу, несанкционированное чтение файлов и их модификация.', 4,'2012-08-24 14:00:00 +00:00'),
       ('Впервые зафиксирована мозговая активность человека в момент смерти', 'Ученые из Китая, Эстонии, Канады и США впервые сумели зафиксировать мозговую активность человека в момент смерти. По их словам, мыслительный орган умирающего продолжает работать даже после того, как сердце перестает биться. Это следует из проведенного анализа 15-минутной записи электроэнцефалограммы.Как уточнили специалисты, научный проект показал, что в тот момент ощущения сравнимы со сновидением, медитацией или воспоминаниями.', 3, '2014-08-24 14:30:00 +00:00'),
       ('какая-то политота', 'Роскомнадзор пригрозил Google штрафом за недостоверную рекламу, где сообщается о многочисленных жертвах среди ВС России и мирного населения Украины. За неисполнение компании может грозить до 5 млн рублей штрафа, а материалы дела будут переданы в СК для привлечения к уголовной отвественности.', 1, '2018-08-24 22:00:00 +00:00'),
       ('рубрика угадай автора цитаты', 'Сегодня в завтрашний день не все могут смотреть. Вернее, смотреть могут не только лишь все. Мало кто может это делать', 2, '2019-08-24 23:23:00 +00:00'),
       ('рубрика угадай автора цитаты', 'Принципы, которые были принципиальны, были не принципиальны.', 2, '2022-01-24 11:00:00 +00:00');

insert into note_comments (author_ref, post_ref, content, created_timestamp)
values (3,5,'Моя специальность и жизнь проходили в атмосфере нефти и газа.
На любом языке я умею говорить со всеми, но этим инструментом я стараюсь не пользоваться.
Мы продолжаем делать то, что мы уже много наделали.', '2022-01-24 10:00:00 +00:00'),
       (4,5,'Виктор Степанович :)','2022-01-25 11:00:00 +00:00'),

       (3,4,'ну тут очевидно', '2022-02-21 11:00:00 +00:00'),

       (3,2,'нервная система живет 10-15 минут после остановки сердца', '2022-01-20 08:00:00 +00:00'),
       (4,2,'измерения проводилсь с начала 50х, результат преставлен только сейчас - как-то подозрительно...', '2022-01-31 11:00:00 +00:00'),

       (1,1,'тест от системы', '2022-01-25 14:00:00 +00:00'),
       (5,1,'куку', '2022-02-25 10:00:00 +00:00'),
       (5,1,'джава превыше всего', '2022-03-01 22:00:00 +00:00'),

       (3,3,'тесттест', '2022-01-25 14:30:00 +00:00'),
       (1,3,'система передает привет', '2022-01-31 09:00:00 +00:00');