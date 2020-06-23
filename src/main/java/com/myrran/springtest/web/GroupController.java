package com.myrran.springtest.web;

import com.myrran.springtest.model.Group;
import com.myrran.springtest.model.dtos.GroupDTO;
import com.myrran.springtest.model.repo.GroupRepository;
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
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    public @Autowired GroupController(GroupRepository groupRepository, ModelMapper modelMapper)
    {   this.groupRepository = groupRepository; this.modelMapper = modelMapper; }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("/groups")
    Collection<GroupDTO> groups()
    {
        log.info("Request to submit all groups");

        return groupRepository.findAll().stream()
            .map(this::groupToDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/group/{id}")
    ResponseEntity<?> getGroup(@PathVariable Long id)
    {
        log.info("Request to submit group with the id: {}", id);
        Optional<Group> group = groupRepository.findById(id);

        return group
            .map(group1 -> ResponseEntity.ok().body(group1))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/group")
    ResponseEntity<GroupDTO> createGroup(@Validated @RequestBody GroupDTO groupDTO) throws URISyntaxException
    {
        log.info("Request to create group: {}", groupDTO);
        Group result = groupRepository.save(dtoToGroup(groupDTO));

        return ResponseEntity
            .created(new URI("/api/group/" + result.getId()))
            .body(groupToDTO(result));
    }

    @PutMapping("/group")
    ResponseEntity<GroupDTO> updateGroup(@Validated @RequestBody GroupDTO groupDTO)
    {
        log.info("Request to update group: {}", groupDTO);
        Group result = groupRepository.save(dtoToGroup(groupDTO));

        return ResponseEntity
            .ok()
            .body(groupToDTO(result));
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id)
    {
        log.info("Request to delete group: {}", id);
        groupRepository.deleteById(id);

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