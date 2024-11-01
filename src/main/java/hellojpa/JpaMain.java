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
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
            // jpa에 저장
//            em.persist(member);
            // 조회
//            Member findMember = em.find(Member.class, 1L); // EntityManager는 마치 자바 컬렉션처럼 생각하면 됨 >> 객체를 대신 저장해주는 놈
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());
            // 수정
//            findMember.setName("HelloJPA");
            // 삭제
//            em.remove(findMember);
            // jpa 입장에서는 table이 아닌 엔티티 객체를 대상으로 함 (JPQL)
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5) // 이런 식으로 limit이나 rownum 설정 할 수 있음
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

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
