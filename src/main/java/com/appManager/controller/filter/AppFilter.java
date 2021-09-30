package com.appManager.controller.filter;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppFilter {

    private String name;

    private String type;
}
