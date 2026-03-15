package com.dmslob.roomservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDto {
    private Long id;
    private String name;
    private String roomNumber;
    private String bedInfo;
}
