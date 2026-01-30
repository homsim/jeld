INSERT INTO bank_account (name, iban, bic, role)
VALUES ('John Doe Main Account', 'DE89370400440532013000', 'COBADEFFXXX', 0);

INSERT INTO bank_account (name, iban, bic, role)
VALUES ('Amazon EU', 'DE44500105175407324931', 'INGDDEFFXXX', 1);

INSERT INTO bank_account (name, iban, bic, role)
VALUES ('Local Supermarket', 'FR7630006000011234567890189', 'AGRIFRPPXXX', 1);

INSERT INTO bank_account (name, iban, bic, role)
VALUES ('Electric Company', 'NL91ABNA0417164300', 'ABNANL2AXXX', 1);

INSERT INTO finance_time_series (bank_account_id)
SELECT id FROM bank_account WHERE name = 'John Doe Main Account';

INSERT INTO finance_entry (finance_time_series_id, date_time, number, currencycode)
SELECT fts.id, TIMESTAMP '2025-01-01 09:00:00', 2500.0000, 'EUR'
FROM finance_time_series fts
JOIN bank_account ba ON ba.id = fts.bank_account_id
WHERE ba.name = 'John Doe Main Account';

INSERT INTO finance_entry (finance_time_series_id, date_time, number, currencycode)
SELECT fts.id, TIMESTAMP '2025-01-02 09:00:00', 2350.0000, 'EUR'
FROM finance_time_series fts
JOIN bank_account ba ON ba.id = fts.bank_account_id
WHERE ba.name = 'John Doe Main Account';

INSERT INTO finance_entry (finance_time_series_id, date_time, number, currencycode)
SELECT fts.id, TIMESTAMP '2025-01-03 09:00:00', 2100.0000, 'EUR'
FROM finance_time_series fts
JOIN bank_account ba ON ba.id = fts.bank_account_id
WHERE ba.name = 'John Doe Main Account';

INSERT INTO spending (finance_entry_id, number, currencycode, usage, counterparty_id)
SELECT
    fe.id,
    99.9900,
    'EUR',
    'Electronics purchase',
    ba_cp.id
FROM finance_entry fe
JOIN finance_time_series fts ON fe.finance_time_series_id = fts.id
JOIN bank_account ba_user ON ba_user.id = fts.bank_account_id
JOIN bank_account ba_cp ON ba_cp.name = 'Amazon EU'
WHERE fe.date_time = TIMESTAMP '2025-01-02 09:00:00'
  AND ba_user.name = 'John Doe Main Account';

INSERT INTO spending (finance_entry_id, number, currencycode, usage, counterparty_id)
SELECT
    fe.id,
    50.0100,
    'EUR',
    'Weekly groceries',
    ba_cp.id
FROM finance_entry fe
JOIN finance_time_series fts ON fe.finance_time_series_id = fts.id
JOIN bank_account ba_user ON ba_user.id = fts.bank_account_id
JOIN bank_account ba_cp ON ba_cp.name = 'Local Supermarket'
WHERE fe.date_time = TIMESTAMP '2025-01-02 09:00:00'
  AND ba_user.name = 'John Doe Main Account';

INSERT INTO spending (finance_entry_id, number, currencycode, usage, counterparty_id)
SELECT
    fe.id,
    200.0000,
    'EUR',
    'Monthly electricity bill',
    ba_cp.id
FROM finance_entry fe
JOIN finance_time_series fts ON fe.finance_time_series_id = fts.id
JOIN bank_account ba_user ON ba_user.id = fts.bank_account_id
JOIN bank_account ba_cp ON ba_cp.name = 'Electric Company'
WHERE fe.date_time = TIMESTAMP '2025-01-03 09:00:00'
  AND ba_user.name = 'John Doe Main Account';
