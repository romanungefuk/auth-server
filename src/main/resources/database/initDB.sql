CREATE TABLE IF NOT EXISTS users
(
    id bigserial, PRIMARY KEY(id),
    name  VARCHAR(200) NOT NULL unique,
    first_name  VARCHAR(200) NOT NULL ,
    last_name  VARCHAR(200) NOT NULL ,
    email VARCHAR(254) NOT NULL unique,
    password VARCHAR(254)  NOT NULL,
    created TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status  VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(100) NOT NULL unique,
    created TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status  VARCHAR(25) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS user_roles ( user_id BIGINT NOT NULL, role_id BIGINT NOT NULL, primary key (user_id, role_id),
                                 FOREIGN KEY (user_id) REFERENCES users (id), FOREIGN KEY (role_id) REFERENCES roles (id) );


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

CREATE TABLE IF NOT EXISTS users_paswords_status
(
    user_id int not null,
    status VARCHAR(100) NOT NULL
);