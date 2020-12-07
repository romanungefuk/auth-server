CREATE TABLE IF NOT EXISTS users
(
    id    BIGSERIAL PRIMARY KEY,
    username  VARCHAR(200) NOT NULL unique,
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
