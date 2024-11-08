package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
//@TableGenerator(name = "member_seq_generator", table = "my_sequences", pkColumnValue = "member_seq", allocationSize = 1)
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50) // 1부터 시작하고, 1씩 증가시켜라
//@Table(name = "MBR")
public class Member {

    @Id // 직접할당
//    @GeneratedValue(strategy = GenerationType.AUTO) // AUTO: DB 방언에 맞춰 자동 생성
      /*
      * IDENTITY: DB에 값이 들어가야만 ID값을 알 수 있음 (단점)
      * 다시 말해, DB에 insert되기 전까지 id값을 모름
      * jpa 입장에서는 영속성 컨텍스트 내 1차캐시에 엔티티를 저장하려면 pk값이 필요한데 방법이 없음
      * 그래서 이 경우에만 commit 전, em.persist(~)를 호출하는 시점에 쿼리를 날림
      * 그 후 내부적으로 pk값을 가져와서 영속성 컨텍스트의 pk값으로 사용함
      * => sql문장을 모았다가(buffering) 한꺼번에 commit하는 것이 IDENTITY 전략에서는 불가능
      * */
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
      /*
      * SEQUENCE: 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트 (ex. oracle sequence)
      * 시퀀스 오브젝트는 DB에서 시퀀스 값을 애플리케이션으로 가져옴
      * buffering 방식 가능 >> 한번에 모았다가 commit 시점에 insert쿼리 돎
      * */
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "member_seq_generator")
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    /*private Integer age;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL은 사용x >> 별도의 데이터 타입이 추가되면 순서에 의한 문제 발생할 수 있기 때문
    private RoleType roleType;

    // 과거
    @Temporal(TemporalType.TIMESTAMP) // java8 이후로 잘 사용하지 않음
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // 최신
    private LocalDate testLcDt; // date
    private LocalDateTime testLcDtTm; // timestamp

    @Lob // 매핑하는 필드 타입이 문자이면 CLOB, 나머지는 BLOB 매핑
    private String description;

    @Transient // 메모리 상에서만 필드를 사용하고 싶을 때
    private int temp;*/

    public Member() {}

    /*public Member(Long id, String username, Integer age, RoleType roleType, Date createdDate, Date lastModifiedDate, String description) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.roleType = roleType;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.description = description;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }*/
}


