
CREATE TABLE bank_account (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    iban VARCHAR(34) NOT NULL,
    bic VARCHAR(12) NOT NULL,
    role INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE finance_time_series (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bank_account_id BIGINT NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_fts_bank_account FOREIGN KEY (bank_account_id)
        REFERENCES bank_account(id) ON DELETE CASCADE
);

CREATE TABLE finance_entry (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    finance_time_series_id BIGINT NOT NULL,
    date_time TIMESTAMP NOT NULL,
    number NUMERIC(19, 4) NOT NULL,
    currencycode VARCHAR(3) DEFAULT 'EUR',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_fe_time_series FOREIGN KEY (finance_time_series_id)
        REFERENCES finance_time_series(id) ON DELETE CASCADE
);

CREATE TABLE spending (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    finance_entry_id BIGINT NOT NULL,
    number NUMERIC(19, 4) NOT NULL,
    currencycode VARCHAR(3) DEFAULT 'EUR',
    usage VARCHAR(1000),  -- Transaction description/purpose
    counterparty_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_spending_finance_entry FOREIGN KEY (finance_entry_id)
        REFERENCES finance_entry(id) ON DELETE CASCADE,
    CONSTRAINT fk_spending_counterparty FOREIGN KEY (counterparty_id)
        REFERENCES bank_account(id) ON DELETE RESTRICT
);

-- add indexes later
-- maybe also add checks for the format of bank_account.iban and bank_account.bic via regex later