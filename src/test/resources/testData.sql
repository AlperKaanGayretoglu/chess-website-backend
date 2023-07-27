-- User Data (guest pass is 12345)
INSERT INTO users (id, created, updated, name, surname, role, email, password_hash)
VALUES ('test-guest-exists', now(), now(), 'TEST GUEST EXISTS', 'TEST GUEST EXISTS', 'GUEST',
        'test_guest_exists@mail.com', '$2a$10$Kx0m.aLRrdx1gNiqezMBmuDRhnJ4.G8b9IPCPK3whInW5MgTeC51u');

INSERT INTO users (id, created, updated, name, surname, role, email, password_hash)
VALUES ('test-guest-to-be-deleted', now(), now(), 'TEST GUEST TO BE DELETED', 'TEST GUEST TO BE DELETED', 'GUEST',
        'test_guest_to_be_deleted@mail.com', '$2a$10$Kx0m.aLRrdx1gNiqezMBmuDRhnJ4.G8b9IPCPK3whInW5MgTeC51u');
