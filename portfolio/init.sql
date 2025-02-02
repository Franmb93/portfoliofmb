CREATE
USER franmb93 WITH PASSWORD 'franmb93';
ALTER
USER franmb93 WITH SUPERUSER;
GRANT ALL PRIVILEGES ON DATABASE
portfolio TO franmb93;

-- Crear tabla de usuarios
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de roles
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Crear tabla de relación usuarios-roles (many-to-many)
CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- Insertar roles básicos
INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

-- Insertar usuario admin por defecto (password: adminPass)
INSERT INTO users (username, password)
VALUES ('franmb93', '$2a$10$vI8aWBnW3fID.ZQ4/zo1G.q1lRps.9cGLcZEiGDMVr5yUP1KUOYTa');

-- Asignar roles al usuario admin
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.username = 'franmb93'
  AND r.name IN ('ROLE_USER', 'ROLE_ADMIN');

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_users_username ON users (username);
CREATE INDEX idx_user_roles_user_id ON user_roles (user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles (role_id);

-- Crear trigger para actualizar updated_at
CREATE
OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at
= CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$
language 'plpgsql';

CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE
    ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();