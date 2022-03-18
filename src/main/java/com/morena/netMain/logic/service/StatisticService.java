package com.morena.netMain.logic.service;

import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.model.builder.PStatisticBuilder;
import com.morena.netMain.logic.model.dao.PStatistic;
import com.morena.netMain.logic.repository.NoteCommentsRepository;
import com.morena.netMain.logic.repository.NotePostsRepository;
import com.morena.netMain.logic.utils.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final SysUsersService sysUsersService;
    private final NoteCommentsRepository noteCommentsRepository;
    private final NotePostsRepository notePostsRepository;

    public PStatistic getUserStatistic(Long userId){

        Optional<SysUsers> user = sysUsersService.getUserById(userId);
        if (user.isEmpty())
            return null;

        Long comments = noteCommentsRepository.countByAuthor(user.get());
        List<Pair> posts = notePostsRepository.customFindAllByCommenter(userId)
                .stream()
                .map(post -> Pair.builder()
                        .value(post.getUniqueId())
                        .label(post.getHeader())
                        .build())
                .collect(Collectors.toList());

        return PStatisticBuilder.toPojo(user.get(),comments,posts);
    }

}
