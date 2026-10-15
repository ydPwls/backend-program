package me.yjyang.springdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // fintAll() : select * from member;
    // findById() : select * from member where id = ?
    // daleteAll() : delete from member;
    // deleteById() : delete from member where id = ?
    // save() : insert into member(id, name) values (?, ?), update mamber set ... where id = ?

    // findByName() : selete * from member where name = ?
    // select * from member where email = ?

    public List<Member> findByName(String name);

    // findByEmail() L select * from member where email = ?
    // public Optinal<Member> findByEmail(String email);

    // findByNameAndEmail() : select * from member where name = ? and email = ?
    // public Optional<Member> findByNameAndEmail(String name, String email);
}