package com.likelion.innerjoin.post.model.dto.request;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitingRequestDTO {
    private Long formId;
    private String jobTitle;
}
