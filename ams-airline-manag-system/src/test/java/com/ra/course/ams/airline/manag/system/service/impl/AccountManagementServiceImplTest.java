package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.entity.person.AccountStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exception.AccountAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.AccountNotExistException;
import com.ra.course.ams.airline.manag.system.exception.BadCredentialsException;
import com.ra.course.ams.airline.manag.system.exception.UnauthorizedOperationException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.AuthenticationService;
import com.ra.course.ams.airline.manag.system.service.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

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
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();

        Account accountOpened = accountManagementService.createAccount(account);
        assertThat(accountOpened).isEqualToComparingFieldByField(account);
        verify(accountRepository, times(1)).getInstance(any());
        verify(accountRepository, times(1)).addInstance(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue()).isEqualToComparingFieldByField(account);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test()
    public void testCreateAccountThrowExceptionIfAccountExist(){
        when(accountRepository.getInstance(any())).thenReturn(getAccountSample());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        try {
            accountManagementService.createAccount(account);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AccountAlreadyExistException.class);
        }
        verify(accountRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testGetAccountsReturnListOfThreeAccounts(){
        when(accountRepository.getInstances()).thenReturn(getAccountsListSample());
        List<Account> accounts = accountManagementService.getAccounts();

        assertThat(accounts).hasSize(4);

        verify(accountRepository, times(1)).getInstances();
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testDeleteAccountSuccessOperation(){
        when(accountRepository.getInstance(any())).thenReturn(getAccountSample());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        accountManagementService.deleteAccount(account, admin);

        verify(authorizationService, times(1)).checkGrantsForDeleteAccountOperation(eq(account), eq(admin));
        verify(accountRepository, times(1)).getInstance(any());
        verify(accountRepository, times(1)).removeInstance(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue()).isEqualTo(account);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testDeleteAccountThrownExceptionIfAccountNotExist(){
        when(accountRepository.getInstance(any())).thenReturn(null);
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        try {
            accountManagementService.deleteAccount(account, admin);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AccountNotExistException.class);
        }

        verify(authorizationService, times(1)).checkGrantsForDeleteAccountOperation(eq(account), eq(admin));
        verify(accountRepository, times(1)).getInstance(eq(account.getId()));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testDeleteAccountThrownExceptionIfAdminHasNoSuchGrants(){
        doThrow(new UnauthorizedOperationException()).when(authorizationService).checkGrantsForDeleteAccountOperation(any(), any());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        try {
            accountManagementService.deleteAccount(account, admin);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UnauthorizedOperationException.class);
        }

        verify(authorizationService, times(1)).checkGrantsForDeleteAccountOperation(eq(account), eq(admin));
        verifyZeroInteractions(accountRepository);
    }

    @Test
    public void testUpdateAccountStatusSuccessOperation(){
        when(accountRepository.getInstance(any())).thenReturn(getAccountSample());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();

        Account accountUpdated = accountManagementService.updateStatus(account, AccountStatus.BLOCKED, admin);

        assertThat(accountUpdated).isEqualTo(account);
        assertThat(accountUpdated.getAccountStatus()).isEqualTo(AccountStatus.BLOCKED);

        verify(authorizationService, times(1)).checkGrantsForUpdateAccountOperation(eq(account), eq(admin));
        verify(accountRepository, times(1)).getInstance(any());
        verify(accountRepository, times(1)).updateInstance(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue().getAccountStatus()).isEqualTo(AccountStatus.BLOCKED);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testUpdateAccountStatusThrownExceptionIfAccountNotExist(){
        when(accountRepository.getInstance(any())).thenReturn(null);
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        try {
            accountManagementService.updateStatus(account, AccountStatus.BLOCKED, admin);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AccountNotExistException.class);
        }

        verify(authorizationService, times(1)).checkGrantsForUpdateAccountOperation(eq(account), eq(admin));
        verify(accountRepository, times(1)).getInstance(eq(account.getId()));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testUpdateAccountStatusThrownExceptionIfAdminHasNoSuchGrants(){
        doThrow(new UnauthorizedOperationException()).when(authorizationService).checkGrantsForUpdateAccountOperation(any(), any());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        try {
            accountManagementService.updateStatus(account, AccountStatus.BLOCKED, admin);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UnauthorizedOperationException.class);
        }

        verify(authorizationService, times(1)).checkGrantsForUpdateAccountOperation(eq(account), eq(admin));
        verifyZeroInteractions(accountRepository);
    }

    @Test
    public void testBlockAccountSuccessOperation(){
        when(accountRepository.getInstance(any())).thenReturn(getAccountSample());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        accountManagementService.blockAccount(account, admin);

        verify(authorizationService, times(1)).checkGrantsForBlockAccountOperation(eq(account), eq(admin));
        verify(accountRepository, times(1)).getInstance(any());
        verify(accountRepository, times(1)).updateInstance(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue().getAccountStatus()).isEqualTo(AccountStatus.BLOCKED);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testBlockAccountThrownExceptionIfAccountNotExist(){
        when(accountRepository.getInstance(any())).thenReturn(null);
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        try {
            accountManagementService.blockAccount(account, admin);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AccountNotExistException.class);
        }

        verify(authorizationService, times(1)).checkGrantsForBlockAccountOperation(eq(account), eq(admin));
        verify(accountRepository, times(1)).getInstance(eq(account.getId()));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testBlockAccountThrownExceptionIfAdminHasNoSuchGrants(){
        doThrow(new UnauthorizedOperationException()).when(authorizationService).checkGrantsForBlockAccountOperation(any(), any());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        try {
            accountManagementService.blockAccount(account, admin);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UnauthorizedOperationException.class);
        }

        verify(authorizationService, times(1)).checkGrantsForBlockAccountOperation(eq(account), eq(admin));
        verifyZeroInteractions(accountRepository);
    }

    @Test
    public void testResetPasswordByAdminSuccessOperation(){
        when(accountRepository.getInstance(any())).thenReturn(getAccountSample());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();

        accountManagementService.resetPassword(account, "newpswd", admin);

        verify(authorizationService, times(1)).checkGrantsForResetPasswordOperation(eq(account), eq(admin));
        verify(accountRepository, times(1)).getInstance(any());
        verify(accountRepository, times(1)).updateInstance(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue().getPassword()).isEqualTo("newpswd");
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testResetPasswordByAdminThrownExceptionIfAccountNotExist(){
        when(accountRepository.getInstance(any())).thenReturn(null);
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        try {
            accountManagementService.resetPassword(account, "newpswd", admin);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AccountNotExistException.class);
        }

        verify(authorizationService, times(1)).checkGrantsForResetPasswordOperation(eq(account), eq(admin));
        verify(accountRepository, times(1)).getInstance(eq(account.getId()));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testResetPasswordByAdminThrownExceptionIfAdminHasNoSuchGrants(){
        doThrow(new UnauthorizedOperationException()).when(authorizationService).checkGrantsForResetPasswordOperation(any(), any());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();
        Admin admin = getAdminSample();
        try {
            accountManagementService.resetPassword(account, "newpswd", admin);
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UnauthorizedOperationException.class);
        }

        verify(authorizationService, times(1)).checkGrantsForResetPasswordOperation(eq(account), eq(admin));
        verifyZeroInteractions(accountRepository);
    }

    @Test
    public void testResetPasswordSuccessOperation(){
        when(authenticationService.login("1", "qazwsx")).thenReturn(getAccountSample());
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();

        accountManagementService.resetPassword(account, "qazwsx", "newpswd");

        verify(authenticationService, times(1)).login(eq("1"), eq("qazwsx"));
        verify(accountRepository, times(1)).updateInstance(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue().getPassword()).isEqualTo("newpswd");
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testResetPasswordThrownExceptionIfLoginFails(){
        doThrow(new BadCredentialsException()).when(authenticationService).login("1", "qazwsx");
        Account account = new Account.Builder("1", getPersonSample())
                .setPassword("qazwsx").setAccountStatus(AccountStatus.ACTIVE).build();

        try {
            accountManagementService.resetPassword(account, "qazwsx", "newpswd");
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadCredentialsException.class);
        }

        verify(authenticationService, times(1)).login(eq("1"), eq("qazwsx"));
        verifyZeroInteractions(accountRepository);
    }

    private static Admin getAdminSample() {
        return new Admin.Builder().setName("Adminov Andrey").setEmail("admin@example.com").setPhone("77777").build();
    }


    private static Person getPersonSample() {
        return new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
    }

    private static Account getAccountSample() {
        return new Account.Builder("1", getPersonSample()).setPassword("example").setAccountStatus(AccountStatus.ACTIVE).build();
    }

    private static List<Account> getAccountsListSample() {
        Account[] accounts = {
                new Account.Builder("1", getPersonSample()).setPassword("example").setAccountStatus(AccountStatus.ACTIVE).build(),
                new Account.Builder("2", getPersonSample()).setPassword("example2").setAccountStatus(AccountStatus.BLOCKED).build(),
                new Account.Builder("3", getPersonSample()).setPassword("example3").setAccountStatus(AccountStatus.CANCELED).build(),
                new Account.Builder("4", getPersonSample()).setPassword("example4").setAccountStatus(AccountStatus.CLOSED).build()
        };
        return Arrays.asList(accounts);
    }
}
