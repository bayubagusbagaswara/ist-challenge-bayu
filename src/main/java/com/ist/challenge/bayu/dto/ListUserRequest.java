package com.ist.challenge.bayu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListUserRequest {

    private Integer pageNo;

    private Integer pageSize;

    private String sortBy;

    private String sortDir;
}
