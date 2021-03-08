CREATE TABLE IF NOT EXISTS users
(
    id BIGINT AUTO_INCREMENT, PRIMARY KEY(id),
    name  VARCHAR(200) NOT NULL,
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
    id    BIGINT AUTO_INCREMENT,PRIMARY KEY(id),
    name  VARCHAR(100) NOT NULL unique,
    created TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status  VARCHAR(25) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS user_roles ( user_id BIGINT NOT NULL, role_id BIGINT NOT NULL, primary key (user_id, role_id),
                                        FOREIGN KEY (user_id) REFERENCES users (id), FOREIGN KEY (role_id) REFERENCES roles (id) );

