package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.RoomType;
import htl.steyr.springdesktop.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddRoomTypeController {
    @Autowired
    private RoomTypeRepository roomTypeRepository;


    public void addRoomType(RoomType roomType) {
        roomTypeRepository.save(roomType);
    }






}
