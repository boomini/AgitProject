package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.EventResDto;
import com.ssafy.db.entity.Event;
import com.ssafy.db.entity.QEvent;
import com.ssafy.db.entity.QTeam;
import com.ssafy.db.entity.QUserTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QEvent qEvent = QEvent.event;
    QTeam qTeam = QTeam.team;
    QUserTeam qUserTeam = QUserTeam.userTeam;

    public Optional<List<Event>> findEventsAllByUserID(Long id){
        List<Event> eventList = jpaQueryFactory.select(qEvent)
                .from(qUserTeam)
                .join(qTeam)
                .on(qUserTeam.team.id.eq(qTeam.id))
                .join(qEvent)
                .on(qEvent.team.id.eq(qTeam.id))
                .where(qUserTeam.user.id.eq(id))
                .fetch();

        return Optional.ofNullable(eventList);

    }

    // 특정 user에 관련된 전체 event 리스트  // 중복 제거
    public Optional<List<EventResDto>> findEventResAllByUserID(Long id){
        List<EventResDto> eventResDtoList = new ArrayList<>();
        List<Tuple> result = jpaQueryFactory.select(qEvent.eventTitle, qEvent.eventContent, qEvent.teamName, qEvent.eventDate)
                .distinct()
                .from(qUserTeam)
                .join(qTeam)
                .on(qUserTeam.team.id.eq(qTeam.id))
                .join(qEvent)
                .on(qEvent.team.id.eq(qTeam.id))
                .where(qUserTeam.user.id.eq(id))
                .fetch();
        for (Tuple tuple : result){
            EventResDto eventResDto = new EventResDto();
            eventResDto.setEventTitle(tuple.get(qEvent.eventTitle));
            eventResDto.setEventContent(tuple.get(qEvent.eventContent));
            eventResDto.setTeamName(tuple.get(qEvent.teamName));
            LocalDate eventDate = tuple.get(qEvent.eventDate);
            eventResDto.setEventDate(eventDate);
            LocalDate nowDate = LocalDate.now();
            // 남은 날짜 계산
            Long dDay = nowDate.until(eventDate, ChronoUnit.DAYS);
            eventResDto.setDDay(dDay);

            eventResDtoList.add(eventResDto);
        }
        return Optional.ofNullable(eventResDtoList);
    }

    // 특정 user에 관련된 event중 특정 달에 해당하는 리스트  // 중복 제거
    public Optional<List<EventResDto>> findEventResListByUserIdInMonth(Long id, int month){
        List<EventResDto> eventResDtoList = new ArrayList<>();
        List<Tuple> result = jpaQueryFactory.select(qEvent.eventTitle, qEvent.eventContent, qEvent.teamName, qEvent.eventDate)
                .distinct()
                .from(qUserTeam)
                .join(qTeam)
                .on(qUserTeam.team.id.eq(qTeam.id))
                .join(qEvent)
                .on(qEvent.team.id.eq(qTeam.id))
                .where(qUserTeam.user.id.eq(id))
                .groupBy(qEvent.eventDate)
                .fetch();
        for (Tuple tuple : result){
            LocalDate nowDate = LocalDate.now();
            LocalDate eventDate = tuple.get(qEvent.eventDate);
            // 특정 달에만 해당하는 쿼리만 추가해서 반환
            if (nowDate.getYear() == eventDate.getYear() && eventDate.getMonthValue() == month){
                EventResDto eventResDto = new EventResDto();
                eventResDto.setEventTitle(tuple.get(qEvent.eventTitle));
                eventResDto.setEventContent(tuple.get(qEvent.eventContent));
                eventResDto.setTeamName(tuple.get(qEvent.teamName));
                eventResDto.setEventDate(eventDate);
                // 남은 날짜 계산
                Long dDay = nowDate.until(eventDate, ChronoUnit.DAYS);
                eventResDto.setDDay(dDay);

                eventResDtoList.add(eventResDto);
            }
        }
        return Optional.ofNullable(eventResDtoList);
    }


}
