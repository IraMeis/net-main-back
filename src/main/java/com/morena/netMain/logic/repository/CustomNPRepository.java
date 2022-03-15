package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.NotePosts;

import java.time.LocalDate;
import java.util.List;

public interface CustomNPRepository {
    List<NotePosts> findAllByParsedRequest(LocalDate from, LocalDate to,
                                           String label, Boolean inHead, Boolean inContent, Boolean inComment,
                                           List<Long> scopes,
                                           List<Long> commentatorIds);
}
