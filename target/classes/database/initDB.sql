CREATE TABLE IF NOT EXISTS users
(
    id bigserial, PRIMARY KEY(id),
    name  VARCHAR(200) NOT NULL ,
    email VARCHAR(254) NOT NULL ,
    password VARCHAR(254)  NOT NULL
);

CREATE TABLE IF NOT EXISTS experiences
(
    id bigserial, PRIMARY KEY(id),
    start_location DOUBLE PRECISION NOT NULL,
    mindfulness  DOUBLE PRECISION NOT NULL ,
    attitudes  DOUBLE PRECISION NOT NULL ,
    selfregulation  DOUBLE PRECISION NOT NULL ,
    empathy  DOUBLE PRECISION NOT NULL

);

CREATE TABLE IF NOT EXISTS test_result
(
    id bigserial, PRIMARY KEY(id),
    start_location DOUBLE PRECISION NOT NULL,
    mindfulness  DOUBLE PRECISION NOT NULL ,
    attitudes  DOUBLE PRECISION NOT NULL ,
    selfregulation  DOUBLE PRECISION NOT NULL ,
    empathy  DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS moods
(
    id bigserial, PRIMARY KEY(id),
    user_id bigint REFERENCES users(id),
    date bigint default 0 not null,
    mood int not null,
    emotion int not null,
    text text
);

CREATE TABLE IF NOT EXISTS moods_user
(
    mood_id int not null,
    user_id int not null
);