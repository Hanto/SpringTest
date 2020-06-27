package com.myrran.springtest.web;

import com.myrran.springtest.model.demo.Group;
import com.myrran.springtest.model.demo.GroupDAO;
import com.myrran.springtest.model.demo.dtos.GroupDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController @RequestMapping("/api")
class GroupController
{
    private final GroupDAO groupRepo;
    private final ModelMapper modelMapper;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    public @Autowired GroupController(GroupDAO groupRepo, ModelMapper modelMapper)
    {   this.groupRepo = groupRepo; this.modelMapper = modelMapper; }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("/groups")
    public Collection<GroupDTO> groups()
    {
        log.info("Request to submit all groups");

        return groupRepo.findAll().stream()
            .map(this::groupToDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id)
    {
        log.info("Request to submit group with the id: {}", id);
        Optional<Group> group = groupRepo.findById(id);

        return group
            .map(group1 -> ResponseEntity.ok().body(group1))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/group")
    public ResponseEntity<GroupDTO> createGroup(@Validated @RequestBody GroupDTO groupDTO) throws URISyntaxException
    {
        log.info("Request to create group: {}", groupDTO);
        Group result = groupRepo.save(dtoToGroup(groupDTO));

        return ResponseEntity
            .created(new URI("/api/group/" + result.getId()))
            .body(groupToDTO(result));
    }

    @PutMapping("/group")
    public ResponseEntity<GroupDTO> updateGroup(@Validated @RequestBody GroupDTO groupDTO)
    {
        log.info("Request to update group: {}", groupDTO);
        Group result = groupRepo.save(dtoToGroup(groupDTO));

        return ResponseEntity
            .ok()
            .body(groupToDTO(result));
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id)
    {
        log.info("Request to delete group: {}", id);
        groupRepo.deleteById(id);

        return ResponseEntity
            .ok()
            .build();
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private GroupDTO groupToDTO(Group group)
    {   return modelMapper.map(group, GroupDTO.class); }

    private Group dtoToGroup(GroupDTO dto)
    {   return modelMapper.map(dto, Group.class); }
}