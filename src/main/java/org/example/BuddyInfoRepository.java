package org.example;
import org.springframework.data.repository.CrudRepository;


public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {
    Iterable<BuddyInfo> findByNameContainingIgnoreCase(String name);
}
