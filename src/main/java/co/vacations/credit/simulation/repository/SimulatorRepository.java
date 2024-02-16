package co.vacations.credit.simulation.repository;

import co.vacations.credit.simulation.entity.SimulatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulatorRepository extends JpaRepository<SimulatorEntity, Long> {
}
