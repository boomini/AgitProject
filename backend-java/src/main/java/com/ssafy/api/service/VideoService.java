package com.ssafy.api.service;


import com.ssafy.api.dto.VideoDto;
import com.ssafy.db.entity.Video;

public interface VideoService {
    Video addVideo(VideoDto videoDto, String userId, Long teamId);
}
