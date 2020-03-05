package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.entity.person.AccountStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.AuthenticationService;
import com.ra.course.ams.airline.manag.system.service.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class AccountManagementServiceImplTest {

    AccountManagementServiceImpl accountManagementService;
    @Mock
    private Repository<Account, String> accountRepository;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        accountManagementService = new AccountManagementServiceImpl();
        accountManagementService.setAccountRepository(accountRepository);
        accountManagementService.setAuthenticationService(authenticationService);
        accountManagementService.setAuthorizationService(authorizationService);
    }

    @Test
    public void testCreateAccountSuccess(){
        when(accountRepository.getInstance(any())).thenReturn(null);
        Account account = new Account.Builder("1", getPerson())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();

        Account accountOpened = accountManagementService.createAccount(account);
        assertThat(accountOpened).isEqualToComparingFieldByField(account);
    }

    private static final Person getPerson() {
        return new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
    }
}
