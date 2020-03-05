package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.AdminAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.AdminNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class AdminManagementServiceImplTest {

        @Mock
        private Repository<Admin, String> adminRepository;

        private AdminManagementServiceImpl adminManagementService;

        @BeforeEach
        public void setup(){
                MockitoAnnotations.initMocks(this);
                adminManagementService = new AdminManagementServiceImpl();
                adminManagementService.setAdminRepository(adminRepository);
        }

        @Test
        public void testThatFindByEmailReturnsAdmin(){
                when(adminRepository.getInstances()).thenReturn(getAdmin());
                Admin admin = adminManagementService.findByEmail("ivanov@example.com");

                assertThat(admin).isNotNull();
                assertThat(admin.getName()).isEqualTo("Ivanov Ivan");
                assertThat(admin.getEmail()).isEqualTo("ivanov@example.com");

                verify(adminRepository, times(1)).getInstances();
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithEmptyArgument(){
                try {
                        adminManagementService.findByEmail("");
                        fail("Expected IllegalArgumentException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
                }
                verifyZeroInteractions(adminRepository);
        }

        @Test
        public void testThatFindByEmailThrowsAdminNotExistExceptionWhenCallingWhenCannotFindPersonWithEmail(){
                when(adminRepository.getInstances()).thenReturn(getAdmin());

                try {
                        adminManagementService.findByEmail("unknown@example.com");
                        fail("Expected AdminNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(AdminNotExistException.class);
                }
                verify(adminRepository, times(1)).getInstances();
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatFindByPhoneReturnsAdmin(){
                Admin adminGiven = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(adminRepository.getInstance(any(String.class))).thenReturn(adminGiven);

                Admin admin = adminManagementService.findByPhoneNumber("11111");
                assertThat(admin).isEqualToComparingFieldByField(adminGiven);
                verify(adminRepository, times(1)).getInstance("11111");
        }

        @Test
        public void testThatFindByPhoneReturnsAdminThrowsAdminNotExistExceptionWhenNoSuchPersonAvalialable(){
                when(adminRepository.getInstance(any(String.class))).thenReturn(null);

                try {
                        adminManagementService.findByPhoneNumber("11111");
                        fail("Expected AdminNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(AdminNotExistException.class);
                }

                verify(adminRepository, times(1)).getInstance(any());
        }

        @Test
        public void testThatFindByPhoneReturnsAdminThrowsIllegalArgumentExceptionWhenCallWithEmptyArg(){
                try {
                        adminManagementService.findByPhoneNumber("");
                        fail("Expected IllegalArgumentException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
                }
                verifyZeroInteractions(adminRepository);
        }

        @Test
        public void testThatAddInstanceReturnsAdmin(){
                when(adminRepository.getInstance(any())).thenReturn(null);

                Admin adminToAdd = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Admin admin = adminManagementService.add(adminToAdd);

                assertThat(admin).isEqualToComparingFieldByField(adminToAdd);
                verify(adminRepository, times(1)).getInstance(any());
                verify(adminRepository, times(1)).addInstance(any());
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatAddInstanceThrowsAdminAlreadyExistExceptionWhenTryToAddExistingAdmin(){
                Admin adminInRepo = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(adminRepository.getInstance(any())).thenReturn(adminInRepo);

                Admin adminToAdd = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                try {
                        adminManagementService.add(adminToAdd);
                        fail("Expected AdminAlreadyExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(AdminAlreadyExistException.class);
                }

                verify(adminRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatAddInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        adminManagementService.add(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(adminRepository);
        }

        @Test
        public void testThatUpdatePhoneNumberWithoutExceptions(){
                Admin adminInRepo = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(adminRepository.getInstance(any())).thenReturn(adminInRepo);

                Admin admin = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Admin updatedAdmin = adminManagementService.updatePhone(admin, "55285");

                assertThat(updatedAdmin).isEqualTo(admin);
                assertThat(updatedAdmin.getPhone()).isEqualTo("55285");

                verify(adminRepository, times(1)).updateInstance(eq(adminInRepo));
                verify(adminRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatUpdatePhoneNumberThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        adminManagementService.updatePhone(null, "55285");
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(adminRepository);
        }

        @Test
        public void testThatUpdatePhoneNumberThrowAdminNotExistExceptionIfNoSuchAdminFind(){
                when(adminRepository.getInstance(any())).thenReturn(null);
                try {
                        Admin admin = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        adminManagementService.updatePhone(admin, "55285");
                        fail("Expected AdminNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(AdminNotExistException.class);
                }
                verify(adminRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatUpdateEmailWithoutExceptions(){
                Admin adminInRepo = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(adminRepository.getInstance(any())).thenReturn(adminInRepo);

                Admin admin = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Admin updatedAdmin = adminManagementService.updateEmail(admin, "ivanov@test.com");

                assertThat(updatedAdmin).isEqualTo(admin);
                assertThat(updatedAdmin.getEmail()).isEqualTo("ivanov@test.com");

                verify(adminRepository, times(1)).updateInstance(eq(adminInRepo));
                verify(adminRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatUpdateEmailThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        adminManagementService.updateEmail(null, "ivanov@test.com");
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(adminRepository);
        }

        @Test
        public void testThatUpdateEmailThrowAdminNotExistExceptionIfNoSuchAdminFind(){
                when(adminRepository.getInstance(any())).thenReturn(null);
                try {
                        Admin admin = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        adminManagementService.updateEmail(admin, "ivanov@test.com");
                        fail("Expected AdminNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(AdminNotExistException.class);
                }
                verify(adminRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatRemoveAdminWithoutExceptions(){
                Admin adminInRepo = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(adminRepository.getInstance(any())).thenReturn(adminInRepo);

                Admin adminToRemove = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                adminManagementService.remove(adminToRemove);

                verify(adminRepository, times(1)).removeInstance(eq(adminInRepo));
                verify(adminRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(adminRepository);
        }

        @Test
        public void testThatRemoveInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        adminManagementService.remove(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(adminRepository);
        }

        @Test
        public void testThatRemoveInstanceThrowAdminNotExistExceptionWhenCallWithNullValueArgument(){
                when(adminRepository.getInstance(any())).thenReturn(null);

                try {
                        Admin admin = new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        adminManagementService.remove(admin);
                        fail("Expected AdminNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(AdminNotExistException.class);
                }
                verify(adminRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(adminRepository);
        }

        private static Collection<Admin> getAdmin() {
                Admin[] admins = {
                        new Admin.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build(),
                        new Admin.Builder().setName("Petrov Petro").setEmail("petrov@example.com").setPhone("22222").build(),
                        new Admin.Builder().setName("Sidorov Sidor").setEmail("sidorov@example.com").setPhone("33333").build(),
                        new Admin.Builder().setName("Egorov Egor").setEmail("egorov@example.com").setPhone("4444").build()
                };
                return Arrays.asList(admins);
        }

        @Test
        public void whenUpdateEmailWithAdminNullThenThrowNullPointerException(){
                Admin admin = null;
                Assertions.assertThrows(NullPointerException.class, () ->
                        adminManagementService.updateEmail(admin, "email"));
        }
}
