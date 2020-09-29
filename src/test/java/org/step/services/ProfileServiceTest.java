package org.step.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.step.configuration.DBConfiguration;
import org.step.entities.Profile;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBConfiguration.class})
@ActiveProfiles("dev")
public class ProfileServiceTest {
    @Autowired
    @Qualifier("profileServiceImpl")
    private CrudService<Profile> profileCrudService;

    private Profile profile;

    @Before
    public void setup() {
        profile = Profile.builder()
                .fullName("Mark Mark")
                .graduation("master's degree")
                .abilities("Java")
                .workExperience("5 years")
                .build();
        profileCrudService.save(profile);
    }

    @After
    public void clean() {
        profileCrudService.deleteAll();
    }

    @Test
    public void saveNewProfile() {
        final Profile newProfile = Profile.builder()
                .build();
        Profile check = profileCrudService.save(newProfile);

        Assert.assertEquals(newProfile.getId(), check.getId());
    }

    @Test
    public void deleteProfile() {
        profileCrudService.delete(profile.getId());
    }

    @Test
    public void findAllProfiles() {
        List<Profile> profiles = profileCrudService.findAll();

        Assert.assertFalse(profiles.isEmpty());
    }
}
