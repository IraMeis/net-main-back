package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.ViewPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewPostCommentRepository extends JpaRepository<ViewPostComment, Long>, QuerydslPredicateExecutor<ViewPostComment> {
}
