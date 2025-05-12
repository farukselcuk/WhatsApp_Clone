package tr.com.ind.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.ind.dto.*;
import tr.com.ind.exception.GroupNotFoundException;
import tr.com.ind.exception.UserAlreadyMemberException;
import tr.com.ind.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/createGroup")
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupCreateRequest request) {
        GroupDto groupDto = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupDto);
    }

    @GetMapping()
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<GroupDto> groups = groupService.getAllgroups();
        if (groups.isEmpty()) {
            throw new GroupNotFoundException("Groups not found");
        }
        return ResponseEntity.ok(groups);
    }
    @GetMapping("/{groupid}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupid) {
        return ResponseEntity.ok(groupService.getGroupById(groupid));
    }

    @GetMapping("/userid/{userid}")   //Sadece grubun adını yazdırır.
    public ResponseEntity<List<String>> getGroupByUserId(@PathVariable Long userid) {
        return ResponseEntity.ok(groupService.getGroupByUserId(userid));
    }


    @PutMapping("/{groupid}/updateGroup")
    public ResponseEntity<GroupDto> updateUser(@PathVariable Long groupid, @RequestBody GroupUpdateRequest request) {
        return ResponseEntity.ok(groupService.updateGroup(groupid ,request));
    }

    @PatchMapping("/{groupid}/deactive")
    public ResponseEntity<Void> deactivate(@PathVariable Long groupid) {
        groupService.deactivateGroup(groupid);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{groupid}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long groupid) {
        groupService.activateGroup(groupid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupId}/addMember/{userId}")
    public ResponseEntity<?> addMemberToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        try{
            groupService.addMemberToGroup(groupId, userId);
            return ResponseEntity.ok().build();
        }catch (UserAlreadyMemberException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User is already a member of this group");
        }


    }

}
