-- User Data (guest pass is 12345)
INSERT INTO users (id, created, updated, username, role, email, password_hash)
VALUES ('test-guest-exists', now(), now(), 'TEST_GUEST_EXISTS', 'GUEST',
        'test_guest_exists@mail.com', '$2a$10$Kx0m.aLRrdx1gNiqezMBmuDRhnJ4.G8b9IPCPK3whInW5MgTeC51u');

INSERT INTO users (id, created, updated, username, role, email, password_hash)
VALUES ('test-guest-to-be-deleted', now(), now(), 'TEST_GUEST_TO_BE_DELETED', 'GUEST',
        'test_guest_to_be_deleted@mail.com', '$2a$10$Kx0m.aLRrdx1gNiqezMBmuDRhnJ4.G8b9IPCPK3whInW5MgTeC51u');
