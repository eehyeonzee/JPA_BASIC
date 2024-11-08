package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        /*
        * EntityManagerFactory
        * 1) 공장 가동한다고 생각하면 됨 (persistence.xml에 설정된 persistence-unit name값 넣어주면 내용 읽어옴)
        * 2) 단, 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        * */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        /*
        * EntityManager
        * 1) 공장 돌아가야 하니까 공장에 있는 매니저 불러온다고 생각하자
        * 2) 엔티티 매니저는 쓰레드간에 공유x >> 사용 후 버려야 함
        * 3) JPA의 모든 데이터 변경*은 트랜잭션 안에서 실행되어야 함 (단순 조회는 트랜잭션 밖에서도 가능하긴 함)
        * * */
        EntityManager em = emf.createEntityManager();

        // database transaction 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 데이터 생성
            /*Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");*/
            // jpa에 저장
            // em.persist(member);
            // 조회
            /*Member findMember = em.find(Member.class, 1L); // EntityManager는 마치 자바 컬렉션처럼 생각하면 됨 >> 객체를 대신 저장해주는 놈
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());*/
            // 수정
            // findMember.setName("HelloJPA");
            // 삭제
            // em.remove(findMember);
            // jpa 입장에서는 table이 아닌 엔티티 객체를 대상으로 함 (JPQL)
            /*List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5) // 이런 식으로 limit이나 rownum 설정할 수 있음
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            Member member = new Member(3L, "HelloC");
            em.persist(member); // 영속성 컨텍스트에 엔티티가 담기는 시점*/
            /*
            * flush
            * 1) flush 직접 호출
            * 2) 미리 db에 반영되는 게 보고 싶거나 쿼리가 어떻게 날아가는지 보고 싶을 때 확인 가능
            * 3) 1차캐시가 리셋되거나 하지 않음 (전혀 상관없음)
            * 4) 플러시는 오직 영속성 컨텍스 내 쓰기지연SQL저장소에 쌓여있는 쿼리문들이 DB에 저장되는 과정일 뿐임
            * */
            // em.flush();

            // 영속 -> 준영속
            /* Member member = em.find(Member.class, 3L);
            member.setUsername("HelloCCC"); // 1) 변경값을 세팅해도
            // [detach] 특정 엔티티만 준영속 상태로 전환
            em.detach(member); // 2-1) 영속성 상태의 엔티티를 영속성 컨텍스트로부터 분리시킴(detached)
            // [clear] 영속성 컨텍스트를 완전히 초기화
            em.clear(); // 2-2) 1차 캐시 통으로 지움 >> clear 이후에 다시 find로 조회 시, 완전히 새로운 영속성 컨텍스트가 올라가고 select 쿼리가 돎
            Member member2 = em.find(Member.class, 3L);
            // 3) JPA가 더이상 해당 엔티티를 관리하지 않음
            System.out.println("==============================");
            // 4) 결론적으로 find(select) 쿼리만 돌고 set(update) 쿼리는 돌지 않음*/

            // 엔티티 매핑
            Member member1 = new Member();
//            member.setId("ID_A");
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

            System.out.println("=====================");

            em.persist(member1); // 1, 51 (미리 51에서 100까지 확보해야 하므로 call next for member_seq 한번 더 호출 >> 총 2번 호출)
            em.persist(member2); // MEM
            em.persist(member3); // MEM

            System.out.println("member1.getId() = " + member1.getId());
            System.out.println("member2.getId() = " + member2.getId());
            System.out.println("member3.getId() = " + member3.getId());

            System.out.println("=====================");

            // commit
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
