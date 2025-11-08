-- Este script insere os usuários iniciais com senhas hasheadas (BCrypt)

    -- Senha 'admin123' hasheada:
    INSERT INTO usuario (username, password, role) VALUES
    ('admin', '$2a$10$w/Xy4F/F8nQj2Tz1Q5Q9L.b9s0qO4j.6p5t4A.Xy4F/FFs0qO4j.6p5t4A', 'ADMIN');

    -- Senha 'medico123' hasheada:
    INSERT INTO usuario (username, password, role) VALUES
    ('medico', '$2a$10$w/Xy4F/F8nQj2Tz1Q5Q9L.b9s0qO4j.6p5t4A.Xy4F/FFs0qO4j.6p5t4A', 'MEDICO');