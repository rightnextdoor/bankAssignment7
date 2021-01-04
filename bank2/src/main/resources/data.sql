INSERT INTO bank_user(user_id, username, password, authority, is_active, total_value, closed_accounts)
 VALUES(0, 'admin', 'admin', 'Administrator', TRUE, 0, '')  
 ON DUPLICATE KEY UPDATE authority = 'Administrator';