package com.pedrovaledev.linktree.repository;

import com.pedrovaledev.linktree.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {}
