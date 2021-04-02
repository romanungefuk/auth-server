INSERT INTO  roles(name) VALUES
('ROLE_USER'),
('ROLE_ADMIN') ;

-- 12345
INSERT INTO  users VALUES
(1, 'user', 'Default newUser','Default newUser','user@mail.ru',null ,'$2a$04$sweeYfA1IOmERGq0mXFjXeY7ksEK.7h0AHgj0yM84XSOICobdw9nC', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(2, 'pamishenko', 'Pavel','Mischenko','pamishenko@gmail.com',null,'$2a$04$TOWdB3.7NVUhKNPS/LE6ze6OBG.slIGlVTlaa0iQL0ZhfpB2BUm/q', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(3, 'olduser', 'Default oldUser','Default oldUser','olduser@mail.ru',null,'$2a$04$YHvzd5R5Ioxnsbmhp8CjA..lcPcv28fnRbzIFcLafNvQ11RdIwJdO', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(4, 'zlatanik', 'Zlata','Perepelits','zlatanik2008@rambler.ru',null,'$2a$04$kvGYn5.3EAVnXhKtQx4a6.YKUlNZATd6FZaybDYJ7/nPo6WNFSSTC', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(5, 'uizemenkova', 'Julia','Zemenkova','uizemenkova@gmail.com',null,'$2a$04$S4qAaobBtOldutDsde1MDeRGVEXcNmPKaWSBt9QVw.7OJn9Z83hCC', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(6, 'jvorobjeva', 'Julia','Vorobjeva','j.vorobjeva@mail.ru',null,'$2a$04$IvfulGd/fwPOtGCrjlkMWOby1ecmjFSCckScGS5Gee7WcoDV8dZnG', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(7, 'manimusic', 'Mani','Music','manimusic@yandex.ru',null,'$2a$04$A2ISF3jbBccMuHkWaTyiLOYrbHfhUQ0wgLmrXRAsJgC8OD8tYZ8wm', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(8, 'romanungefuk', 'Roman','Ungefuk','roman.ungefuk@gmail.com',null,'$2a$04$yrw4nT/LZzRJeiptGHGuKO4rhhb.LTzakr0MayNLrgW.T55iYEdNq', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE');
(9, 'testuser', 'Test','User','testuser@icloud.com',null,'$2y$12$P5aOczmzIpLg.fkwB2TEvu/gskl1ZhL3FXwsps8fOGmRBhmJx8Pva', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE');

INSERT INTO  user_roles VALUES
(1,1),(2,2),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1), (8,2), (9,1);