package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.enums.AccountStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.AccountTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountRecord extends UpdatableRecordImpl<AccountRecord> implements Record6<Long, String, String, String, Integer, AccountStatusType> {

    private static final long serialVersionUID = -1469008318;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setPassword(String value) {
        set(1, value);
    }

    public String getPassword() {
        return (String) get(1);
    }

    public void setName(String value) {
        set(2, value);
    }

    public String getName() {
        return (String) get(2);
    }

    public void setEmail(String value) {
        set(3, value);
    }

    public String getEmail() {
        return (String) get(3);
    }

    public void setReputation(Integer value) {
        set(4, value);
    }

    public Integer getReputation() {
        return (Integer) get(4);
    }

    public void setAccountStatus(AccountStatusType value) {
        set(5, value);
    }

    public AccountStatusType getAccountStatus() {
        return (AccountStatusType) get(5);
    }

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    public Row6<Long, String, String, String, Integer, AccountStatusType> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, String, String, Integer, AccountStatusType> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return AccountTable.ID;
    }

    @Override
    public Field<String> field2() {
        return AccountTable.PASSWORD;
    }

    @Override
    public Field<String> field3() {
        return AccountTable.NAME;
    }

    @Override
    public Field<String> field4() {
        return AccountTable.EMAIL;
    }

    @Override
    public Field<Integer> field5() {
        return AccountTable.REPUTATION;
    }

    @Override
    public Field<AccountStatusType> field6() {
        return AccountTable.ACCOUNT_STATUS;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getPassword();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String component4() {
        return getEmail();
    }

    @Override
    public Integer component5() {
        return getReputation();
    }

    @Override
    public AccountStatusType component6() {
        return getAccountStatus();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getPassword();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public String value4() {
        return getEmail();
    }

    @Override
    public Integer value5() {
        return getReputation();
    }

    @Override
    public AccountStatusType value6() {
        return getAccountStatus();
    }

    @Override
    public AccountRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public AccountRecord value2(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public AccountRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public AccountRecord value4(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public AccountRecord value5(Integer value) {
        setReputation(value);
        return this;
    }

    @Override
    public AccountRecord value6(AccountStatusType value) {
        setAccountStatus(value);
        return this;
    }

    @Override
    public AccountRecord values(Long value1, String value2, String value3, String value4, Integer value5, AccountStatusType value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    public AccountRecord() {
        super(AccountTable.ACCOUNT_TABLE);
    }

    public AccountRecord(Long id, String password, String name, String email, Integer reputation, AccountStatusType accountStatus) {
        super(AccountTable.ACCOUNT_TABLE);

        set(0, id);
        set(1, password);
        set(2, name);
        set(3, email);
        set(4, reputation);
        set(5, accountStatus);
    }
}
