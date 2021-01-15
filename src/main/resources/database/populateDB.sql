INSERT INTO  roles(name) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

-- 12345
INSERT INTO  users VALUES
(1, 'user', 'Default newUser','Default newUser','user@mail.ru','$2a$04$sweeYfA1IOmERGq0mXFjXeY7ksEK.7h0AHgj0yM84XSOICobdw9nC', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(2, 'pamishenko', 'Pavel','Mischenko','pamishenko@gmail.com','$2a$04$TOWdB3.7NVUhKNPS/LE6ze6OBG.slIGlVTlaa0iQL0ZhfpB2BUm/q', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(3, 'olduser', 'Default oldUser','Default oldUser','olduser@mail.ru','$2a$04$YHvzd5R5Ioxnsbmhp8CjA..lcPcv28fnRbzIFcLafNvQ11RdIwJdO', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(4, 'zlatanik', 'Zlata','Perepelits','Zlatanik2008@rambler.ru','$2a$04$kvGYn5.3EAVnXhKtQx4a6.YKUlNZATd6FZaybDYJ7/nPo6WNFSSTC', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(5, 'uizemenkova', 'Julia','Zemenkova','Uizemenkova@gmail.com','$2a$04$S4qAaobBtOldutDsde1MDeRGVEXcNmPKaWSBt9QVw.7OJn9Z83hCC', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(6, 'jvorobjeva', 'Julia','Vorobjeva','J.vorobjeva@mail.ru','$2a$04$IvfulGd/fwPOtGCrjlkMWOby1ecmjFSCckScGS5Gee7WcoDV8dZnG', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(7, 'manimusic', 'Mani','Music','Manimusic@yandex.ru','$2a$04$A2ISF3jbBccMuHkWaTyiLOYrbHfhUQ0wgLmrXRAsJgC8OD8tYZ8wm', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE'),
(8, 'romanungefuk', 'Roman','Ungefuk','roman.ungefuk@gmail.com','$2a$04$yrw4nT/LZzRJeiptGHGuKO4rhhb.LTzakr0MayNLrgW.T55iYEdNq', '2020-12-10 00:38:25', '2020-12-10 00:38:31','ACTIVE');


INSERT INTO  user_roles VALUES
(1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1), (8,2);

INSERT INTO experiences VALUES
(1, 0.0, 0.0, 0.0, 0.0, 0.0),
(2, 0.0, 0.0, 0.0, 0.0, 0.0),
(3, 99.9, 99.9, 45.2, 0.0, 0.0),
(4, 0.0, 0.0, 0.0, 0.0, 0.0),
(5, 0.0, 0.0, 0.0, 0.0, 0.0),
(6, 0.0, 0.0, 0.0, 0.0, 0.0),
(7, 0.0, 0.0, 0.0, 0.0, 0.0);

INSERT INTO test_result VALUES
(1, 0.0, 0.0, 0.0, 0.0, 0.0),
(2, 0.0, 0.0, 0.0, 0.0, 0.0),
(3, 23.9, 9.9, 21.22, 1.09, 0.0),
(4, 0.0, 0.0, 0.0, 0.0, 0.0),
(5, 0.0, 0.0, 0.0, 0.0, 0.0),
(6, 0.0, 0.0, 0.0, 0.0, 0.0),
(7, 0.0, 0.0, 0.0, 0.0, 0.0);

INSERT INTO moods VALUES
(1, 2, 32142134, 21, 34, 'medfg'),
(2, 2, 32142435, 21, 34, 'dfgdfgd dfg dg'),
(3, 2, 32147657, 21, 34, ' gfdhhd hgd rgd rg'),
(4, 2, 32142879, 21, 34, 'd fgdr gdg rdfg drfgd fgd');