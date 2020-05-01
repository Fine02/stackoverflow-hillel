package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.vo.AccountActionVO;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.AccountActionVOMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@PropertySource("sql-requests.yml")
public class AccountDaoImpl implements AccountDao {
    @Value("${getAccounts}")
    private transient String getAccounts;
    @Value("${insertAccount}")
    private transient String insertAccount;
    @Value("${updateAccount}")
    private transient String updateAccount;
    @Value("${deleteAccount}")
    private transient String deleteAccount;

    private transient final JdbcTemplate jdbcTemplate;
    private transient final AccountActionVOMapper voMapper;
    private transient final KeyHolderFactory keyHolderFactory;

    public AccountDaoImpl(final JdbcTemplate jdbcTemplate, final AccountActionVOMapper accVOMapper, final KeyHolderFactory keyHolderFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.voMapper = accVOMapper;
        this.keyHolderFactory = keyHolderFactory;
    }

    @Override
    public Long save(final Account account, final Long addressId) {
        final KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement ps = con.prepareStatement(insertAccount, new String[]{"id"});
            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getStatus().toString());
            ps.setString(4, account.getName());
            ps.setInt(5, addressId.intValue());
            ps.setString(6, account.getEmail());
            ps.setString(7, account.getPhone());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public boolean update(final Account account) {
        jdbcTemplate.update(updateAccount, account.getUserName(), account.getPassword(), account.getStatus().toString(),
                account.getName(), account.getEmail(), account.getPhone(), account.getId());
        return true;
    }

    @Override
    public boolean remove(final Long id) {
        jdbcTemplate.update(deleteAccount, id);
        return true;
    }

    @Override
    public Account findById(final Long id) {
        final String getAccount = getAccounts + " WHERE acc.id=? ";
        final List<AccountActionVO> accountVOs;
        accountVOs = jdbcTemplate.query(getAccount, new Object[]{id}, voMapper);
        return voMapper.getMappedAccountsFromVO(accountVOs).stream().findAny().orElse(null);
    }

    @Override
    public List<Account> getAll() {
        final List<AccountActionVO> accountVOs;
        accountVOs = jdbcTemplate.query(getAccounts, voMapper);
        return voMapper.getMappedAccountsFromVO(accountVOs);
    }
}