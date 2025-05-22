package id.milestone.milestone4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import id.milestone.milestone4.model.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{

}
