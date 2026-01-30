package com.homsim.jeld.data;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.usertype.CompositeUserType;
import org.javamoney.moneta.Money;

// For explanation see here: https://docs.hibernate.org/orm/7.2/javadocs/org/hibernate/usertype/CompositeUserType.html

/**
 * Maps (splits) the single javamoney.moneta.Money class to the two types NUMERIC and VARCHAR in the database.
 */
public class MoneyUserType implements CompositeUserType<Money> {
    @Override
    public Object getPropertyValue(Money money, int property) {
        return switch (property) {
            case 0 ->
                    money.getNumber();
            case 1 ->
                    money.getCurrency();
            default ->
                    throw new HibernateException("Illegal property index: " + property);
        };
    }

    @Override
    public Money instantiate(ValueAccess valueAccess) {
        // found the indices through trial-and-error: Looking at the parameter-order in the executed query for findById...
        final String currencyCode = valueAccess.getValue(0, String.class);
        final BigDecimal number = valueAccess.getValue(1, BigDecimal.class);

        if ( number == null || currencyCode == null ) {
            return null;
        }
        return Money.of(number, currencyCode);
    }

    @Override
    public Class<MoneyEmbeddable> embeddable() {
        return MoneyEmbeddable.class;
    }

    @Override
    public Class<Money> returnedClass() {
        return Money.class;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Money deepCopy(Money money) {
        return money; // Money is immutable
    }

    @Override
    public boolean equals(Money x, Money y) {
        if ( x == y ) {
            return true;
        }
        if ( x == null || y == null ) {
            return false;
        }
        return x.equals( y );
    }

    @Override
    public Serializable disassemble(Money money) {
        return money;
    }

    @Override
    public Money assemble(Serializable cached, Object owner) {
        return (Money) cached;
    }

    @Override
    public Money replace(Money original, Money target, Object owner) {
        return original;
    }

    @Override
    public int hashCode(Money money) throws HibernateException {
        return money.hashCode();
    }

    // the embeddable class which acts as a source of metadata
    public static class MoneyEmbeddable {
        private BigDecimal number;
        private String currencyCode;
    }

}
