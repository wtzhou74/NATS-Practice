package org.cyverse.technicalchallenge.repository;

import org.cyverse.technicalchallenge.model.TimelineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author WentaoZhou
 * @Date 10/17/2020
 *
 */
@Repository
public interface TimelineStatusRepository extends JpaRepository<TimelineStatus, Long> {

}
