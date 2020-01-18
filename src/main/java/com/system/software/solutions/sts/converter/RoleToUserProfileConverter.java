package com.system.software.solutions.sts.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.system.software.solutions.sts.model.UserProfile;
import com.system.software.solutions.sts.service.UserProfileService;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Component
public class RoleToUserProfileConverter implements Converter<Object, UserProfile> {

    static final Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);

    @Autowired
    private UserProfileService userProfileService;

    /**
     * Gets UserProfile by Id
     *
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public UserProfile convert(Object element) {
        UserProfile profile = null;

        if (element instanceof UserProfile) {
            profile = (UserProfile) element;
        } else {
            Integer id = Integer.parseInt((String) element);
            profile = userProfileService.findById(id);
            logger.info("Profile : {}", profile);
        }
        return profile;
    }

}
