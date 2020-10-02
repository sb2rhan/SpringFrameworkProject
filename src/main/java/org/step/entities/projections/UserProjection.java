package org.step.entities.projections;

import java.util.List;

public interface UserProjection {

    String getUsername();
    Integer getAge();

    List<MessageProjection> getMessages();

    interface MessageProjection {
        String getMessage();
    }
}
