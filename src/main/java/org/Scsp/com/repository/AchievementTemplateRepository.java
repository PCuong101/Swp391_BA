package org.Scsp.com.repository;

import org.Scsp.com.model.AchievementTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementTemplateRepository  extends JpaRepository<AchievementTemplate,Long> {
}
