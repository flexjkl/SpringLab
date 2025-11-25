package dev.vorstu.projections;

import dev.vorstu.entity.Role;

public interface UserProjection {
    Long getId();
    Long getStudentId();
    String getPassword();
    String getUsername();
    Role getRole();
    Boolean getEnable();
}
