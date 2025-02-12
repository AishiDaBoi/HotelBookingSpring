package htl.steyr.springdesktop.repository;

import htl.steyr.springdesktop.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    @Override
    List<RoomType> findAll();
}
