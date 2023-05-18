package java.com.uca.entity;

public class FriendShipEntity {
    private int id1;
    private int id2;

    public FriendShipEntity(UserEntity u1, UserEntity u2) {
        setId1(u1.getId());
        setId2(u2.getId());
    }
    public int getId1(){
        return this.id1;
    }

    public int getId2(){
        return this.id2;
    }

    public void setId1(int id){
        this.id1 = id;
    }

    public void setId2(int id){
        this.id2 =  id;
    }


}
