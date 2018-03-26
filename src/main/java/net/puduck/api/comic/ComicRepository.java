package net.puduck.api.comic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicRepository extends JpaRepository<ComicVO, Long> {
}
