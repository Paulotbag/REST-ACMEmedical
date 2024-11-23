-- Insert data into SECURITY_ROLE
INSERT INTO security_role (role_id, name) VALUES
(1, 'ADMIN_ROLE'), -- Role for users with admin privileges
(2, 'USER_ROLE');  -- Role for regular users with restricted privileges

-- Insert data into SECURITY_USER (Make sure the physician_ids are valid)
INSERT INTO security_user (user_id, username, password_hash, physician_id) VALUES
(1, 'admin_user', 'hashedadminpassword', 1),  -- Admin user associated with physician ID 1
(2, 'user1', 'hasheduserpassword', 2),       -- Regular user associated with physician ID 2
(3, 'user2', 'hasheduserpassword', 3);       -- Regular user associated with physician ID 3

-- Insert data into USER_HAS_ROLE to associate users with roles
INSERT INTO user_has_role (user_id, role_id) VALUES
(1, 1),  -- Assign 'ADMIN_ROLE' to admin_user
(2, 2),  -- Assign 'USER_ROLE' to user1
(3, 2);  -- Assign 'USER_ROLE' to user2
