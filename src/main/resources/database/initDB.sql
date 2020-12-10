CREATE TABLE IF NOT EXISTS users
(
    id    BIGSERIAL PRIMARY KEY ,
    name  VARCHAR(200) NOT NULL ,
    email VARCHAR(254) NOT NULL ,
    password VARCHAR(254)  NOT NULL
);

CREATE TABLE IF NOT EXISTS experiences
(
    id    BIGSERIAL PRIMARY KEY ,
    start_location DOUBLE PRECISION NOT NULL,
    mindfulness  DOUBLE PRECISION NOT NULL ,
    attitudes  DOUBLE PRECISION NOT NULL ,
    selfregulation  DOUBLE PRECISION NOT NULL ,
    empathy  DOUBLE PRECISION NOT NULL

);

CREATE TABLE IF NOT EXISTS test_result
(
    id    BIGSERIAL PRIMARY KEY ,
    start_location DOUBLE PRECISION NOT NULL,
    mindfulness  DOUBLE PRECISION NOT NULL ,
    attitudes  DOUBLE PRECISION NOT NULL ,
    selfregulation  DOUBLE PRECISION NOT NULL ,
    empathy  DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS moods
(
    id bigserial,
    id_user bigserial not null,
    date bigint default 0 not null,
    mood int not null,
    emotion int not null,
    text text
);