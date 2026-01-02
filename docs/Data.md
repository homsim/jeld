# Data models

## Finance time series model

```mermaid
classDiagram
    
    FinanceTimeSeries "1" -- "1" BankAccount
    FinanceTimeSeries "1" -- "*" FinanceEntry
    FinanceEntry "1" -- "*" Spending    
    Spending "*" -- "1" BankAccount
    
    class FinanceTimeSeries{
        +BankAccount bankAccount
        +FinanceEntry[] entries
    }
    class FinanceEntry{
        +LocaleDateTime dateTime
        +Money balance
        +MetaData metaData
        +Spending[] spendings
    }
    class BankAccount{
        +String name
        +Bic bic
        +Iban iban
        +BankAccountRole role
    }
    class Spending{
        +Money amount
        +String usage
        +BankAccount counterparty
    }
```

```mermaid
classDiagram
    class BankAccountRole{
        <<Enumeration>>
        USERACCOUNT
        COUNTERPARTY
    }
```