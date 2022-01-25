package com.ssafy.api.service;


import com.ssafy.api.dto.VideoDto;
import com.ssafy.db.entity.Team;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Video;
import com.ssafy.db.repository.TeamRepositorySupport;
import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.db.repository.VideoRepository;
import com.ssafy.db.repository.VideoRepositorySupport;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("videoService")
public class VideoServiceImpl implements VideoService{


    @Autowired
    VideoRepository videoRepository;

    @Autowired
    VideoRepositorySupport videoRepositorySupport;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Override
    public Video addVideo(VideoDto videoDto, String userId, Long teamId) {
        Video video = videoDto.toEntity();

        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);

        video.setUser(user.get());
        video.setTeam(team.get());

        return videoRepository.save(video);
    }
}

