package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile){
        try {
            if(userMobile.contains(mobile)){
                throw new Exception("Mobile Number Already Exists");
            }
            else{
                User newUser = new User(name, mobile);
                userMobile.add(mobile);
            }

        }
        catch (Exception e){
            e.getMessage();
        }
        return "SUCCESS";

    }

    public Group createGroup(List<User> users) {
        if(users.size()==2){
            Group grp = new Group(users.get(1).getName(), 2);
            groupUserMap.put(grp,users);
            groupMessageMap.put(grp, new ArrayList<>());
            return grp;
        }
        else{
            customGroupCount++;
            Group grp = new Group("Group"+customGroupCount, users.size());
            groupUserMap.put(grp,users);
            groupMessageMap.put(grp, new ArrayList<>());
            return grp;
        }
    }

    public int createMessage(String content) {
        messageId++;

        Message msg = new Message(messageId,content);
        return msg.getId();

    }

    public int sendMessage(Message message, User sender, Group group) throws  Exception {
        if(!groupUserMap.containsKey(group))
            throw new Exception("Group does not exist");

        if(!groupUserMap.get(group).contains(sender)){
            throw new Exception("You are not a member of the group");
        }

        List<Message> messageList = groupMessageMap.get(group);
        groupMessageMap.put(group,messageList);

        return groupMessageMap.get(group).size();

    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        if(!groupUserMap.containsKey(group))
            throw new Exception("Group does not exist");

        if(adminMap.get(group)!= approver)
            throw new Exception("Approver does not exist");

        if(!groupUserMap.get(group).contains(user))
            throw new Exception("User is not a participant");

        adminMap.replace(group,user);
        return "SUCCESS";
    }

//    public int removeUser(User user) throws Exception{
//
//        for(Group grp : groupUserMap.keySet()){
//            List<User> list = groupUserMap.get(grp);
//            if(list.contains(user)){
//                if()
//            }
//        }
//
//    }
}
