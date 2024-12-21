package com.likelion.innerjoin.post.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingTimeSelectionDto {
    private Long applicationId;
    private Long meetingTimeId;
}
