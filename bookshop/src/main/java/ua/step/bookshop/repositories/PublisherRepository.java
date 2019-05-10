package ua.step.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.step.bookshop.models.Publiser;

public interface PublisherRepository extends JpaRepository<Publiser, Short> {

}
