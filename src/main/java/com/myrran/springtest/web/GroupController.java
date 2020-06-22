package com.myrran.springtest.web;

import com.myrran.springtest.model.Group;
import com.myrran.springtest.model.repo.GroupRepository;
import com.myrran.springtest.model.dtos.GroupDTO;
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

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Autowired public GroupController(GroupRepository groupRepository)
    {   this.groupRepository = groupRepository; }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("/groups")
    Collection<GroupDTO> groups()
    {
        return groupRepository.findAll().stream()
            .map(this::fromGroupToGroupDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/group/{id}")
    ResponseEntity<?> getGroup(@PathVariable Long id)
    {
        Optional<Group> group = groupRepository.findById(id);

        return group
            .map(group1 -> ResponseEntity.ok().body(group1))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/group")
    ResponseEntity<GroupDTO> createGroup(@Validated @RequestBody GroupDTO groupDTO) throws URISyntaxException
    {
        log.info("Request to create group: {}", groupDTO);
        Group result = groupRepository.save(fromGroupDTOtoGroup(groupDTO));

        return ResponseEntity
            .created(new URI("/api/group/" + result.getId()))
            .body(fromGroupToGroupDTO(result));
    }

    @PutMapping("/group")
    ResponseEntity<GroupDTO> updateGroup(@Validated @RequestBody GroupDTO groupDTO)
    {
        log.info("Request to update group: {}", groupDTO);
        Group result = groupRepository.save(fromGroupDTOtoGroup(groupDTO));

        return ResponseEntity
            .ok()
            .body(fromGroupToGroupDTO(result));
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

    private GroupDTO fromGroupToGroupDTO(Group group)
    {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(group, GroupDTO.class);
    }

    private Group fromGroupDTOtoGroup(GroupDTO dto)
    {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Group.class);
    }
}