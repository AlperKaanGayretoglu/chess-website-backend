-- User Data (admin pass is 12345)
INSERT INTO users (id, created, updated, username, role, email, password_hash)
VALUES ('cdf8d686-3bbb-4a64-a8ee-dd24df45e7d9', now(), now(), 'ADMIN', 'ADMIN',
        'admin@mail.com', '$2a$10$Kx0m.aLRrdx1gNiqezMBmuDRhnJ4.G8b9IPCPK3whInW5MgTeC51u');
